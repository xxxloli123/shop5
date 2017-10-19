package com.example.xxxloli.zshmerchant.objectmodel;

/**
 * Created by Administrator on 2017/9/15.
 */

public class ExpenseCode {


    /**
     * id : 402880ea5ef06906015ef08378ff0003
     * shopName : 金刀客
     * shopId : 402880e65ed0bda0015ed0c876e00007
     * shopkeeperId : 402880e65ed0bda0015ed0c876500005
     * shopkeeperName : 荷叶
     * consumeCode : 999
     * tableId : null
     * status : Unused
     * status_value : 空闲
     */

    private String id;
    private String shopName;
    private String shopId;
    private String shopkeeperId;
    private String shopkeeperName;
    private String consumeCode;
    private Object tableId;
    private String status;
    private String status_value;

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

    public String getConsumeCode() {
        return consumeCode;
    }

    public void setConsumeCode(String consumeCode) {
        this.consumeCode = consumeCode;
    }

    public Object getTableId() {
        return tableId;
    }

    public void setTableId(Object tableId) {
        this.tableId = tableId;
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
}
