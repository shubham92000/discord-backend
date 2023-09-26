package com.example.discordBackend.models;

import com.example.discordBackend.utils.ConversationType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DirectConversationDetail extends ConversationDetail {
    private UserDetail userDetail;

    public DirectConversationDetail(String conversationId, ConversationType type, UserDetail userDetail){
        super(conversationId, type);
        this.userDetail = userDetail;
    }
}
