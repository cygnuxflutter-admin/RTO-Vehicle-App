package com.vehicle.information.trending.rtoexam.rto.Task_utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;

import com.vehicle.information.trending.rtoexam.rto.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Utils {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNullOrEmptyOrNA(String str) {
        return str == null || str.isEmpty() || str.toLowerCase().equalsIgnoreCase("na") || str.toLowerCase().equalsIgnoreCase("n/a") || str.toLowerCase().equalsIgnoreCase("not available") || str.toLowerCase().equalsIgnoreCase("null") || str.toLowerCase().equalsIgnoreCase("0");
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        try {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.isConnected()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String formatString(String str) {
        return isNullOrEmpty(str) ? "" : str.replaceAll("[^A-Za-z0-9]", "");
    }

    private static File getFile(File file, String str) {
        File file2 = new File(file, "images");
        if (!file2.exists()) {
            boolean mkdirs = file2.mkdirs();
            Log.i("Utils", "Folder created: " + mkdirs);
        }
        return new File(file2, str);
    }

    public static void shareTo3rdPartyApps(Activity activity) {
        Bitmap decodeResource = BitmapFactory.decodeResource(activity.getResources(), activity.getResources().getIdentifier("app_share_image", "drawable", activity.getPackageName()));
        File cacheDir = activity.getCacheDir();
        File file = getFile(cacheDir, System.currentTimeMillis() + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            decodeResource.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Context applicationContext = activity.getApplicationContext();
            Uri uriForFile = FileProvider.getUriForFile(applicationContext, activity.getPackageName() + ".provider", file);
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("android.intent.extra.SUBJECT", activity.getString(R.string.share_app_subject));
            intent.putExtra("android.intent.extra.TEXT", activity.getString(R.string.share_app_text));
            intent.putExtra("android.intent.extra.STREAM", uriForFile);
            intent.setType("image/jpeg");
            activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.send_to)));
        } catch (Exception e) {
            Log.e(activity.getClass().getSimpleName(), "Error writing bitmap", e);
        }
    }

    public static Date formatDateByPattern(String str, String str2) {
        if (str2 == null || str2.contentEquals("")) {
            return new Date();
        }
        try {
            return new SimpleDateFormat(str, Locale.US).parse(str2);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getVehicleAge(String str) {
        Date formatDateByPattern;
        String concat;
        String concat2;
        if (isNullOrEmpty(str) || (formatDateByPattern = formatDateByPattern("dd-MMM-yyyy", str)) == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatDateByPattern);
        Calendar calendar2 = Calendar.getInstance();
        int i = calendar2.get(1) - calendar.get(1);
        int i2 = calendar2.get(2) + 1;
        int i3 = calendar.get(2) + 1;
        int i4 = i2 - i3;
        if (i4 < 0) {
            i--;
            i4 = (12 - i3) + i2;
            if (calendar2.get(5) < calendar.get(5)) {
                i4--;
            }
        } else if (i4 == 0 && calendar2.get(5) < calendar.get(5)) {
            i--;
            i4 = 11;
        }
        int i5 = 0;
        if (calendar2.get(5) > calendar.get(5)) {
            i5 = calendar2.get(5) - calendar.get(5);
        } else if (calendar2.get(5) < calendar.get(5)) {
            int i6 = calendar2.get(5);
            calendar2.add(2, -1);
            i5 = (calendar2.getActualMaximum(5) - calendar.get(5)) + i6;
        } else if (i4 == 12) {
            i++;
            i4 = 0;
        }
        if (i <= 1) {
            concat = "".concat(String.valueOf(i)).concat(" Year ");
        } else {
            concat = "".concat(String.valueOf(i)).concat(" Years ");
        }
        if (i4 <= 1) {
            concat2 = concat.concat(String.valueOf(i4)).concat(" Month ");
        } else {
            concat2 = concat.concat(String.valueOf(i4)).concat(" Months ");
        }
        if (i5 <= 1) {
            return concat2.concat(String.valueOf(i5)).concat(" Day");
        }
        return concat2.concat(String.valueOf(i5)).concat(" Days");
    }

    public static String getInsuranceAge(String str) {
        Date formatDateByPattern;
        String concat;
        String concat2;
        if (isNullOrEmpty(str) || (formatDateByPattern = formatDateByPattern("dd-MMM-yyyy", str)) == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatDateByPattern);
        Calendar calendar2 = Calendar.getInstance();
        if (calendar2.get(1) > calendar.get(1) || calendar2.get(2) + 1 > calendar.get(2) + 1) {
            return "EXPIRED";
        }
        int i = calendar.get(1) - calendar2.get(1);
        int i2 = calendar.get(2) + 1;
        int i3 = calendar2.get(2) + 1;
        int i4 = i2 - i3;
        if (i4 < 0) {
            i--;
            i4 = (12 - i3) + i2;
            if (calendar2.get(5) < calendar.get(5)) {
                i4--;
            }
        } else if (i4 == 0 && calendar2.get(5) < calendar.get(5)) {
            i--;
            i4 = 11;
        }
        int i5 = 0;
        if (calendar2.get(5) > calendar.get(5)) {
            i5 = calendar2.get(5) - calendar.get(5);
        } else if (calendar2.get(5) < calendar.get(5)) {
            int i6 = calendar2.get(5);
            calendar2.add(2, -1);
            i5 = (calendar2.getActualMaximum(5) - calendar.get(5)) + i6;
        } else if (i4 == 12) {
            i++;
            i4 = 0;
        }
        if (i <= 1) {
            concat = "".concat(String.valueOf(i)).concat(" Year ");
        } else {
            concat = "".concat(String.valueOf(i)).concat(" Years ");
        }
        if (i4 <= 1) {
            concat2 = concat.concat(String.valueOf(i4)).concat(" Month ");
        } else {
            concat2 = concat.concat(String.valueOf(i4)).concat(" Months ");
        }
        if (i5 <= 1) {
            return concat2.concat(String.valueOf(i5)).concat(" Day");
        }
        return concat2.concat(String.valueOf(i5)).concat(" Days");
    }

    public static boolean isActivityFinished(Context context) {
        if (!(context instanceof Activity)) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 17) {
            return !((Activity) context).isFinishing();
        }
        Activity activity = (Activity) context;
        return !activity.isFinishing() && !activity.isDestroyed();
    }

    public static String[] splitRegistrationNo(String str) {
        String[] strArr = new String[2];
        try {
            strArr[0] = str.split("\\d*$")[0];
            strArr[1] = str.replace(strArr[0], "");
            if (isNullOrEmpty(strArr[1])) {
                strArr[1] = "0";
            }
        } catch (Exception e) {
            strArr[0] = str;
            strArr[1] = "0";
        }
        return strArr;
    }

    public static String getSearchTypeByNo(String str) {
        return isNullOrEmpty(str) ? "" : str.length() <= 11 ? "RC" : "DL";
    }

    public static String extractWarningMessage(String str, String str2) {
        if (isNullOrEmpty(str)) {
            return "";
        }
        try {
            String substring = str.substring(str.indexOf("showMessageInDialog"));
            if (isNullOrEmpty(substring) || !substring.contains(",")) {
                return "";
            }
            String[] split = substring.split("\",");
            if (split.length < 3) {
                return "";
            }
            String[] split2 = split[2].split("\"");
            return split2.length < 2 ? "" : split2[1].trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while fetching " + str2 + " details, please try again!";
        }
    }

    public static String getOwnershipString(String str) {
        if (isNullOrEmpty(str) || str.equalsIgnoreCase("1")) {
            return "FIRST OWNER";
        }
        if (str.equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D)) {
            return "SECOND OWNER";
        }
        if (str.equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_3D)) {
            return "THIRD OWNER";
        }
        if (str.equalsIgnoreCase("4")) {
            return "FOURTH OWNER";
        }
        if (str.equalsIgnoreCase("5")) {
            return "FIFTH OWNER";
        }
        if (str.equalsIgnoreCase("6")) {
            return "SIXTH OWNER";
        }
        if (str.equalsIgnoreCase("7")) {
            return "SEVENTH OWNER";
        }
        if (str.equalsIgnoreCase("8")) {
            return "EIGHTH OWNER";
        }
        if (str.equalsIgnoreCase("9")) {
            return "NINTH OWNER";
        }
        if (str.equalsIgnoreCase("10")) {
            return "TENTH OWNER";
        }
        if (str.equalsIgnoreCase("11")) {
            return "ELEVENTH OWNER";
        }
        if (str.equalsIgnoreCase("12")) {
            return "TWELFTH OWNER";
        }
        if (str.equalsIgnoreCase("13")) {
            return "THIRTEENTH OWNER";
        }
        if (str.equalsIgnoreCase("14")) {
            return "FOUTEENTH OWNER";
        }
        if (str.equalsIgnoreCase("15")) {
            return "FIFTEENTH OWNER";
        }
        return "FIRST OWNER";
    }

    public static String hideString(String str) {
        return isNullOrEmpty(str) ? "" : str.replaceAll("\\w", "X");
    }
}
