package com.vehicle.information.trending.rtoexam.rto.Task_Extra;

import android.util.Base64;

import androidx.exifinterface.media.ExifInterface;


import com.vehicle.information.trending.rtoexam.rto.Task_utils.GlobalReferenceEngine;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class EncryptionHandler {
    private static String generateMD5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            byte[] digest = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                String hexString = Integer.toHexString(b & (-1));
                while (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String encrypt(String str) {
        return encrypt(str, GlobalReferenceEngine.dataAccessKey);
    }

    public static String encrypt(String str, String str2) {
        byte[] bArr = new byte[0];
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(1, secretKeySpec);
            bArr = cipher.doFinal(str.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(bArr, 2);
    }

    public static String decrypt(String str, String str2) {
        byte[] bArr = new byte[0];
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), ExifInterface.TAG_FLASH);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(2, secretKeySpec);
            bArr = cipher.doFinal(Base64.decode(str, 2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(bArr);
    }
}
