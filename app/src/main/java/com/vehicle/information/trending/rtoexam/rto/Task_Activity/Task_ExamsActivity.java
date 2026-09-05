package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Intent;
import android.graphics.Color;
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

public class Task_ExamsActivity extends AllBaseActivity {
    Button btnStart;
    String str_language;
    TextView tv_header;
    TextView tv_instructions;
    TextView tv_title_bar, tv_pattern_sub;
    TextView tv_stat_q_lbl, tv_stat_t_lbl, tv_stat_p_lbl;
    TextView tv_point_1, tv_point_2, tv_point_3;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_exam);

        RelativeLayout rl_ad = this.findViewById(R.id.rl_ad);
        if (Task_NetworkUtils.isNetworkAvailable(this)) {
            Task_PreferenceClass taskPreferenceClass = new Task_PreferenceClass(this);
            if (taskPreferenceClass.getInt("BannerAdShow", 1) == 1){
                Task_LoadAds.loadAdmobBannerAd(this, rl_ad);
            } else {
                rl_ad.setVisibility(View.GONE);
                findViewById(R.id.rlBanner).setVisibility(View.GONE);
            }
        }

        ((ImageView) findViewById(R.id.iv_back)).setOnClickListener(view -> onBackPressed());

        this.tv_title_bar = findViewById(R.id.tv_title_bar);
        this.tv_header = findViewById(R.id.tv_header);
        this.tv_pattern_sub = findViewById(R.id.tv_pattern_sub);
        this.tv_instructions = findViewById(R.id.tv_instructions);
        this.tv_stat_q_lbl = findViewById(R.id.tv_stat_q_lbl);
        this.tv_stat_t_lbl = findViewById(R.id.tv_stat_t_lbl);
        this.tv_stat_p_lbl = findViewById(R.id.tv_stat_p_lbl);
        this.tv_point_1 = findViewById(R.id.tv_point_1);
        this.tv_point_2 = findViewById(R.id.tv_point_2);
        this.tv_point_3 = findViewById(R.id.tv_point_3);
        this.btnStart = findViewById(R.id.btnStart);

        String stringExtra = getIntent().getStringExtra("language");
        if (stringExtra == null) stringExtra = "english";
        this.str_language = stringExtra;

        if (stringExtra.equalsIgnoreCase("gujarati")) {
            this.tv_title_bar.setText("RTO પરીક્ષા");
            this.btnStart.setText("પ્રારંભ પરીક્ષા");
            this.tv_header.setText("પરીક્ષા માર્ગદર્શિકા");
            this.tv_pattern_sub.setText("ડ્રાઇવિંગ લાયસન્સ ટેસ્ટ પેટર્ન");
            this.tv_stat_q_lbl.setText("પ્રશ્નો");
            this.tv_stat_t_lbl.setText("પ્રતિ પ્રશ્ન");
            this.tv_stat_p_lbl.setText("પાસ ગુણ");
            this.tv_point_1.setText("ટ્રાફિક અને ટ્રાફિક સંકેતોના નિયમો જેવા વિષયોમાંથી ટેસ્ટમાં પ્રશ્નો પૂછવામાં આવશે.");
            this.tv_point_2.setText("પરીક્ષામાં કુલ 15 પ્રશ્નો પૂછવામાં આવશે. પાસ થવા માટે ઓછામાં ઓછા 9 પ્રશ્નોના સાચા જવાબ આપવા જરૂરી છે.");
            this.tv_point_3.setText("દરેક પ્રશ્નનો જવાબ આપવા માટે 30 સેકન્ડનો સમય મળશે.");
        } else if (stringExtra.equalsIgnoreCase("hindi")) {
            this.tv_title_bar.setText("RTO परीक्षा");
            this.btnStart.setText("परीक्षा प्रारंभ करें");
            this.tv_header.setText("परीक्षा निर्देश");
            this.tv_pattern_sub.setText("ड्राइविंग लाइसेंस टेस्ट पैटर्न");
            this.tv_stat_q_lbl.setText("प्रश्न");
            this.tv_stat_t_lbl.setText("प्रति प्रश्न");
            this.tv_stat_p_lbl.setText("उत्तीर्ण अंक");
            this.tv_point_1.setText("यातायात नियमों और सड़क संकेतों से संबंधित महत्वपूर्ण प्रश्न परीक्षा में पूछे जाएंगे।");
            this.tv_point_2.setText("परीक्षा में कुल 15 प्रश्न पूछे जाएंगे। परीक्षा पास करने के लिए कम से कम 9 प्रश्नों के सही उत्तर आवश्यक हैं।");
            this.tv_point_3.setText("प्रत्येक प्रश्न का उत्तर देने के लिए 30 सेकंड का समय दिया जाएगा।");
        } else {
            this.tv_title_bar.setText("RTO Exam");
            this.btnStart.setText("START EXAM");
            this.tv_header.setText("Exam Instructions");
            this.tv_pattern_sub.setText("Driving License Test Pattern");
            this.tv_stat_q_lbl.setText("Questions");
            this.tv_stat_t_lbl.setText("Per Que");
            this.tv_stat_p_lbl.setText("Pass Marks");
            this.tv_point_1.setText("Questions related to traffic rules, regulations and road signages will be included in the test.");
            this.tv_point_2.setText("15 multiple choice questions will be asked randomly. You must answer at least 9 questions correctly to pass.");
            this.tv_point_3.setText("You are allowed 30 seconds to answer each question.");
        }

        this.btnStart.setOnClickListener(view -> {
            Intent intent = new Intent(Task_ExamsActivity.this, Task_QuizActivity.class);
            intent.putExtra("language", "" + Task_ExamsActivity.this.str_language);
            Task_ExamsActivity.this.startActivity(intent);
            Task_ExamsActivity.this.finish();
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