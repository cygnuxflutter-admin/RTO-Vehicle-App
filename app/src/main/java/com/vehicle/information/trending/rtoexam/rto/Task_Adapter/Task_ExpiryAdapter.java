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
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_VehicleDocumentModel;

import java.util.List;

public class Task_ExpiryAdapter extends RecyclerView.Adapter<Task_ExpiryAdapter.ViewHolder> {

    public interface OnDeleteClickListener {
        void onDeleteClick(Task_VehicleDocumentModel model);
    }

    private Context context;
    private List<Task_VehicleDocumentModel> list;
    private OnDeleteClickListener listener;

    public Task_ExpiryAdapter(Context context, List<Task_VehicleDocumentModel> list, OnDeleteClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_item_expiry_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task_VehicleDocumentModel item = list.get(position);
        holder.tv_vehicle_number.setText(item.getVehicleNumber());
        holder.tv_vehicle_name.setText(item.getVehicleName().isEmpty() ? "My Vehicle" : item.getVehicleName());
        holder.tv_insurance_date.setText(item.getInsuranceExpiry().isEmpty() ? "Not Set" : item.getInsuranceExpiry());
        holder.tv_puc_date.setText(item.getPucExpiry().isEmpty() ? "Not Set" : item.getPucExpiry());
        holder.tv_service_date.setText(item.getServiceDueDate().isEmpty() ? "Not Set" : item.getServiceDueDate());

        holder.iv_delete_vehicle.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_vehicle_number;
        TextView tv_vehicle_name;
        TextView tv_insurance_date;
        TextView tv_puc_date;
        TextView tv_service_date;
        ImageView iv_delete_vehicle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_vehicle_number = itemView.findViewById(R.id.tv_vehicle_number);
            tv_vehicle_name = itemView.findViewById(R.id.tv_vehicle_name);
            tv_insurance_date = itemView.findViewById(R.id.tv_insurance_date);
            tv_puc_date = itemView.findViewById(R.id.tv_puc_date);
            tv_service_date = itemView.findViewById(R.id.tv_service_date);
            iv_delete_vehicle = itemView.findViewById(R.id.iv_delete_vehicle);
        }
    }
}