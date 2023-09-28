package com.example.discordBackend.controllers;

import com.example.discordBackend.dtos.ApiResponse;
import com.example.discordBackend.dtos.auth.RegisterReqDto;
import com.example.discordBackend.dtos.socketStore.GenerateSocketIdReqDto;
import com.example.discordBackend.service.SocketStore;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socketstore")
public class SocketStoreController {
    private Logger log = LoggerFactory.getLogger(SocketStoreController.class);

    private SocketStore socketStore;

    public SocketStoreController(SocketStore socketStore) {
        this.socketStore = socketStore;
    }

    @PostMapping("/generatesocketid")
    public ResponseEntity<ApiResponse> generateSocketId(@Valid @RequestBody GenerateSocketIdReqDto generateSocketIdReqDto){
        log.info("generatesocketid req: "+generateSocketIdReqDto);
        ApiResponse response = socketStore.generateSocketId(generateSocketIdReqDto);
        log.info("generatesocketid res: "+response);
        return ResponseEntity.ok(response);
    }
}
