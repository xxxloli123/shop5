package com.example.xxxloli.zshmerchant.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by xxxloli on 2017/11/30.
 */

public class CompressImg {

    /**
     * 按照图片尺寸压缩：
     */
    public void limit2M(File srcPath, File desPath) {
        FileOutputStream fos = null;
        BitmapFactory.Options op = new BitmapFactory.Options();

        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        op.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath.getAbsolutePath(), op);
        op.inJustDecodeBounds = false;
        if (op.outWidth == 200f) return;
        // 缩放图片的尺寸
        float w = op.outWidth;
        float h = op.outHeight;
        // 最长宽度或高度1024
        float be = 1.0f;
        try {
            be = getFileSize(srcPath) / 1000 / 1000 / 2f;
//            int be1= (int) (getFileSize(file) / 1024);
//
            Log.e("大小", "丢了个雷姆" + be);
//            return;
        } catch (Exception e) {
//            Log.e("大小", "丢了个雷姆" + be);
            e.printStackTrace();
            return;
        }
//        if (w > h || w > ww) {
//            be = (float) (w / ww);
//        } else if (w < h || h > hh) {
//            be = (float) (h / hh);
//        }
//        if (h>hh  && w > ww) be = (float) (w / ww);
        if (be <= 1) {
            return;

        }
        op.inSampleSize = (int) be;// 设置缩放比例,这个数字越大,图片大小越小.
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath.getAbsolutePath(), op);
        int desWidth = (int) (w / be);
        int desHeight = (int) (h / be);
        Log.e("大小", "丢了个雷姆" + desHeight+"  "+desWidth);

        bitmap = Bitmap.createScaledBitmap(bitmap, desWidth, desHeight, true);
        try {
            fos = new FileOutputStream(desPath);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取指定文件大小
     */
    public static float getFileSize(File file) throws Exception {
        float size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

}
