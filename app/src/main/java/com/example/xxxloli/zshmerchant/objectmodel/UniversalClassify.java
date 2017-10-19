package com.example.xxxloli.zshmerchant.objectmodel;

/**
 * Created by xxxloli on 2017/10/18.
 */

public class UniversalClassify {
    /**
     * id : 2c9ad8435dd0534e015de3ba93430052
     * sort : 1
     * fatherId : 2c9ad8435dd0534e015dd05fe2380004
     * fatherName : 超市
     * grade : 2
     * genericClassName : 饮料、酒水、冲饮
     * shopgrade : null
     */

    private String id;
    private String sort;
    private String fatherId;
    private String fatherName;
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

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
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
