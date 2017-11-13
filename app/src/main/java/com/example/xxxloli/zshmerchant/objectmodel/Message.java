package com.example.xxxloli.zshmerchant.objectmodel;

import com.util.ParcelableUtil;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/28.
 */

public class Message  implements Serializable {

    /**
     * id : 402880e55f4c84a0015f4c97e9db0005
     * title : 下架活动商品
     * content : 您已下架名称为564416的活动商品。
     * createDate : 2017-10-24 12:15:45
     * shopId : 402880e75f000ab6015f0043a1fc0004
     * type : ShopNews
     * type_value : 商家消息
     * status : Normal
     * status_value : 已发布
     */

    private String id;
    private String title;
    private String content;
    private String createDate;
    private String shopId;
    private String type;
    private String type_value;
    private String status;
    private String status_value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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
}
