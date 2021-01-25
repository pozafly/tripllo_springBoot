package com.pozafly.tripllo.fileUpload.controller;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.security.securityUser.SecurityUser;
import com.pozafly.tripllo.fileUpload.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @GetMapping("{cardId}")
    @ResponseBody
    public ResponseEntity<Message> readFile(
            @PathVariable Long cardId
    ) {
        return fileUploadService.readFile(cardId);
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<Message> upload(
            @RequestParam("data") MultipartFile multipartFile,
            @RequestParam("cardId") Long cardId,
            @AuthenticationPrincipal SecurityUser securityUser
    ) throws IOException {

        return fileUploadService.upload(multipartFile, cardId, securityUser.getUsername());
    }

    @PostMapping("image")
    @ResponseBody
    public String imgUpload(
            @RequestParam("data") MultipartFile multipartFile,
            @AuthenticationPrincipal SecurityUser securityUser
    ) throws IOException {
        return fileUploadService.imgUpload(multipartFile, securityUser.getUsername());
    }

    @DeleteMapping("{fileId}")
    public ResponseEntity<Message> deleteFile(
            @PathVariable Long fileId,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        return fileUploadService.deleteFile(fileId, securityUser.getUsername());
    }

}
