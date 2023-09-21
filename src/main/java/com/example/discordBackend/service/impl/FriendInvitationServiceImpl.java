package com.example.discordBackend.service.impl;

import com.example.discordBackend.dtos.ApiResponse;
import com.example.discordBackend.dtos.friendInvitation.InviteResDto;
import com.example.discordBackend.dtos.friendInvitation.AcceptReqDto;
import com.example.discordBackend.dtos.friendInvitation.InviteReqDto;
import com.example.discordBackend.dtos.friendInvitation.RejectReqDto;
import com.example.discordBackend.exception.DiscordException;
import com.example.discordBackend.models.FriendInvitation;
import com.example.discordBackend.models.User;
import com.example.discordBackend.repos.FriendInvitationRepo;
import com.example.discordBackend.repos.UserRepo;
import com.example.discordBackend.service.FriendInvitationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FriendInvitationServiceImpl implements FriendInvitationService {
    private UserRepo userRepo;
    private FriendInvitationRepo friendInvitationRepo;

    public FriendInvitationServiceImpl(UserRepo userRepo, FriendInvitationRepo friendInvitationRepo) {
        this.userRepo = userRepo;
        this.friendInvitationRepo = friendInvitationRepo;
    }

    @Override
    public ApiResponse invite(InviteReqDto inviteReqDto, Authentication authentication) {
        String email = authentication.getName();
        String targetEmail = inviteReqDto.getTargetMailAddress();

        if(Objects.equals(email, targetEmail)){
            throw new DiscordException(HttpStatus.CONFLICT, "Sorry, you cannot become friend with yourself");
        }

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new DiscordException(HttpStatus.NOT_FOUND, String.format("User with email %s not found. please check mail address", email)));

        User targetUser = userRepo.findByEmail(targetEmail)
                .orElseThrow(() -> new DiscordException(HttpStatus.NOT_FOUND, String.format("User with email %s not found. please check mail address", targetEmail)));

        boolean invitationAlreadyReceived = friendInvitationRepo.findBySenderIdAndReceiverId(user.getId(), targetUser.getId()).isPresent();
        if(invitationAlreadyReceived){
            throw new DiscordException(HttpStatus.CONFLICT, "Invitation has already been sent");
        }

        var userAlreadyFriends = targetUser.getFriends().contains(user.getId());
        if(userAlreadyFriends){
            throw new DiscordException(HttpStatus.CONFLICT, "Friend already added. Please check friend list");
        }

        FriendInvitation newInvitation = new FriendInvitation(user, targetUser);
        newInvitation = friendInvitationRepo.save(newInvitation);

        // todo -> send invitations via web socket

        return new ApiResponse(new InviteResDto("Invitation has been sent"), null);
    }

    @Override
    public ApiResponse accept(AcceptReqDto acceptReqDto, Authentication authentication) {
        return null;
    }

    @Override
    public ApiResponse reject(RejectReqDto rejectReqDto, Authentication authentication) {
        return null;
    }
}
