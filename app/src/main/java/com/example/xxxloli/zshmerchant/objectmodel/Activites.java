package com.example.xxxloli.zshmerchant.objectmodel;

import java.io.Serializable;

/**
 * Created by xxxloli on 2017/10/19.
 */

public class Activites implements Serializable {
    /**
     * id : 402880e75f33c755015f33d155230001
     * priority : 88
     * shopId : 402880e75f000ab6015f0043a1fc0004
     * shopName : asdhfkjhasd
     * activityName : dsafsf
     * createId : 402880e75f000ab6015f0043a1210002
     * createName : 梁非凡
     * createPhone : 17300210252
     * createDate : 2017-10-19 16:47:58
     * status : Wait_audit
     * status_value : 未发布
     * limitCost : 56341
     * cost : 456
     * couponName : dasff
     * startTime : 2017-11-16 00:00:00
     * endTime : 2017-11-16 00:00:00
     * type : Collection
     * type_value : 成为会员送优惠券
     * fullFee : 0
     * cutFee : 0
     * followNumber : 45
     * giveProductName : null
     * activityRemarks : dsafadsfdga
     */

    private String id;
    private int priority;
    private String shopId;
    private String shopName;
    private String activityName;
    private String createId;
    private String createName;
    private String createPhone;
    private String createDate;
    private String status;
    private String status_value;
    private int limitCost;
    private int cost;
    private String couponName;
    private String startTime;
    private String endTime;
    private String type;
    private String type_value;
    private int fullFee;
    private int cutFee;
    private int followNumber;
    private Object giveProductName;
    private String activityRemarks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreatePhone() {
        return createPhone;
    }

    public void setCreatePhone(String createPhone) {
        this.createPhone = createPhone;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_value() {
        return status_value;
    }

    public void setStatus_value(String status_value) {
        this.status_value = status_value;
    }

    public int getLimitCost() {
        return limitCost;
    }

    public void setLimitCost(int limitCost) {
        this.limitCost = limitCost;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_value() {
        return type_value;
    }

    public void setType_value(String type_value) {
        this.type_value = type_value;
    }

    public int getFullFee() {
        return fullFee;
    }

    public void setFullFee(int fullFee) {
        this.fullFee = fullFee;
    }

    public int getCutFee() {
        return cutFee;
    }

    public void setCutFee(int cutFee) {
        this.cutFee = cutFee;
    }

    public int getFollowNumber() {
        return followNumber;
    }

    public void setFollowNumber(int followNumber) {
        this.followNumber = followNumber;
    }

    public Object getGiveProductName() {
        return giveProductName;
    }

    public void setGiveProductName(Object giveProductName) {
        this.giveProductName = giveProductName;
    }

    public String getActivityRemarks() {
        return activityRemarks;
    }

    public void setActivityRemarks(String activityRemarks) {
        this.activityRemarks = activityRemarks;
    }
}
