package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vehicle.information.trending.rtoexam.rto.MyApplication;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.Task_RulesAdapter;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_RuleModel;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

import java.util.ArrayList;
import java.util.List;

public class Task_RulesActivity extends AppCompatActivity {

    private ImageView iv_back;
    private EditText et_search_rules;
    private ImageView iv_clear_search;
    private RecyclerView rv_rules;
    private LinearLayout ll_empty_state;
    private Task_RulesAdapter rulesAdapter;
    private List<Task_RuleModel> rulesList;

    private TextView[] categoryChips;
    private String selectedCategory = "All";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_rules);

        RelativeLayout rl_ad = this.findViewById(R.id.rl_ad);
        if (Task_NetworkUtils.isNetworkAvailable(this)) {
            Task_PreferenceClass taskPreferenceClass = new Task_PreferenceClass(this);
            if (taskPreferenceClass.getInt("BannerAdShow") == 1) {
                Task_LoadAds.loadAdmobBannerAd(this, rl_ad);
            } else {
                rl_ad.setVisibility(View.GONE);
                findViewById(R.id.rlBanner).setVisibility(View.GONE);
            }
        }

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        et_search_rules = findViewById(R.id.et_search_rules);
        iv_clear_search = findViewById(R.id.iv_clear_search);
        rv_rules = findViewById(R.id.rv_rules);
        ll_empty_state = findViewById(R.id.ll_empty_state);

        rv_rules.setLayoutManager(new LinearLayoutManager(this));
        rv_rules.setHasFixedSize(true);

        setupCategoryChips();
        setupRulesData();
        setupSearch();
    }

    private void setupCategoryChips() {
        TextView chipAll = findViewById(R.id.chip_all);
        TextView chipReg = findViewById(R.id.chip_registration);
        TextView chipRc = findViewById(R.id.chip_rc_services);
        TextView chipNoc = findViewById(R.id.chip_transfer_noc);
        TextView chipLoan = findViewById(R.id.chip_loan_finance);
        TextView chipComm = findViewById(R.id.chip_commercial);
        TextView chipNorms = findViewById(R.id.chip_vehicle_norms);

        categoryChips = new TextView[]{chipAll, chipReg, chipRc, chipNoc, chipLoan, chipComm, chipNorms};

        chipAll.setOnClickListener(v -> selectCategory("All", chipAll));
        chipReg.setOnClickListener(v -> selectCategory("Registration", chipReg));
        chipRc.setOnClickListener(v -> selectCategory("RC Services", chipRc));
        chipNoc.setOnClickListener(v -> selectCategory("Transfer & NOC", chipNoc));
        chipLoan.setOnClickListener(v -> selectCategory("Loan & Finance", chipLoan));
        chipComm.setOnClickListener(v -> selectCategory("Commercial", chipComm));
        chipNorms.setOnClickListener(v -> selectCategory("Vehicle Norms", chipNorms));
    }

    private void selectCategory(String category, TextView activeChip) {
        this.selectedCategory = category;
        for (TextView chip : categoryChips) {
            if (chip == activeChip) {
                chip.setBackgroundResource(R.drawable.bg_chip_selected);
                chip.setTextColor(Color.WHITE);
            } else {
                chip.setBackgroundResource(R.drawable.bg_chip_unselected);
                chip.setTextColor(Color.parseColor("#475569"));
            }
        }

        if (rulesAdapter != null) {
            String query = et_search_rules.getText().toString();
            rulesAdapter.filter(query, selectedCategory);
            updateEmptyState();
        }
    }

    private void setupRulesData() {
        rulesList = new ArrayList<>();
        rulesList.add(new Task_RuleModel("temp_reg", "Temporary Registration", "1 Month validity & required documents", "REGISTRATION", "FORM 20 • REGISTRATION", R.drawable.icon_3d_temp_reg, "file:///android_asset/temporaryRegistration.html", "#EFF6FF", "#1D4ED8"));
        rulesList.add(new Task_RuleModel("perm_reg", "Permanent Registration", "Road tax, chassis verification & smart card RC", "REGISTRATION", "FORM 20/21 • REGISTRATION", R.drawable.icon_3d_perm_reg, "file:///android_asset/permanentRegistration.html", "#F0FDF4", "#15803D"));
        rulesList.add(new Task_RuleModel("renewal_reg", "Renewal of Registration", "15-Year vehicle fitness renewal procedure", "RC SERVICES", "FORM 25 • RC SERVICES", R.drawable.icon_3d_renewal, "file:///android_asset/rcRenewal.html", "#FEFCE8", "#A16207"));
        rulesList.add(new Task_RuleModel("duplicate_rc", "Duplicate RC Book", "Application for lost, torn or stolen RC", "RC SERVICES", "FORM 26 • RC SERVICES", R.drawable.icon_3d_duplicate_rc, "file:///android_asset/duplicateRC.html", "#FDF2F8", "#BE185D"));
        rulesList.add(new Task_RuleModel("noc", "No Objection Certificate", "Inter-state vehicle transfer & clearance", "TRANSFER & NOC", "FORM 28 • TRANSFER & NOC", R.drawable.icon_3d_noc, "file:///android_asset/Objection.html", "#FAF5FF", "#7E22CE"));
        rulesList.add(new Task_RuleModel("hp_endors", "Loan Hypothecation (HP)", "Adding bank vehicle loan entry in RC", "LOAN & FINANCE", "FORM 34 • LOAN & FINANCE", R.drawable.icon_3d_hp_endorse, "file:///android_asset/endorsement.html", "#EEF2FF", "#4338CA"));
        rulesList.add(new Task_RuleModel("hp_term", "Loan Clearance (NOC)", "Removing loan hypothecation after payoff", "LOAN & FINANCE", "FORM 35 • LOAN & FINANCE", R.drawable.icon_3d_hp_terminate, "file:///android_asset/termination.html", "#ECFDF5", "#047857"));
        rulesList.add(new Task_RuleModel("add_change", "Change of Address", "Updating residential address in RC record", "RC SERVICES", "FORM 33 • RC SERVICES", R.drawable.icon_3d_address_change, "file:///android_asset/addressChange.html", "#FFF7ED", "#C2410C"));
        rulesList.add(new Task_RuleModel("owner_trans", "Ownership Transfer", "Sale, succession & legal vehicle transfer", "TRANSFER & NOC", "FORM 29/30 • TRANSFER & NOC", R.drawable.icon_3d_owner_trans, "file:///android_asset/ownership.html", "#FDF4FF", "#A21CAF"));
        rulesList.add(new Task_RuleModel("reassi_vehi", "State Reassignment", "New registration mark after moving state", "REGISTRATION", "SECTION 47 • REGISTRATION", R.drawable.icon_3d_reassign, "file:///android_asset/reassignment.html", "#F0F9FF", "#0369A1"));
        rulesList.add(new Task_RuleModel("trade_cert", "Trade Certificate", "Vehicle dealer trade license & guidelines", "COMMERCIAL", "FORM 16 • COMMERCIAL", R.drawable.icon_3d_trade_cert, "file:///android_asset/trade.html", "#F0FDFA", "#0F766E"));
        rulesList.add(new Task_RuleModel("cert_issues", "Duplicate Trade Issue", "Replacement of lost dealer trade certificate", "COMMERCIAL", "RULE 38 • COMMERCIAL", R.drawable.icon_3d_cert_issues, "file:///android_asset/duplicateTradeIssue.html", "#F8FAFC", "#334155"));
        rulesList.add(new Task_RuleModel("diplo_vehi", "Diplomatic Vehicles", "Registration for UN & diplomatic missions", "VEHICLE NORMS", "UN / CD • NORMS", R.drawable.icon_3d_diplomatic, "file:///android_asset/diplomatic.html", "#FFF1F2", "#BE123C"));
        rulesList.add(new Task_RuleModel("reg_display", "HSRP Number Plate", "High security number plate standards", "VEHICLE NORMS", "HSRP • NORMS", R.drawable.icon_3d_hsrp, "file:///android_asset/registrationDisplay.html", "#EFF6FF", "#1D4ED8"));

        rulesAdapter = new Task_RulesAdapter(this, rulesList, new Task_RulesAdapter.OnRuleClickListener() {
            @Override
            public void onRuleClick(Task_RuleModel selected) {
                if (selected != null) {
                    Intent intent = new Intent(Task_RulesActivity.this, Task_DetailActivity.class);
                    intent.putExtra("position", selected.getTitle());
                    intent.putExtra("main_url", selected.getHtmlAsset());
                    intent.putExtra("category", selected.getCategory());
                    MyApplication.showInterstitialAd(Task_RulesActivity.this, () -> Task_RulesActivity.this.startActivity(intent));
                }
            }
        });
        rv_rules.setAdapter(rulesAdapter);
    }

    private void setupSearch() {
        et_search_rules.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                if (rulesAdapter != null) {
                    rulesAdapter.filter(query, selectedCategory);
                    if (iv_clear_search != null) {
                        iv_clear_search.setVisibility(query.length() > 0 ? View.VISIBLE : View.GONE);
                    }
                    updateEmptyState();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        iv_clear_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search_rules.setText("");
            }
        });
    }

    private void updateEmptyState() {
        if (ll_empty_state != null && rulesAdapter != null) {
            ll_empty_state.setVisibility(rulesAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}