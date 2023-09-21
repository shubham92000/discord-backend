package com.example.discordBackend.service;

import com.example.discordBackend.dtos.ApiResponse;
import com.example.discordBackend.dtos.friendInvitation.AcceptReqDto;
import com.example.discordBackend.dtos.friendInvitation.InviteReqDto;
import com.example.discordBackend.dtos.friendInvitation.RejectReqDto;
import org.springframework.security.core.Authentication;

public interface FriendInvitationService {
    ApiResponse invite(InviteReqDto inviteReqDto, Authentication authentication);
    ApiResponse accept(AcceptReqDto acceptReqDto, Authentication authentication);
    ApiResponse reject(RejectReqDto rejectReqDto, Authentication authentication);
}
