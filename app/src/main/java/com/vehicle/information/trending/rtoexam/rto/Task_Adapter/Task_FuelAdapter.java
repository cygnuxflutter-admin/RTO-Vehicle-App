package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.internal.view.SupportMenu;
import androidx.recyclerview.widget.RecyclerView;


import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_FuelModel;
import com.vehicle.information.trending.rtoexam.rto.R;

import java.util.ArrayList;

public class Task_FuelAdapter extends RecyclerView.Adapter<Task_FuelAdapter.ViewHolder> {
    Context context;
    ArrayList<Task_FuelModel> list;

    public Task_FuelAdapter(Context context2, ArrayList<Task_FuelModel> arrayList) {
        this.context = context2;
        this.list = arrayList;
    }

    @Override 
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_lay_fuel_price, viewGroup, false));
    }

    @SuppressLint("RestrictedApi")
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Task_FuelModel taskFuelModel = this.list.get(i);
        viewHolder.fuelName.setText(taskFuelModel.getFuelName());
        viewHolder.currently.setText(taskFuelModel.getCurrently());
        viewHolder.day.setText(taskFuelModel.getDay());
        viewHolder.month.setText(taskFuelModel.getMonth());
        viewHolder.year.setText(taskFuelModel.getYear());
        viewHolder.currentPrice.setText(taskFuelModel.getCurrentPrice());
        viewHolder.previousPrice.setText(taskFuelModel.getPreviousPrice());
        if (taskFuelModel.getPreviousPrice().contains("+")) {
            viewHolder.scale.setImageTintList(ColorStateList.valueOf(SupportMenu.CATEGORY_MASK));
            viewHolder.scale.setRotation(-90.0f);
        }
/*        if (i == 1) {
            viewHolder.bg.setBackgroundResource(R.drawable.bg_fuel2);
            viewHolder.bg2.setBackgroundResource(R.drawable.bg_fuel2_bg);
        } else if (i == 2) {
            viewHolder.bg.setBackgroundResource(R.drawable.bg_fuel3);
            viewHolder.bg2.setBackgroundResource(R.drawable.bg_fuel3_bg);
        } else if (i == 3) {
            viewHolder.bg.setBackgroundResource(R.drawable.bg_fuel4);
            viewHolder.bg2.setBackgroundResource(R.drawable.bg_fuel4_bg);
        }*/
    }

    @Override 
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout bg;
        ConstraintLayout bg2;
        TextView currentPrice;
        TextView currently;
        TextView day;
        TextView fuelName;
        TextView month;
        TextView previousPrice;
        ImageView scale;
        TextView year;

        public ViewHolder(View view) {
            super(view);
            this.fuelName = (TextView) view.findViewById(R.id.FuelName);
            this.currently = (TextView) view.findViewById(R.id.current);
            this.day = (TextView) view.findViewById(R.id.day);
            this.month = (TextView) view.findViewById(R.id.month);
            this.year = (TextView) view.findViewById(R.id.year);
            this.currentPrice = (TextView) view.findViewById(R.id.price);
            this.previousPrice = (TextView) view.findViewById(R.id.previousPrice);
            this.scale = (ImageView) view.findViewById(R.id.PriceIndicator);
            this.bg = (ConstraintLayout) view.findViewById(R.id.lay);
            this.bg2 = (ConstraintLayout) view.findViewById(R.id.constraintLayout);
        }
    }
}
