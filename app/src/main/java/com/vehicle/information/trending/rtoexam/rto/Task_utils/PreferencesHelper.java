package com.vehicle.information.trending.rtoexam.rto.Task_utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class PreferencesHelper {
    public static boolean isShowSearchChallanButton() {
        return getSharedPreference().getBoolean("KEY_SHOW_SEARCH_CHALLAN_BUTTON", false);
    }

    public static boolean isClickToSeeAvailable() {
        return getSharedPreference().getBoolean("KEY_CLICK_TO_SEE", false);
    }

    private static SharedPreferences getSharedPreference() {
        return PreferenceManager.getDefaultSharedPreferences(GlobalContext.getInstance().getContext());
    }
}
