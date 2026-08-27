package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;


public class Task_ExamsActivity extends AppCompatActivity {
    Button btnStart;

    String str_language;
    TextView tv_header;
    TextView tv_instructions;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.task_activity_exam);
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
                Task_ExamsActivity.this.onBackPressed();
            }
        });
        this.tv_header = (TextView) findViewById(R.id.tv_header);
        this.tv_instructions = (TextView) findViewById(R.id.tv_instructions);
        this.btnStart = (Button) findViewById(R.id.btnStart);
        String stringExtra = getIntent().getStringExtra("language");
        this.str_language = stringExtra;
        if (stringExtra.equals("gujarati")) {
            this.btnStart.setText("પ્રારંભ પરીક્ષા");
            this.tv_header.setText("સૂચનાઓ");
            this.tv_instructions.setText(getResources().getString(R.string.bullet_ed_list_gujarati));
        } else if (this.str_language.equals("hindi")) {
            this.btnStart.setText("परीक्षा प्रारंभ करें");
            this.tv_header.setText("सूचनाएं");
            this.tv_instructions.setText(getResources().getString(R.string.bullet_ed_list_hindi));
        } else if (this.str_language.equals("english")) {
            this.btnStart.setText("Start Exam");
            this.tv_header.setText("Instructions");
            this.tv_instructions.setText(getResources().getString(R.string.bullet_ed_list_english));
        }
        this.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task_ExamsActivity.this, Task_QuizActivity.class);
                intent.putExtra("language", "" + Task_ExamsActivity.this.str_language);
                Task_ExamsActivity.this.startActivity(intent);
                Task_ExamsActivity.this.finish();
           /*     AdsManager.getInstance().showInterstitialAd(Task_ExamsActivity.this, new AdsManager.AdCloseListener() {
                    @Override
                    public void onAdClosed() {

                    }
                });*/
            }
        });
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
