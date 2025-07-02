package com.rentme.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tools")
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name") // تطابق مع اسم العمود في قاعدة البيانات
    private String name;
    private String description;
    private double price;

    @Column(name = "available")
    private boolean available;

    public boolean Available() {
        return this.available;
    }


    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean getAvailable() {
        return this.available;
    }


    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "power_type")
    private String powerType;

    @Column(name = "tool_condition")
    private String toolCondition;

    @Column(name = "accessories")
    private String accessories;

    @Column(name = "accessories_required")
    private boolean accessoriesRequired;

    @Column(name = "risk_level")
    private int riskLevel;

    @Column(name = "rating_avg")
    private double ratingAvg;

    @Column(name = "rating_count")
    private int ratingCount;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @OneToMany(mappedBy = "tool", cascade = CascadeType.ALL)
    private List<Rental> rentals;

    @OneToMany(mappedBy = "tool", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ToolReview> toolReviews;

    // Getters and Setters ...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }


    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", description='" + getDescription() + "'" +
                ", price='" + getPrice() + "'" +
                ", imageUrl='" + getImageUrl() + "'" +
                ", powerType='" + getPowerType() + "'" +
                ", toolCondition='" + getToolCondition() + "'" +
                ", accessories='" + getAccessories() + "'" +
                ", accessoriesRequired='" + isAccessoriesRequired() + "'" +
                ", riskLevel='" + getRiskLevel() + "'" +
                ", ratingAvg='" + getRatingAvg() + "'" +
                ", ratingCount='" + getRatingCount() + "'" +
                ", owner='" + getOwner() + "'" +
                // ", rentals='" + getRentals() + "'" +
                // ", reviews='" + getToolReviews() + "'" +
                "}";
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

    public List<Rental> getRentals() {
        return this.rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public List<ToolReview> getToolReviews() {
        return this.toolReviews;
    }

    public void setToolReviews(List<ToolReview> toolReviews) {
        this.toolReviews = toolReviews;
    }

}
