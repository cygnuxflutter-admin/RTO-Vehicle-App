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

import com.vehicle.information.trending.rtoexam.rto.MyApplication;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_DataBase.Task_DBHandler;
import com.vehicle.information.trending.rtoexam.rto.Task_Extra.Task_QueConstructor;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

import java.util.ArrayList;

public class Task_ResultActivity extends AppCompatActivity {
    Button btnHome;
    Button btnViewResult;
    ImageView ivSmiley;
    String str_language;
    TextView tvResult;
    TextView tvScore;
    TextView tv_title;
    TextView tvUserScore;
    TextView tvScoreLabel;
    TextView tvPassLabel;

    private Task_PreferenceClass taskPreferenceClass;
    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayList<String> stringArrayList2 = new ArrayList<>();
    ArrayList<String> stringArrayList3 = new ArrayList<>();
    ArrayList<Integer> integerArrayList = new ArrayList<>();
    ArrayList<Integer> integerArrayList2 = new ArrayList<>();
    ArrayList<String> stringArrayList4 = new ArrayList<>();
    ArrayList<String> arrayList3 = new ArrayList<>();
    ArrayList<Integer> arrayList4 = new ArrayList<>();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_results);

        RelativeLayout nativeAdContainer = findViewById(R.id.native_banner_ad_container);
        if (nativeAdContainer != null) {
            com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_NativeAdUtil.loadNativeAd(nativeAdContainer, this);
        }
        View rlBanner = findViewById(R.id.rlBanner);
        if (rlBanner != null) {
            rlBanner.setVisibility(View.GONE);
        }

        taskPreferenceClass = new Task_PreferenceClass(this);

        ImageView ivBack = findViewById(R.id.iv_back);
        if (ivBack != null) {
            ivBack.setOnClickListener(view -> goHome());
        }

        this.tv_title = findViewById(R.id.tv_title);
        this.tvResult = findViewById(R.id.tvResult);
        this.tvScore = findViewById(R.id.tvScore);
        this.tvUserScore = findViewById(R.id.tvUserScore);
        this.tvScoreLabel = findViewById(R.id.tvScoreLabel);
        this.tvPassLabel = findViewById(R.id.tvPassLabel);
        this.ivSmiley = findViewById(R.id.ivSmiley);
        this.btnHome = findViewById(R.id.btnHome);
        this.btnViewResult = findViewById(R.id.btnViewResult);

        Bundle extras = getIntent().getExtras();
        int score = 0;
        if (extras != null) {
            score = extras.getInt("score", 0);
            ArrayList<String> tempAns = extras.getStringArrayList("myanswer");
            if (tempAns != null) stringArrayList = tempAns;
            ArrayList<String> tempQues = extras.getStringArrayList("questionnumbers");
            if (tempQues != null) stringArrayList2 = tempQues;
            ArrayList<String> tempCorr = extras.getStringArrayList("Correct");
            if (tempCorr != null) stringArrayList3 = tempCorr;
            ArrayList<Integer> tempImg = extras.getIntegerArrayList("Image");
            if (tempImg != null) integerArrayList = tempImg;
            ArrayList<Integer> tempNums = extras.getIntegerArrayList("Numbers");
            if (tempNums != null) integerArrayList2 = tempNums;
            ArrayList<String> tempPhoto = extras.getStringArrayList("photo");
            if (tempPhoto != null) stringArrayList4 = tempPhoto;
            this.str_language = extras.getString("language");
        }

        // Fallback safety if lists were empty
        if (stringArrayList2.isEmpty()) {
            ArrayList<Task_QueConstructor> dbList = new Task_DBHandler(this).getAllQuestions2();
            int limit = Math.min(15, dbList.size());
            for (int k = 0; k < limit; k++) {
                Task_QueConstructor q = dbList.get(k);
                stringArrayList2.add(q.getQuestion());
                stringArrayList3.add(q.getAnswer());
                stringArrayList.add(q.getAnswer());
                stringArrayList4.add(q.getPhoto());
                integerArrayList.add(q.getId());
                integerArrayList2.add(k + 1);
            }
        }

        if (this.str_language == null || this.str_language.isEmpty()) {
            this.str_language = "english";
        }

        if (this.str_language.equalsIgnoreCase("gujarati")) {
            this.tv_title.setText("પરીક્ષા પરિણામ");
            this.btnHome.setText("હોમ પર જાઓ");
            this.btnViewResult.setText("જવાબો જુઓ");
            if (this.tvScoreLabel != null) this.tvScoreLabel.setText("તમારો સ્કોર");
            if (this.tvPassLabel != null) this.tvPassLabel.setText("પાસ માર્ક");
        } else if (this.str_language.equalsIgnoreCase("hindi")) {
            this.tv_title.setText("परीक्षा परिणाम");
            this.btnHome.setText("होम पर जाएं");
            this.btnViewResult.setText("जवाब देखिए");
            if (this.tvScoreLabel != null) this.tvScoreLabel.setText("आपका स्कोर");
            if (this.tvPassLabel != null) this.tvPassLabel.setText("पास स्कोर");
        } else {
            this.tv_title.setText("Exam Result");
            this.btnHome.setText("Back to Home");
            this.btnViewResult.setText("See Answers");
            if (this.tvScoreLabel != null) this.tvScoreLabel.setText("Your Score");
            if (this.tvPassLabel != null) this.tvPassLabel.setText("Pass Mark");
        }

        if (this.tvUserScore != null) {
            this.tvUserScore.setText(score + " / 15");
        }

        if (score >= 9) {
            if (this.str_language.equalsIgnoreCase("gujarati")) {
                this.tvResult.setText("અભિનંદન, તમે પરીક્ષા પાસ કરી છે! 🎉");
            } else if (this.str_language.equalsIgnoreCase("hindi")) {
                this.tvResult.setText("बधाई हो, आपने परीक्षा उत्तीर्ण कर ली है! 🎉");
            } else {
                this.tvResult.setText("Congratulations, You passed the exam! 🎉");
            }
            this.ivSmiley.setImageResource(R.drawable.emojis_happy);
            this.tvResult.setTextColor(Color.parseColor("#16A34A"));
        } else {
            if (this.str_language.equalsIgnoreCase("gujarati")) {
                this.tvResult.setText("દુઃખદ, તમે પરીક્ષા પાસ કરી શક્યા નથી");
            } else if (this.str_language.equalsIgnoreCase("hindi")) {
                this.tvResult.setText("दुःखद, आप परीक्षा पास नहीं कर सके");
            } else {
                this.tvResult.setText("You did not pass the exam");
            }
            this.ivSmiley.setImageResource(R.drawable.emojis_sad);
            this.tvResult.setTextColor(Color.parseColor("#DC2626"));
        }

        this.btnHome.setOnClickListener(view -> goHome());

        arrayList3 = stringArrayList4;
        arrayList4 = integerArrayList2;

        this.btnViewResult.setOnClickListener(view -> MyApplication.showInterstitialAd(Task_ResultActivity.this, this::Next_ResultCardsActivity));
    }

    private void goHome() {
        Intent homeIntent = new Intent(Task_ResultActivity.this, Task_MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(homeIntent);
        finish();
    }

    public void Next_ResultCardsActivity() {
        Intent intent = new Intent(Task_ResultActivity.this, Task_ResultCardsActivity.class);
        intent.putExtra("myanswer", stringArrayList);
        intent.putExtra("questionnumbers", stringArrayList2);
        intent.putExtra("Correct", stringArrayList3);
        intent.putExtra("Image", integerArrayList);
        intent.putExtra("Numbers", arrayList4);
        intent.putExtra("photo", arrayList3);
        intent.putExtra("language", str_language);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        goHome();
    }
}