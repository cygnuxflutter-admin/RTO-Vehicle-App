package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_NotificationModel;

import java.util.List;

public class Task_NotificationAdapter extends RecyclerView.Adapter<Task_NotificationAdapter.ViewHolder> {

    private final Context context;
    private final List<Task_NotificationModel> list;
    private final OnNotificationClickListener listener;

    public interface OnNotificationClickListener {
        void onNotificationClick(Task_NotificationModel item);
    }

    public Task_NotificationAdapter(Context context, List<Task_NotificationModel> list, OnNotificationClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task_NotificationModel item = list.get(position);

        holder.tv_title.setText(item.getTitle());
        holder.tv_message.setText(item.getMessage());
        holder.tv_date.setText(item.getDate());

        // Unread styling
        if (item.isRead()) {
            holder.view_unread_dot.setVisibility(View.GONE);
            holder.card.setCardBackgroundColor(Color.WHITE);
            holder.card.setStrokeColor(Color.parseColor("#E2E8F0"));
        } else {
            holder.view_unread_dot.setVisibility(View.VISIBLE);
            holder.card.setCardBackgroundColor(Color.parseColor("#F8FAFC"));
            holder.card.setStrokeColor(Color.parseColor("#93C5FD"));
        }

        // Icon based on type
        if ("PUC".equalsIgnoreCase(item.getType()) || "INSURANCE".equalsIgnoreCase(item.getType())) {
            holder.iv_icon.setImageResource(R.drawable.ic_nav_car);
        } else if ("EXAM".equalsIgnoreCase(item.getType())) {
            holder.iv_icon.setImageResource(R.drawable.ic_exam_que);
        } else {
            holder.iv_icon.setImageResource(R.drawable.ic_notification_bell);
        }

        holder.card.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNotificationClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView card;
        ImageView iv_icon;
        TextView tv_title, tv_message, tv_date;
        View view_unread_dot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card_notification);
            iv_icon = itemView.findViewById(R.id.iv_notif_icon);
            tv_title = itemView.findViewById(R.id.tv_notif_title);
            tv_message = itemView.findViewById(R.id.tv_notif_message);
            tv_date = itemView.findViewById(R.id.tv_notif_date);
            view_unread_dot = itemView.findViewById(R.id.view_unread_dot);
        }
    }
}
