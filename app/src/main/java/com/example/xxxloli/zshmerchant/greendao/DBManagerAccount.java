package com.example.xxxloli.zshmerchant.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.xxxloli.zshmerchant.greendao.gen.AccountDao;
import com.example.xxxloli.zshmerchant.greendao.gen.DaoMaster;
import com.example.xxxloli.zshmerchant.greendao.gen.DaoSession;
import com.example.xxxloli.zshmerchant.greendao.gen.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */

public class DBManagerAccount {
    private final static String dbName = "test_db";
    private static DBManagerAccount mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    public DBManagerAccount(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManagerAccount getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManagerAccount.class) {
                if (mInstance == null) {
                    mInstance = new DBManagerAccount(context);
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
     * @param
     */
    public void insertAccount(Account account) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        AccountDao accountDao = daoSession.getAccountDao();
        accountDao.insert(account);
    }

    /**
     * 插入用户集合
     *
     * @param
     */
    public void insertUserList(List<Account> accounts) {
        if (accounts == null || accounts.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        AccountDao accountDao = daoSession.getAccountDao();
        accountDao.insertInTx(accounts);
    }
    /**
     * 删除一条记录
     *
     * @param
     */
    public void deleteById(Long key) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        AccountDao accountDao = daoSession.getAccountDao();
        accountDao.deleteByKey(key);
    }
    /**
     * 更新一条记录
     *
     * @param
     */
    public void updateUser(Account account) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        AccountDao accountDao = daoSession.getAccountDao();
        accountDao.update(account);
    }

    /**
     * 查询用户列表
     */
    public List<Account> queryAccountList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        AccountDao accountDao = daoSession.getAccountDao();
        QueryBuilder<Account> qb = accountDao.queryBuilder();
        List<Account> list = qb.list();
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
     * 通过Name查找数据
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
     * 通过Phone查找数据
     * @param s
     * @return
     */
    public List<Account> queryByPhone(String s){
        DaoMaster daoMaster=new DaoMaster(getReadableDatabase());
        DaoSession daoSession=daoMaster.newSession();
        AccountDao accountDao=daoSession.getAccountDao();
        QueryBuilder<Account> qb=accountDao.queryBuilder();
        qb.where(AccountDao.Properties.Phone.eq(s));
        return qb.list();
    }
    /**
     * 通过WritId查找数据
     * @param s
     * @return
     */
    public List<Account> queryById(Long s){
        DaoMaster daoMaster=new DaoMaster(getReadableDatabase());
        DaoSession daoSession=daoMaster.newSession();
        AccountDao accountDao=daoSession.getAccountDao();
        QueryBuilder<Account> qb=accountDao.queryBuilder();
        qb.where(AccountDao.Properties.WritId.eq(s));
        List<Account> shuju=qb.list();
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