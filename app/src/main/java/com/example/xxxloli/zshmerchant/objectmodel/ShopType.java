package com.example.xxxloli.zshmerchant.objectmodel;

/**
 * Created by Administrator on 2017/10/5.
 */

public class ShopType {

    /**
     * id : 402880e95ecbfe1c015ecc012e6a0000
     * sort : 1
     * fatherId :
     * fatherName : null
     * grade : -1
     * genericClassName : 超市
     * shopgrade : null
     */

    private String id;
    private String sort;
    private String fatherId;
    private Object fatherName;
    private int grade;
    private String genericClassName;
    private Object shopgrade;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public Object getFatherName() {
        return fatherName;
    }

    public void setFatherName(Object fatherName) {
        this.fatherName = fatherName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getGenericClassName() {
        return genericClassName;
    }

    public void setGenericClassName(String genericClassName) {
        this.genericClassName = genericClassName;
    }

    public Object getShopgrade() {
        return shopgrade;
    }

    public void setShopgrade(Object shopgrade) {
        this.shopgrade = shopgrade;
    }
}
