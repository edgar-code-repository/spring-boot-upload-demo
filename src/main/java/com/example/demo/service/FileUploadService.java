package com.example.demo.service;

import com.example.demo.dto.ResponseUploadDTO;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    ResponseUploadDTO uploadClientsFile(MultipartFile file);

    boolean cleanClients();

}
