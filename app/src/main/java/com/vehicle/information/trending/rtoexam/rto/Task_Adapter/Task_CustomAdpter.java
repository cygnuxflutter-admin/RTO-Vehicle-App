package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_SignModel;
import com.vehicle.information.trending.rtoexam.rto.R;
import java.util.ArrayList;


public class Task_CustomAdpter extends BaseAdapter {
    Context f12c;
    LayoutInflater inflater;
    ArrayList<Task_SignModel> signs;

    @Override
    public long getItemId(int i) {
        return i;
    }

    public Task_CustomAdpter(Context context, ArrayList<Task_SignModel> arrayList) {
        this.f12c = context;
        this.signs = arrayList;
    }

    @Override
    public int getCount() {
        return this.signs.size();
    }

    @Override
    public Object getItem(int i) {
        return this.signs.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (this.inflater == null) {
            this.inflater = (LayoutInflater) this.f12c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {
            view = this.inflater.inflate(R.layout.task_m_model, viewGroup, false);
        }
        final String text = this.signs.get(i).getText();
        int image = this.signs.get(i).getImage();
        ((TextView) view.findViewById(R.id.nameTxt)).setText(text);
        ((ImageView) view.findViewById(R.id.movieImage)).setImageResource(image);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                Toast.makeText(Task_CustomAdpter.this.f12c, text, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
