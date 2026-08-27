package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.vehicle.information.trending.rtoexam.rto.R;

import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_NativeAdUtil;
import com.vehicle.information.trending.rtoexam.rto.MyApplication;

public class Task_MainActivity extends AppCompatActivity {
    private static final String TAG = "123";

    ImageView iv_celebrity_info;
    ImageView iv_owner_details;
    ImageView iv_rc_details;
    ImageView iv_rto_exam;
    ImageView iv_rto_exam_preparation;
    ImageView iv_rto_office;
    ImageView iv_rto_symbols;
    ImageView iv_rules_rto;
    private Task_PreferenceClass taskPreferenceClass;
/*    private NativeAd mobNativeView;

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
                if (Task_MainActivity.this.mobNativeView != null) {
                    Task_MainActivity.this.mobNativeView.destroy();
                }
                Task_MainActivity.this.mobNativeView = nativeAd;
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
        setContentView(R.layout.task_activity_main);
//        NativeLoad();

        taskPreferenceClass = new Task_PreferenceClass(this);

        RelativeLayout native_banner_ad_container = findViewById(R.id.native_banner_ad_container);
        RelativeLayout rl_ad = this.findViewById(R.id.rl_ad);
        RelativeLayout rl_collapsible = this.findViewById(R.id.rl_collapsible);

        if (taskPreferenceClass.getInt("MainScreenAd") == 1){
            rl_collapsible.setVisibility(View.VISIBLE);
            findViewById(R.id.ads).setVisibility(View.GONE);
            findViewById(R.id.rlBanner).setVisibility(View.GONE);
            Task_LoadAds.loadCollapsibleBanner(this,"bottom", findViewById(R.id.CollapsibleContainer), rl_collapsible,findViewById(R.id.shimmer_view_CollapsibleContainer));
        } else if (taskPreferenceClass.getInt("MainScreenAd") == 2){
            Task_LoadAds.loadAdmobBannerAd(this, rl_ad);
            native_banner_ad_container.setVisibility(View.GONE);
            findViewById(R.id.ads).setVisibility(View.GONE);
            rl_collapsible.setVisibility(View.GONE);
        }else if (taskPreferenceClass.getInt("MainScreenAd") == 3){
            Task_NativeAdUtil.loadNativeAd(native_banner_ad_container, this);
            rl_ad.setVisibility(View.GONE);
            findViewById(R.id.rlBanner).setVisibility(View.GONE);
            rl_collapsible.setVisibility(View.GONE);
        } else {
            native_banner_ad_container.setVisibility(View.GONE);
            findViewById(R.id.ads).setVisibility(View.GONE);
            findViewById(R.id.rlBanner).setVisibility(View.GONE);
            rl_ad.setVisibility(View.GONE);
            rl_collapsible.setVisibility(View.GONE);
        }

        ((ImageView) findViewById(R.id.iv_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task_MainActivity.this.onBackPressed();
            }
        });
        this.iv_owner_details = (ImageView) findViewById(R.id.iv_owner_details);
        this.iv_rc_details = (ImageView) findViewById(R.id.iv_rc_details);
        this.iv_rto_office = (ImageView) findViewById(R.id.iv_rto_office);
        this.iv_celebrity_info = (ImageView) findViewById(R.id.iv_celebrity_info);
        this.iv_rto_symbols = (ImageView) findViewById(R.id.iv_rto_symbols);
        this.iv_rto_exam_preparation = (ImageView) findViewById(R.id.iv_rto_exam_preparation);
        this.iv_rto_exam = (ImageView) findViewById(R.id.iv_rto_exam);
        this.iv_rules_rto = (ImageView) findViewById(R.id.iv_rules_rto);
        this.iv_owner_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.showInterstitialAd(Task_MainActivity.this, () -> Task_MainActivity.this.startActivity(new Intent(Task_MainActivity.this, SearchVehicleActivity.class).putExtra("TYPE", "RC")));

//                MyApplication.showInterstitialAd(Task_MainActivity.this, () -> Task_MainActivity.this.startActivity(new Intent(Task_MainActivity.this, Task_EnterInformationActivity.class)));
        /*        AdsManager.getInstance().showInterstitialAd(Task_MainActivity.this, new AdsManager.AdCloseListener() {
                    @Override
                    public void onAdClosed() {
                        Task_MainActivity.this.startActivity(new Intent(Task_MainActivity.this, Task_EnterInformationActivity.class));
                   }
               });*/

            }
        });
        this.iv_rc_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.showInterstitialAd(Task_MainActivity.this, () -> Task_MainActivity.this.startActivity(new Intent(Task_MainActivity.this, DLActivity.class)));

            }
        });
        this.iv_rto_office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.showInterstitialAd(Task_MainActivity.this, () -> Task_MainActivity.this.startActivity(new Intent(Task_MainActivity.this, Task_OficeActivity.class)));

            }
        });
        this.iv_rto_symbols.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.showInterstitialAd(Task_MainActivity.this, () -> Task_MainActivity.this.startActivity(new Intent(Task_MainActivity.this, Task_SymbolActivity.class)));

            }
        });
        this.iv_rto_exam_preparation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.showInterstitialAd(Task_MainActivity.this, () -> Next_LanguageSelectActivity1());
            }
        });

        this.iv_rto_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.showInterstitialAd(Task_MainActivity.this, () -> Next_LanguageSelectActivity2());
            }
        });

        this.iv_celebrity_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyApplication.showInterstitialAd(Task_MainActivity.this, () -> Task_MainActivity.this.startActivity(new Intent(Task_MainActivity.this, Task_CelebrityListActivity.class))
                );
            }
        });
        this.iv_rules_rto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.showInterstitialAd(Task_MainActivity.this, () -> Next_RulesActivity()
                );
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void Next_LanguageSelectActivity1() {
        Intent intent = new Intent(Task_MainActivity.this, Task_LanguageSelectActivity.class);
        intent.putExtra("from", "from_preparation");
        Task_MainActivity.this.startActivity(intent);
    }

    private void Next_LanguageSelectActivity2() {
        Intent intent = new Intent(Task_MainActivity.this, Task_LanguageSelectActivity.class);
        intent.putExtra("from", "from_exam");
        Task_MainActivity.this.startActivity(intent);
    }

    private void Next_RulesActivity() {
        Intent intent = new Intent(Task_MainActivity.this, Task_RulesActivity.class);
        intent.putExtra("from", "from_exam");
        Task_MainActivity.this.startActivity(intent);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }


}
