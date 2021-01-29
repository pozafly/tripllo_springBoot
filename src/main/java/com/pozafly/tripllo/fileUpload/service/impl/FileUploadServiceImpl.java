package com.pozafly.tripllo.fileUpload.service.impl;

import com.pozafly.tripllo.card.dao.CardDao;
import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.domain.network.ResponseMessage;
import com.pozafly.tripllo.common.domain.network.StatusEnum;
import com.pozafly.tripllo.fileUpload.ImageResize;
import com.pozafly.tripllo.fileUpload.S3Uploader;
import com.pozafly.tripllo.fileUpload.dao.FilesDao;
import com.pozafly.tripllo.fileUpload.model.Files;
import com.pozafly.tripllo.fileUpload.service.FileUploadService;
import com.pozafly.tripllo.user.dao.UserDao;
import com.pozafly.tripllo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final S3Uploader s3Uploader;
    private final UserDao userDao;
    private final FilesDao filesDao;
    private final CardDao cardDao;

    Message message = new Message();
    HttpHeaders headers = new HttpHeaders();

    @Value("${custom.path.file}")
    private String PATH;

    @Override
    public ResponseEntity<Message> readFile(Long cardId) {
        List<Files> file = filesDao.readFile(cardId);
        if(!ObjectUtils.isEmpty(file)) {

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.READ_FILE);
            message.setData(file);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);

        } else {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_FILE);
            message.setData(null);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Message> upload(MultipartFile multipartFile, Long cardId, String userId) throws IOException {
        File convertedFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        // 여기서 실질적으로 file -> s3 등록
        String link = s3Uploader.upload(convertedFile, "static_" + userId);

        String fileName = multipartFile.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        Map<String, Object> fileInfo = new HashMap<>();
        fileInfo.put("userId", userId);
        fileInfo.put("link", link);
        fileInfo.put("extension", extension);
        fileInfo.put("cardId", cardId);
        fileInfo.put("fileName", fileName);

        int result = filesDao.createFile(fileInfo);

        if(filesDao.countFiles(cardId) == 1) {
            Map<String, Object> cardInfo = new HashMap<>();
            cardInfo.put("userId", userId);
            cardInfo.put("cardId", cardId);
            cardInfo.put("isAttachment", "Y");

            cardDao.updateCard(cardInfo);
        }


        if(result == 1) {

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.CREATED_FILE);
            message.setData(fileInfo);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);

        } else {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(message, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

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

    @Override
    public ResponseEntity<Message> deleteFile(Long fileId, String userId) {
        try{
            Files file = filesDao.readFileOne(fileId);

            s3Uploader.deleteFile(file.getFileName(), "static_" + userId);
            filesDao.deleteFile(fileId);

            // 지우고 나서 count가 0 이면 N으로.
            if(filesDao.countFiles(file.getCardId()) == 0) {
                Map<String, Object> cardInfo = new HashMap<>();
                cardInfo.put("userId", userId);
                cardInfo.put("cardId", file.getCardId());
                cardInfo.put("isAttachment", "N");

                cardDao.updateCard(cardInfo);
            }

            Map<String, Long> rtnMap = new HashMap<>();
            rtnMap.put("id", fileId);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.DELETE_FILE);
            message.setData(rtnMap);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_FILE);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    // s3에는 MultipartFile이 전송이 안되므로 File로 전환함.
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(PATH + file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    private Optional<File> convert(MultipartFile file, String newFileName) throws IOException {
        File convertFile = new File(PATH + newFileName);
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
