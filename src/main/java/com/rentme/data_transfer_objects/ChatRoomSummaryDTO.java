package com.rentme.data_transfer_objects;

import java.time.LocalDateTime;

public class ChatRoomSummaryDTO {
  private Long chatRoomId;
  private String otherParticipantName;
  private String lastMessage;
  private LocalDateTime lastMessageTime;
  private boolean closed;

  public Long getChatRoomId() {
    return this.chatRoomId;
  }

  public void setChatRoomId(Long chatRoomId) {
    this.chatRoomId = chatRoomId;
  }

  public String getOtherParticipantName() {
    return this.otherParticipantName;
  }

  public void setOtherParticipantName(String otherParticipantName) {
    this.otherParticipantName = otherParticipantName;
  }

  public String getLastMessage() {
    return this.lastMessage;
  }

  public void setLastMessage(String lastMessage) {
    this.lastMessage = lastMessage;
  }

  public LocalDateTime getLastMessageTime() {
    return this.lastMessageTime;
  }

  public void setLastMessageTime(LocalDateTime lastMessageTime) {
    this.lastMessageTime = lastMessageTime;
  }

  public boolean isClosed() {
    return this.closed;
  }

  public boolean getClosed() {
    return this.closed;
  }

  public void setClosed(boolean closed) {
    this.closed = closed;
  }

  @Override
  public String toString() {
    return "{" +
        " chatRoomId='" + getChatRoomId() + "'" +
        ", otherParticipantName='" + getOtherParticipantName() + "'" +
        ", lastMessage='" + getLastMessage() + "'" +
        ", lastMessageTime='" + getLastMessageTime() + "'" +
        ", closed='" + isClosed() + "'" +
        "}";
  }

  public ChatRoomSummaryDTO(long chatRoomId, String name, String lastContent, LocalDateTime lastTime,
      boolean isClosed) {
    this.chatRoomId = chatRoomId;
    otherParticipantName = name;
    lastMessage = lastContent;
    lastMessageTime = lastTime;
    closed = isClosed;

  }

}
