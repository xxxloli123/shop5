package com.example.xxxloli.zshmerchant.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by xxxloli on 2017/10/20.
 */
@Entity

public class Account {
    @Id(autoincrement = true)
    private Long writId;
    private String phone;
    private String pwd;
    private String name;
    private String head;
    @Generated(hash = 1054625552)
    public Account(Long writId, String phone, String pwd, String name,
            String head) {
        this.writId = writId;
        this.phone = phone;
        this.pwd = pwd;
        this.name = name;
        this.head = head;
    }
    @Generated(hash = 882125521)
    public Account() {
    }
    public Long getWritId() {
        return this.writId;
    }
    public void setWritId(Long writId) {
        this.writId = writId;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPwd() {
        return this.pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHead() {
        return this.head;
    }
    public void setHead(String head) {
        this.head = head;
    }
}
