package com.example.demo.service;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.ResponseUploadDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ResponseUploadDTO uploadClientsFile(MultipartFile file) {
        log.debug("[uploadClientsFile][BEGIN]");

        ResponseUploadDTO responseDTO = ResponseUploadDTO.builder().build();
        try {
            List<ClientDTO> clients = objectMapper.readValue(file.getInputStream(), new TypeReference<List<ClientDTO>>() {});
            clients.forEach(client -> log.debug("[uploadClientsFile][client: {}]", client));
            responseDTO.setCode(101);
            responseDTO.setMessage("File uploaded successfully: " + file.getOriginalFilename());
        } catch (Exception e) {
            responseDTO.setCode(102);
            responseDTO.setMessage("Failed to upload file: " + e.getMessage());
            log.error("[uploadClientsFile][error: {}]", e.toString());
        }

        log.debug("[uploadClientsFile][END]");
        return responseDTO;
    }

}
