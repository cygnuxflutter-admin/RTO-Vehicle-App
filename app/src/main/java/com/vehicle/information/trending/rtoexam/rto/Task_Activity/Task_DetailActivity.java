package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.vehicle.information.trending.rtoexam.rto.R;


public class Task_DetailActivity extends AppCompatActivity {

    ImageView iv_back;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.task_activity_detail);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        String stringExtra = getIntent().getStringExtra("position");
        String stringExtra2 = getIntent().getStringExtra("main_url");
        ((TextView) findViewById(R.id.txt_header)).setText(stringExtra);
        ((WebView) findViewById(R.id.txt_desc)).loadUrl(stringExtra2);
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
