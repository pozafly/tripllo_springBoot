package com.pozafly.tripllo.fileUpload.service.impl;

import com.pozafly.tripllo.fileUpload.ImageResize;
import com.pozafly.tripllo.fileUpload.S3Uploader;
import com.pozafly.tripllo.fileUpload.service.FileUploadService;
import com.pozafly.tripllo.user.dao.UserDao;
import com.pozafly.tripllo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final S3Uploader s3Uploader;
    private final UserDao userDao;

    @Override
    public String upload(MultipartFile multipartFile) throws IOException {
        File convertedFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
        return s3Uploader.upload(convertedFile, "static");
    }

    @Override
    public String imgUpload(MultipartFile multipartFile, String userId) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newFileName = ImageResize.createFileName(userId, extension);

        // 만약 프로필이 등록되어 있는 경우,
        User user = userDao.readUser(userId);
        if(!StringUtils.isEmpty(user.getPicture())) {
            if(StringUtils.isEmpty(user.getSocial())) {
                String picture = user.getPicture();
                String deleteFileName = picture.substring(picture.indexOf("image") + 6);
                s3Uploader.deleteFile(deleteFileName, "image");
            }
        }


        File uploadImage;
        File convertedFile = convert(multipartFile, newFileName)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        if(!extension.toLowerCase().equals("svg")) {
            uploadImage = ImageResize.resize(convertedFile, userId, extension);
        } else {
            uploadImage = convertedFile;
        }

        String uploadImageUrl = s3Uploader.upload(uploadImage, "image");
        Map<String, Object> map = new HashMap<>();
        map.put("id", userId);
        map.put("picture", uploadImageUrl);

        int reuslt = userDao.updateUser(map);
        if(reuslt == 1) {
            return uploadImageUrl;
        } else {
            return "FAIL";
        }
    }

    // s3에는 MultipartFile이 전송이 안되므로 File로 전환함.
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    private Optional<File> convert(MultipartFile file, String newFileName) throws IOException {
        File convertFile = new File(newFileName);
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
