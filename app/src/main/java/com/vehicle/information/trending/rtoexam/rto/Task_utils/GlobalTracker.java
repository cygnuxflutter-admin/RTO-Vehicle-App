package com.vehicle.information.trending.rtoexam.rto.Task_utils;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;


public class GlobalTracker {
    public static final String BIKE = "BIKE";
    public static final String BIKE_BRAND = "BIKE_BRAND";
    public static final String BIKE_MODEL = "BIKE_MODEL";
    public static final String BIKE_PRICE_RANGE = "BIKE_PRICE_RANGE";
    public static final String BIKE_VARIANT = "BIKE_VARIANT";
    public static final String BUTTON = "BUTTON";
    public static final String BUTTON_BIKE_BRANDS = "BIKE_BRANDS";
    public static final String BUTTON_CAR_BRANDS = "CAR_BRANDS";
    public static final String BUTTON_COMPARE_BIKES = "COMPARE_BIKES";
    public static final String BUTTON_COMPARE_CARS = "COMPARE_CARS";
    public static final String BUTTON_CONTACT_US = "CONTACT_US";
    public static final String BUTTON_FAVOURITES = "FAVOURITES";
    public static final String BUTTON_MORE_APPS = "MORE_APPS";
    public static final String BUTTON_NEW_FEATURES = "NEW_FEATURES";
    public static final String BUTTON_PLAY_GAMES = "PLAY_GAMES";
    public static final String BUTTON_PROFILE = "PROFILE";
    public static final String BUTTON_RATE_APP = "RATE_APP";
    public static final String BUTTON_RATE_APP_NO = "RATE_APP_NO";
    public static final String BUTTON_RATE_APP_YES = "RATE_APP_YES";
    public static final String BUTTON_REPORT_INACCURACY = "REPORT_INACCURACY";
    public static final String BUTTON_RESALE_VALUE_CALCULATOR = "RESALE_VALUE_CALCULATOR";
    public static final String BUTTON_RTO_OFFICES = "RTO_OFFICES";
    public static final String BUTTON_SEARCH_CHALLAN = "SEARCH_CHALLAN";
    public static final String BUTTON_SEARCH_LICENSE = "SEARCH_LICENSE";
    public static final String BUTTON_SEARCH_RC = "SEARCH_RC";
    public static final String BUTTON_SHARE_APP = "SHARE_APP";
    public static final String BUTTON_SHARE_BIKE_DEALERS = "SHARE_BIKE_DEALERS";
    public static final String BUTTON_SHARE_BIKE_MODEL = "SHARE_BIKE_MODEL";
    public static final String BUTTON_SHARE_CAR_DEALERS = "SHARE_CAR_DEALERS";
    public static final String BUTTON_SHARE_CAR_MODEL = "SHARE_CAR_MODEL";
    public static final String BUTTON_SHARE_CHALLAN_DETAILS = "SHARE_CHALLAN_DETAILS";
    public static final String BUTTON_SHARE_DRIVING_SCHOOLS = "SHARE_DRIVING_SCHOOLS";
    public static final String BUTTON_SHARE_FINANCE_DETAILS = "SHARE_FINANCE_DETAILS";
    public static final String BUTTON_SHARE_INSURANCE_DETAILS = "SHARE_INSURANCE_DETAILS";
    public static final String BUTTON_SHARE_LICENSE_DETAILS = "SHARE_LICENSE_DETAILS";
    public static final String BUTTON_SHARE_VEHICLE_DETAILS = "SHARE_VEHICLE_DETAILS";
    public static final String BUTTON_START_APP = "START_APP";
    public static final String BUTTON_TRENDING_PERSONS = "TRENDING_PERSONS";
    public static final String CAR = "CAR";
    public static final String CAR_BRAND = "CAR_BRAND";
    public static final String CAR_MODEL = "CAR_MODEL";
    public static final String CAR_PRICE_RANGE = "CAR_PRICE_RANGE";
    public static final String CAR_VARIANT = "CAR_VARIANT";
    public static final String EVENT_CHALLAN_NO = "CHALLAN_NO";
    public static final String EVENT_DL_NO = "DL_NO";
    public static final String EVENT_DOB = "DOB";
    public static final String EVENT_JSON_RESPONSE = "JSON_RESPONSE";
    public static final String EVENT_LOG_LICENSE_DETAILS = "EVENT_LOG_LICENSE_DETAILS";
    public static final String EVENT_LOG_VEHICLE_DETAILS = "EVENT_LOG_VEHICLE_DETAILS";
    public static final String EVENT_VEHICLE_DETAILS_RESPONSE_MESSAGE = "VEHICLE_DETAILS_RESPONSE_MESSAGE";
    public static final String EVENT_VEHICLE_DETAILS_RESPONSE_STATUS = "VEHICLE_DETAILS_RESPONSE_STATUS";
    public static final String EVENT_VEHICLE_NO = "VEHICLE_NO";
    private static final String TAG = "GlobalTracker";

    
    public interface Provider {
        GlobalTracker getTracker();
    }

    public static GlobalTracker from(Context context) {
        return ((Provider) context.getApplicationContext()).getTracker();
    }

    public static GlobalTracker from(Fragment fragment) {
        return from(fragment.getActivity());
    }

    public void sendSelectButtonEvent(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("item_name", str);
        bundle.putString("content_type", BUTTON);
    }
}
