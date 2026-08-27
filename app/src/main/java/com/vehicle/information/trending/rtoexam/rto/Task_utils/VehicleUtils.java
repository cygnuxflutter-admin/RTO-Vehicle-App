package com.vehicle.information.trending.rtoexam.rto.Task_utils;

import android.app.Activity;
import android.widget.Toast;


public class VehicleUtils {
    public static final String BUNDLE_REG_LIST = "reg";
    public static final String CONNECT = "Please check internet connection.";
    public static final String REG_NOT_EXIT = "Registration No. does not exist!!!";
    public static final int SERVER_TIMEOUT = 120000;
    public static final String[] VEHICLE_TITLE = {"Registration No.", "Chassis No.", "Engine No.", "FitUpto", "Fuel Type", "Insurance Upto", "Maker Name", "Owner Name", "Registration Date", "Rto Name", "Vehicle Class", "Fuel Norms"};
    public static final String SUB_URL = "https://parivahan.gov.in/rcdlstatus/vahan/rcDlHome.xhtml";
    public static String local_source_final_url = SUB_URL;
    public static final String BASE_URL = "https://parivahan.gov.in/rcdlstatus/?pur_cd=102";
    public static String local_source_init_url = BASE_URL;

    public static void showSnake(String str, Activity activity) {
        Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
    }
}
