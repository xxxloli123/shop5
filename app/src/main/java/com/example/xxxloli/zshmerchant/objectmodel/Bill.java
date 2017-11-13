package com.example.xxxloli.zshmerchant.objectmodel;

/**
 * Created by xxxloli on 2017/10/26.
 */

public class Bill {

    /**
     * id : 2c9ad8435d4093f2015d40975b58000a
     * accountId : 402880e65ed0bda0015ed0c876500006
     * accountName : 惠递物流(涪陵)
     * orderId : 2c9ad8435d4093f2015d4095feb60003
     * orderOfCompanyId : f0cad24a5c9f9af4015ca054760c0002
     * userType : ExpressCompany
     * userType_value : 快递公司
     * payType : Cash
     * payType_value : 现金
     * payRecordsId : null
     * createDate : 2017-07-14 18:14:07
     * longtime : 0
     * type : consumption
     * type_value : 消费
     * status : success
     * status_value : 成功
     * incomeSourceId :
     * recordsdeail : 平台于：2017-07-14 18:14:07支出平台快递员接单的现金支付费用15.0元。
     * cost : 15
     * balance : -28.5
     */

    private String id;
    private String accountId;
    private String accountName;
    private String orderId;
    private String orderOfCompanyId;
    private String userType;
    private String userType_value;
    private String payType;
    private String payType_value;
    private Object payRecordsId;
    private String createDate;
    private int longtime;
    private String type;
    private String type_value;
    private String status;
    private String status_value;
    private String incomeSourceId;
    private String recordsdeail;
    private int cost;
    private double balance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderOfCompanyId() {
        return orderOfCompanyId;
    }

    public void setOrderOfCompanyId(String orderOfCompanyId) {
        this.orderOfCompanyId = orderOfCompanyId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType_value() {
        return userType_value;
    }

    public void setUserType_value(String userType_value) {
        this.userType_value = userType_value;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayType_value() {
        return payType_value;
    }

    public void setPayType_value(String payType_value) {
        this.payType_value = payType_value;
    }

    public Object getPayRecordsId() {
        return payRecordsId;
    }

    public void setPayRecordsId(Object payRecordsId) {
        this.payRecordsId = payRecordsId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getLongtime() {
        return longtime;
    }

    public void setLongtime(int longtime) {
        this.longtime = longtime;
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

    public String getIncomeSourceId() {
        return incomeSourceId;
    }

    public void setIncomeSourceId(String incomeSourceId) {
        this.incomeSourceId = incomeSourceId;
    }

    public String getRecordsdeail() {
        return recordsdeail;
    }

    public void setRecordsdeail(String recordsdeail) {
        this.recordsdeail = recordsdeail;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
