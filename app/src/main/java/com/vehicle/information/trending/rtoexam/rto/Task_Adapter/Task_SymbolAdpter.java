package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_NativeAdUtil;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

public class Task_SymbolAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_AD = 1;

    private final Activity activity;
    private final int[] img_ary;
    private final String[] txt_ary;
    private final int interval;
    private final boolean isAdEnabled;

    public Task_SymbolAdpter(Activity activity, String[] strArr, int[] iArr) {
        this.activity = activity;
        this.txt_ary = strArr != null ? strArr : new String[0];
        this.img_ary = iArr != null ? iArr : new int[0];

        if (activity != null) {
            Task_PreferenceClass pref = new Task_PreferenceClass(activity);
            this.interval = pref.getAdsStatus("NativeAdIntervalCount");
            this.isAdEnabled = pref.getInt("NativeAdShow", 1) == 1 && Task_NetworkUtils.isNetworkAvailable(activity);
        } else {
            this.interval = 5;
            this.isAdEnabled = false;
        }
    }

    public Task_SymbolAdpter(String[] strArr, int[] iArr) {
        this(null, strArr, iArr);
    }

    private boolean isAdPosition(int position) {
        if (!isAdEnabled || interval <= 0) return false;
        return (position + 1) % (interval + 1) == 0;
    }

    private int getRealPosition(int position) {
        if (!isAdEnabled || interval <= 0) return position;
        return position - (position / (interval + 1));
    }

    @Override
    public int getItemViewType(int position) {
        return isAdPosition(position) ? VIEW_TYPE_AD : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_AD) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_item_native_ad_row, viewGroup, false);
            return new AdViewHolder(view);
        }
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_item_symbolic_detail, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_AD) {
            AdViewHolder adHolder = (AdViewHolder) holder;
            if (activity != null && !activity.isFinishing()) {
                Task_NativeAdUtil.loadNativeAd(adHolder.nativeAdContainer, activity);
            }
        } else {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            int realPos = getRealPosition(position);
            if (realPos >= 0 && realPos < this.img_ary.length && realPos < this.txt_ary.length) {
                itemHolder.iv_imgg.setImageResource(this.img_ary[realPos]);
                itemHolder.tv_txtt.setText(this.txt_ary[realPos]);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (this.txt_ary.length == 0) return 0;
        if (!isAdEnabled || interval <= 0) return this.txt_ary.length;
        int totalAds = this.txt_ary.length / interval;
        return this.txt_ary.length + totalAds;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_imgg;
        public TextView tv_txtt;

        public ItemViewHolder(View view) {
            super(view);
            this.iv_imgg = view.findViewById(R.id.iv_imgg);
            this.tv_txtt = view.findViewById(R.id.tv_txtt);
        }
    }

    public static class AdViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout nativeAdContainer;

        public AdViewHolder(View view) {
            super(view);
            this.nativeAdContainer = view.findViewById(R.id.native_ad_container);
        }
    }
}
