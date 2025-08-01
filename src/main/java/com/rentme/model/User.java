package com.rentme.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    private Double defaultLatitude;
    private Double defaultLongitude;
    private String defaultAddress;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role = "USER"; // القيمة الافتراضية

    private String loginProvider;

    private String name;

    private String email;

    private String phone;

    private String city;



    private String country;

    private String profilePicUrl;

    private boolean verified;

    @Column(nullable = true)
    private String password;

    private double ratingAvg;

    private int stars;

    private int ratingCount;
    private LocalDateTime idVerifyTime;



    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Tool> tools;
    // ✅ الأدوات المحفوظة كـ IDs فقط
    @ElementCollection
    @CollectionTable(name = "user_saved_tool_ids", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "tool_id")
    private Set<Long> savedToolsIds = new HashSet<>();
    @Column(name = "fcm_token")
    private String fcmToken;
    @ManyToMany
    @JoinTable(name = "user_saved_tools", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tool_id"))
    private Set<Tool> savedTools = new HashSet<>();

    public String getDefaultAddress() {
        return this.defaultAddress;
    }

    public void setDefaultAddress(String defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public Double getDefaultLatitude() {
        return this.defaultLatitude;
    }

    public void setDefaultLatitude(double defaultLatitude) {
        this.defaultLatitude = defaultLatitude;
    }

    public Double getDefaultLongitude() {
        return this.defaultLongitude;
    }

    public void setDefaultLongitude(double defaultLongitude) {
        this.defaultLongitude = defaultLongitude;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLoginProvider() {
        return this.loginProvider;
    }

    public void setLoginProvider(String loginProvider) {
        this.loginProvider = loginProvider;
    }

    // Getters & Setters

    public LocalDateTime getIdVerifyTime() {
        return this.idVerifyTime;
    }

    public void setIdVerifyTime(LocalDateTime idVerifyTime) {
        this.idVerifyTime = idVerifyTime;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }

    // UserDetails overrides

    public Set<Long> getSavedToolsIds() {
        return savedToolsIds;
    }

    public void setSavedToolsIds(Set<Long> savedToolsIds) {
        this.savedToolsIds = savedToolsIds;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public boolean getVerified() {
        return this.verified;
    }


    public double getRatingAvg() {
        return this.ratingAvg;
    }

    public void setRatingAvg(double ratingAvg) {
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

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public Set<Tool> getSavedTools() {
        return savedTools;
    }

    public void setSavedTools(Set<Tool> savedTools) {
        this.savedTools = savedTools;
    }



}
