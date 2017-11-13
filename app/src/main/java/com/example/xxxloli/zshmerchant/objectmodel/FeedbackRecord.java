package com.example.xxxloli.zshmerchant.objectmodel;

import android.net.Uri;

import com.interfaceconfig.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/24.
 */

public class FeedbackRecord {
    private String time,query,reply;
    private List<Uri> images;

    public FeedbackRecord(String time, String query, String reply, List<String> images) {
        this.time = time;
        this.query = query;
        this.reply = reply;
        this.images = new ArrayList<>();
        for(String string : images)
            this.images.add(Uri.parse(Config.LOCAL_HOST+"slowlife/img/userinfo/"+string));
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public List<Uri> getImages() {
        return images;
    }

    public void setImages(List<Uri> images) {
        this.images = images;
    }
}
