package com.rentme.data_transfer_objects;

public class contacUsDTO {
  private String email;
  private String subject;
  private String message;
  private Long userId;
  private String userName;
  /*
   * "email": emailController.text, "subject": subjectController.text, "message":
   * messageController.text, "userId": AppGlobals.currentUser.id, "userName":
   * AppGlobals.currentUser.name,
   */

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSubject() {
    return this.subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Long getUserId() {
    return this.userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Override
  public String toString() {
    return "{" + " email='" + getEmail() + "'" + ", subject='" + getSubject() + "'" + ", message='"
        + getMessage() + "'" + ", userId='" + getUserId() + "'" + ", userName='" + getUserName()
        + "'" + "}";
  }


}
