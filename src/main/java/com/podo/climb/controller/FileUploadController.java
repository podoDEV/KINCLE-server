package com.podo.climb.controller;

import com.podo.climb.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {
    private FileUploadService fileUploadService;

    @Autowired
    FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("image") MultipartFile file) {
        return fileUploadService.restore(file);
    }
}
