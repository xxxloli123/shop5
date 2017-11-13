package com.example.xxxloli.zshmerchant.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by xxxloli on 2017/10/20.
 */
@Entity

public class Shop {
    @Id(autoincrement = true)
    private Long writId;

    private String id;
    private String special;
    private String shopCode;
    private String collectMoneyCode;
    private String shopName;
    private String shopkeeperId;
    private String shopkeeperName;
    private String shopkeeperPhone;
    private String othershopnum;
    private String selfproductnum;
    private String shopNumber;
    private String telePhone;
    private String shopImage;
    private String classId;
    private String className;
    private String startDate;
    private String endDate;
    private String status;
    private String bookingOrder;
    private String lineConsume;
    private String togetherTable;
    private String autoOrder;
    private String business;
    private String appDisplay;
    private String cod;
    private String distance;
    private String deliveryFee;
    private String packingprice;
    private String pro;
    private String city;
    private String district;
    private String street;
    private String houseNumber;
    private double lng;
    private double lat;
    private String registerDate;
    private String shopUserDistance;
    private String shopNotices;
    @Generated(hash = 295518808)
    public Shop(Long writId, String id, String special, String shopCode,
            String collectMoneyCode, String shopName, String shopkeeperId,
            String shopkeeperName, String shopkeeperPhone, String othershopnum,
            String selfproductnum, String shopNumber, String telePhone,
            String shopImage, String classId, String className, String startDate,
            String endDate, String status, String bookingOrder, String lineConsume,
            String togetherTable, String autoOrder, String business,
            String appDisplay, String cod, String distance, String deliveryFee,
            String packingprice, String pro, String city, String district,
            String street, String houseNumber, double lng, double lat,
            String registerDate, String shopUserDistance, String shopNotices) {
        this.writId = writId;
        this.id = id;
        this.special = special;
        this.shopCode = shopCode;
        this.collectMoneyCode = collectMoneyCode;
        this.shopName = shopName;
        this.shopkeeperId = shopkeeperId;
        this.shopkeeperName = shopkeeperName;
        this.shopkeeperPhone = shopkeeperPhone;
        this.othershopnum = othershopnum;
        this.selfproductnum = selfproductnum;
        this.shopNumber = shopNumber;
        this.telePhone = telePhone;
        this.shopImage = shopImage;
        this.classId = classId;
        this.className = className;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.bookingOrder = bookingOrder;
        this.lineConsume = lineConsume;
        this.togetherTable = togetherTable;
        this.autoOrder = autoOrder;
        this.business = business;
        this.appDisplay = appDisplay;
        this.cod = cod;
        this.distance = distance;
        this.deliveryFee = deliveryFee;
        this.packingprice = packingprice;
        this.pro = pro;
        this.city = city;
        this.district = district;
        this.street = street;
        this.houseNumber = houseNumber;
        this.lng = lng;
        this.lat = lat;
        this.registerDate = registerDate;
        this.shopUserDistance = shopUserDistance;
        this.shopNotices = shopNotices;
    }
    @Generated(hash = 633476670)
    public Shop() {
    }
    public Long getWritId() {
        return this.writId;
    }
    public void setWritId(Long writId) {
        this.writId = writId;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getSpecial() {
        return this.special;
    }
    public void setSpecial(String special) {
        this.special = special;
    }
    public String getShopCode() {
        return this.shopCode;
    }
    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }
    public String getCollectMoneyCode() {
        return this.collectMoneyCode;
    }
    public void setCollectMoneyCode(String collectMoneyCode) {
        this.collectMoneyCode = collectMoneyCode;
    }
    public String getShopName() {
        return this.shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getShopkeeperId() {
        return this.shopkeeperId;
    }
    public void setShopkeeperId(String shopkeeperId) {
        this.shopkeeperId = shopkeeperId;
    }
    public String getShopkeeperName() {
        return this.shopkeeperName;
    }
    public void setShopkeeperName(String shopkeeperName) {
        this.shopkeeperName = shopkeeperName;
    }
    public String getShopkeeperPhone() {
        return this.shopkeeperPhone;
    }
    public void setShopkeeperPhone(String shopkeeperPhone) {
        this.shopkeeperPhone = shopkeeperPhone;
    }
    public String getOthershopnum() {
        return this.othershopnum;
    }
    public void setOthershopnum(String othershopnum) {
        this.othershopnum = othershopnum;
    }
    public String getSelfproductnum() {
        return this.selfproductnum;
    }
    public void setSelfproductnum(String selfproductnum) {
        this.selfproductnum = selfproductnum;
    }
    public String getShopNumber() {
        return this.shopNumber;
    }
    public void setShopNumber(String shopNumber) {
        this.shopNumber = shopNumber;
    }
    public String getTelePhone() {
        return this.telePhone;
    }
    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }
    public String getShopImage() {
        return this.shopImage;
    }
    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }
    public String getClassId() {
        return this.classId;
    }
    public void setClassId(String classId) {
        this.classId = classId;
    }
    public String getClassName() {
        return this.className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getStartDate() {
        return this.startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return this.endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getBookingOrder() {
        return this.bookingOrder;
    }
    public void setBookingOrder(String bookingOrder) {
        this.bookingOrder = bookingOrder;
    }
    public String getLineConsume() {
        return this.lineConsume;
    }
    public void setLineConsume(String lineConsume) {
        this.lineConsume = lineConsume;
    }
    public String getTogetherTable() {
        return this.togetherTable;
    }
    public void setTogetherTable(String togetherTable) {
        this.togetherTable = togetherTable;
    }
    public String getAutoOrder() {
        return this.autoOrder;
    }
    public void setAutoOrder(String autoOrder) {
        this.autoOrder = autoOrder;
    }
    public String getBusiness() {
        return this.business;
    }
    public void setBusiness(String business) {
        this.business = business;
    }
    public String getAppDisplay() {
        return this.appDisplay;
    }
    public void setAppDisplay(String appDisplay) {
        this.appDisplay = appDisplay;
    }
    public String getCod() {
        return this.cod;
    }
    public void setCod(String cod) {
        this.cod = cod;
    }
    public String getDistance() {
        return this.distance;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }
    public String getDeliveryFee() {
        return this.deliveryFee;
    }
    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
    public String getPackingprice() {
        return this.packingprice;
    }
    public void setPackingprice(String packingprice) {
        this.packingprice = packingprice;
    }
    public String getPro() {
        return this.pro;
    }
    public void setPro(String pro) {
        this.pro = pro;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getDistrict() {
        return this.district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public String getStreet() {
        return this.street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getHouseNumber() {
        return this.houseNumber;
    }
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
    public double getLng() {
        return this.lng;
    }
    public void setLng(double lng) {
        this.lng = lng;
    }
    public double getLat() {
        return this.lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public String getRegisterDate() {
        return this.registerDate;
    }
    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }
    public String getShopUserDistance() {
        return this.shopUserDistance;
    }
    public void setShopUserDistance(String shopUserDistance) {
        this.shopUserDistance = shopUserDistance;
    }
    public String getShopNotices() {
        return this.shopNotices;
    }
    public void setShopNotices(String shopNotices) {
        this.shopNotices = shopNotices;
    }

}
