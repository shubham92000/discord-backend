package com.example.discordBackend.controllers;

import com.example.discordBackend.dtos.ApiResponse;
import com.example.discordBackend.dtos.auth.LoginReqDto;
import com.example.discordBackend.dtos.friendInvitation.AcceptReqDto;
import com.example.discordBackend.dtos.friendInvitation.InviteReqDto;
import com.example.discordBackend.dtos.friendInvitation.RejectReqDto;
import com.example.discordBackend.service.FriendInvitationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/friend-invitation")
public class FriendInvitationController {
    private Logger log = LoggerFactory.getLogger(FriendInvitationController.class);

    private FriendInvitationService friendInvitationService;

    public FriendInvitationController(FriendInvitationService friendInvitationService) {
        this.friendInvitationService = friendInvitationService;
    }

    @PostMapping("/invite")
    public ResponseEntity<ApiResponse> invite(@Valid @RequestBody InviteReqDto inviteReqDto, Authentication authentication){
        ApiResponse response = friendInvitationService.invite(inviteReqDto, authentication);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/accept")
    public ResponseEntity<ApiResponse> accept(@Valid @RequestBody AcceptReqDto acceptReqDto, Authentication authentication){
        ApiResponse response = friendInvitationService.accept(acceptReqDto, authentication);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reject")
    public ResponseEntity<ApiResponse> reject(@Valid @RequestBody RejectReqDto rejectReqDto, Authentication authentication){
        ApiResponse response = friendInvitationService.reject(rejectReqDto, authentication);
        return ResponseEntity.ok(response);
    }
}
