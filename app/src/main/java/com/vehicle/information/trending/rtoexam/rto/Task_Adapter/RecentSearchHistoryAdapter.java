package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

//import com.vehicle.information.trending.rtoexam.rto.R;
//import com.vehicle.information.trending.rtoexam.rto.Task_Model.SearchVehicleHistory;
//import com.vehicle.information.trending.rtoexam.rto.utils.Utils;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.SearchVehicleHistory;
import com.vehicle.information.trending.rtoexam.rto.Task_interfaces.IRecyclerViewClickListener;
import com.vehicle.information.trending.rtoexam.rto.Task_interfaces.IRecyclerViewLongClickListener;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class RecentSearchHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /* renamed from: a  reason: collision with root package name */
    Context f1275a;
    IRecyclerViewClickListener b;
    IRecyclerViewLongClickListener c;
    private List<SearchVehicleHistory> historyList = new ArrayList();

    public RecentSearchHistoryAdapter(Context context2, String str, IRecyclerViewClickListener iRecyclerViewClickListener, IRecyclerViewLongClickListener iRecyclerViewLongClickListener) {
        this.f1275a = context2;
        this.b = iRecyclerViewClickListener;
        this.c = iRecyclerViewLongClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new SearchVehicleHistoryItemHolder(LayoutInflater.from(this.f1275a).inflate(R.layout.row_search_vehicle_history_item, viewGroup, false), this.b, this.c);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof SearchVehicleHistoryItemHolder) {
            SearchVehicleHistoryItemHolder searchVehicleHistoryItemHolder = (SearchVehicleHistoryItemHolder) viewHolder;
            SearchVehicleHistory searchVehicleHistory = this.historyList.get(viewHolder.getAdapterPosition());
            searchVehicleHistoryItemHolder.s.setText(searchVehicleHistory.getRegistrationNo());
            if (Utils.isNullOrEmpty(searchVehicleHistory.getName())) {
                searchVehicleHistoryItemHolder.r.setVisibility(View.GONE);
            } else {
                searchVehicleHistoryItemHolder.r.setText(searchVehicleHistory.getName());
                searchVehicleHistoryItemHolder.r.setVisibility(View.VISIBLE);
            }
            searchVehicleHistoryItemHolder.itemView.setTag(Integer.valueOf(viewHolder.getAdapterPosition()));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<SearchVehicleHistory> list = this.historyList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return (this.historyList.get(i) == null || this.historyList.get(i).getRegistrationNo() != null) ? 1 : 2;
    }

    public void updateListData(List<SearchVehicleHistory> list) {
        if (this.historyList == null) {
            this.historyList = new ArrayList();
        }
        this.historyList = list;
        pushAds();
        notifyDataSetChanged();
    }

    private void pushAds() {
        List<SearchVehicleHistory> list = this.historyList;
        if (list != null && list.size() > 0) {
            int i = this.historyList.size() > 29 ? 8 : 4;
            int size = (this.historyList.size() / i) + 1;
            int size2 = this.historyList.size();
            for (int i2 = 1; i2 < size + size2 && this.historyList.size() > i2; i2++) {
                if (i2 % i == 0) {
                    this.historyList.add(i2, new SearchVehicleHistory());
                }
            }
        }
    }


    static class SearchVehicleHistoryItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        IRecyclerViewClickListener p;
        IRecyclerViewLongClickListener q;
        TextView r;
        TextView s;

        SearchVehicleHistoryItemHolder(View view, IRecyclerViewClickListener iRecyclerViewClickListener, IRecyclerViewLongClickListener iRecyclerViewLongClickListener) {
            super(view);
            this.s = (TextView) view.findViewById(R.id.txvRegNo);
            this.r = (TextView) view.findViewById(R.id.txvName);
            this.p = iRecyclerViewClickListener;
            this.q = iRecyclerViewLongClickListener;
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            this.p.onItemSelected(getAdapterPosition());
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View view) {
            this.q.onItemLongClick(getAdapterPosition());
            return true;
        }
    }
}
