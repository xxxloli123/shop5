package com.example.xxxloli.zshmerchant.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.xxxloli.zshmerchant.greendao.gen.DaoMaster;
import com.example.xxxloli.zshmerchant.greendao.gen.DaoSession;
import com.example.xxxloli.zshmerchant.greendao.gen.ShopDao;
import com.example.xxxloli.zshmerchant.greendao.gen.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */

public class DBManagerShop {
    private final static String dbName = "test_db";
    private static DBManagerShop mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    public DBManagerShop(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManagerShop getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManagerShop.class) {
                if (mInstance == null) {
                    mInstance = new DBManagerShop(context);
                }
            }
        }
        return mInstance;
    }
    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }
    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }
    /**
     * 插入一条User记录
     *
     * @param shop
     */
    public void insertShop(Shop shop) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ShopDao shopDao = daoSession.getShopDao();
        shopDao.insert(shop);
    }

    /**
     * 插入用户集合
     *
     * @param
     */
    public void insertShopList(List<Shop> shops) {
        if (shops == null || shops.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ShopDao shopDao = daoSession.getShopDao();
        shopDao.insertInTx(shops);
    }
    /**
     * 删除一条记录
     *
     * @param
     */
    public void deleteById(Long key) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ShopDao shopDao = daoSession.getShopDao();
        shopDao.deleteByKey(key);
    }
    /**
     * 更新一条记录
     *
     * @param
     */
    public void updateShop(Shop shop) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ShopDao shopDao = daoSession.getShopDao();
        shopDao.update(shop);
    }
    /**
     * 查询用户列表
     */
    public List<Shop> queryShopList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ShopDao shopDao = daoSession.getShopDao();
        QueryBuilder<Shop> qb = shopDao.queryBuilder();
        List<Shop> list = qb.list();
        return list;
    }
//    /**
//     * 查询LoginIfio列表
//     */
//    public List<LoginIfio> queryLoginIfioList() {
//        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        LoginIfioDao loginIfioDao = daoSession.getLoginIfioDao();
//        QueryBuilder<LoginIfio> qb = loginIfioDao.queryBuilder();
//        List<LoginIfio> list = qb.list();
//        return list;
//    }

    /**
     * 查询用户列表
     */
    public List<User> queryUserList(String age) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(UserDao.Properties.Name.le(age)).orderAsc(UserDao.Properties.Name);
        List<User> list = qb.list();
        return list;
    }

    /**
     * 通过Name查找某一条数据
     * @param s
     * @return
     */
    public List<User> queryByName(String s){
        DaoMaster daoMaster=new DaoMaster(getReadableDatabase());
        DaoSession daoSession=daoMaster.newSession();
        UserDao userDao=daoSession.getUserDao();
        QueryBuilder<User> qb=userDao.queryBuilder();
        qb.where(UserDao.Properties.Name.eq(s));
        List<User> shuju=qb.list();
        return shuju;
    }
    /**
     * 通过Phone查找某一条数据
     * @param s
     * @return
     */
    public List<User> queryByPhone(String s){
        DaoMaster daoMaster=new DaoMaster(getReadableDatabase());
        DaoSession daoSession=daoMaster.newSession();
        UserDao userDao=daoSession.getUserDao();
        QueryBuilder<User> qb=userDao.queryBuilder();
//        qb.where(UserDao.Properties.Phone.eq(s));
        List<User> shuju=qb.list();
        return shuju;
    }
    /**
     * 通过Phone查找某一条数据
     * @param s
     * @return
     */
    public List<Shop> queryById(Long s){
        DaoMaster daoMaster=new DaoMaster(getReadableDatabase());
        DaoSession daoSession=daoMaster.newSession();
        ShopDao shopDao=daoSession.getShopDao();
        QueryBuilder<Shop> qb=shopDao.queryBuilder();
        qb.where(ShopDao.Properties.WritId.eq(s));
        List<Shop> shuju=qb.list();
        return shuju;
    }
//    /**
//     * 插入一条LoginIfio记录
//     *
//     * @param loginIfio
//     */
//    public void insertLoginIfio(LoginIfio loginIfio) {
//        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        LoginIfioDao loginIfioDao = daoSession.getLoginIfioDao();
//        loginIfioDao.insert(loginIfio);
//    }
//    /**
//     * 更新一条记录
//     *
//     * @param loginIfio
//     */
//    public void updateLoginIfio(LoginIfio loginIfio) {
//        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        LoginIfioDao loginIfioDao = daoSession.getLoginIfioDao();
//        loginIfioDao.update(loginIfio);
//    }
}