package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

import java.text.NumberFormat;
import java.util.Locale;

public class Task_LoanEmiActivity extends AllBaseActivity {

    private EditText et_loan_amount;
    private EditText et_interest_rate;
    private EditText et_loan_tenure;
    private TextView tv_emi_amount;
    private TextView tv_principal_amount;
    private TextView tv_total_interest;
    private TextView tv_total_amount;
    private Button btn_calculate;

    private TextView[] amountChips;
    private TextView[] rateChips;
    private TextView[] tenureChips;

    private NumberFormat indianFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_loan_emi);
        RelativeLayout rl_ad = findViewById(R.id.rl_ad);
        if (rl_ad != null) { com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds.loadAdmobBannerAd(this, rl_ad); }

        indianFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        indianFormat.setMaximumFractionDigits(0);

        

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        et_loan_amount = findViewById(R.id.et_loan_amount);
        et_interest_rate = findViewById(R.id.et_interest_rate);
        et_loan_tenure = findViewById(R.id.et_loan_tenure);
        tv_emi_amount = findViewById(R.id.tv_emi_amount);
        tv_principal_amount = findViewById(R.id.tv_principal_amount);
        tv_total_interest = findViewById(R.id.tv_total_interest);
        tv_total_amount = findViewById(R.id.tv_total_amount);
        btn_calculate = findViewById(R.id.btn_calculate);

        setupChips();
        btn_calculate.setOnClickListener(v -> calculateEmi());

        calculateEmi();
    }

    private void setupChips() {
        TextView chip1L = findViewById(R.id.chip_amt_1l);
        TextView chip3L = findViewById(R.id.chip_amt_3l);
        TextView chip5L = findViewById(R.id.chip_amt_5l);
        TextView chip10L = findViewById(R.id.chip_amt_10l);
        TextView chip15L = findViewById(R.id.chip_amt_15l);
        amountChips = new TextView[]{chip1L, chip3L, chip5L, chip10L, chip15L};

        chip1L.setOnClickListener(v -> selectAmountChip("100000", chip1L));
        chip3L.setOnClickListener(v -> selectAmountChip("300000", chip3L));
        chip5L.setOnClickListener(v -> selectAmountChip("500000", chip5L));
        chip10L.setOnClickListener(v -> selectAmountChip("1000000", chip10L));
        chip15L.setOnClickListener(v -> selectAmountChip("1500000", chip15L));

        TextView chipR85 = findViewById(R.id.chip_rate_85);
        TextView chipR95 = findViewById(R.id.chip_rate_95);
        TextView chipR105 = findViewById(R.id.chip_rate_105);
        TextView chipR12 = findViewById(R.id.chip_rate_12);
        rateChips = new TextView[]{chipR85, chipR95, chipR105, chipR12};

        chipR85.setOnClickListener(v -> selectRateChip("8.5", chipR85));
        chipR95.setOnClickListener(v -> selectRateChip("9.5", chipR95));
        chipR105.setOnClickListener(v -> selectRateChip("10.5", chipR105));
        chipR12.setOnClickListener(v -> selectRateChip("12.0", chipR12));

        TextView chipT1 = findViewById(R.id.chip_tenure_1);
        TextView chipT3 = findViewById(R.id.chip_tenure_3);
        TextView chipT5 = findViewById(R.id.chip_tenure_5);
        TextView chipT7 = findViewById(R.id.chip_tenure_7);
        tenureChips = new TextView[]{chipT1, chipT3, chipT5, chipT7};

        chipT1.setOnClickListener(v -> selectTenureChip("1", chipT1));
        chipT3.setOnClickListener(v -> selectTenureChip("3", chipT3));
        chipT5.setOnClickListener(v -> selectTenureChip("5", chipT5));
        chipT7.setOnClickListener(v -> selectTenureChip("7", chipT7));
    }

    private void selectAmountChip(String amount, TextView selected) {
        et_loan_amount.setText(amount);
        highlightChip(amountChips, selected);
        calculateEmi();
    }

    private void selectRateChip(String rate, TextView selected) {
        et_interest_rate.setText(rate);
        highlightChip(rateChips, selected);
        calculateEmi();
    }

    private void selectTenureChip(String tenure, TextView selected) {
        et_loan_tenure.setText(tenure);
        highlightChip(tenureChips, selected);
        calculateEmi();
    }

    private void highlightChip(TextView[] group, TextView selected) {
        for (TextView t : group) {
            if (t == selected) {
                t.setBackgroundResource(R.drawable.bg_chip_selected);
                t.setTextColor(Color.WHITE);
            } else {
                t.setBackgroundResource(R.drawable.bg_chip_unselected);
                t.setTextColor(Color.parseColor("#475569"));
            }
        }
    }

    private void calculateEmi() {
        String pStr = et_loan_amount.getText().toString().trim();
        String rStr = et_interest_rate.getText().toString().trim();
        String tStr = et_loan_tenure.getText().toString().trim();

        if (pStr.isEmpty() || rStr.isEmpty() || tStr.isEmpty()) {
            return;
        }

        try {
            double p = Double.parseDouble(pStr);
            double annualRate = Double.parseDouble(rStr);
            double years = Double.parseDouble(tStr);

            if (p <= 0 || annualRate <= 0 || years <= 0) {
                return;
            }

            double monthlyRate = (annualRate / 12.0) / 100.0;
            double months = years * 12.0;

            double emi = (p * monthlyRate * Math.pow(1.0 + monthlyRate, months)) / (Math.pow(1.0 + monthlyRate, months) - 1.0);
            double totalPayable = emi * months;
            double totalInterest = totalPayable - p;

            tv_emi_amount.setText(formatRupees(emi));
            tv_principal_amount.setText(formatRupees(p));
            tv_total_interest.setText(formatRupees(totalInterest));
            tv_total_amount.setText(formatRupees(totalPayable));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String formatRupees(double amount) {
        try {
            return indianFormat.format(Math.round(amount)).replace(".00", "");
        } catch (Exception e) {
            return "₹ " + String.format(Locale.getDefault(), "%,d", Math.round(amount));
        }
    }
}
