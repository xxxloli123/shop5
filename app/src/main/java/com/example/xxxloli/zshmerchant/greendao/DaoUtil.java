package com.example.xxxloli.zshmerchant.greendao;

import android.content.Context;

/**
 * Created by xxxloli on 2017/11/23.
 */

public class DaoUtil {
    private static DBManagerShop dbManagerShop;
    private static DBManagerUser dbManagerUser;
    private static DBManagerAccount dbManagerAccount;

    public static boolean isSaveShop(Context context) {
        dbManagerShop = DBManagerShop.getInstance(context);
        return dbManagerShop.queryById((long) 2333).size()!=0;
    }

    public static boolean isSaveUser(Context context) {
        dbManagerUser = DBManagerUser.getInstance(context);
        return dbManagerUser.queryById((long) 2333).size()!=0;
    }

    public static boolean isSaveAccount(Context context) {
        dbManagerAccount = DBManagerAccount.getInstance(context);
        return dbManagerUser.queryById((long) 2333).size()!=0;
    }
}
