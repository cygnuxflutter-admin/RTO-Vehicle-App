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
    private AlertDialog mInternetDialog;

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

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(getLayoutInflater().inflate(R.layout.aa_dialog_no_internet, (ViewGroup) null));
            mInternetDialog = builder.create();
            mInternetDialog.setCancelable(false);
            if (mInternetDialog.getWindow() != null) {
                mInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
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
    protected void onResume() {
        super.onResume();
        
        try {
            // Check immediately on resume
            if (!getConnectivityStatus(this)) {
                if (mInternetDialog != null && !mInternetDialog.isShowing() && !isFinishing()) {
                    mInternetDialog.show();
                }
            } else {
                if (mInternetDialog != null && mInternetDialog.isShowing()) {
                    mInternetDialog.dismiss();
                }
            }

            BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
                @Override 
                public void onReceive(Context context, Intent intent) {
                    try {
                        if (AllBaseActivity.getConnectivityStatus(context)) {
                            if (mInternetDialog != null && mInternetDialog.isShowing()) {
                                mInternetDialog.dismiss();
                            }
                        } else if (mInternetDialog != null && !mInternetDialog.isShowing() && !AllBaseActivity.this.isFinishing()) {
                            mInternetDialog.show();
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

    public void Go_ad_Page(Intent intent) {
        startActivity(intent);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (mInternetDialog != null && mInternetDialog.isShowing()) {
            try {
                mInternetDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
