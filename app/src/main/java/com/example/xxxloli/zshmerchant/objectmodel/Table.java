package com.example.xxxloli.zshmerchant.objectmodel;

/**
 * Created by Administrator on 2017/9/15.
 */

public class Table {

    /**
     * id : 402880ea5eef8452015eefa7275c0001
     * shopName : 金刀客
     * shopId : 402880e65ed0bda0015ed0c876e00007
     * shopkeeperId : 402880e65ed0bda0015ed0c876500005
     * shopkeeperName : 荷叶
     * tableNumber : 4
     * tableCode : 1507259263124.jpg
     * status : Unused
     * status_value : 空闲
     * joinTable : no
     */

    private String id;
    private String shopName;
    private String shopId;
    private String shopkeeperId;
    private String shopkeeperName;
    private int tableNumber;
    private String tableCode;
    private String status;
    private String status_value;
    private String joinTable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopkeeperId() {
        return shopkeeperId;
    }

    public void setShopkeeperId(String shopkeeperId) {
        this.shopkeeperId = shopkeeperId;
    }

    public String getShopkeeperName() {
        return shopkeeperName;
    }

    public void setShopkeeperName(String shopkeeperName) {
        this.shopkeeperName = shopkeeperName;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
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

    public String getJoinTable() {
        return joinTable;
    }

    public void setJoinTable(String joinTable) {
        this.joinTable = joinTable;
    }
}
