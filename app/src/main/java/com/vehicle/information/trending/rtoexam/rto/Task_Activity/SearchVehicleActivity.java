package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.PointerIconCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.vehicle.information.trending.rtoexam.rto.Activity.AllBaseActivity;
//import com.vehicle.information.trending.rtoexam.rto.AdAdmob;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.RecentSearchHistoryAdapter;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.SearchVehicleHistoryTableAdapter;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.VehicleDetailsTableAdapter;
import com.vehicle.information.trending.rtoexam.rto.Task_Extra.BaseBottomSheet;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.SearchVehicleHistory;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_NativeAdUtil;
import com.vehicle.information.trending.rtoexam.rto.Task_interfaces.IRecyclerViewClickListener;
import com.vehicle.information.trending.rtoexam.rto.Task_interfaces.IRecyclerViewLongClickListener;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.GlobalTracker;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Utils;
//import com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.adapters.IRecyclerViewClickListener;
//import com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.adapters.IRecyclerViewLongClickListener;
//import com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.adapters.RecentSearchHistoryAdapter;
//import com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.database.SearchVehicleHistoryTableAdapter;
//import com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.database.VehicleDetailsTableAdapter;
//import com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.datamodels.SearchVehicleHistory;
//import com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.helpers.GlobalTracker;
//import com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.helpers.ToastHelper;
//import com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.utils.Utils;
//import com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.widget.BaseBottomSheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


public class SearchVehicleActivity extends AllBaseActivity implements IRecyclerViewClickListener, IRecyclerViewLongClickListener {
    public RecentSearchHistoryAdapter adapter;
    private Button btnSearchDetails;
    private CardView cvRecentSearches;
    public EditText etFirst;
    public List<SearchVehicleHistory> historyList;
    public ImageView imageClear;
//    public ImageView imageVoice;
    private boolean isHistoryItemClicked = false;
    private String type;
    private Task_PreferenceClass taskPreferenceClass;

    public static void showToast(Context context, String str, boolean z) {
        Toast.makeText(context, str, !z ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_search_vehicle);

        taskPreferenceClass = new Task_PreferenceClass(this);
        RelativeLayout native_banner_ad_container = findViewById(R.id.native_banner_ad_container);
        if (taskPreferenceClass.getInt("NativeAdShow") == 1){
            Task_NativeAdUtil.loadNativeAd(native_banner_ad_container, this);
        } else {
            native_banner_ad_container.setVisibility(View.GONE);
            findViewById(R.id.ads).setVisibility(View.GONE);
        }

        this.type = getIntent().getStringExtra("TYPE");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        String str = this.type;
        if (str == null || str.equalsIgnoreCase("RC")) {
            ((TextView) toolbar.findViewById(R.id.action_bar_title)).setText(R.string.activity_search_vehicle);
        } else if (this.type.equalsIgnoreCase("INSURANCE")) {
            ((TextView) toolbar.findViewById(R.id.action_bar_title)).setText(R.string.activity_search_insurance);
        } else if (this.type.equalsIgnoreCase("FINANCE")) {
            ((TextView) toolbar.findViewById(R.id.action_bar_title)).setText(R.string.activity_search_finance);
        }
        EditText editText = (EditText) findViewById(R.id.first_part);
        this.etFirst = editText;
        editText.setOnKeyListener(new View.OnKeyListener() { // from class: com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.SearchVehicleActivity.1
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i2, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 0) {
                    return false;
                }
                if (i2 != 66) {
                    if (i2 == 67 && SearchVehicleActivity.this.etFirst.getText().length() == 0) {
                        SearchVehicleActivity.this.etFirst.requestFocus();
                    }
                    return false;
                } else if (SearchVehicleActivity.this.isValidRegistrationNo()) {
                    showToast(SearchVehicleActivity.this, "Please enter vehicle registration number", false);
                    return true;
                } else {
                    SearchVehicleActivity searchVehicleActivity = SearchVehicleActivity.this;
                    searchVehicleActivity.btnSearchVehicleDetailsClickListener(searchVehicleActivity.etFirst.getText().toString());
                    return true;
                }
            }
        });
        applyEditTextFilters();
        this.imageClear = (ImageView) findViewById(R.id.iv_clear);
