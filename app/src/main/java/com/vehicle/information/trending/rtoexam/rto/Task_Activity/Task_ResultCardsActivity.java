package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.Task_ResultLstAdpter;
import com.vehicle.information.trending.rtoexam.rto.Task_DataBase.Task_DBHandler;
import com.vehicle.information.trending.rtoexam.rto.Task_Extra.Task_QueConstructor;

import java.util.ArrayList;
import java.util.HashMap;

public class Task_ResultCardsActivity extends AppCompatActivity {
    public static String KEY_CANS = "canswer";
    public static String KEY_Photo = "Pphoto";
    public static String KEY_QUES = "questions";
    public static String KEY_YANS = "yanswer";
    public static String key_image = "key_image";
    public static String key_int = "key_int";

    Task_ResultLstAdpter adapter;
    Button btnHome;
    Button btnRetry;
    ListView lvAnswers;
    TextView tvHeaderTitle;
    String str_language;
    ArrayList<HashMap<String, Object>> originalValues = new ArrayList<>();
    ArrayList<HashMap<String, Integer>> originalValues1 = new ArrayList<>();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_result_cards);

        ImageView ivBack = findViewById(R.id.iv_back);
        if (ivBack != null) {
            ivBack.setOnClickListener(view -> onBackPressed());
        }

        this.tvHeaderTitle = findViewById(R.id.tv_header_title);
        this.lvAnswers = findViewById(R.id.lvAnwers);
        this.btnHome = findViewById(R.id.btnHome);
        this.btnRetry = findViewById(R.id.btnRetry);

        Bundle extras = getIntent().getExtras();
        ArrayList<String> stringArrayList = new ArrayList<>();
        ArrayList<String> stringArrayList2 = new ArrayList<>();
        ArrayList<String> stringArrayList3 = new ArrayList<>();
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        ArrayList<String> stringArrayList4 = new ArrayList<>();
        ArrayList<Integer> integerArrayList2 = new ArrayList<>();

        if (extras != null) {
            ArrayList<String> t1 = extras.getStringArrayList("myanswerlist");
            if (t1 != null) stringArrayList = t1;
            ArrayList<String> t2 = extras.getStringArrayList("Questionnumbers");
            if (t2 != null) stringArrayList2 = t2;
            ArrayList<String> t3 = extras.getStringArrayList("Correct");
            if (t3 != null) stringArrayList3 = t3;
            ArrayList<Integer> t4 = extras.getIntegerArrayList("image");
            if (t4 != null) integerArrayList = t4;
            ArrayList<String> t5 = extras.getStringArrayList("photo");
            if (t5 != null) stringArrayList4 = t5;
            ArrayList<Integer> t6 = extras.getIntegerArrayList("numbers");
            if (t6 != null) integerArrayList2 = t6;
            this.str_language = extras.getString("language");
        }

        if (this.str_language == null || this.str_language.isEmpty()) {
            this.str_language = "english";
        }

        if (this.str_language.equalsIgnoreCase("gujarati")) {
            if (this.tvHeaderTitle != null) this.tvHeaderTitle.setText("તપાસેલા જવાબો");
            this.btnHome.setText("હોમ");
            this.btnRetry.setText("ફરી ક્વિઝ આપો");
        } else if (this.str_language.equalsIgnoreCase("hindi")) {
            if (this.tvHeaderTitle != null) this.tvHeaderTitle.setText("जांचे गए उत्तर");
            this.btnHome.setText("होम");
            this.btnRetry.setText("फिर से क्विज़ दें");
        } else {
            if (this.tvHeaderTitle != null) this.tvHeaderTitle.setText("Reviewed Answers");
            this.btnHome.setText("Home");
            this.btnRetry.setText("Retry Quiz");
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

        int count = Math.min(stringArrayList2.size(), Math.min(stringArrayList3.size(), stringArrayList.size()));
        for (int i = 0; i < count; i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put(KEY_QUES, stringArrayList2.get(i));
            hashMap.put(KEY_CANS, stringArrayList3.get(i));
            hashMap.put(KEY_YANS, stringArrayList.get(i));
            hashMap.put(KEY_Photo, i < stringArrayList4.size() ? stringArrayList4.get(i) : "nophoto");
            this.originalValues.add(hashMap);

            HashMap<String, Integer> hashMap2 = new HashMap<>();
            hashMap2.put(key_image, i < integerArrayList.size() ? integerArrayList.get(i) : 0);
            hashMap2.put(key_int, i < integerArrayList2.size() ? integerArrayList2.get(i) : (i + 1));
            this.originalValues1.add(hashMap2);
        }

        this.adapter = new Task_ResultLstAdpter(this, R.layout.task_view_ans_custom, this.originalValues, this.originalValues1, integerArrayList, integerArrayList2, this.str_language);
        this.lvAnswers.setAdapter((ListAdapter) this.adapter);

        this.btnHome.setOnClickListener(view -> goHome());

        this.btnRetry.setOnClickListener(view -> {
            Intent intent = new Intent(Task_ResultCardsActivity.this, Task_QuizActivity.class);
            intent.putExtra("language", Task_ResultCardsActivity.this.str_language);
            Task_ResultCardsActivity.this.startActivity(intent);
            Task_ResultCardsActivity.this.finish();
        });
    }

    private void goHome() {
        Intent homeIntent = new Intent(Task_ResultCardsActivity.this, Task_MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(homeIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goHome();
    }
}