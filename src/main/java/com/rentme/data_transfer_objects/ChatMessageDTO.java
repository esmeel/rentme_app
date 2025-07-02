package com.rentme.data_transfer_objects;

import java.time.LocalDateTime;

import com.rentme.model.ChatMessage;

public class ChatMessageDTO {
    private Long senderId;
    private Long chatRoomId;
    private String content;
    private LocalDateTime timestamp;

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ChatMessageDTO(String content, LocalDateTime timestamp, long senderId) {
        this.content = content;
        this.timestamp = timestamp;
        this.senderId = senderId;
        System.out.println(content);
        System.out.println(timestamp);

    }

    public static ChatMessageDTO fromEntity(ChatMessage msg) {
        return new ChatMessageDTO(msg.getContent(), msg.getTimestamp(), msg.getSender().getId());
    }

    @Override
    public String toString() {
        return "{" +
                " senderId='" + getSenderId() + "'" +
                ", chatRoomId='" + getChatRoomId() + "'" +
                ", content='" + getContent() + "'" +
                "}";
    }

    public String toJson() {
        return "{" +
                "\"senderId\":" + getSenderId() + "," +
                "\"chatRoomId\":" + getChatRoomId() + "," +
                "\"content:\"" + getContent() +
                "}";
    }

    // '{"name":"John", "age":30, "car":null}'
}
