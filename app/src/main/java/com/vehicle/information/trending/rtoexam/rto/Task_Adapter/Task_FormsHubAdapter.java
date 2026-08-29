package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_FormModel;

import java.util.ArrayList;
import java.util.List;

public class Task_FormsHubAdapter extends RecyclerView.Adapter<Task_FormsHubAdapter.ViewHolder> {

    public interface OnFormClickListener {
        void onFormClick(Task_FormModel form);
    }

    private Context context;
    private List<Task_FormModel> originalList;
    private List<Task_FormModel> filteredList;
    private OnFormClickListener listener;

    public Task_FormsHubAdapter(Context context, List<Task_FormModel> list, OnFormClickListener listener) {
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
        String q = (query != null) ? query.trim().toLowerCase() : "";
        String cat = (category != null) ? category : "All";

        this.filteredList.clear();
        for (Task_FormModel item : this.originalList) {
            boolean matchesCat = cat.equalsIgnoreCase("All") || item.getCategory().equalsIgnoreCase(cat);
            boolean matchesQuery = q.isEmpty() ||
                    item.getFormNumber().toLowerCase().contains(q) ||
                    item.getTitle().toLowerCase().contains(q) ||
                    item.getDescription().toLowerCase().contains(q) ||
                    item.getCategory().toLowerCase().contains(q);

            if (matchesCat && matchesQuery) {
                this.filteredList.add(item);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_item_form_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task_FormModel item = filteredList.get(position);
        holder.tv_form_number.setText(item.getFormNumber());
        holder.tv_form_category.setText("• " + item.getCategory());
        holder.tv_form_title.setText(item.getTitle());
        holder.tv_form_desc.setText(item.getDescription());
        holder.iv_form_icon.setImageResource(item.getIconResId());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFormClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_form_icon;
        TextView tv_form_number;
        TextView tv_form_category;
        TextView tv_form_title;
        TextView tv_form_desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_form_icon = itemView.findViewById(R.id.iv_form_icon);
            tv_form_number = itemView.findViewById(R.id.tv_form_number);
            tv_form_category = itemView.findViewById(R.id.tv_form_category);
            tv_form_title = itemView.findViewById(R.id.tv_form_title);
            tv_form_desc = itemView.findViewById(R.id.tv_form_desc);
        }
    }
}