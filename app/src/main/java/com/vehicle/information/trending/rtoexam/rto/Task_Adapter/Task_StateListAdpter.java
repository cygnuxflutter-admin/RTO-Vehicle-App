package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.vehicle.information.trending.rtoexam.rto.R;


public class Task_StateListAdpter extends BaseAdapter {
    String[] ary_states;
    Context context;

    @Override
    public long getItemId(int i) {
        return i;
    }

    public Task_StateListAdpter(Context context, String[] strArr) {
        this.context = context;
        this.ary_states = strArr;
    }

    @Override
    public int getCount() {
        return this.ary_states.length;
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
        viewHolder.tv_states.setText(this.ary_states[i]);
        return view;
    }
}
