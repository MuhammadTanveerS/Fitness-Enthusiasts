package com.example.fitnessenthusiasts.activities.HelperClasses;

public class PostModel {
    int profileImage, postImage;
    String profileName, postStatus, likeCount, commentCount;

    public PostModel(int profileImage, int postImage, String profileName, String postStatus, String likeCount, String commentCount) {
        this.profileImage = profileImage;
        this.postImage = postImage;
        this.profileName = profileName;
        this.postStatus = postStatus;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public int getPostImage() {
        return postImage;
    }

    public void setPostImage(int postImage) {
        this.postImage = postImage;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }
}
