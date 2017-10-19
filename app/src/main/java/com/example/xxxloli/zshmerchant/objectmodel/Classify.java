package com.example.xxxloli.zshmerchant.objectmodel;

/**
 * Created by Administrator on 2017/9/18.
 */

public class Classify {


    /**
     * id : 402880ea5efebf0c015efee1a7d30001
     * shopName : 金刀客
     * shopId : 402880e65ed0bda0015ed0c876e00007
     * shopkeeperId : 402880e65ed0bda0015ed0c876500005
     * shopkeeperName : 荷叶
     * sort : 2
     * productClassName : 一级分类2
     * fatherId : 0
     */

    private String id;
    private String shopName;
    private String shopId;
    private String shopkeeperId;
    private String shopkeeperName;
    private int sort;
    private String productClassName;
    private String fatherId;

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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getProductClassName() {
        return productClassName;
    }

    public void setProductClassName(String productClassName) {
        this.productClassName = productClassName;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }
}
