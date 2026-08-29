package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_RuleModel;

import java.util.ArrayList;
import java.util.List;

public class Task_RulesAdapter extends RecyclerView.Adapter<Task_RulesAdapter.ViewHolder> {
    public interface OnRuleClickListener {
        void onRuleClick(Task_RuleModel rule);
    }

    private Context context;
    private List<Task_RuleModel> originalList;
    private List<Task_RuleModel> filteredList;
    private OnRuleClickListener listener;
    private String currentCategory = "All";
    private String currentQuery = "";

    public Task_RulesAdapter(Context context, List<Task_RuleModel> list, OnRuleClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.originalList = new ArrayList<>();
        this.filteredList = new ArrayList<>();
        if (list != null) {
            this.originalList.addAll(list);
            this.filteredList.addAll(list);
        }
    }

    public void filter(String query, String category) {
        this.currentQuery = (query != null) ? query.trim().toLowerCase() : "";
        this.currentCategory = (category != null) ? category : "All";

        this.filteredList.clear();
        for (Task_RuleModel item : this.originalList) {
            boolean matchesCategory = this.currentCategory.equalsIgnoreCase("All") ||
                    item.getCategory().equalsIgnoreCase(this.currentCategory) ||
                    (this.currentCategory.equalsIgnoreCase("Transfer & NOC") && (item.getCategory().contains("NOC") || item.getCategory().contains("Transfer"))) ||
                    (this.currentCategory.equalsIgnoreCase("Loan & Finance") && (item.getCategory().contains("Finance") || item.getCategory().contains("Loan")));

            boolean matchesQuery = this.currentQuery.isEmpty() ||
                    item.getTitle().toLowerCase().contains(this.currentQuery) ||
                    item.getDescription().toLowerCase().contains(this.currentQuery) ||
                    item.getCategory().toLowerCase().contains(this.currentQuery) ||
                    item.getFormTag().toLowerCase().contains(this.currentQuery);

            if (matchesCategory && matchesQuery) {
                this.filteredList.add(item);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_item_rule_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task_RuleModel item = this.filteredList.get(position);
        holder.tv_rule_title.setText(item.getTitle());
        holder.tv_rule_desc.setText(item.getDescription());
        holder.iv_rule_icon.setImageResource(item.getIconResId());

        if (item.getFormTag() != null && !item.getFormTag().isEmpty()) {
            holder.tv_form_tag.setVisibility(View.VISIBLE);
            holder.tv_form_tag.setText(item.getFormTag());
            try {
                int badgeBgColor = Color.parseColor(item.getBadgeBgColorHex());
                int badgeTextColor = Color.parseColor(item.getBadgeTextColorHex());

                GradientDrawable badgeBg = new GradientDrawable();
                badgeBg.setColor(badgeBgColor);
                badgeBg.setCornerRadius(dpToPx(6));
                badgeBg.setStroke(dpToPx(1), Color.parseColor("#E2E8F0"));
                holder.tv_form_tag.setBackground(badgeBg);
                holder.tv_form_tag.setTextColor(badgeTextColor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            holder.tv_form_tag.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRuleClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.filteredList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_rule_icon;
        TextView tv_form_tag;
        TextView tv_rule_title;
        TextView tv_rule_desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_rule_icon = itemView.findViewById(R.id.iv_rule_icon);
            tv_form_tag = itemView.findViewById(R.id.tv_form_tag);
            tv_rule_title = itemView.findViewById(R.id.tv_rule_title);
            tv_rule_desc = itemView.findViewById(R.id.tv_rule_desc);
        }
    }

    private int dpToPx(int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
}