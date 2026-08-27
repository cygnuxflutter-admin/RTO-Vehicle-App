package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.vehicle.information.trending.rtoexam.rto.R;


public class Task_SymbolAdpter extends RecyclerView.Adapter<Task_SymbolAdpter.ViewHolder> {
    int[] img_ary;
    String[] txt_ary;

    public Task_SymbolAdpter(String[] strArr, int[] iArr) {
        this.txt_ary = strArr;
        this.img_ary = iArr;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_item_symbolic_detail, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.iv_imgg.setImageResource(this.img_ary[i]);
        viewHolder.tv_txtt.setText(this.txt_ary[i]);
    }

    @Override
    public int getItemCount() {
        return this.txt_ary.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_imgg;
        public TextView tv_txtt;

        public ViewHolder(View view) {
            super(view);
            this.iv_imgg = (ImageView) view.findViewById(R.id.iv_imgg);
            this.tv_txtt = (TextView) view.findViewById(R.id.tv_txtt);
        }
    }
}
