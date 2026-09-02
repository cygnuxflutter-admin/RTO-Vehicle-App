package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_SignModel;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_NativeAdUtil;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;
import java.util.ArrayList;

public class Task_CustomAdpter extends BaseAdapter {
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_AD = 1;

    private final Context f12c;
    private LayoutInflater inflater;
    private final ArrayList<Task_SignModel> signs;
    private final int interval;
    private final boolean isAdEnabled;

    public Task_CustomAdpter(Context context, ArrayList<Task_SignModel> arrayList) {
        this.f12c = context;
        this.signs = arrayList != null ? arrayList : new ArrayList<>();

        if (context != null) {
            Task_PreferenceClass pref = new Task_PreferenceClass(context);
            this.interval = pref.getAdsStatus("NativeAdIntervalCount");
            this.isAdEnabled = pref.getInt("NativeAdShow", 1) == 1 && Task_NetworkUtils.isNetworkAvailable(context);
        } else {
            this.interval = 5;
            this.isAdEnabled = false;
        }
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return isAdPosition(position) ? VIEW_TYPE_AD : VIEW_TYPE_ITEM;
    }

    @Override
    public int getCount() {
        if (this.signs.isEmpty()) return 0;
        if (!isAdEnabled || interval <= 0) return this.signs.size();
        int totalAds = this.signs.size() / interval;
        return this.signs.size() + totalAds;
    }

    @Override
    public Object getItem(int i) {
        if (isAdPosition(i)) return null;
        int realPos = getRealPosition(i);
        if (realPos >= 0 && realPos < this.signs.size()) {
            return this.signs.get(realPos);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (this.inflater == null) {
            this.inflater = (LayoutInflater) this.f12c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        int viewType = getItemViewType(i);

        if (viewType == VIEW_TYPE_AD) {
            if (view == null) {
                view = this.inflater.inflate(R.layout.task_item_native_ad_row, viewGroup, false);
            }
            RelativeLayout nativeAdContainer = view.findViewById(R.id.native_ad_container);
            if (nativeAdContainer != null && f12c instanceof Activity) {
                Activity activity = (Activity) f12c;
                if (!activity.isFinishing()) {
                    Task_NativeAdUtil.loadNativeAd(nativeAdContainer, activity);
                }
            }
            return view;
        }

        if (view == null) {
            view = this.inflater.inflate(R.layout.task_m_model, viewGroup, false);
        }

        int realPos = getRealPosition(i);
        if (realPos >= 0 && realPos < this.signs.size()) {
            final String text = this.signs.get(realPos).getText();
            int image = this.signs.get(realPos).getImage();
            ((TextView) view.findViewById(R.id.nameTxt)).setText(text);
            ((ImageView) view.findViewById(R.id.movieImage)).setImageResource(image);
            view.setOnClickListener(view2 -> Toast.makeText(Task_CustomAdpter.this.f12c, text, Toast.LENGTH_SHORT).show());
        }
        return view;
    }
}
