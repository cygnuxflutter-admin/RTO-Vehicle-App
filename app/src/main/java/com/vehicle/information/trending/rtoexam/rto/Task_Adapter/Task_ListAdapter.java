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


public class Task_ListAdapter extends BaseAdapter {
    ArrayList<Task_CitiesModel> ary_states;
    Context context;

    @Override
    public long getItemId(int i) {
        return i;
    }

    public Task_ListAdapter(Context context, ArrayList<Task_CitiesModel> arrayList) {
        this.context = context;
        this.ary_states = arrayList;
    }

    @Override
    public int getCount() {
        return this.ary_states.size();
    }

    @Override
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }


    private class ViewHolder {
        TextView tv_states;

        private ViewHolder() {
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.task_item_list_offices_state, (ViewGroup) null);
            viewHolder = new ViewHolder();
            viewHolder.tv_states = (TextView) view.findViewById(R.id.tv_states);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_states.setText(this.ary_states.get(i).getField2());
        return view;
    }
}
