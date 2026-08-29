package com.vehicle.information.trending.rtoexam.rto.Task_utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Task_PreferenceClass {

    private final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private final SharedPreferences prefs;

    public Task_PreferenceClass(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isFirstTimeLaunch() {
        return prefs.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        prefs.edit().putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime).apply();
    }

    public void setDataType(String type, String value) {
        prefs.edit().putString(type, value).apply();
    }

    public String getDataType(String type) {
        return prefs.getString(type, "");
    }

    public String getDataType(String type, String defaultValue) {
        return prefs.getString(type, defaultValue);
    }

    public void setAdsId(String type, String value) {
        prefs.edit().putString(type, value).apply();
    }

    public String getAdsId(String type) {
        if (prefs != null) {
            String val = prefs.getString(type, "");
            if (val != null && !val.trim().isEmpty()) {
                return val;
            }
        }
        // Fallback official Google AdMob test IDs
        if ("GoogleBannerAd".equals(type) || "AdxBannerAdunitID".equals(type)) {
            return "ca-app-pub-3940256099942544/6300978111";
        } else if ("GoogleNativeAd".equals(type) || "AdxNativeUnitID".equals(type)) {
            return "ca-app-pub-3940256099942544/2247696110";
        } else if ("GoogleInterstitialAd".equals(type) || "AdxInterstitalAdunitID".equals(type)) {
            return "ca-app-pub-3940256099942544/1033173712";
        } else if ("GoogleAppopenAd".equals(type) || "AdxAppOpenID".equals(type)) {
            return "ca-app-pub-3940256099942544/9257395921";
        } else if ("GoogleRewardedAd".equals(type) || "AdxRewardVideoUnitID".equals(type)) {
            return "ca-app-pub-3940256099942544/5224354917";
        } else if ("GoogleInterstialRewardAd".equals(type)) {
            return "ca-app-pub-3940256099942544/5354046379";
        } else if ("CollapsibleBannerID".equals(type)) {
            return "ca-app-pub-3940256099942544/9214589741";
        }
        return "";
    }

    public void setRateSubmited(String type, Boolean value) {
        prefs.edit().putBoolean(type, value).apply();
    }

    public Boolean getRateSubmited(String type) {
        if (prefs != null) {
            return prefs.getBoolean(type, false);
        }
        return false;
    }

    public void setAdsStatus(String type, int value) {
        prefs.edit().putInt(type, value).apply();
    }

    public int getAdsStatus(String type) {
        return prefs.getInt(type, 1);
    }

    public void setInt(String type, int value) {
        prefs.edit().putInt(type, value).apply();
    }

    public int getInt(String type) {
        return prefs.getInt(type, 0);
    }

    public int getInt(String type, int def) {
        return prefs.getInt(type, def);
    }

    public void setFirstDate(String firstDate) {
        prefs.edit().putString("firstDate", firstDate).apply();
    }

    public String getFirstDate() {
        return prefs.getString("firstDate", null);
    }

    public void setDecryptionType(int type) {
        prefs.edit().putInt("decryptionType", type).apply();
    }

    public int getDecryptionType() {
        return prefs.getInt("decryptionType", 0);
    }

    public void setLong(String type, long value) {
        prefs.edit().putLong(type, value).apply();
    }

    public long getLong(String type) {
        return prefs.getLong(type, 0);
    }
}