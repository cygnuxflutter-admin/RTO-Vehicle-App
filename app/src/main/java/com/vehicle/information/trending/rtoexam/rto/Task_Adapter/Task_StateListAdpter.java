package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.vehicle.information.trending.rtoexam.rto.R;
import java.util.ArrayList;
import java.util.List;

public class Task_StateListAdpter extends BaseAdapter {
    private List<String> originalList;
    private List<String> filteredList;
    private Context context;

    public Task_StateListAdpter(Context context, String[] strArr) {
        this.context = context;
        this.originalList = new ArrayList<>();
        this.filteredList = new ArrayList<>();
        if (strArr != null) {
            for (String s : strArr) {
                this.originalList.add(s);
                this.filteredList.add(s);
            }
        }
    }

    public void filter(String query) {
        filteredList.clear();
        if (query == null || query.trim().isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            String lower = query.trim().toLowerCase();
            for (String s : originalList) {
                if (s.toLowerCase().contains(lower)) {
                    filteredList.add(s);
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
    public String getItem(int i) {
        return this.filteredList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        TextView tv_states;
        TextView tv_state_sub;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.task_item_list_offices_state, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_states = view.findViewById(R.id.tv_states);
            viewHolder.tv_state_sub = view.findViewById(R.id.tv_state_sub);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String state = this.filteredList.get(i);
        viewHolder.tv_states.setText(state);
        if (viewHolder.tv_state_sub != null) {
            viewHolder.tv_state_sub.setText("Tap to view RTO codes & offices");
        }
        return view;
    }
}