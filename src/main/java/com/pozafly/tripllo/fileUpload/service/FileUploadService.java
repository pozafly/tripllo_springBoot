package com.pozafly.tripllo.fileUpload.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

    public String upload(MultipartFile multipartFile) throws IOException;
    public String imgUpload(MultipartFile multipartFile, String userId) throws IOException;

}
