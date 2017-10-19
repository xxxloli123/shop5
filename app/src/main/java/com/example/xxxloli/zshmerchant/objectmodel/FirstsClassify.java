package com.example.xxxloli.zshmerchant.objectmodel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/18.
 */

public class FirstsClassify {

    private int id;
    private String name;
    private ArrayList<SecondsClassify> secondsClassifies;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<SecondsClassify> getSecondsClassifies() {
        return secondsClassifies;
    }

    public void setSecondsClassifies(ArrayList<SecondsClassify> secondsClassifies) {
        this.secondsClassifies = secondsClassifies;
    }
}
