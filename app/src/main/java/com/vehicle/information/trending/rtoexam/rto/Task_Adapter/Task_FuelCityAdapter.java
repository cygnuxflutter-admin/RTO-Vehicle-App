package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.vehicle.information.trending.rtoexam.rto.Task_Activity.Task_StartActivity;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Extra.Task_Constant;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_StateListModel;

import java.util.ArrayList;

public class Task_FuelCityAdapter extends RecyclerView.Adapter<Task_FuelCityAdapter.ViewHolder> {
    Context context;
    ArrayList<Task_StateListModel> list;

    public Task_FuelCityAdapter(Context context2, ArrayList<Task_StateListModel> arrayList) {
        this.context = context2;
        this.list = arrayList;
    }

    public void setFilteredList(ArrayList<Task_StateListModel> arrayList) {
        this.list = arrayList;
        notifyDataSetChanged();
    }

    @Override 
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_lay_fuel, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.setIsRecyclable(false);
        final Task_StateListModel taskStateListModel = this.list.get(i);
        viewHolder.txt.setText(taskStateListModel.getStateName());
        if (taskStateListModel.getId().equalsIgnoreCase("null")) {
            viewHolder.layout.setBackgroundResource(R.drawable.bg_state_header);
            viewHolder.txt.setTextColor(ContextCompat.getColor(this.context, R.color.theme_primary));
            viewHolder.txt.setTypeface(null, Typeface.BOLD);
            viewHolder.txt.setTextSize(13.0f);
            if (viewHolder.ivIcon != null) {
                viewHolder.ivIcon.setVisibility(View.GONE);
            }
            if (viewHolder.ivArrow != null) {
                viewHolder.ivArrow.setVisibility(View.GONE);
            }
        } else {
            viewHolder.layout.setBackgroundResource(R.drawable.bg_city_item);
            viewHolder.txt.setTextColor(ContextCompat.getColor(this.context, R.color.theme_text_primary));
            viewHolder.txt.setTypeface(null, Typeface.NORMAL);
            viewHolder.txt.setTextSize(15.0f);
            if (viewHolder.ivIcon != null) {
                viewHolder.ivIcon.setVisibility(View.VISIBLE);
            }
            if (viewHolder.ivArrow != null) {
                viewHolder.ivArrow.setVisibility(View.VISIBLE);
            }
        }
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!taskStateListModel.getId().equalsIgnoreCase("null")) {
                    Intent intent = new Intent(Task_FuelCityAdapter.this.context, Task_StartActivity.class);
                    SharedPreferences.Editor edit = Task_FuelCityAdapter.this.context.getSharedPreferences(Task_Constant.MY_PREFS_NAME, 0).edit();
                    edit.putString("cityName", taskStateListModel.getStateName());
                    edit.putString("cityId", taskStateListModel.getId());
                    edit.apply();
                    Task_FuelCityAdapter.this.context.startActivity(intent);
                    if (Task_FuelCityAdapter.this.context instanceof Activity) {
                        ((Activity) Task_FuelCityAdapter.this.context).finish();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View layout;
        TextView txt;
        ImageView ivIcon;
        ImageView ivArrow;

        public ViewHolder(View view) {
            super(view);
            this.txt = (TextView) view.findViewById(R.id.textView4);
            this.layout = view.findViewById(R.id.lay);
            this.ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
            this.ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
        }
    }
}
