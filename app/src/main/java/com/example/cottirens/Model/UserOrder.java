package com.example.cottirens.Model;

public class UserOrder {
    private  String remainingTime,cloth,orderId,userId;

    public UserOrder(String remainingTime, String cloth, String orderId,String userId) {
        this.remainingTime = remainingTime;
        this.cloth = cloth;
        this.orderId = orderId;
        this.userId=userId;
    }
    public UserOrder() {

    }

    public void setCloth(String cloth) {
        this.cloth = cloth;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getCloth() {
        return cloth;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getRemainingTime() {
        return remainingTime;
    }
}
