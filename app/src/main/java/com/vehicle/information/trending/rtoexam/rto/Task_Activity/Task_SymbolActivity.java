package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vehicle.information.trending.rtoexam.rto.MyApplication;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

public class Task_SymbolActivity extends AppCompatActivity {

    private String str_language = "gujarati";
    private TextView tvHeaderTitle;
    private TextView tabGujarati, tabHindi, tabEnglish;
    private TextView tvMandatoryTitle, tvMandatorySub;
    private TextView tvCautionaryTitle, tvCautionarySub;
    private TextView tvInformatoryTitle, tvInformatorySub;
    private TextView tvRoadSignalsTitle, tvRoadSignalsSub;
    private TextView tvDrivingRulesTitle, tvDrivingRulesSub;
    private TextView tvTrafficPoliceTitle, tvTrafficPoliceSub;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_symbol);

        String langExtra = getIntent().getStringExtra("language");
        if (langExtra != null && !langExtra.isEmpty()) {
            this.str_language = langExtra;
        }

        RelativeLayout rl_ad = this.findViewById(R.id.rl_ad);
        if (Task_NetworkUtils.isNetworkAvailable(this)) {
            Task_PreferenceClass taskPreferenceClass = new Task_PreferenceClass(this);
            if (taskPreferenceClass.getInt("BannerAdShow") == 1) {
                Task_LoadAds.loadAdmobBannerAd(this, rl_ad);
            } else {
                if (rl_ad != null) rl_ad.setVisibility(View.GONE);
                View rlBanner = findViewById(R.id.rlBanner);
                if (rlBanner != null) rlBanner.setVisibility(View.GONE);
            }
        }

        ImageView ivBack = findViewById(R.id.iv_back);
        if (ivBack != null) {
            ivBack.setOnClickListener(view -> onBackPressed());
        }

        tvHeaderTitle = findViewById(R.id.tv_header_title);
        tabGujarati = findViewById(R.id.tab_gujarati);
        tabHindi = findViewById(R.id.tab_hindi);
        tabEnglish = findViewById(R.id.tab_english);

        tvMandatoryTitle = findViewById(R.id.tv_mandatory_title);
        tvMandatorySub = findViewById(R.id.tv_mandatory_sub);
        tvCautionaryTitle = findViewById(R.id.tv_cautionary_title);
        tvCautionarySub = findViewById(R.id.tv_cautionary_sub);
        tvInformatoryTitle = findViewById(R.id.tv_informatory_title);
        tvInformatorySub = findViewById(R.id.tv_informatory_sub);
        tvRoadSignalsTitle = findViewById(R.id.tv_roadsignals_title);
        tvRoadSignalsSub = findViewById(R.id.tv_roadsignals_sub);
        tvDrivingRulesTitle = findViewById(R.id.tv_drivingrules_title);
        tvDrivingRulesSub = findViewById(R.id.tv_drivingrules_sub);
        tvTrafficPoliceTitle = findViewById(R.id.tv_trafficpolice_title);
        tvTrafficPoliceSub = findViewById(R.id.tv_trafficpolice_sub);

        if (tabGujarati != null) {
            tabGujarati.setOnClickListener(v -> setLanguage("gujarati"));
        }
        if (tabHindi != null) {
            tabHindi.setOnClickListener(v -> setLanguage("hindi"));
        }
        if (tabEnglish != null) {
            tabEnglish.setOnClickListener(v -> setLanguage("english"));
        }

        updateUiLanguage();

        View cvMandatory = findViewById(R.id.cv_mandatory);
        if (cvMandatory != null) {
            cvMandatory.setOnClickListener(view -> openDetail("Mandatory"));
        }

        View cvCautionary = findViewById(R.id.cv_cautionary);
        if (cvCautionary != null) {
            cvCautionary.setOnClickListener(view -> openDetail("Cautionary"));
        }

        View cvInformatory = findViewById(R.id.cv_informatory);
        if (cvInformatory != null) {
            cvInformatory.setOnClickListener(view -> openDetail("Informatory"));
        }

        View cvRoadSignals = findViewById(R.id.cv_roadsignals);
        if (cvRoadSignals != null) {
            cvRoadSignals.setOnClickListener(view -> openDetail("Road & Signals"));
        }

        View cvDrivingRules = findViewById(R.id.cv_drivingrules);
        if (cvDrivingRules != null) {
            cvDrivingRules.setOnClickListener(view -> openDetail("Driving Rules"));
        }

        View cvTrafficPolice = findViewById(R.id.cv_trafficpolice_signals);
        if (cvTrafficPolice != null) {
            cvTrafficPolice.setOnClickListener(view -> openDetail("Traffic Police Signals"));
        }
    }

    public void setLanguage(String lang) {
        this.str_language = lang;
        updateUiLanguage();
    }

    private void updateUiLanguage() {
        // Update Segmented Tabs styling
        if (tabGujarati != null && tabHindi != null && tabEnglish != null) {
            if ("hindi".equalsIgnoreCase(str_language)) {
                tabHindi.setBackgroundResource(R.drawable.bg_tab_active);
                tabHindi.setTextColor(Color.parseColor("#1E40AF"));
                tabGujarati.setBackgroundResource(R.drawable.bg_tab_inactive);
                tabGujarati.setTextColor(Color.parseColor("#64748B"));
                tabEnglish.setBackgroundResource(R.drawable.bg_tab_inactive);
                tabEnglish.setTextColor(Color.parseColor("#64748B"));
            } else if ("english".equalsIgnoreCase(str_language)) {
                tabEnglish.setBackgroundResource(R.drawable.bg_tab_active);
                tabEnglish.setTextColor(Color.parseColor("#1E40AF"));
                tabGujarati.setBackgroundResource(R.drawable.bg_tab_inactive);
                tabGujarati.setTextColor(Color.parseColor("#64748B"));
                tabHindi.setBackgroundResource(R.drawable.bg_tab_inactive);
                tabHindi.setTextColor(Color.parseColor("#64748B"));
            } else {
                tabGujarati.setBackgroundResource(R.drawable.bg_tab_active);
                tabGujarati.setTextColor(Color.parseColor("#1E40AF"));
                tabHindi.setBackgroundResource(R.drawable.bg_tab_inactive);
                tabHindi.setTextColor(Color.parseColor("#64748B"));
                tabEnglish.setBackgroundResource(R.drawable.bg_tab_inactive);
                tabEnglish.setTextColor(Color.parseColor("#64748B"));
            }
        }

        if ("gujarati".equalsIgnoreCase(str_language)) {
            if (tvHeaderTitle != null) tvHeaderTitle.setText("RTO સંકેતો / ચિહ્નો");
            if (tvMandatoryTitle != null) tvMandatoryTitle.setText("ફરજિયાત સંકેતો");
            if (tvMandatorySub != null) tvMandatorySub.setText("ફરજિયાત પાલન કરવાના ટ્રાફિક ચિહ્નો");
            if (tvCautionaryTitle != null) tvCautionaryTitle.setText("ચેતવણી સંકેતો");
            if (tvCautionarySub != null) tvCautionarySub.setText("સાવચેતી અને ચેતવણી આપતા ચિહ્નો");
            if (tvInformatoryTitle != null) tvInformatoryTitle.setText("માહિતીદર્શક સંકેતો");
            if (tvInformatorySub != null) tvInformatorySub.setText("દિશા, પાર્કિંગ અને જાહેર સુવિધાઓ");
            if (tvRoadSignalsTitle != null) tvRoadSignalsTitle.setText("રોડ માર્કિંગ અને સિગ્નલ");
            if (tvRoadSignalsSub != null) tvRoadSignalsSub.setText("લેન માર્કિંગ અને ટ્રાફિક સિગ્નલ");
            if (tvDrivingRulesTitle != null) tvDrivingRulesTitle.setText("ડ્રાઇવિંગના નિયમો");
            if (tvDrivingRulesSub != null) tvDrivingRulesSub.setText("ડ્રાઇવરના હાથના સંકેતો અને નિયમો");
            if (tvTrafficPoliceTitle != null) tvTrafficPoliceTitle.setText("ટ્રાફિક પોલીસના સંકેતો");
            if (tvTrafficPoliceSub != null) tvTrafficPoliceSub.setText("હાથ વડે ટ્રાફિક નિયંત્રણના સંકેતો");
        } else if ("hindi".equalsIgnoreCase(str_language)) {
            if (tvHeaderTitle != null) tvHeaderTitle.setText("RTO संकेत और चिन्ह");
            if (tvMandatoryTitle != null) tvMandatoryTitle.setText("अनिवार्य संकेत");
            if (tvMandatorySub != null) tvMandatorySub.setText("अनिवार्य ट्रैफिक नियम और संकेत");
            if (tvCautionaryTitle != null) tvCautionaryTitle.setText("चेतावनी संकेत");
            if (tvCautionarySub != null) tvCautionarySub.setText("सावधानी और चेतावनी देने वाले संकेत");
            if (tvInformatoryTitle != null) tvInformatoryTitle.setText("सूचनात्मक संकेत");
            if (tvInformatorySub != null) tvInformatorySub.setText("दिशा, पार्किंग और सार्वजनिक सुविधाएं");
            if (tvRoadSignalsTitle != null) tvRoadSignalsTitle.setText("सड़क और सिग्नल");
            if (tvRoadSignalsSub != null) tvRoadSignalsSub.setText("लेन मार्किंग और ट्रैफिक सिग्नल");
            if (tvDrivingRulesTitle != null) tvDrivingRulesTitle.setText("ड्राइविंग के नियम");
            if (tvDrivingRulesSub != null) tvDrivingRulesSub.setText("चालक के हाथ के संकेत और सुरक्षा नियम");
            if (tvTrafficPoliceTitle != null) tvTrafficPoliceTitle.setText("ट्रैफिक पुलिस के संकेत");
            if (tvTrafficPoliceSub != null) tvTrafficPoliceSub.setText("हाथ द्वारा यातायात नियंत्रण के संकेत");
        } else {
            if (tvHeaderTitle != null) tvHeaderTitle.setText("RTO Symbols");
            if (tvMandatoryTitle != null) tvMandatoryTitle.setText("Mandatory");
            if (tvMandatorySub != null) tvMandatorySub.setText("Compulsory traffic signs");
            if (tvCautionaryTitle != null) tvCautionaryTitle.setText("Cautionary");
            if (tvCautionarySub != null) tvCautionarySub.setText("Warning & caution alerts");
            if (tvInformatoryTitle != null) tvInformatoryTitle.setText("Informatory");
            if (tvInformatorySub != null) tvInformatorySub.setText("Directions, parking & facilities");
            if (tvRoadSignalsTitle != null) tvRoadSignalsTitle.setText("Road & Signals");
            if (tvRoadSignalsSub != null) tvRoadSignalsSub.setText("Lane markings & traffic signals");
            if (tvDrivingRulesTitle != null) tvDrivingRulesTitle.setText("Driving Rules");
            if (tvDrivingRulesSub != null) tvDrivingRulesSub.setText("Driver hand signals & safety rules");
            if (tvTrafficPoliceTitle != null) tvTrafficPoliceTitle.setText("Traffic Police Signals");
            if (tvTrafficPoliceSub != null) tvTrafficPoliceSub.setText("Manual traffic control gestures");
        }
    }

    private void openDetail(String passValue) {
        Intent intent = new Intent(Task_SymbolActivity.this, Task_SymbolDetailActivity.class);
        intent.putExtra("passvalue", passValue);
        intent.putExtra("language", str_language);
        MyApplication.showInterstitialAd(Task_SymbolActivity.this, () -> Task_SymbolActivity.this.startActivity(intent));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}