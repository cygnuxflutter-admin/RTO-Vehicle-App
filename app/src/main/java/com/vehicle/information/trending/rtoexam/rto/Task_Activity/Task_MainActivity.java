package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.vehicle.information.trending.rtoexam.rto.MyApplication;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_NativeAdUtil;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

public class Task_MainActivity extends AppCompatActivity {
    private static final String TAG = "Task_MainActivity";

    private Task_PreferenceClass taskPreferenceClass;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.task_activity_main);

                taskPreferenceClass = new Task_PreferenceClass(this);

        RelativeLayout native_banner_ad_container = findViewById(R.id.native_banner_ad_container);
        RelativeLayout rl_ad = findViewById(R.id.rl_ad);
        RelativeLayout rl_collapsible = findViewById(R.id.rl_collapsible);
        View adsView = findViewById(R.id.ads);
        View rlBanner = findViewById(R.id.rlBanner);

        int mainAdPref = taskPreferenceClass.getInt("MainScreenAd");
        if (mainAdPref == 1) {
            if (rl_collapsible != null) rl_collapsible.setVisibility(View.VISIBLE);
            if (adsView != null) adsView.setVisibility(View.GONE);
            if (rlBanner != null) rlBanner.setVisibility(View.GONE);
            Task_LoadAds.loadCollapsibleBanner(this, "bottom", findViewById(R.id.CollapsibleContainer), rl_collapsible, findViewById(R.id.shimmer_view_CollapsibleContainer));
        } else if (mainAdPref == 2) {
            if (rl_ad != null) Task_LoadAds.loadAdmobBannerAd(this, rl_ad);
            if (native_banner_ad_container != null) native_banner_ad_container.setVisibility(View.GONE);
            if (adsView != null) adsView.setVisibility(View.GONE);
            if (rl_collapsible != null) rl_collapsible.setVisibility(View.GONE);
        } else if (mainAdPref == 3) {
            if (native_banner_ad_container != null) Task_NativeAdUtil.loadNativeAd(native_banner_ad_container, this);
            if (rl_ad != null) rl_ad.setVisibility(View.GONE);
            if (rlBanner != null) rlBanner.setVisibility(View.GONE);
            if (rl_collapsible != null) rl_collapsible.setVisibility(View.GONE);
        } else {
            if (native_banner_ad_container != null) native_banner_ad_container.setVisibility(View.GONE);
            if (adsView != null) adsView.setVisibility(View.GONE);
            if (rl_collapsible != null) rl_collapsible.setVisibility(View.GONE);
            if (rl_ad != null) {
                Task_LoadAds.loadAdmobBannerAd(this, rl_ad);
            }
        }

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        // 1. RTO Office
        View cardOffice = findViewById(R.id.card_rto_office);
        if (cardOffice != null) {
            cardOffice.setOnClickListener(v ->
                    MyApplication.showInterstitialAd(Task_MainActivity.this, () ->
                            Task_MainActivity.this.startActivity(new Intent(Task_MainActivity.this, Task_OficeActivity.class))
                    )
            );
        }

        // 2. Symbols / Traffic Signs
        View cardSymbols = findViewById(R.id.card_rto_symbols);
        if (cardSymbols != null) {
            cardSymbols.setOnClickListener(v ->
                    MyApplication.showInterstitialAd(Task_MainActivity.this, () ->
                            Task_MainActivity.this.startActivity(new Intent(Task_MainActivity.this, Task_SymbolActivity.class))
                    )
            );
        }

        // 3. Exam Preparation Bank
        View cardExamPrep = findViewById(R.id.card_rto_exam_prep);
        if (cardExamPrep != null) {
            cardExamPrep.setOnClickListener(v ->
                    MyApplication.showInterstitialAd(Task_MainActivity.this, this::Next_LanguageSelectActivity1)
            );
        }

        // 4. RTO Mock Exam
        View cardExam = findViewById(R.id.card_rto_exam);
        if (cardExam != null) {
            cardExam.setOnClickListener(v ->
                    MyApplication.showInterstitialAd(Task_MainActivity.this, this::Next_LanguageSelectActivity2)
            );
        }

        // 5. RTO Rules & Forms
        View cardRules = findViewById(R.id.card_rules_rto);
        if (cardRules != null) {
            cardRules.setOnClickListener(v ->
                    MyApplication.showInterstitialAd(Task_MainActivity.this, this::Next_RulesActivity)
            );
        }

        // 6. e-Challan Official Status
        View cardChallan = findViewById(R.id.card_challan_status);
        if (cardChallan != null) {
            cardChallan.setOnClickListener(v ->
                    MyApplication.showInterstitialAd(Task_MainActivity.this, () ->
                            Task_MainActivity.this.startActivity(new Intent(Task_MainActivity.this, Task_ChallanSearchActivity.class))
                    )
            );
        }

        // 7. Vehicle Loan & EMI Calculator
        View cardLoanEmi = findViewById(R.id.card_loan_emi);
        if (cardLoanEmi != null) {
            cardLoanEmi.setOnClickListener(v ->
                    MyApplication.showInterstitialAd(Task_MainActivity.this, () ->
                            Task_MainActivity.this.startActivity(new Intent(Task_MainActivity.this, Task_LoanEmiActivity.class))
                    )
            );
        }

        // 8. Mileage & Trip Cost Calculator
        View cardMileage = findViewById(R.id.card_mileage_calc);
        if (cardMileage != null) {
            cardMileage.setOnClickListener(v ->
                    MyApplication.showInterstitialAd(Task_MainActivity.this, () ->
                            Task_MainActivity.this.startActivity(new Intent(Task_MainActivity.this, Task_MileageCalculatorActivity.class))
                    )
            );
        }

        // 9. Resale Value Estimator
        View cardResale = findViewById(R.id.card_resale_calc);
        if (cardResale != null) {
            cardResale.setOnClickListener(v ->
                    MyApplication.showInterstitialAd(Task_MainActivity.this, () ->
                            Task_MainActivity.this.startActivity(new Intent(Task_MainActivity.this, Task_ResaleEstimatorActivity.class))
                    )
            );
        }

        // 10. RTO Forms Hub
        View cardFormsHub = findViewById(R.id.card_forms_hub);
        if (cardFormsHub != null) {
            cardFormsHub.setOnClickListener(v ->
                    MyApplication.showInterstitialAd(Task_MainActivity.this, () ->
                            Task_MainActivity.this.startActivity(new Intent(Task_MainActivity.this, Task_FormsHubActivity.class))
                    )
            );
        }

        // 11. PUC & Insurance Expiry Reminder (Document Vault)
        View cardExpiry = findViewById(R.id.card_expiry_reminder);
        if (cardExpiry != null) {
            cardExpiry.setOnClickListener(v ->
                    MyApplication.showInterstitialAd(Task_MainActivity.this, () ->
                            Task_MainActivity.this.startActivity(new Intent(Task_MainActivity.this, Task_ExpiryReminderActivity.class))
                    )
            );
        }
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
}