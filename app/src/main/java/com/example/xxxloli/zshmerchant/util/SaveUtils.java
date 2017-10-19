package com.example.xxxloli.zshmerchant.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceActivity;
import android.text.TextUtils;


import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.app.MyApplication;
import com.example.xxxloli.zshmerchant.objectmodel.UserInfoEntity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Administrator on 2017/2/7 .
 */
public class SaveUtils {


    /**
     * 读取配置
     *
     * @param context
     * @param xmlName 保存文件名
     * @param key     字段
     * @return "none"
     */
    public static String ReadSharedPerference(Context context, String xmlName,
                                              String key) {
        SharedPreferences preferences = context.getSharedPreferences(xmlName,
                PreferenceActivity.MODE_PRIVATE);
        // 得到文件中的name标签值，第二个参数表示如果没有这个标签的话，返回的默认值
        String value = preferences.getString(key, "");
        // 提示用户读取成功
        return value;
    }

    /**
     * 保存配置
     *
     * @param context
     * @param xmlName 保存文件名
     * @param key     字段
     * @param value   值
     */
    public static void SharedPerferencesCreat(Context context, String xmlName,
                                              String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(xmlName,
                PreferenceActivity.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(key, value);
        edit.commit();
    }

    /**
     * 清空数据
     *
     * @param context
     * @param xmlName
     */
    public static void clearPerfssences(Context context, String xmlName) {

        SharedPreferences preferences = context.getSharedPreferences(xmlName,
                PreferenceActivity.MODE_WORLD_READABLE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear();
        edit.commit();

    }

    /**
     * 保存在手机里面的文件名
     */
    private static final String APP_COMMON_FILE_NAME = "xxx";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static boolean saveToApp(String key, Object object) {
        SharedPreferences sp = MyApplication.mContext
                .getSharedPreferences(APP_COMMON_FILE_NAME,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        return editor.commit();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getFromApp(String key, Object defaultObject) {
        SharedPreferences sp = MyApplication.mContext
                .getSharedPreferences(APP_COMMON_FILE_NAME,
                        Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * 保存个人信息内存
     *
     * @param context
     * @param userInfo
     * @return
     */
    public static boolean putUserInfo(Context context, UserInfoEntity userInfo) {
        String str = "";
        try {
            str = serialize(userInfo);
//            saveToApp(Config.KEY_ID, userInfo.id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saveToApp(Config.KEY_INFO, str);
    }

    /**
     * 提取保存在内存的个人信息
     *
     * @param context
     * @return
     */
    public static UserInfoEntity getUserInfo(Context context) {
        UserInfoEntity userInfo = null;
        String str = (String) getFromApp(Config.KEY_INFO, "");
        try {
            userInfo = deSerialization(str);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    /**
     * 序列化对象
     *
     * @return
     * @throws IOException
     */
    public static String serialize(UserInfoEntity userInfo) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(userInfo);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return serStr;
    }

    /**
     * 反序列化对象
     *
     * @param str
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static UserInfoEntity deSerialization(String str) throws IOException, ClassNotFoundException {
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        UserInfoEntity userInfo = (UserInfoEntity) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return userInfo;
    }


    /**
     * 保存到手机的id
     *
     * @param context
     * @return
     */
    public static String getKeyId(Context context) {
        String userId = (String) getFromApp(Config.KEY_ID, "");
        if (TextUtils.isEmpty(userId)) {
            return "";
        }
        return userId;
    }

    /**
     * 清空保存在手机的数据
     *
     * @param context
     */
    public static void clear(Context context) {
        remove(context, Config.KEY_INFO);
        remove(context, Config.KEY_ID);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        try {
            SharedPreferences sp = context.getSharedPreferences(
                    APP_COMMON_FILE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(key);
            editor.commit();
        } catch (Exception e) {
        }
    }


    /**
     * 加载本地头像缓存
     *
     * @param context
     */
    public static Bitmap loadHead(Context context) {
        File file=new File(context.getFilesDir().getAbsolutePath() + "/head");
        if (file.exists())
        return BitmapFactory.decodeFile(context.getFilesDir().getAbsolutePath() + "/head");
        else return BitmapFactory.decodeResource(context.getResources(), R.drawable.daijinquan);
    }

    /**
     * 缓存头像到本地
     *
     * @param context
     * @param img
     */
    public static void saveHead(Context context, Bitmap img) {
        FileOutputStream fos = null;
        try {
            File file = new File(context.getFilesDir().getAbsolutePath() + "/head");
            if (img != null) {
                fos = new FileOutputStream(file);
                img.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            } else {
                if (file.exists() && file.canRead()) file.delete();
            }
        } catch (IOException e) {
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
            }
        }
    }
}
