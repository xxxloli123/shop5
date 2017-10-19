package com.example.xxxloli.zshmerchant.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.xxxloli.zshmerchant.R;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class Common {
    public static String weichat = "0";//0未绑定 1已绑定
    public static String bankcard = "0";//0未绑定 1已绑定
    public static String alipay = "0";//0未绑定 1已绑定

    /**
     * 判断是否为空
     */
    public static boolean isNull(Object o) {
        if (o == null || o.equals("") || o == "" || o == "null"
                || o.equals("null") || o.equals("undefined")) {
            return true;
        }
        if (o != null) {
            if (o.toString().replaceAll("\\s*", "").length() != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 设置部分字体的颜色
     */
    public static SpannableStringBuilder textColor(String hint, int fstart, int fend) {
        SpannableStringBuilder style = new SpannableStringBuilder(hint);
        style.setSpan(new ForegroundColorSpan(Color.BLUE), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }

    /**
     * 把bitmap转成圆形
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int r = 0;
        //取最短边做边长
        if (width < height) {
            r = width;
        } else {
            r = height;
        }
        //构建一个bitmap
        Bitmap backgroundBm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //new一个Canvas，在backgroundBmp上画图
        Canvas canvas = new Canvas(backgroundBm);
        Paint p = new Paint();
        //设置边缘光滑，去掉锯齿
        p.setAntiAlias(true);
        RectF rect = new RectF(0, 0, r, r);
        //通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
        //且都等于r/2时，画出来的圆角矩形就是圆形
        canvas.drawRoundRect(rect, r / 2, r / 2, p);
        //设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(bitmap, null, rect, p);
        return backgroundBm;
    }

    /**
     * 弹出拨打电话窗口
     */
    public static void phoneDialog(final Context mContext, String phone) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        LinearLayout mDialog = (LinearLayout) inflater.inflate(R.layout.dialog_revise_phone, null);
        final Dialog dialog = new AlertDialog.Builder(mContext).create();
        final TextView text = (TextView) mDialog.findViewById(R.id.title);
        text.setText(phone);
        Button mYes = (Button) mDialog.findViewById(R.id.sure_bt);
        mYes.setText("呼叫");
        mYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = text.getText().toString().trim();
                dialPhone(mContext, s);
                dialog.dismiss();
            }
        });
        Button mNo = (Button) mDialog.findViewById(R.id.cancel_bt);
        mNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setContentView(mDialog);
    }

    /**
     * 版本名
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    /**
     * 版本号
     */
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    /**
     * 以下是获得版本信息的工具方法
     */
    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    /**
     * 拨打电话
     */
    public static void dialPhone(Context mContext, String phone) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        mContext.startActivity(intent);
    }

    /**
     * 把客户端对象值赋值给服务器端对象(不为空的值)
     */
    public static Object objectMerge(Object serverObject, Object clientObject,
                                     String[] attributes) throws NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {
        Field s_field = null, c_field = null;
        // 属性名
        for (String attr : attributes) {
            s_field = serverObject.getClass().getDeclaredField(attr);
            c_field = clientObject.getClass().getDeclaredField(attr);
            s_field.setAccessible(true);
            c_field.setAccessible(true);
            s_field.set(serverObject, c_field.get(clientObject)); // 对象合并允许为空
        }
        return serverObject;
    }

    /**
     * 判断一个对象的必填属性
     */
    public static boolean isObjectRequiredAttributes(Object o,
                                                     String[] attributes) throws NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {
        boolean result = true;
        Field field = null;
        for (String attr : attributes) {
            field = o.getClass().getDeclaredField(attr);
            field.setAccessible(true);
            if (field.get(o) == null || field.get(o) == ""
                    || "".equals((field.get(o) + "").trim())) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 把对象显示到页面上
     */
    public static void showObject(Context context, Object o) {


    }

    /**
     * 把页面上的数据封装成对象
     */
    public static Object viewToObject(Context context, Object o) {
        return o;
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass
                        .getField("status_bar_height").get(localObject)
                        .toString());
                statusHeight = activity.getResources()
                        .getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    /**
     * 验证手机号
     *
     * @param phone 手机号
     * @return 格式是否正确
     */
    public static boolean matchePhone(String phone) {
        return phone != null && phone.matches("^1[3|5|7|8]\\d{9}$");
    }

    /**
     * 获取验证码
     *
     * @param codeType 类型
     * @param phone    手机号
     */
    public static Map<String, Object> getCode(String phone, String codeType) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("codeType", codeType);
        return map;
    }
}
