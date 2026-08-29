package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.Task_FormsHubAdapter;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_FormModel;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

import java.util.ArrayList;
import java.util.List;

public class Task_FormsHubActivity extends AppCompatActivity {

    private EditText et_search_forms;
    private ImageView iv_clear_search;
    private RecyclerView rv_forms;
    private LinearLayout ll_empty_state;
    private Task_FormsHubAdapter formsAdapter;
    private List<Task_FormModel> formsList;

    private TextView[] categoryChips;
    private String selectedCategory = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_forms_hub);

        RelativeLayout rl_ad = findViewById(R.id.rl_ad);
        if (rl_ad != null) {
            com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds.loadAdmobBannerAd(this, rl_ad);
        }

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        et_search_forms = findViewById(R.id.et_search_forms);
        iv_clear_search = findViewById(R.id.iv_clear_search);
        rv_forms = findViewById(R.id.rv_forms);
        ll_empty_state = findViewById(R.id.ll_empty_state);

        rv_forms.setLayoutManager(new LinearLayoutManager(this));
        rv_forms.setHasFixedSize(true);

        setupChips();
        setupFormsData();
        setupSearch();
    }

    private void setupChips() {
        TextView chipAll = findViewById(R.id.chip_all);
        TextView chipReg = findViewById(R.id.chip_registration);
        TextView chipNoc = findViewById(R.id.chip_transfer_noc);
        TextView chipLoan = findViewById(R.id.chip_loan_finance);
        TextView chipLic = findViewById(R.id.chip_duplicate_licence);

        categoryChips = new TextView[]{chipAll, chipReg, chipNoc, chipLoan, chipLic};

        chipAll.setOnClickListener(v -> selectCategory("All", chipAll));
        chipReg.setOnClickListener(v -> selectCategory("Registration", chipReg));
        chipNoc.setOnClickListener(v -> selectCategory("Transfer & NOC", chipNoc));
        chipLoan.setOnClickListener(v -> selectCategory("Loan / Finance", chipLoan));
        chipLic.setOnClickListener(v -> selectCategory("RC & Licence", chipLic));
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

        if (formsAdapter != null) {
            String query = et_search_forms.getText().toString();
            formsAdapter.filter(query, selectedCategory);
            ll_empty_state.setVisibility(formsAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        }
    }

    private void setupFormsData() {
        formsList = new ArrayList<>();
        formsList.add(new Task_FormModel("Form 20", "Application for Registration of a Motor Vehicle", "Mandatory form submitted to RTO for new vehicle permanent registration & number allocation.", "Registration", "₹ 300 - ₹ 600", R.drawable.icon_3d_temp_reg));
        formsList.add(new Task_FormModel("Form 21", "Sale Certificate", "Official certificate issued by the authorized automobile dealer for purchase proof.", "Registration", "Provided by Dealer", R.drawable.icon_3d_perm_reg));
        formsList.add(new Task_FormModel("Form 25", "Renewal of Certificate of Fitness & RC", "Required after 15 years from original registration to renew vehicle fitness.", "RC & Licence", "₹ 600 - ₹ 1,000", R.drawable.icon_3d_renewal));
        formsList.add(new Task_FormModel("Form 26", "Application for Issue of Duplicate RC", "Submitted when the original Smart Card or RC Book is lost, stolen, or damaged.", "RC & Licence", "₹ 150 - ₹ 300", R.drawable.icon_3d_duplicate_rc));
        formsList.add(new Task_FormModel("Form 28", "No Objection Certificate (NOC)", "Required for inter-state vehicle transfer and moving to another state RTO jurisdiction.", "Transfer & NOC", "₹ 100", R.drawable.icon_3d_noc));
        formsList.add(new Task_FormModel("Form 29", "Notice of Transfer of Ownership", "Form submitted by the seller notifying RTO of vehicle sale to the buyer.", "Transfer & NOC", "₹ 150", R.drawable.icon_3d_owner_trans));
        formsList.add(new Task_FormModel("Form 30", "Report of Transfer of Ownership", "Official confirmation form signed jointly by buyer & seller for RC name change.", "Transfer & NOC", "₹ 150 - ₹ 300", R.drawable.icon_3d_reassign));
        formsList.add(new Task_FormModel("Form 31", "Transfer of Ownership (Succession/Death)", "Application for transfer of ownership on death of the registered owner.", "Transfer & NOC", "₹ 200", R.drawable.icon_3d_cert_issues));
        formsList.add(new Task_FormModel("Form 33", "Intimation of Change of Address", "Submitted within 30 days of changing residence to update RC record.", "RC & Licence", "₹ 100", R.drawable.icon_3d_address_change));
        formsList.add(new Task_FormModel("Form 34", "Making Entry of Hypothecation (Bank Loan)", "Form to record bank loan / financier hypothecation agreement on the vehicle RC.", "Loan / Finance", "₹ 500", R.drawable.icon_3d_hp_endorse));
        formsList.add(new Task_FormModel("Form 35", "Termination of Hypothecation (Loan Payoff)", "Form submitted after full loan repayment to remove bank name from RC.", "Loan / Finance", "₹ 100", R.drawable.icon_3d_hp_terminate));
        formsList.add(new Task_FormModel("Form 16", "Application for Trade Certificate", "Dealership license application for displaying unregistered demo vehicles.", "Registration", "₹ 1,000", R.drawable.icon_3d_trade_cert));
        formsList.add(new Task_FormModel("Form 60", "Declaration Form (No PAN Card)", "Income tax declaration form when buyer does not hold a PAN Card.", "Registration", "Free", R.drawable.icon_3d_diplomatic));
        formsList.add(new Task_FormModel("Form 2", "Application for Learner's Licence (LL)", "Official application submitted on Parivahan Sarathi for learning licence.", "RC & Licence", "₹ 150 - ₹ 200", R.drawable.icon_3d_hsrp));

        formsAdapter = new Task_FormsHubAdapter(this, formsList, this::showFormDetailsDialog);
        rv_forms.setAdapter(formsAdapter);
    }

    private void showFormDetailsDialog(Task_FormModel form) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_form_details);
        RelativeLayout rl_ad = findViewById(R.id.rl_ad);
        if (rl_ad != null) { com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds.loadAdmobBannerAd(this, rl_ad); }
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        TextView tvNumber = dialog.findViewById(R.id.tv_dialog_form_number);
        TextView tvTitle = dialog.findViewById(R.id.tv_dialog_form_title);
        TextView tvCategory = dialog.findViewById(R.id.tv_dialog_form_category);
        TextView tvDesc = dialog.findViewById(R.id.tv_dialog_form_desc);
        TextView tvFee = dialog.findViewById(R.id.tv_dialog_form_fee);
        ImageView ivIcon = dialog.findViewById(R.id.iv_dialog_form_icon);
        Button btnShare = dialog.findViewById(R.id.btn_dialog_share);
        ImageView ivClose = dialog.findViewById(R.id.iv_dialog_close);

        tvNumber.setText(form.getFormNumber());
        tvTitle.setText(form.getTitle());
        tvCategory.setText(form.getCategory());
        tvDesc.setText(form.getDescription());
        tvFee.setText("Official Govt Fee: " + form.getFeeInfo());
        ivIcon.setImageResource(form.getIconResId());

        ivClose.setOnClickListener(v -> dialog.dismiss());

        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareBody = "📌 Parivahan RTO " + form.getFormNumber() + ": " + form.getTitle() + "\n\n"
                    + "📝 Purpose: " + form.getDescription() + "\n"
                    + "💰 Estimated Fee: " + form.getFeeInfo() + "\n"
                    + "📂 Category: " + form.getCategory() + "\n\n"
                    + "Download all official RTO forms on Parivahan: https://parivahan.gov.in";
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, form.getFormNumber() + " Details");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(shareIntent, "Share " + form.getFormNumber() + " Info"));
        });

        dialog.show();
    }

    private void setupSearch() {
        et_search_forms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                if (formsAdapter != null) {
                    formsAdapter.filter(query, selectedCategory);
                    iv_clear_search.setVisibility(query.length() > 0 ? View.VISIBLE : View.GONE);
                    ll_empty_state.setVisibility(formsAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        iv_clear_search.setOnClickListener(v -> et_search_forms.setText(""));
    }
}
