package com.slowlife.lib;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sgrape on 2017/5/3.
 */

public class MD5 {
    public static String md5(String str) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密密码
     *
     * @param pwd
     * @return
     */
    public static String md5Pwd(String pwd) {
        String md532 = md5(pwd);
        return md5(md532 + md532);
    }
}
