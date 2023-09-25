package com.example.discordBackend.service.impl;

import com.example.discordBackend.dtos.ApiResponse;
import com.example.discordBackend.dtos.friendInvitation.*;
import com.example.discordBackend.exception.DiscordException;
import com.example.discordBackend.models.Conversation;
import com.example.discordBackend.models.FriendInvitation;
import com.example.discordBackend.models.User;
import com.example.discordBackend.repos.ConversationRepo;
import com.example.discordBackend.repos.FriendInvitationRepo;
import com.example.discordBackend.repos.UserRepo;
import com.example.discordBackend.service.ChatSocketService;
import com.example.discordBackend.service.FriendInvitationService;
import com.example.discordBackend.service.FriendSocketService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FriendInvitationServiceImpl implements FriendInvitationService {
    private UserRepo userRepo;
    private FriendInvitationRepo friendInvitationRepo;
    private FriendSocketService friendSocketService;
    private ConversationRepo conversationRepo;
    private ChatSocketService chatSocketService;

    public FriendInvitationServiceImpl(UserRepo userRepo, FriendInvitationRepo friendInvitationRepo, FriendSocketService friendSocketService, ConversationRepo conversationRepo, ChatSocketService chatSocketService) {
        this.userRepo = userRepo;
        this.friendInvitationRepo = friendInvitationRepo;
        this.friendSocketService = friendSocketService;
        this.conversationRepo = conversationRepo;
        this.chatSocketService = chatSocketService;
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

        friendSocketService.updateFriendsPendingInvitations(targetUser.getEmail());

        return new ApiResponse(true, new InviteResDto("Invitation has been sent"), null);
    }

    @Override
    public ApiResponse accept(AcceptReqDto acceptReqDto, Authentication authentication) {
        FriendInvitation invitation = friendInvitationRepo.findById(acceptReqDto.getId())
                .orElseThrow(() -> new DiscordException(HttpStatus.NOT_FOUND, "Error occured. Please try again"));

        User sender = invitation.getSender();
        User receiver = invitation.getReceiver();

        sender.getFriends().add(receiver);
        receiver.getFriends().add(sender);

        // create conversation for this sender and receiver
        var newConversation = new Conversation();
        newConversation.setParticipants(List.of(sender, receiver));
        newConversation = conversationRepo.save(newConversation);

        sender.getConversations().add(newConversation);
        receiver.getConversations().add(newConversation);

        sender = userRepo.save(sender);
        receiver = userRepo.save(receiver);

        friendInvitationRepo.delete(invitation);

        // update list of pending invitations for receiver
        friendSocketService.updateFriendsPendingInvitations(receiver.getEmail());

        // update list of friends for both sender and receiver
        friendSocketService.updateFriends(receiver.getEmail());
        friendSocketService.updateFriends(sender.getEmail());

        // send the total list of conversation_ids to both sender and user
        chatSocketService.sendConversations(sender.getEmail());
        chatSocketService.sendConversations(receiver.getEmail());

        return new ApiResponse(true, new AcceptResDto("Invitation successfully accepted"), null);
    }

    @Override
    public ApiResponse reject(RejectReqDto rejectReqDto, Authentication authentication) {
        var invitationExists = friendInvitationRepo.existsById(rejectReqDto.getId());
        if(invitationExists){
            friendInvitationRepo.deleteById(rejectReqDto.getId());
        }

        // update the list of pending invitations for this user
        friendSocketService.updateFriendsPendingInvitations(authentication.getName());

        return new ApiResponse(true, new RejectResDto("Invitation successfully rejected"), null);
    }
}
