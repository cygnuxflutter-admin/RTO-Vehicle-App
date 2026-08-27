package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.Task_PagerAdapter;
import com.vehicle.information.trending.rtoexam.rto.Task_Fragment.Task_QuestFragment;
import com.vehicle.information.trending.rtoexam.rto.Task_Fragment.Task_SignFragment;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

public class Task_SelectExamsPrepareActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    String str_language;
    TabLayout tabLayout;
    ViewPager vp;

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    @Override
    public void onPageScrolled(int i, float f, int i2) {
    }

    @Override
    public void onPageSelected(int i) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.task_activity_select_exam_prepare);
       //AdsManager.getInstance().loadBanner(this);

        RelativeLayout rl_ad = this.findViewById(R.id.rl_ad);
        if (Task_NetworkUtils.isNetworkAvailable(this)) {
            Task_PreferenceClass taskPreferenceClass = new Task_PreferenceClass(this);
            if (taskPreferenceClass.getInt("BannerAdShow") == 1){
                Task_LoadAds.loadAdmobBannerAd(this, rl_ad);
            } else {
                rl_ad.setVisibility(View.GONE);
            findViewById(R.id.rlBanner).setVisibility(View.GONE);
            }
        }

        ((ImageView) findViewById(R.id.iv_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task_SelectExamsPrepareActivity.this.onBackPressed();
            }
        });
        this.str_language = getIntent().getStringExtra("language");
        this.vp = (ViewPager) findViewById(R.id.mViewpager_ID);
        addPages();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.mTab_ID);
        this.tabLayout = tabLayout;
        tabLayout.setTabGravity(0);
        this.tabLayout.setupWithViewPager(this.vp);
        this.tabLayout.addOnTabSelectedListener((TabLayout.OnTabSelectedListener) this);
    }

    private void addPages() {
        Task_PagerAdapter m_rtoTaskPagerAdapter = new Task_PagerAdapter(getSupportFragmentManager());
        m_rtoTaskPagerAdapter.addFragment(new Task_QuestFragment(this.str_language));
        m_rtoTaskPagerAdapter.addFragment(new Task_SignFragment(this.str_language));
        this.vp.setAdapter(m_rtoTaskPagerAdapter);
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }



    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        this.vp.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
