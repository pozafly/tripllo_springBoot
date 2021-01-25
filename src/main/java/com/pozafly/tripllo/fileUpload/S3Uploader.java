package com.pozafly.tripllo.fileUpload;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

// https://jojoldu.tistory.com/300?category=777282
@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    // @RequiredArgsConstructor 때문에 DI 받음.
    // Configuration 코드 없이 AmazonS3Client를 DI. Spring Boot Cloud AWS를 사용하게 되면 S3관련 Bean을 자동 생성해주기 때문.
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // dirName : S3에 생성된 디렉토리를 나타냄
    public String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadUrl = putS3(uploadFile, fileName);
        removeLocalFile(uploadFile);
        return uploadUrl;
    }

    public void deleteFile(String deleteFileName, String dirName) {
        String pathName = dirName + "/" + deleteFileName;
        final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, pathName);
        amazonS3Client.deleteObject(deleteObjectRequest);
    }

    // 외부에서 정적 파일을 읽을 수 있도록 하기 위함
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // s3에 올리고 난 후 로컬에 생성된 File 삭제.(MultipartFile -> File로 전환되면서 로컬에 파일 생성된 것을 삭제)
    private void removeLocalFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("로컬 파일이 삭제되었습니다.");
        } else {
            log.info("로컬 파일이 삭제되지 못했습니다.");
        }
    }
}
