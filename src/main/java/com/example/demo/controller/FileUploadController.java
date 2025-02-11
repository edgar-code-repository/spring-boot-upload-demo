package com.example.demo.controller;

import com.example.demo.dto.ResponseUploadDTO;
import com.example.demo.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequestMapping("/upload")
@Slf4j
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping
    public ResponseEntity<ResponseUploadDTO> uploadFile(@RequestParam("file") MultipartFile file) {
        log.debug("[uploadFile][BEGIN]");
        ResponseUploadDTO responseUploadDTO = fileUploadService.uploadClientsFile(file);
        log.debug("[uploadFile][END]");
        return ResponseEntity.ok(responseUploadDTO);
    }

}
