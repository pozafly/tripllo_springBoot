package com.pozafly.tripllo.fileUpload.service;

import com.pozafly.tripllo.common.domain.network.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    public ResponseEntity<Message> readFile(Long cardId);
    public ResponseEntity<Message> upload(MultipartFile multipartFile, Long cardId, String userId) throws IOException;
    public String imgUpload(MultipartFile multipartFile, String userId) throws IOException;
    public ResponseEntity<Message> deleteFile(Long fileId, String userId);

}
