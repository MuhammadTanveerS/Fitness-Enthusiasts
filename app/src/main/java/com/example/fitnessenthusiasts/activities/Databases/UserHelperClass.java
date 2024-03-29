package com.example.fitnessenthusiasts.activities.Databases;

public class UserHelperClass {

    String fullName;
    String username;
    String email;
    String password;
    String date;
    String gender;
    String profilePhoto;
    String userID;
    String weight;
    String height;
    String token;
    String joinedCommunity;
    Boolean trainer;
    int followersCount;

    public UserHelperClass(){}

    public UserHelperClass(String fullName, String username, String email,  String password, String date, String gender) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.date = date;
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getTrainer() {
        return trainer;
    }

    public void setTrainer(Boolean trainer) {
        this.trainer = trainer;
    }

    public String getJoinedCommunity() {
        return joinedCommunity;
    }

    public void setJoinedCommunity(String joinedCommunity) {
        this.joinedCommunity = joinedCommunity;
    }
}
