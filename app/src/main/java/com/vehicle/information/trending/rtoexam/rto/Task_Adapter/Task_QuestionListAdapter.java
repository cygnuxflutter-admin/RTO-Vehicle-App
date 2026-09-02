package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vehicle.information.trending.rtoexam.rto.Task_Extra.Task_QueConstructor;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_NativeAdUtil;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_RewardVideoManager;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;
import java.util.ArrayList;

public class Task_QuestionListAdapter extends BaseAdapter {
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_AD = 1;
    private static final int VIEW_TYPE_UNLOCK = 2;

    private final ArrayList<Task_QueConstructor> arrayList;
    private final Context context;
    private final LayoutInflater inflater;
    private final String str_language;
    private final int interval;
    private final boolean isAdEnabled;
    private final boolean isRewardGateEnabled;
    private final int freeLimit;
    private boolean isUnlocked;
    private final Task_PreferenceClass pref;

    public Task_QuestionListAdapter(Context context, ArrayList<Task_QueConstructor> arrayList, String str) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<>();
        if (arrayList != null) {
            this.arrayList.addAll(arrayList);
        }
        this.str_language = str != null ? str : "english";

        if (context != null) {
            this.pref = new Task_PreferenceClass(context);
            this.interval = pref.getAdsStatus("NativeAdIntervalCount");
            this.isAdEnabled = pref.getInt("NativeAdShow", 1) == 1 && Task_NetworkUtils.isNetworkAvailable(context);
            this.isRewardGateEnabled = pref.getAdsStatus("RewardUnlockEnabled") == 1;
            this.freeLimit = pref.getAdsStatus("FreeQuestionsCount");
            this.isUnlocked = pref.getInt("is_questions_unlocked", 0) == 1 || !isRewardGateEnabled;
        } else {
            this.pref = null;
            this.interval = 5;
            this.isAdEnabled = false;
            this.isRewardGateEnabled = false;
            this.freeLimit = 20;
            this.isUnlocked = true;
        }
    }

    private boolean isLocked() {
        return !isUnlocked && isRewardGateEnabled && arrayList.size() > freeLimit;
    }

    private int getEffectiveQuestionsCount() {
        return isLocked() ? freeLimit : arrayList.size();
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
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (isLocked() && position == getCount() - 1) {
            return VIEW_TYPE_UNLOCK;
        }
        return isAdPosition(position) ? VIEW_TYPE_AD : VIEW_TYPE_ITEM;
    }

    @Override
    public int getCount() {
        int effCount = getEffectiveQuestionsCount();
        if (effCount == 0) return 0;
        int totalAds = (isAdEnabled && interval > 0) ? (effCount / interval) : 0;
        int total = effCount + totalAds;
        return isLocked() ? total + 1 : total;
    }

    @Override
    public Object getItem(int i) {
        if (getItemViewType(i) != VIEW_TYPE_ITEM) return null;
        int realPos = getRealPosition(i);
        if (realPos >= 0 && realPos < this.arrayList.size()) {
            return this.arrayList.get(realPos);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int viewType = getItemViewType(i);

        if (viewType == VIEW_TYPE_UNLOCK) {
            if (view == null) {
                view = this.inflater.inflate(R.layout.task_item_unlock_card, viewGroup, false);
            }
            TextView tvTitle = view.findViewById(R.id.tv_unlock_title);
            TextView tvDesc = view.findViewById(R.id.tv_unlock_desc);
            View btnUnlock = view.findViewById(R.id.btn_watch_unlock);

            if ("gujarati".equalsIgnoreCase(this.str_language)) {
                if (tvTitle != null) tvTitle.setText("૧૫૦+ વધુ પ્રશ્નો અને જવાબો અનલોક કરો");
                if (tvDesc != null) tvDesc.setText("RTO પરીક્ષાની સંપૂર્ણ તૈયારી માટે ૧ નાનો વીડિયો જોઈને બાકીના તમામ પ્રશ્નો ફ્રીમાં અનલોક કરો.");
                if (btnUnlock instanceof TextView) ((TextView) btnUnlock).setText("🎬 વીડિયો જુઓ અને અનલોક કરો");
            } else if ("hindi".equalsIgnoreCase(this.str_language)) {
                if (tvTitle != null) tvTitle.setText("१५०+ और प्रश्न और उत्तर अनलॉक करें");
                if (tvDesc != null) tvDesc.setText("RTO परीक्षा की पूरी तैयारी के लिए १ छोटा वीडियो देखकर सभी प्रश्न मुफ्त में अनलॉक करें।");
                if (btnUnlock instanceof TextView) ((TextView) btnUnlock).setText("🎬 वीडियो देखें और अनलॉक करें");
            } else {
                if (tvTitle != null) tvTitle.setText("Unlock 150+ More Questions");
                if (tvDesc != null) tvDesc.setText("Watch a short video ad to unlock all remaining questions for free.");
                if (btnUnlock instanceof TextView) ((TextView) btnUnlock).setText("🎬 Watch Video to Unlock");
            }

            if (btnUnlock != null) {
                btnUnlock.setOnClickListener(v -> {
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        Task_RewardVideoManager.showRewardVideoAd(activity, new Task_RewardVideoManager.OnRewardAdLoadInterface() {
                            @Override
                            public void onAdClose(boolean isWithReward) {
                                if (isWithReward) {
                                    isUnlocked = true;
                                    if (pref != null) pref.setInt("is_questions_unlocked", 1);
                                    notifyDataSetChanged();
                                    String msg = "gujarati".equalsIgnoreCase(str_language) ? "🎉 તમામ પ્રશ્નો સફળતાપૂર્વક અનલોક થયા!" :
                                            ("hindi".equalsIgnoreCase(str_language) ? "🎉 सभी प्रश्न सफलतापूर्वक अनलॉक हो गए!" : "🎉 All questions unlocked successfully!");
                                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onAdFail() {
                                isUnlocked = true;
                                if (pref != null) pref.setInt("is_questions_unlocked", 1);
                                notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
            return view;
        }

        if (viewType == VIEW_TYPE_AD) {
            if (view == null) {
                view = this.inflater.inflate(R.layout.task_item_native_ad_row, viewGroup, false);
            }
            RelativeLayout nativeAdContainer = view.findViewById(R.id.native_ad_container);
            if (nativeAdContainer != null && context instanceof Activity) {
                Activity activity = (Activity) context;
                if (!activity.isFinishing()) {
                    Task_NativeAdUtil.loadNativeAd(nativeAdContainer, activity);
                }
            }
            return view;
        }

        if (view == null) {
            view = this.inflater.inflate(R.layout.task_add_questions, viewGroup, false);
        }

        TextView textView = view.findViewById(R.id.tv_question);
        TextView textView2 = view.findViewById(R.id.tv_question_data);
        TextView textView3 = view.findViewById(R.id.tv_answer);
        TextView textView4 = view.findViewById(R.id.tv_answer_data);

        int realPos = getRealPosition(i);
        if (realPos >= 0 && realPos < this.arrayList.size()) {
            Task_QueConstructor q = this.arrayList.get(realPos);

            if ("gujarati".equalsIgnoreCase(this.str_language)) {
                textView.setText("પ્રશ્ન");
                textView3.setText("જવાબ");
            } else if ("hindi".equalsIgnoreCase(this.str_language)) {
                textView.setText("प्रश्न");
                textView3.setText("उत्तर");
            } else {
                textView.setText("Que");
                textView3.setText("Ans");
            }

            textView2.setText(q.getQuestion());
            textView4.setText(q.getAnswer());
        }

        return view;
    }
}