//        this.imageVoice = (ImageView) findViewById(R.id.iv_voice);
        this.btnSearchDetails = (Button) findViewById(R.id.btnSearchDetails);
        this.cvRecentSearches = (CardView) findViewById(R.id.cvRecentSearches);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSearchHistories);
        this.historyList = new ArrayList();
        RecentSearchHistoryAdapter recentSearchHistoryAdapter = new RecentSearchHistoryAdapter(this, this.type, this, this);
        this.adapter = recentSearchHistoryAdapter;
        recentSearchHistoryAdapter.setHasStableIds(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(this.adapter);
        recyclerView.setNestedScrollingEnabled(false);
        this.etFirst.addTextChangedListener(new TextWatcher() { // from class: com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.SearchVehicleActivity.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i22, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i22, int i3) {
                if (Utils.isNullOrEmpty(charSequence.toString()) || charSequence.toString().length() == 0) {
                    SearchVehicleActivity.this.imageClear.setVisibility(View.GONE);
//                    SearchVehicleActivity.this.imageVoice.setVisibility(View.VISIBLE);
                    return;
                }
                SearchVehicleActivity.this.imageClear.setVisibility(View.VISIBLE);
//                SearchVehicleActivity.this.imageVoice.setVisibility(View.GONE);
            }
        });
        this.imageClear.setOnClickListener(new View.OnClickListener() { // from class: com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.SearchVehicleActivity.3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SearchVehicleActivity.this.etFirst.setText("");
            }
        });
