package com.rentme.data_transfer_objects;

import com.rentme.model.Tool;

public class ToolResponseDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private String powerType;
    private String toolCondition;
    private String accessories;
    private boolean accessoriesRequired;
    private int riskLevel;
    private double ratingAvg;
    private int ratingCount;
    private boolean available;

    private Long ownerId;
    private String ownerName;
    private String ownerCity;
    private String ownerCountry;

    public ToolResponseDTO(Tool tool) {
        this.id = tool.getId();
        this.name = tool.getName();
        this.description = tool.getDescription();
        this.price = tool.getPrice();
        this.imageUrl = tool.getImageUrl();
        this.powerType = tool.getPowerType();
        this.toolCondition = tool.getToolCondition();
        this.accessories = tool.getAccessories();
        this.accessoriesRequired = tool.isAccessoriesRequired();
        this.riskLevel = tool.getRiskLevel();
        this.ratingAvg = tool.getRatingAvg();
        this.ratingCount = tool.getRatingCount();
        this.available = tool.getAvailable();
        if (tool.getOwner() != null) {
            this.ownerId = tool.getOwner().getId();
            this.ownerName = tool.getOwner().getName();
            this.ownerCity = tool.getOwner().getCity();
            this.ownerCountry = tool.getOwner().getCountry();
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPowerType() {
        return this.powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public String getToolCondition() {
        return this.toolCondition;
    }

    public void setToolCondition(String toolCondition) {
        this.toolCondition = toolCondition;
    }

    public String getAccessories() {
        return this.accessories;
    }

    public void setAccessories(String accessories) {
        this.accessories = accessories;
    }

    public boolean isAccessoriesRequired() {
        return this.accessoriesRequired;
    }

    public boolean getAccessoriesRequired() {
        return this.accessoriesRequired;
    }

    public void setAccessoriesRequired(boolean accessoriesRequired) {
        this.accessoriesRequired = accessoriesRequired;
    }

    public int getRiskLevel() {
        return this.riskLevel;
    }

    public void setRiskLevel(int riskLevel) {
        this.riskLevel = riskLevel;
    }

    public double getRatingAvg() {
        return this.ratingAvg;
    }

    public void setRatingAvg(double ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public int getRatingCount() {
        return this.ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public boolean getAvailable() {
        return this.available;
    }


    public void setAvailable(boolean isAvailable) {
        this.available = isAvailable;
    }

    public Long getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerCity() {
        return this.ownerCity;
    }

    public void setOwnerCity(String ownerCity) {
        this.ownerCity = ownerCity;
    }

    public String getOwnerCountry() {
        return this.ownerCountry;
    }

    public void setOwnerCountry(String ownerCountry) {
        this.ownerCountry = ownerCountry;
    }

    @Override
    public String toString() {
        return "{" +
                "----------------------------------------" +
                " +> id='" + getId() + "'\n" +
                "+> name='" + getName() + "'\n" + //
                "+> description='" + getDescription() + "'\n" + //
                "+> price='" + getPrice() + "'\n" +
                "+> imageUrl='" + getImageUrl() + "'\n" +
                "+> powerType='" + getPowerType() + "'\n" + //
                "+> toolCondition='" + getToolCondition() + "'\n" + //
                "+> accessories='" + getAccessories() + "'\n" + //
                "+> accessoriesRequired='" + isAccessoriesRequired() + "'\n" + //
                "+> riskLevel='" + getRiskLevel() + "'\n" + //
                "+> ratingAvg='" + getRatingAvg() + "'\n" + //
                "+> ratingCount='" + getRatingCount() + "'\n" + //
                "+> Available='" + getAvailable() + "'\n" + //
                "+> ownerId='" + getOwnerId() + "'\n" + //
                "+> ownerName='" + getOwnerName() + "'\n" + //
                "+>  owner City='" + getOwnerCity() + "'\n" + //
                "+> owner Country='" + getOwnerCountry() + "'\n" +
                "-------------------------------------------" +
                "}";
    }

}
