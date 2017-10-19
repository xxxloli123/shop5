package com.example.xxxloli.zshmerchant.greendao;

import com.example.xxxloli.zshmerchant.objectmodel.Info;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/9/30.
 */

@Entity
public class User {
    @Id
    private Long writId;
    
    private String id;
    private String qqOpenId;
    private String wxOpenId;
    private String superId;
    private String jobId;
    private String name;
    private String discount;
    private int discountProportion;
    private String phone;
    private String phoneBeforeDelete;
    private String adminId;
    private String telePhone;
    private String mailbox;
    private String area;
    private String adcode;
    private String type;
    private String type_value;
    private String status;
    private String status_value;
    private String shopBusinessNumber;
    private String shopBusinessConfirm;
    private String shopBusinessConfirm_value;
    private String idNumber;
    private String idConfirm;
    private String idConfirm_value;
    private String healthyNumber;
    private String healthyConfirm;
    private String healthyConfirm_value;
    private String drivingNumber;
    private String drivingConfirm;
    private String drivingConfirm_value;
    private String carNumber;
    private String carConfirm;
    private String carConfirm_value;
    private String registerDate;
    private String sex;
    private String sex_value;
    private String token;
    private String phoneType;
    private String phoneType_value;
    private String nickName;
    private String job;
    private String headerImg;
    private String idStartTime;
    private String idEndTime;
    private String brief;
    private String latestLoginDatetime;
    private String signtime;
    private int integralNumber;
    private String lng;
    private String lat;
    private String updatePositionDate;
    private String role_name;
    private String commpany_id;
    private String commpany_name;
    private String postmanStreet;
    private String shop_id;
    private String shop_name;
    private int packageNumber;
    private int validRecommendNumber;
    private String onlineStatus;
    private String onlineStatus_value;
    private String id2;
    @Generated(hash = 1720102621)
    public User(Long writId, String id, String qqOpenId, String wxOpenId,
            String superId, String jobId, String name, String discount,
            int discountProportion, String phone, String phoneBeforeDelete,
            String adminId, String telePhone, String mailbox, String area,
            String adcode, String type, String type_value, String status,
            String status_value, String shopBusinessNumber,
            String shopBusinessConfirm, String shopBusinessConfirm_value,
            String idNumber, String idConfirm, String idConfirm_value,
            String healthyNumber, String healthyConfirm,
            String healthyConfirm_value, String drivingNumber,
            String drivingConfirm, String drivingConfirm_value, String carNumber,
            String carConfirm, String carConfirm_value, String registerDate,
            String sex, String sex_value, String token, String phoneType,
            String phoneType_value, String nickName, String job, String headerImg,
            String idStartTime, String idEndTime, String brief,
            String latestLoginDatetime, String signtime, int integralNumber,
            String lng, String lat, String updatePositionDate, String role_name,
            String commpany_id, String commpany_name, String postmanStreet,
            String shop_id, String shop_name, int packageNumber,
            int validRecommendNumber, String onlineStatus,
            String onlineStatus_value, String id2) {
        this.writId = writId;
        this.id = id;
        this.qqOpenId = qqOpenId;
        this.wxOpenId = wxOpenId;
        this.superId = superId;
        this.jobId = jobId;
        this.name = name;
        this.discount = discount;
        this.discountProportion = discountProportion;
        this.phone = phone;
        this.phoneBeforeDelete = phoneBeforeDelete;
        this.adminId = adminId;
        this.telePhone = telePhone;
        this.mailbox = mailbox;
        this.area = area;
        this.adcode = adcode;
        this.type = type;
        this.type_value = type_value;
        this.status = status;
        this.status_value = status_value;
        this.shopBusinessNumber = shopBusinessNumber;
        this.shopBusinessConfirm = shopBusinessConfirm;
        this.shopBusinessConfirm_value = shopBusinessConfirm_value;
        this.idNumber = idNumber;
        this.idConfirm = idConfirm;
        this.idConfirm_value = idConfirm_value;
        this.healthyNumber = healthyNumber;
        this.healthyConfirm = healthyConfirm;
        this.healthyConfirm_value = healthyConfirm_value;
        this.drivingNumber = drivingNumber;
        this.drivingConfirm = drivingConfirm;
        this.drivingConfirm_value = drivingConfirm_value;
        this.carNumber = carNumber;
        this.carConfirm = carConfirm;
        this.carConfirm_value = carConfirm_value;
        this.registerDate = registerDate;
        this.sex = sex;
        this.sex_value = sex_value;
        this.token = token;
        this.phoneType = phoneType;
        this.phoneType_value = phoneType_value;
        this.nickName = nickName;
        this.job = job;
        this.headerImg = headerImg;
        this.idStartTime = idStartTime;
        this.idEndTime = idEndTime;
        this.brief = brief;
        this.latestLoginDatetime = latestLoginDatetime;
        this.signtime = signtime;
        this.integralNumber = integralNumber;
        this.lng = lng;
        this.lat = lat;
        this.updatePositionDate = updatePositionDate;
        this.role_name = role_name;
        this.commpany_id = commpany_id;
        this.commpany_name = commpany_name;
        this.postmanStreet = postmanStreet;
        this.shop_id = shop_id;
        this.shop_name = shop_name;
        this.packageNumber = packageNumber;
        this.validRecommendNumber = validRecommendNumber;
        this.onlineStatus = onlineStatus;
        this.onlineStatus_value = onlineStatus_value;
        this.id2 = id2;
    }
    @Generated(hash = 586692638)
    public User() {
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
    public String getQqOpenId() {
        return this.qqOpenId;
    }
    public void setQqOpenId(String qqOpenId) {
        this.qqOpenId = qqOpenId;
    }
    public String getWxOpenId() {
        return this.wxOpenId;
    }
    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }
    public String getSuperId() {
        return this.superId;
    }
    public void setSuperId(String superId) {
        this.superId = superId;
    }
    public String getJobId() {
        return this.jobId;
    }
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDiscount() {
        return this.discount;
    }
    public void setDiscount(String discount) {
        this.discount = discount;
    }
    public int getDiscountProportion() {
        return this.discountProportion;
    }
    public void setDiscountProportion(int discountProportion) {
        this.discountProportion = discountProportion;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhoneBeforeDelete() {
        return this.phoneBeforeDelete;
    }
    public void setPhoneBeforeDelete(String phoneBeforeDelete) {
        this.phoneBeforeDelete = phoneBeforeDelete;
    }
    public String getAdminId() {
        return this.adminId;
    }
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
    public String getTelePhone() {
        return this.telePhone;
    }
    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }
    public String getMailbox() {
        return this.mailbox;
    }
    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }
    public String getArea() {
        return this.area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getAdcode() {
        return this.adcode;
    }
    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType_value() {
        return this.type_value;
    }
    public void setType_value(String type_value) {
        this.type_value = type_value;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus_value() {
        return this.status_value;
    }
    public void setStatus_value(String status_value) {
        this.status_value = status_value;
    }
    public String getShopBusinessNumber() {
        return this.shopBusinessNumber;
    }
    public void setShopBusinessNumber(String shopBusinessNumber) {
        this.shopBusinessNumber = shopBusinessNumber;
    }
    public String getShopBusinessConfirm() {
        return this.shopBusinessConfirm;
    }
    public void setShopBusinessConfirm(String shopBusinessConfirm) {
        this.shopBusinessConfirm = shopBusinessConfirm;
    }
    public String getShopBusinessConfirm_value() {
        return this.shopBusinessConfirm_value;
    }
    public void setShopBusinessConfirm_value(String shopBusinessConfirm_value) {
        this.shopBusinessConfirm_value = shopBusinessConfirm_value;
    }
    public String getIdNumber() {
        return this.idNumber;
    }
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    public String getIdConfirm() {
        return this.idConfirm;
    }
    public void setIdConfirm(String idConfirm) {
        this.idConfirm = idConfirm;
    }
    public String getIdConfirm_value() {
        return this.idConfirm_value;
    }
    public void setIdConfirm_value(String idConfirm_value) {
        this.idConfirm_value = idConfirm_value;
    }
    public String getHealthyNumber() {
        return this.healthyNumber;
    }
    public void setHealthyNumber(String healthyNumber) {
        this.healthyNumber = healthyNumber;
    }
    public String getHealthyConfirm() {
        return this.healthyConfirm;
    }
    public void setHealthyConfirm(String healthyConfirm) {
        this.healthyConfirm = healthyConfirm;
    }
    public String getHealthyConfirm_value() {
        return this.healthyConfirm_value;
    }
    public void setHealthyConfirm_value(String healthyConfirm_value) {
        this.healthyConfirm_value = healthyConfirm_value;
    }
    public String getDrivingNumber() {
        return this.drivingNumber;
    }
    public void setDrivingNumber(String drivingNumber) {
        this.drivingNumber = drivingNumber;
    }
    public String getDrivingConfirm() {
        return this.drivingConfirm;
    }
    public void setDrivingConfirm(String drivingConfirm) {
        this.drivingConfirm = drivingConfirm;
    }
    public String getDrivingConfirm_value() {
        return this.drivingConfirm_value;
    }
    public void setDrivingConfirm_value(String drivingConfirm_value) {
        this.drivingConfirm_value = drivingConfirm_value;
    }
    public String getCarNumber() {
        return this.carNumber;
    }
    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
    public String getCarConfirm() {
        return this.carConfirm;
    }
    public void setCarConfirm(String carConfirm) {
        this.carConfirm = carConfirm;
    }
    public String getCarConfirm_value() {
        return this.carConfirm_value;
    }
    public void setCarConfirm_value(String carConfirm_value) {
        this.carConfirm_value = carConfirm_value;
    }
    public String getRegisterDate() {
        return this.registerDate;
    }
    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSex_value() {
        return this.sex_value;
    }
    public void setSex_value(String sex_value) {
        this.sex_value = sex_value;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getPhoneType() {
        return this.phoneType;
    }
    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }
    public String getPhoneType_value() {
        return this.phoneType_value;
    }
    public void setPhoneType_value(String phoneType_value) {
        this.phoneType_value = phoneType_value;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getJob() {
        return this.job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public String getHeaderImg() {
        return this.headerImg;
    }
    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }
    public String getIdStartTime() {
        return this.idStartTime;
    }
    public void setIdStartTime(String idStartTime) {
        this.idStartTime = idStartTime;
    }
    public String getIdEndTime() {
        return this.idEndTime;
    }
    public void setIdEndTime(String idEndTime) {
        this.idEndTime = idEndTime;
    }
    public String getBrief() {
        return this.brief;
    }
    public void setBrief(String brief) {
        this.brief = brief;
    }
    public String getLatestLoginDatetime() {
        return this.latestLoginDatetime;
    }
    public void setLatestLoginDatetime(String latestLoginDatetime) {
        this.latestLoginDatetime = latestLoginDatetime;
    }
    public String getSigntime() {
        return this.signtime;
    }
    public void setSigntime(String signtime) {
        this.signtime = signtime;
    }
    public int getIntegralNumber() {
        return this.integralNumber;
    }
    public void setIntegralNumber(int integralNumber) {
        this.integralNumber = integralNumber;
    }
    public String getLng() {
        return this.lng;
    }
    public void setLng(String lng) {
        this.lng = lng;
    }
    public String getLat() {
        return this.lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getUpdatePositionDate() {
        return this.updatePositionDate;
    }
    public void setUpdatePositionDate(String updatePositionDate) {
        this.updatePositionDate = updatePositionDate;
    }
    public String getRole_name() {
        return this.role_name;
    }
    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
    public String getCommpany_id() {
        return this.commpany_id;
    }
    public void setCommpany_id(String commpany_id) {
        this.commpany_id = commpany_id;
    }
    public String getCommpany_name() {
        return this.commpany_name;
    }
    public void setCommpany_name(String commpany_name) {
        this.commpany_name = commpany_name;
    }
    public String getPostmanStreet() {
        return this.postmanStreet;
    }
    public void setPostmanStreet(String postmanStreet) {
        this.postmanStreet = postmanStreet;
    }
    public String getShop_id() {
        return this.shop_id;
    }
    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }
    public String getShop_name() {
        return this.shop_name;
    }
    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }
    public int getPackageNumber() {
        return this.packageNumber;
    }
    public void setPackageNumber(int packageNumber) {
        this.packageNumber = packageNumber;
    }
    public int getValidRecommendNumber() {
        return this.validRecommendNumber;
    }
    public void setValidRecommendNumber(int validRecommendNumber) {
        this.validRecommendNumber = validRecommendNumber;
    }
    public String getOnlineStatus() {
        return this.onlineStatus;
    }
    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
    public String getOnlineStatus_value() {
        return this.onlineStatus_value;
    }
    public void setOnlineStatus_value(String onlineStatus_value) {
        this.onlineStatus_value = onlineStatus_value;
    }
    public String getId2() {
        return this.id2;
    }
    public void setId2(String id2) {
        this.id2 = id2;
    }

}