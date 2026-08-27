package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.vehicle.information.trending.rtoexam.rto.R;




public class AllBaseActivity extends AppCompatActivity {
    private static final String TAG = "AllBaseActivity";
    BroadcastReceiver h;
    boolean i = false;

    public static boolean getConnectivityStatus(Context context) {
        if (context == null) return true;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) return true;
            NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception e) {
            return true;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (this.i && this.h != null) {
            try {
                this.i = false;
                unregisterReceiver(this.h);
            } catch (Exception e) {
                Log.e(TAG, "onPause: unregisterReceiver = " + e);
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(getLayoutInflater().inflate(R.layout.aa_dialog_no_internet, (ViewGroup) null));
            final AlertDialog create = builder.create();
            create.setCancelable(false);
            if (create.getWindow() != null) {
                create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.vehicle.information.trending.rtoexam.rto.Activity.AllBaseActivity.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    try {
                        if (AllBaseActivity.getConnectivityStatus(context)) {
                            if (create != null && create.isShowing()) {
                                create.dismiss();
                            }
                        } else if (create != null && !create.isShowing() && !AllBaseActivity.this.isFinishing()) {
                            create.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            this.h = broadcastReceiver;
            this.i = true;
            if (Build.VERSION.SDK_INT >= 33) {
                registerReceiver(broadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"), Context.RECEIVER_EXPORTED);
            } else {
                registerReceiver(broadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void Go_ad_Page(Intent intent) {
        startActivity(intent);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        if (this.i && this.h != null) {
            try {
                this.i = false;
                unregisterReceiver(this.h);
            } catch (Exception e) {
                Log.e(TAG, "onDestroy: unregisterReceiver = " + e);
            }
        }
    }
}
