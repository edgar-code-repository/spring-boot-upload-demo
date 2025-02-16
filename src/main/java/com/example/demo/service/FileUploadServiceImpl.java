package com.example.demo.service;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.ResponseUploadDTO;
import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ResponseUploadDTO uploadClientsFile(MultipartFile file) {
        log.debug("[uploadClientsFile][BEGIN]");

        ResponseUploadDTO responseDTO = ResponseUploadDTO.builder().build();
        if (!cleanClients()) {
            responseDTO.setCode(101);
            responseDTO.setMessage("Error when cleaning data in Azure SQL table.");
            return responseDTO;
        }

        try {
            List<ClientDTO> clients = objectMapper.readValue(
                    file.getInputStream(), new TypeReference<List<ClientDTO>>() {});
            clients.forEach(client -> {
                    log.debug("[uploadClientsFile][client: {}]", client);
                    Client clientModel = modelMapper.map(client, Client.class);
                    clientRepository.save(clientModel);
                }
            );
            responseDTO.setCode(102);
            responseDTO.setMessage("File uploaded successfully: " + file.getOriginalFilename());
        } catch (Exception e) {
            responseDTO.setCode(103);
            responseDTO.setMessage("Failed to upload file: " + e.getMessage());
            log.error("[uploadClientsFile][error: {}]", e.toString());
        }

        log.debug("[uploadClientsFile][END]");
        return responseDTO;
    }

    @Override
    public boolean cleanClients() {
        log.debug("[cleanClients][BEGIN]");
        boolean responseFlag = false;
        try {
            clientRepository.deleteAll();
            responseFlag = true;
        } catch (Exception e) {
            log.error("[cleanClients][error: {}]", e.toString());
        }
        log.debug("[cleanClients][END]");
        return responseFlag;
    }

}
