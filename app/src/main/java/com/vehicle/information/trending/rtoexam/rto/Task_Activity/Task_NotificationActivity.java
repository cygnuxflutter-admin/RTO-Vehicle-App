package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.Task_NotificationAdapter;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_NotificationModel;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NotificationStorage;

import java.util.ArrayList;
import java.util.List;

public class Task_NotificationActivity extends AllBaseActivity {

    private RecyclerView rvNotifications;
    private LinearLayout llEmptyState;
    private TextView tvClearAll;
    private Task_NotificationAdapter adapter;
    private List<Task_NotificationModel> notificationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_notification);

        // Load Banner Ad
        RelativeLayout rlAd = findViewById(R.id.rl_ad);
        if (rlAd != null) {
            Task_LoadAds.loadAdmobBannerAd(this, rlAd);
        }

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        rvNotifications = findViewById(R.id.rv_notifications);
        llEmptyState = findViewById(R.id.ll_empty_state);
        tvClearAll = findViewById(R.id.tv_clear_all);

        rvNotifications.setLayoutManager(new LinearLayoutManager(this));
        rvNotifications.setHasFixedSize(true);

        tvClearAll.setOnClickListener(v -> {
            Task_NotificationStorage.clearAllNotifications(Task_NotificationActivity.this);
            loadNotifications();
            Toast.makeText(Task_NotificationActivity.this, "All notifications cleared", Toast.LENGTH_SHORT).show();
        });

        loadNotifications();
        
        // Auto mark all as read when opening notification center
        Task_NotificationStorage.markAllAsRead(this);
    }

    private void loadNotifications() {
        notificationList = Task_NotificationStorage.getNotifications(this);

        if (notificationList.isEmpty()) {
            rvNotifications.setVisibility(View.GONE);
            llEmptyState.setVisibility(View.VISIBLE);
            tvClearAll.setVisibility(View.GONE);
        } else {
            rvNotifications.setVisibility(View.VISIBLE);
            llEmptyState.setVisibility(View.GONE);
            tvClearAll.setVisibility(View.VISIBLE);

            adapter = new Task_NotificationAdapter(this, notificationList, item -> {
                // Mark single clicked item as read
                Task_NotificationStorage.markAsRead(Task_NotificationActivity.this, item.getId());
                item.setRead(true);
                adapter.notifyDataSetChanged();

                // Smart Action Navigation
                if ("PUC".equalsIgnoreCase(item.getType()) || "INSURANCE".equalsIgnoreCase(item.getType())) {
                    startActivity(new Intent(Task_NotificationActivity.this, Task_ExpiryReminderActivity.class));
                } else if ("EXAM".equalsIgnoreCase(item.getType())) {
                    Intent intent = new Intent(Task_NotificationActivity.this, Task_LanguageSelectActivity.class);
                    intent.putExtra("from", "from_preparation");
                    startActivity(intent);
                }
            });
            rvNotifications.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotifications();
    }
}
