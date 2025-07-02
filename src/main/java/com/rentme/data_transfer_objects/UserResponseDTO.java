package com.rentme.data_transfer_objects;

import java.util.Set;

import com.rentme.model.User;

public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String city;
    private String country;
    private String profilePicUrl;
    private boolean verified;
    private Set<Long> savedToolsIds;
    private double ratingAvg;
    private int stars;
    private int ratingCount;


    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.city = user.getCity();
        this.country = user.getCountry();
        this.profilePicUrl = user.getProfilePicUrl();
        this.verified = user.isVerified();
        this.savedToolsIds = user.getSavedToolsIds();
        this.stars = user.getStars();
        this.ratingAvg = user.getRatingAvg();
        this.ratingCount = user.getRatingCount();
    }

    public boolean getVerified() {
        return this.verified;
    }


    public double getRatingAvg() {
        return this.ratingAvg;
    }

    public void setRatingAvg(int ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public int getStars() {
        return this.stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getRatingCount() {
        return this.ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Set<Long> getSavedToolsIds() {
        return savedToolsIds;
    }

    public void setSavedToolsIds(Set<Long> savedToolsIds) {
        this.savedToolsIds = savedToolsIds;
    }

    // Builder-style methods (اختياري)
    public UserResponseDTO id(Long id) {
        setId(id);
        return this;
    }

    public UserResponseDTO name(String name) {
        setName(name);
        return this;
    }

    public UserResponseDTO email(String email) {
        setEmail(email);
        return this;
    }

    public UserResponseDTO phone(String phone) {
        setPhone(phone);
        return this;
    }

    public UserResponseDTO city(String city) {
        setCity(city);
        return this;
    }

    public UserResponseDTO country(String country) {
        setCountry(country);
        return this;
    }

    public UserResponseDTO profilePicUrl(String profilePicUrl) {
        setProfilePicUrl(profilePicUrl);
        return this;
    }

    public UserResponseDTO verified(boolean verified) {
        setVerified(verified);
        return this;
    }



    @Override
    public String toString() {
        return "Content:\n{" +
                "id='" + id + "'\n" +
                "name='" + name + "'\n" +
                "email='" + email + "'\n" +
                "phone='" + phone + "'\n" +
                "city='" + city + "'\n" +
                "country='" + country + "'\n" +
                "profilePicUrl='" + getProfilePicUrl() + "'\n" +
                "verified=" + verified + "\n" +
                "savedToolsIds=" + savedToolsIds +
                "}";
    }
}
