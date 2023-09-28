package com.example.discordBackend.controllers;

import com.example.discordBackend.dtos.ApiResponse;
import com.example.discordBackend.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {
    private SubscriptionService subscriptionService;

    private Logger log = LoggerFactory.getLogger(SubscriptionController.class);

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/subscribe-complete")
    public ResponseEntity<ApiResponse> subscribeComplete(Authentication authentication){
        log.info("subscribeComplete req: ");
        ApiResponse response = subscriptionService.subscribeComplete(authentication);
        return ResponseEntity.ok(response);
    }
}
