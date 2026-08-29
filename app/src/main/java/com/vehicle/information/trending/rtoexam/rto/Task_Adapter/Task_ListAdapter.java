package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_CitiesModel;
import com.vehicle.information.trending.rtoexam.rto.R;
import java.util.ArrayList;
import java.util.List;

public class Task_ListAdapter extends BaseAdapter {
    private List<Task_CitiesModel> originalList;
    private List<Task_CitiesModel> filteredList;
    private Context context;
    private String stateName;

    public Task_ListAdapter(Context context, ArrayList<Task_CitiesModel> arrayList, String stateName) {
        this.context = context;
        this.stateName = stateName;
        this.originalList = new ArrayList<>();
        this.filteredList = new ArrayList<>();
        if (arrayList != null) {
            this.originalList.addAll(arrayList);
            this.filteredList.addAll(arrayList);
        }
    }

    public void filter(String query) {
        this.filteredList.clear();
        if (query == null || query.trim().isEmpty()) {
            this.filteredList.addAll(this.originalList);
        } else {
            String lower = query.trim().toLowerCase();
            for (Task_CitiesModel item : this.originalList) {
                String field2 = item.getField2() != null ? item.getField2().toLowerCase() : "";
                String code = item.getCode().toLowerCase();
                String district = item.getDistrict().toLowerCase();
                if (field2.contains(lower) || code.contains(lower) || district.contains(lower)) {
                    this.filteredList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.filteredList.size();
    }

    @Override
    public Task_CitiesModel getItem(int i) {
        return this.filteredList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        TextView tv_rto_code;
        TextView tv_district_name;
        TextView tv_state_name;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.task_item_list_offices_city, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_rto_code = view.findViewById(R.id.tv_rto_code);
            viewHolder.tv_district_name = view.findViewById(R.id.tv_district_name);
            viewHolder.tv_state_name = view.findViewById(R.id.tv_state_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Task_CitiesModel item = this.filteredList.get(i);
        String code = item.getCode();
        String district = item.getDistrict();
        String state = item.getState();
        if (state == null || state.isEmpty()) {
            state = this.stateName;
        }

        viewHolder.tv_rto_code.setText(!code.isEmpty() ? code : "RTO");
        viewHolder.tv_district_name.setText(!district.isEmpty() ? district : item.getField2());
        viewHolder.tv_state_name.setText(state);

        return view;
    }
}