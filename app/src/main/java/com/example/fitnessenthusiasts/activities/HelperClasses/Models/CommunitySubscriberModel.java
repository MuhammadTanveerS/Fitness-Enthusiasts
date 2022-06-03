package com.example.fitnessenthusiasts.activities.HelperClasses.Models;

public class CommunitySubscriberModel {
    private String userId, subscriptionStartDate, subscriptionExpireDate, communityKey;

    public CommunitySubscriberModel() {
    }

    public CommunitySubscriberModel(String userId, String subscriptionStartDate, String subscriptionExpireDate) {
        this.userId = userId;
        this.subscriptionStartDate = subscriptionStartDate;
        this.subscriptionExpireDate = subscriptionExpireDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    public void setSubscriptionStartDate(String subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public String getSubscriptionExpireDate() {
        return subscriptionExpireDate;
    }

    public void setSubscriptionExpireDate(String subscriptionExpireDate) {
        this.subscriptionExpireDate = subscriptionExpireDate;
    }

    public String getCommunityKey() {
        return communityKey;
    }

    public void setCommunityKey(String communityKey) {
        this.communityKey = communityKey;
    }
}