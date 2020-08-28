package com.podo.climb.controller;

import com.podo.climb.model.response.ApiResult;
import com.podo.climb.model.response.FileUploadResponse;
import com.podo.climb.model.response.SuccessfulResult;
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

    //TODO: 성능을 위해 stream 으로 변경
    @PostMapping("/upload")
    public ApiResult<FileUploadResponse> upload(@RequestParam("image") MultipartFile file) {
        return new SuccessfulResult<>(fileUploadService.restore(file));
    }
}
