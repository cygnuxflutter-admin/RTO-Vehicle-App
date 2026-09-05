package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.vehicle.information.trending.rtoexam.rto.Task_Extra.Task_RtoUtil;
import com.vehicle.information.trending.rtoexam.rto.R;

import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_NativeAdUtil;

public class Task_EnterInformationActivity extends AllBaseActivity {

    private Button btnSearch;
    private EditText et_01;
    private EditText et_02;
    private EditText et_03;
    private EditText et_04;


    String str_01;
    String str_02;
    String str_03;
    String str_04;
    private Task_PreferenceClass taskPreferenceClass;

 /*   private NativeAd mobNativeView;

    private void NativeBinding(NativeAd nativeAd, NativeAdView adView) {
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);
    }

    public void NativeShow(final FrameLayout frameLayout) {
        AdLoader.Builder builder = new AdLoader.Builder(getApplication(), getString(R.string.AdMob_Native));

        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {

                boolean isDestroyed = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    isDestroyed = isDestroyed();
                }
                if (isDestroyed || isFinishing() || isChangingConfigurations()) {
                    nativeAd.destroy();
                    return;
                }
                if (Task_EnterInformationActivity.this.mobNativeView != null) {
                    Task_EnterInformationActivity.this.mobNativeView.destroy();
                }
                Task_EnterInformationActivity.this.mobNativeView = nativeAd;
                NativeAdView adView = (NativeAdView) getLayoutInflater().inflate(R.layout.mobnative, null);
                NativeBinding(nativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }
        });
        VideoOptions videoOptions = new VideoOptions.Builder().build();
        com.google.android.gms.ads.nativead.NativeAdOptions adOptions = new com.google.android.gms.ads.nativead.NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
        builder.withNativeAdOptions(adOptions);
        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {


            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());


    }

    public void NativeLoad() {
        NativeShow((FrameLayout) findViewById(R.id.mobadslayout));
    }*/
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.task_activity_enter_information);

       // NativeLoad();
        taskPreferenceClass = new Task_PreferenceClass(this);
        RelativeLayout native_banner_ad_container = findViewById(R.id.native_banner_ad_container);
        if (taskPreferenceClass.getInt("NativeAdShow") == 1) {
            Task_NativeAdUtil.loadNativeAd(native_banner_ad_container, this);
        } else {
            native_banner_ad_container.setVisibility(View.GONE);
            findViewById(R.id.ads).setVisibility(View.GONE);
        }

        ((ImageView) findViewById(R.id.iv_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task_EnterInformationActivity.this.onBackPressed();
            }
        });
        this.et_01 = (EditText) findViewById(R.id.et_01);
        this.et_02 = (EditText) findViewById(R.id.et_02);
        this.et_03 = (EditText) findViewById(R.id.et_03);
        this.et_04 = (EditText) findViewById(R.id.et_04);
        this.btnSearch = (Button) findViewById(R.id.btnSearch);
        this.et_01.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (Task_EnterInformationActivity.this.et_01.getText().toString().length() == 2) {
                    Task_EnterInformationActivity.this.et_02.requestFocus();
                }
            }
        });
        this.et_02.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (Task_EnterInformationActivity.this.et_02.getText().toString().length() == 2) {
                    Task_EnterInformationActivity.this.et_03.requestFocus();
                }
            }
        });
        this.et_03.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (Task_EnterInformationActivity.this.et_03.getText().toString().length() == 2) {
                    Task_EnterInformationActivity.this.et_04.requestFocus();
                }
            }
        });
        this.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Task_EnterInformationActivity.this.isNetworkConnected()) {
                    Task_EnterInformationActivity m_rtoEnterInformationActivity = Task_EnterInformationActivity.this;
                    m_rtoEnterInformationActivity.str_01 = m_rtoEnterInformationActivity.et_01.getText().toString().trim();
                    Task_EnterInformationActivity m_rtoEnterInformationActivity2 = Task_EnterInformationActivity.this;
                    m_rtoEnterInformationActivity2.str_02 = m_rtoEnterInformationActivity2.et_02.getText().toString().trim();
                    Task_EnterInformationActivity m_rtoEnterInformationActivity3 = Task_EnterInformationActivity.this;
                    m_rtoEnterInformationActivity3.str_03 = m_rtoEnterInformationActivity3.et_03.getText().toString().trim();
                    Task_EnterInformationActivity m_rtoEnterInformationActivity4 = Task_EnterInformationActivity.this;
                    m_rtoEnterInformationActivity4.str_04 = m_rtoEnterInformationActivity4.et_04.getText().toString().trim();
                    if (Task_EnterInformationActivity.this.str_01.isEmpty() || Task_EnterInformationActivity.this.str_01.length() == 0 || Task_EnterInformationActivity.this.str_01.equals("") || Task_EnterInformationActivity.this.str_01 == null) {
                        Task_EnterInformationActivity.this.et_01.setError("Please insert Value!");
                        return;
                    } else if (Task_EnterInformationActivity.this.str_02.isEmpty() || Task_EnterInformationActivity.this.str_02.length() == 0 || Task_EnterInformationActivity.this.str_02.equals("") || Task_EnterInformationActivity.this.str_02 == null) {
                        Task_EnterInformationActivity.this.et_02.setError("Please insert Value!");
                        return;
                    } else if (Task_EnterInformationActivity.this.str_03.isEmpty() || Task_EnterInformationActivity.this.str_03.length() == 0 || Task_EnterInformationActivity.this.str_03.equals("") || Task_EnterInformationActivity.this.str_03 == null) {
                        Task_EnterInformationActivity.this.et_03.setError("Please insert Value!");
                        return;
                    } else if (Task_EnterInformationActivity.this.str_04.isEmpty() || Task_EnterInformationActivity.this.str_04.length() == 0 || Task_EnterInformationActivity.this.str_04.equals("") || Task_EnterInformationActivity.this.str_04 == null) {
                        Task_EnterInformationActivity.this.et_04.setError("Please insert Value!");
                        return;
                    } else {
                        Task_EnterInformationActivity m_rtoEnterInformationActivity5 = Task_EnterInformationActivity.this;
                        m_rtoEnterInformationActivity5.btnSearchVehicleDetailsClickListener("" + Task_EnterInformationActivity.this.et_01.getText().toString() + Task_EnterInformationActivity.this.et_02.getText().toString() + Task_EnterInformationActivity.this.et_03.getText().toString() + Task_EnterInformationActivity.this.et_04.getText().toString());
                        return;
                    }
                }
                Toast.makeText(Task_EnterInformationActivity.this.getApplicationContext(), "Please Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btnSearchVehicleDetailsClickListener(String str) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(this.btnSearch.getWindowToken(), 0);
        }
        if (!isNetworkConnected()) {
            Toast.makeText(this, "You are not connected to internet!", Toast.LENGTH_SHORT).show();
            return;
        }
        String formatString = Task_RtoUtil.formatString(str);
        if (Task_RtoUtil.isNullOrEmpty(formatString) || formatString.length() <= 6) {
            Toast.makeText(this, "Please enter the correct vehicle no!", Toast.LENGTH_SHORT).show();
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("VEHICLE_NO", formatString);
        bundle.putString("content_type", "BUTTON");
        Intent intent = new Intent(this, Task_SearchVehicleDetailActivity.class);
        intent.putExtra("REGISTRATION_NO", formatString);
        intent.putExtra("ACTION", "SAVE");
        startActivity(intent);
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }



    public boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
