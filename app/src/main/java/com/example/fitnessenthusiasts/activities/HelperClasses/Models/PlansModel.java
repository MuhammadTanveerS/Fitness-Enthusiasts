package com.example.fitnessenthusiasts.activities.HelperClasses.Models;

public class PlansModel {
    private String planName,planDetails,planImage,planType;
    private int planDays;

    public PlansModel() {
    }

    public PlansModel(String planName, String planDetails, String planImage, String planType, int planDays) {
        this.planName = planName;
        this.planDetails = planDetails;
        this.planImage = planImage;
        this.planType = planType;
        this.planDays = planDays;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanDetails() {
        return planDetails;
    }

    public void setPlanDetails(String planDetails) {
        this.planDetails = planDetails;
    }

    public String getPlanImage() {
        return planImage;
    }

    public void setPlanImage(String planImage) {
        this.planImage = planImage;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public int getPlanDays() {
        return planDays;
    }

    public void setPlanDays(int planDays) {
        this.planDays = planDays;
    }
}
