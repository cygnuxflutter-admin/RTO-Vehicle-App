package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.Task_PagerAdapter;
import com.vehicle.information.trending.rtoexam.rto.Task_Fragment.Task_QuestFragment;
import com.vehicle.information.trending.rtoexam.rto.Task_Fragment.Task_SignFragment;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

public class Task_SelectExamsPrepareActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    String str_language;
    ViewPager vp;
    TextView tvHeaderTitle;
    TextView tvTabQuestions;
    TextView tvTabSigns;

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        selectTab(position);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_select_exam_prepare);

        RelativeLayout rl_ad = findViewById(R.id.rl_ad);
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

        this.str_language = getIntent().getStringExtra("language");
        if (this.str_language == null || this.str_language.isEmpty()) {
            this.str_language = "gujarati";
        }

        this.tvHeaderTitle = findViewById(R.id.tv_header_title);
        this.tvTabQuestions = findViewById(R.id.tvTabQuestions);
        this.tvTabSigns = findViewById(R.id.tvTabSigns);

        if (this.str_language.equalsIgnoreCase("gujarati")) {
            if (this.tvHeaderTitle != null) this.tvHeaderTitle.setText("પરીક્ષાની તૈયારી");
            if (this.tvTabQuestions != null) this.tvTabQuestions.setText("પ્રશ્નો");
            if (this.tvTabSigns != null) this.tvTabSigns.setText("ચિહ્નો");
        } else if (this.str_language.equalsIgnoreCase("hindi")) {
            if (this.tvHeaderTitle != null) this.tvHeaderTitle.setText("परीक्षा की तैयारी");
            if (this.tvTabQuestions != null) this.tvTabQuestions.setText("प्रश्न");
            if (this.tvTabSigns != null) this.tvTabSigns.setText("प्रतीक");
        } else {
            if (this.tvHeaderTitle != null) this.tvHeaderTitle.setText("Exam Preparation");
            if (this.tvTabQuestions != null) this.tvTabQuestions.setText("Questions");
            if (this.tvTabSigns != null) this.tvTabSigns.setText("Signs");
        }

        this.vp = findViewById(R.id.mViewpager_ID);
        addPages();
        this.vp.addOnPageChangeListener(this);

        this.tvTabQuestions.setOnClickListener(view -> this.vp.setCurrentItem(0, true));
        this.tvTabSigns.setOnClickListener(view -> this.vp.setCurrentItem(1, true));

        selectTab(0);
    }

    private void selectTab(int position) {
        if (position == 0) {
            if (this.tvTabQuestions != null) {
                this.tvTabQuestions.setBackgroundResource(R.drawable.bg_tab_selected);
                this.tvTabQuestions.setTextColor(Color.WHITE);
            }
            if (this.tvTabSigns != null) {
                this.tvTabSigns.setBackgroundColor(Color.TRANSPARENT);
                this.tvTabSigns.setTextColor(Color.parseColor("#64748B"));
            }
        } else {
            if (this.tvTabSigns != null) {
                this.tvTabSigns.setBackgroundResource(R.drawable.bg_tab_selected);
                this.tvTabSigns.setTextColor(Color.WHITE);
            }
            if (this.tvTabQuestions != null) {
                this.tvTabQuestions.setBackgroundColor(Color.TRANSPARENT);
                this.tvTabQuestions.setTextColor(Color.parseColor("#64748B"));
            }
        }
    }

    private void addPages() {
        Task_PagerAdapter m_rtoTaskPagerAdapter = new Task_PagerAdapter(getSupportFragmentManager());
        m_rtoTaskPagerAdapter.addFragment(new Task_QuestFragment(this.str_language));
        m_rtoTaskPagerAdapter.addFragment(new Task_SignFragment(this.str_language));
        this.vp.setAdapter(m_rtoTaskPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}