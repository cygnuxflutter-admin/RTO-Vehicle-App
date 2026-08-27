package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.ChallanDetails;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Utils;

import java.util.List;

public class ChallanDetailsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ChallanDetails> f1274a;
    Context b;

    public ChallanDetailsRecyclerViewAdapter(Context context2, List<ChallanDetails> list) {
        this.b = context2;
        this.f1274a = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ItemViewHolder(LayoutInflater.from(this.b).inflate(R.layout.row_challan_detail_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
            ChallanDetails challanDetails = this.f1274a.get(viewHolder.getAdapterPosition());
            itemViewHolder.s.setText(this.b.getString(R.string.format_challan_no, challanDetails.getChallanNo()));
            itemViewHolder.t.setText(challanDetails.getChallanStatus());
            itemViewHolder.q.setText(this.b.getString(R.string.format_price, String.valueOf(challanDetails.getAmount())));
            itemViewHolder.r.setText(challanDetails.getChallanDate());
            if (Utils.isNullOrEmpty(challanDetails.getPaymentDate())) {
                itemViewHolder.p.setVisibility(View.GONE);
            } else {
                itemViewHolder.p.setVisibility(View.VISIBLE);
                itemViewHolder.u.setText(challanDetails.getPaymentDate());
            }
            itemViewHolder.itemView.setTag(challanDetails);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.f1274a.size();
    }

    
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        LinearLayout p;
        TextView q;
        TextView r;
        TextView s;
        TextView t;
        TextView u;

        ItemViewHolder(View view) {
            super(view);
            this.s = (TextView) view.findViewById(R.id.txvChallanNoValue);
            this.r = (TextView) view.findViewById(R.id.txvChallanDate);
            this.q = (TextView) view.findViewById(R.id.txvChallanAmount);
            this.t = (TextView) view.findViewById(R.id.txvChallanStatus);
            this.u = (TextView) view.findViewById(R.id.txvPaymentDate);
            this.p = (LinearLayout) view.findViewById(R.id.layoutPaymentDate);
        }
    }
}
