package com.example.discordBackend.service.impl;

import com.example.discordBackend.dtos.ApiResponse;
import com.example.discordBackend.dtos.friendInvitation.*;
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

        boolean invitationAlreadyReceived = friendInvitationRepo.findBySenderAndReceiver(user, targetUser).isPresent();
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
        FriendInvitation invitation = friendInvitationRepo.findById(acceptReqDto.getId())
                .orElseThrow(() -> new DiscordException(HttpStatus.UNAUTHORIZED, "Error occured. Please try again"));

        User sender = invitation.getSender();
        User receiver = invitation.getReceiver();

        sender.getFriends().add(receiver);
        receiver.getFriends().add(sender);

        sender = userRepo.save(sender);
        receiver = userRepo.save(receiver);

        friendInvitationRepo.delete(invitation);

        // todo ->

        return new ApiResponse(new AcceptResDto("Invitation successfully accepted"), null);
    }

    @Override
    public ApiResponse reject(RejectReqDto rejectReqDto, Authentication authentication) {
        var invitationExists = friendInvitationRepo.existsById(rejectReqDto.getId());
        if(invitationExists){
            friendInvitationRepo.deleteById(rejectReqDto.getId());
        }

        // todo ->

        return new ApiResponse(new RejectResDto("Invitation successfully rejected"), null);
    }
}
