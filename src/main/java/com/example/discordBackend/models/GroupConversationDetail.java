package com.example.discordBackend.models;

import com.example.discordBackend.utils.ConversationType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GroupConversationDetail extends ConversationDetail {
    private GroupDetail groupDetail;
    public GroupConversationDetail(String conversationId, ConversationType type, GroupDetail groupDetail){
        super(conversationId, type);
        this.groupDetail = groupDetail;
    }
}