//        this.imageVoice.setOnClickListener(new View.OnClickListener() { // from class: com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.SearchVehicleActivity.4
//            @Override // android.view.View.OnClickListener
//            public final void onClick(View view) {
//                SearchVehicleActivity.this.checkPermission();
//            }
//        });
        this.btnSearchDetails.setOnClickListener(new View.OnClickListener() { // from class: com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.SearchVehicleActivity.5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (SearchVehicleActivity.this.isValidRegistrationNo()) {
                    showToast(SearchVehicleActivity.this, "Please enter vehicle registration number", false);
                    return;
                }
                SearchVehicleActivity searchVehicleActivity = SearchVehicleActivity.this;
                searchVehicleActivity.btnSearchVehicleDetailsClickListener(searchVehicleActivity.etFirst.getText().toString());
            }
        });
    }

    private void applyEditTextFilters() {
        this.etFirst.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(11)});
    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") == 0) {
            listen();
        } else if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.RECORD_AUDIO"}, PointerIconCompat.TYPE_HAND);
        } else {
            listen();
        }
    }

    private void listen() {
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("calling_package", getPackageName());
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        intent.putExtra("android.speech.extra.PROMPT", getString(R.string.placeholder_speech_prompt_add_vehicle_no));
        intent.putExtra("android.speech.extra.LANGUAGE", Locale.getDefault());
        try {
            startActivityForResult(intent, 1001);
        } catch (Exception e) {
            e.printStackTrace();
            showToast(this, getString(R.string.txt_device_no_speech_recognition), true);
        }
        this.etFirst.setText("");
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i != 1002) {
            return;
        }
        if (iArr.length <= 0 || iArr[0] != 0) {
            Bundle bundle = new Bundle();
            bundle.putString("TITLE", "Permission Denied");
            bundle.putString("MESSAGE", "Kindly allow audio permission from app settings to enable voice input feature.");
            BaseBottomSheet baseBottomSheet = new BaseBottomSheet();
            baseBottomSheet.setArguments(bundle);
            baseBottomSheet.show(getSupportFragmentManager(), "acknowledgement_bottom_sheet");
            return;
        }
        checkPermission();
    }

    public boolean isValidRegistrationNo() {
        return Utils.isNullOrEmpty(this.etFirst.getText().toString());
    }

    public void btnSearchVehicleDetailsClickListener(String str) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null && getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } else if (inputMethodManager != null && this.btnSearchDetails != null && this.btnSearchDetails.getWindowToken() != null) {
                inputMethodManager.hideSoftInputFromWindow(this.btnSearchDetails.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!Utils.isNetworkConnected(this)) {
            showToast(this, getString(R.string.app_internet_msg), false);
            return;
        }
        String formatString = Utils.formatString(str);
        if (Utils.isNullOrEmpty(formatString) || formatString.length() < 5) {
            showToast(this, "Please enter the correct vehicle no!", true);
            return;
        }
        Log.d("VehicleDetailsAPI", "SearchVehicleActivity: Search triggered for Vehicle Reg No: " + formatString + " | Type: " + this.type);
        try {
            Intent intent = new Intent(this, SearchVehicleDetailsLoaderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(GlobalTracker.EVENT_VEHICLE_NO, formatString);
            bundle.putString("content_type", GlobalTracker.BUTTON);
            intent.putExtra("REGISTRATION_NO", formatString);
            intent.putExtra("ACTION", "SAVE");
            intent.putExtra("TYPE", this.type);
            startActivity(intent);
        } catch (Exception e) {
            Log.e("VehicleDetailsAPI", "Error starting SearchVehicleDetailsLoaderActivity: " + e.getMessage(), e);
            showToast(this, "Error starting search. Please try again.", true);
        }
    }

    public void showOrHideHistoryElements(boolean z) {
        this.cvRecentSearches.setVisibility(z ? View.VISIBLE : View.GONE);
    }

    private void loadSearchHistories() {
        this.historyList = new SearchVehicleHistoryTableAdapter(this).getSearchVehicleHistoryList(true, 20);
        Bundle bundle = new Bundle();
        bundle.putString("item_name", "Vehicle search histories loaded");
        List<SearchVehicleHistory> list = this.historyList;
        if (list == null) {
            bundle.putString("item_list", "0");
        } else {
            bundle.putString("item_list", String.valueOf(list.size()));
        }
        bundle.putString("content_type", GlobalTracker.BUTTON);
        List<SearchVehicleHistory> list2 = this.historyList;
        if (list2 != null && list2.size() > 0) {
            showOrHideHistoryElements(true);
            this.adapter.updateListData(this.historyList);
        }
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.adapters.IRecyclerViewClickListener
    public void onItemSelected(int i) {
        SearchVehicleHistory searchVehicleHistory;
        List<SearchVehicleHistory> list = this.historyList;
        if (list != null && list.size() > 0 && i >= 0 && i < this.historyList.size() && (searchVehicleHistory = this.historyList.get(i)) != null) {
            this.isHistoryItemClicked = true;
            String str = this.type;
            if (str == null || (!str.equalsIgnoreCase("INSURANCE") && !this.type.equalsIgnoreCase("FINANCE"))) {
                btnSearchVehicleDetailsClickListener(searchVehicleHistory.getRegistrationNo());
            } else {
                this.etFirst.setText(searchVehicleHistory.getRegistrationNo());
            }
        }
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.adapters.IRecyclerViewLongClickListener
    public void onItemLongClick(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.txt_confirm_delete_search_history);
        builder.setPositiveButton(R.string.txt_yes, new DialogInterface.OnClickListener() { // from class: com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.SearchVehicleActivity.6
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                SearchVehicleHistory searchVehicleHistory;
                List<SearchVehicleHistory> list = SearchVehicleActivity.this.historyList;
                if (list != null && list.size() > 0 && i2 < SearchVehicleActivity.this.historyList.size() && (searchVehicleHistory = SearchVehicleActivity.this.historyList.get(i2)) != null) {
                    boolean z = true;
                    try {
                        new SearchVehicleHistoryTableAdapter(SearchVehicleActivity.this.getApplicationContext()).deleteHistoryById(String.valueOf(searchVehicleHistory.getId()), true);
                        new VehicleDetailsTableAdapter(SearchVehicleActivity.this.getApplicationContext()).deleteHistoryByArgs(searchVehicleHistory.getRegistrationNo());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SearchVehicleActivity.this.historyList.remove(i2);
                    SearchVehicleActivity.this.adapter.notifyDataSetChanged();
                    SearchVehicleActivity searchVehicleActivity = SearchVehicleActivity.this;
                    if (searchVehicleActivity.historyList.size() <= 0) {
                        z = false;
                    }
                    searchVehicleActivity.showOrHideHistoryElements(z);
                }
            }
        });
        builder.setNegativeButton(R.string.txt_no, (DialogInterface.OnClickListener) null).show();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        ArrayList<String> stringArrayListExtra;
        String str;
        super.onActivityResult(i, i2, intent);
        if (i == 1001 && i2 == -1 && intent != null && (stringArrayListExtra = intent.getStringArrayListExtra("android.speech.extra.RESULTS")) != null && !stringArrayListExtra.isEmpty()) {
            Iterator<String> it = stringArrayListExtra.iterator();
            while (true) {
                if (it.hasNext()) {
                    String str2 = it.next();
                    str = Utils.formatString(str2);
                    if (!Utils.isNullOrEmpty(str)) {
                        break;
                    }
                } else {
                    str = "";
                    break;
                }
            }
            if (Utils.isNullOrEmpty(str)) {
                showToast(this, "Invalid registration no.", true);
            } else {
                this.etFirst.setText(str);
            }
        }
    }

//    @Override // android.app.Activity
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.share_details, menu);
//        return true;
//    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
            return true;
        } else if (itemId != R.id.action_share) {
            super.onOptionsItemSelected(menuItem);
            return true;
        } else {
            GlobalTracker.from(this).sendSelectButtonEvent(GlobalTracker.BUTTON_SHARE_APP);
            Utils.shareTo3rdPartyApps(this);
            return true;
        }
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.Activity.AllBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (!this.isHistoryItemClicked) {
            loadSearchHistories();
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
