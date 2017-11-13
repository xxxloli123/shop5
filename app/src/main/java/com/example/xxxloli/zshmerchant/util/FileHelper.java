package com.example.xxxloli.zshmerchant.util;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/10/23.
 * 根据Uri获取文件的绝对路径 http://www.cnblogs.com/baiqiantao/p/6795684.html
 * 通过URI获取的文件路径为null的解决方法 http://blog.csdn.net/dj0379/article/details/50765021
 * ACTION_GET_CONTENT和ACTION_PICK的区别 http://blog.csdn.net/chenfuduo_loveit/article/details/43051467
 */

public class FileHelper {
    private static String UNKNOWN = "unknown";
    public static String toImagePath(Context context, Uri uri){
        String path = UNKNOWN;
        if(null != uri){
            final String scheme = uri.getScheme();
            if(null == scheme || ContentResolver.SCHEME_FILE.equals(scheme))
                path = uri.getPath();
            else if(ContentResolver.SCHEME_CONTENT.equals(scheme)){
                Cursor cursor = context.getContentResolver().query(uri,new String[]{MediaStore.Images.ImageColumns.DATA},null,null,null);
                if(null != cursor)
                    if(cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        if(-1 < index)
                            path = cursor.getString(index);
                    }
                    cursor.close();
            }
        }
        Log.i("FileHelper","filePath->"+path);
        return path;
    }
    public static String getImagePath(Context context,Uri uri){
        String path;
        Cursor cursor = context.getContentResolver().query(uri,new String[]{MediaStore.Images.ImageColumns.DATA},null,null,null);
        if(null != cursor){
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
            cursor.close();
        }else
            path = uri.getPath();
        Log.i("FileHelper","filePath->"+path);
        return path;
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static String getTall18Path(Context context, Uri uri){
        String path = UNKNOWN;
        String wholeId = DocumentsContract.getDocumentId(uri);
        String id = wholeId.split(":")[1];
        String[] uriStr = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,uriStr,MediaStore.Images.Media._ID+"=?",new String[]{id},null);
        int columnIndex = cursor.getColumnIndex(uriStr[0]);
        if(cursor.moveToFirst())
            path = cursor.getString(columnIndex);
        cursor.close();
        return path;
    }
    private static String getBelow18Path(Context context,Uri uri){//适配11<= api <= 18
        String path = UNKNOWN;
        CursorLoader loader = new CursorLoader(context,uri,new String[]{MediaStore.Images.Media.DATA},null,null,null);
        Cursor cursor = loader.loadInBackground();
        if(null != cursor){
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
        }
        return path;
    }
    private static String getBelow11Path(Context context,Uri uri){//适配api < 11
        String path = UNKNOWN;
        Cursor cursor = context.getContentResolver().query(uri,new String[]{MediaStore.Images.Media.DATA},null,null,null);
        if(null != cursor){
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
        }
        return path;
    }
    /*
    * Android压缩图片后再上传图片 http://blog.csdn.net/dzsw0117/article/details/51492172
    * @param quality  Hint to the compressor, 0-100. 0 meaning compress for
    *                 small size, 100 meaning compress for max quality. Some
    *                 formats, like PNG which is lossless, will ignore the
    *                 quality setting
    * */
    public static byte[] compressFile(String path,int quality){
        Bitmap bitmap = getSmallBitmap(path);
        int degree = readPictureDegree(path);
        if(0 != degree)
            bitmap = rotateBitmap(bitmap,degree);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,quality,baos);
        return baos.toByteArray();
    }
    private static Bitmap getSmallBitmap(String path){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(path,options);
        options.inSampleSize = calculateInSamppleSize(options,320,640);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path,options);
    }
    private static int calculateInSamppleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){//计算缩放比利
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;
        if(reqWidth < width || reqHeight < height){
            final int widthRatio = Math.round((float)width/(float)reqWidth);
            final int heightRatio = Math.round((float)height/(float)reqHeight);
            inSampleSize = widthRatio < heightRatio? widthRatio : heightRatio;
        }
        return inSampleSize;
    }
    private static int readPictureDegree(String path){//获取图片角度
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
            switch (orientation){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
    private static Bitmap rotateBitmap(Bitmap bitmap,int degree){//旋转图片
        if(null != bitmap){
            Matrix matrix = new Matrix();
            matrix.postRotate(degree);
            bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            return bitmap;
        }
        return bitmap;
    }
}
