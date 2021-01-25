package com.pozafly.tripllo.fileUpload.controller;

import com.pozafly.tripllo.common.security.securityUser.SecurityUser;
import com.pozafly.tripllo.fileUpload.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("")
    @ResponseBody
    public String upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        return fileUploadService.upload(multipartFile);
    }

    @PostMapping("image")
    @ResponseBody
    public String imgUpload(
            @RequestParam("data") MultipartFile multipartFile,
            @AuthenticationPrincipal SecurityUser securityUser
    ) throws IOException {
        return fileUploadService.imgUpload(multipartFile, securityUser.getUsername());
    }

}
