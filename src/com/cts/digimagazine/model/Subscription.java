package com.cts.digimagazine.model;

import java.util.Date;

public class Subscription {
    private int subscriptionId;
    private int userId;
    private int magazineId;
    private Date subscriptionDate;
    private Date expiryDate;
    private String status;

    public Subscription(int subscriptionId, int userId, int magazineId, Date subscriptionDate, Date expiryDate, String status) {
        this.subscriptionId = subscriptionId;
        this.userId = userId;
        this.magazineId = magazineId;
        this.subscriptionDate = subscriptionDate;
        this.expiryDate = expiryDate;
        this.status = status;
    }

    // Getters and Setters
    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMagazineId() {
        return magazineId;
    }

    public void setMagazineId(int magazineId) {
        this.magazineId = magazineId;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Subscription [ID=" + subscriptionId + ", User ID=" + userId + ", Magazine ID=" + magazineId +
               ", Subscription Date=" + subscriptionDate + ", Expiry Date=" + expiryDate + ", Status=" + status + "]";
    }
}
