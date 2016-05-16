package com.sfj.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/5/10.
 */
public class MD5Utils {

    private static final String last = "ydwl89020001";
    private static final String date = TimeUtils.getYmd();


    public static String getMD5(String value) throws NoSuchAlgorithmException {
        value = value+date+last;
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(value.getBytes());
        byte[] m = md5.digest();
        return new String(m);
    }
}
