package com.rentme.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ChatMessage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JsonIgnore
  private ChatRoom chatRoom;
  private String content;
  @ManyToOne
  private User sender;
  private String message;
  private LocalDateTime timestamp;

  public Long getId() {
    return this.id;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ChatRoom getChatRoom() {
    return this.chatRoom;
  }

  public void setChatRoom(ChatRoom chatRoom) {
    this.chatRoom = chatRoom;
  }

  public User getSender() {
    return this.sender;
  }

  public void setSender(User sender) {
    this.sender = sender;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public LocalDateTime getTimestamp() {
    return this.timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

}
