package com.example.xxxloli.zshmerchant.util;

import android.content.Context;

import com.example.xxxloli.zshmerchant.Activity.CommunityActivity;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.Shop;

/**
 * Created by xxxloli on 2017/12/7.
 */

public class GreenDaoHelp {
    private static DBManagerShop dbManagerShop;
    private static Shop shop;


    public static Shop GetShop(Context context) {
        dbManagerShop = DBManagerShop.getInstance(context);
        return  dbManagerShop.queryById((long) 2333).get(0);
    }

    public static void UpdateShop(Shop shop) {
        dbManagerShop.updateShop(shop);
    }

    public String deleteCharString0(String sourceString, char chElemData) {
        String deleteString = "";
        for (int i = 0; i < sourceString.length(); i++) {
            if (sourceString.charAt(i) != chElemData) {
                deleteString += sourceString.charAt(i);
            }
        }
        return deleteString;
    }
}
