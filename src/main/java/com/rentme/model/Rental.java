package com.rentme.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private Long ownerId;
    private Long renterId;
    @Enumerated(EnumType.STRING)
    private RentalStatus status;
    @Column(name = "requested_return_by_renter")
    private Boolean requestedReturnByRenter;

    public Boolean isRequestedReturnByRenter() {
        return this.requestedReturnByRenter;
    }

    public Boolean getRequestedReturnByRenter() {
        return this.requestedReturnByRenter;
    }

    public void setRequestedReturnByRenter(Boolean requestedReturnByRenter) {
        this.requestedReturnByRenter = requestedReturnByRenter;
    }

    private LocalDateTime activatedAt;

    @ManyToOne
    @JoinColumn(name = "tool_id")
    private Tool tool;

    private String toolPic;

    private String toolName;

    private String ownerName;


    private String renterName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String macAddress;
    private boolean accepted;

    public String getToolName() {
        return this.toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getRenterName() {
        return this.renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }


    private LocalDateTime createdAt = LocalDateTime.now();

    public LocalDateTime getActivatedAt() {
        return this.activatedAt;
    }

    public void setActivatedAt(LocalDateTime activatedAt) {
        this.activatedAt = activatedAt;
    }

    @Column(name = "requesting_return")
    private boolean requestingReturn = false;

    public boolean isRequestingReturn() {
        return this.requestingReturn;
    }

    public boolean getRequestingReturn() {
        return this.requestingReturn;
    }

    public void setRequestingReturn(boolean requestingReturn) {
        this.requestingReturn = requestingReturn;
    }


    public Long getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(Long receiverId) {
        this.ownerId = receiverId;
    }

    public Long getRenterId() {
        return this.renterId;
    }

    public void setRenterId(Long senderId) {
        this.renterId = senderId;
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
        return "{" + " id='" + getId() + "'" + ", ownerId='" + getOwnerId() + "'" + ", renterId='"
                + getRenterId() + "'" + ", status='" + getStatus() + "'"
                + ", requestedReturnByRenter='" + isRequestedReturnByRenter() + "'"
                + ", activatedAt='" + getActivatedAt() + "'" + ", tool='" + getTool() + "'"
                + ", toolPic='" + getToolPic() + "'" + ", toolName='" + getToolName() + "'"
                + ", ownerName='" + getOwnerName() + "'" + ", renterName='" + getRenterName() + "'"
                + ", startDate='" + getStartDate() + "'" + ", endDate='" + getEndDate() + "'"
                + ", macAddress='" + getMacAddress() + "'" + ", accepted='" + isAccepted() + "'"
                + ", createdAt='" + getCreatedAt() + "'" + ", requestingReturn='"
                + isRequestingReturn() + "'" + "}";
    }


}
