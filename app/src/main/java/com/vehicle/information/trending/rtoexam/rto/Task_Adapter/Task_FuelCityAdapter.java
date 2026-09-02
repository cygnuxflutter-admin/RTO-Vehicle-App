package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_StateListModel;

import java.util.ArrayList;

public class Task_FuelCityAdapter extends RecyclerView.Adapter<Task_FuelCityAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<Task_StateListModel> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Task_StateListModel item);
    }

    public Task_FuelCityAdapter(Context context, ArrayList<Task_StateListModel> arrayList, OnItemClickListener listener) {
        this.context = context;
        this.list = arrayList;
        this.listener = listener;
    }

    public void setFilteredList(ArrayList<Task_StateListModel> arrayList) {
        this.list = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_lay_fuel, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Task_StateListModel item = this.list.get(i);
        viewHolder.txt.setText(item.getStateName());

        String subtitle = item.getSubtitle();
        if (subtitle != null && !subtitle.trim().isEmpty()) {
            viewHolder.tvSubtitle.setText(subtitle);
            viewHolder.tvSubtitle.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvSubtitle.setVisibility(View.GONE);
        }

        viewHolder.layout.setBackgroundResource(R.drawable.bg_city_item);
        viewHolder.ivIcon.setColorFilter(ContextCompat.getColor(context, R.color.theme_primary));
        viewHolder.ivArrow.setVisibility(View.VISIBLE);

        viewHolder.layout.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.list != null ? this.list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View layout;
        TextView txt;
        TextView tvSubtitle;
        ImageView ivIcon;
        ImageView ivArrow;

        public ViewHolder(View view) {
            super(view);
            this.txt = view.findViewById(R.id.textView4);
            this.tvSubtitle = view.findViewById(R.id.tv_subtitle);
            this.layout = view.findViewById(R.id.lay);
            this.ivIcon = view.findViewById(R.id.iv_icon);
            this.ivArrow = view.findViewById(R.id.iv_arrow);
        }
    }
}
