package com.rentme.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long receiverId;
    private Long senderId;

    @ManyToOne
    @JoinColumn(name = "tool_id")
    private Tool tool;

    private String toolPic;

    @ManyToOne
    private User renter;

    @ManyToOne
    private User owner;

    private LocalDate startDate;
    private LocalDate endDate;

    private RentalStatus status;

    private String macAddress;

    private boolean accepted;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getReceiverId() {
        return this.receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getSenderId() {
        return this.senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getToolPic() {
        return this.toolPic;
    }

    public void setToolPic(String toolPic) {
        this.toolPic = toolPic;
    }

    public boolean isAccepted() {
        return this.accepted;
    }

    public boolean getAccepted() {
        return this.accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @ManyToOne

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tool getTool() {
        return this.tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public User getRenter() {
        return this.renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }

    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public RentalStatus getStatus() {
        return this.status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    public String getMacAddress() {
        return this.macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", receiverId='" + getReceiverId() + "'" +
                ", senderId='" + getSenderId() + "'" +
                ", tool='" + getTool() + "'" +
                ", toolPic='" + getToolPic() + "'" +
                ", renter='" + getRenter() + "'" +
                ", owner='" + getOwner() + "'" +
                ", startDate='" + getStartDate() + "'" +
                ", endDate='" + getEndDate() + "'" +
                ", status='" + getStatus() + "'" +
                ", macAddress='" + getMacAddress() + "'" +
                ", accepted='" + isAccepted() + "'" +
                ", createdAt='" + getCreatedAt() + "'" +
                "}";
    }
}
