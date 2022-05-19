package com.example.fitnessenthusiasts.activities.HelperClasses.Models;

public class LeaderboardModel {
    private String userId;
    private int points;

    public LeaderboardModel() {
    }

    public LeaderboardModel(String userId, int points) {
        this.userId = userId;
        this.points = points;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
