package com.example.fitnessenthusiasts.activities.HelperClasses.Models;

public class NotificationModel {
    private String notificationBy;
    private String type;
    private String postID;
    private String postedBy;
    private String notificationID;
    private long notificationAt;
    private boolean checkIfOpen;

    public NotificationModel() {
    }

    public String getNotificationBy() {
        return notificationBy;
    }

    public void setNotificationBy(String notificationBy) {
        this.notificationBy = notificationBy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public long getNotificationAt() {
        return notificationAt;
    }

    public void setNotificationAt(long notificationAt) {
        this.notificationAt = notificationAt;
    }


    public boolean isCheckIfOpen() {
        return checkIfOpen;
    }

    public void setCheckIfOpen(boolean checkIfOpen) {
        this.checkIfOpen = checkIfOpen;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }
}
