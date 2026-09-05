package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

import java.text.NumberFormat;
import java.util.Locale;

public class Task_ResaleEstimatorActivity extends AllBaseActivity {

    private TextView tab_type_car;
    private TextView tab_type_bike;
    private EditText et_original_price;
    private Button btn_calc_resale;

    private TextView tv_resale_range;
    private TextView tv_retention_percent;
    private TextView tv_expected_price;
    private TextView tv_depreciation_val;

    private TextView[] priceChips;
    private TextView[] ageChips;
    private TextView[] kmChips;
    private TextView[] condChips;

    private boolean isCar = true;
    private int selectedAge = 3;
    private int selectedKmTier = 1; // 0: <20k, 1: 20-50k, 2: 50k+
    private int selectedCondition = 0; // 0: Good, 1: Excellent, 2: Fair

    private NumberFormat indianFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_resale_estimator);
        RelativeLayout rl_ad = findViewById(R.id.rl_ad);
        if (rl_ad != null) { com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds.loadAdmobBannerAd(this, rl_ad); }

        indianFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        indianFormat.setMaximumFractionDigits(0);

        

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        tab_type_car = findViewById(R.id.tab_type_car);
        tab_type_bike = findViewById(R.id.tab_type_bike);
        et_original_price = findViewById(R.id.et_original_price);
        btn_calc_resale = findViewById(R.id.btn_calc_resale);

        tv_resale_range = findViewById(R.id.tv_resale_range);
        tv_retention_percent = findViewById(R.id.tv_retention_percent);
        tv_expected_price = findViewById(R.id.tv_expected_price);
        tv_depreciation_val = findViewById(R.id.tv_depreciation_val);

        setupToggles();
        setupChips();

        btn_calc_resale.setOnClickListener(v -> calculateResaleValue());
        calculateResaleValue();
    }

    private void setupToggles() {
        tab_type_car.setOnClickListener(v -> {
            isCar = true;
            tab_type_car.setBackgroundResource(R.drawable.bg_chip_selected);
            tab_type_car.setTextColor(Color.WHITE);
            tab_type_bike.setBackgroundResource(R.drawable.bg_chip_unselected);
            tab_type_bike.setTextColor(Color.parseColor("#475569"));
            calculateResaleValue();
        });

        tab_type_bike.setOnClickListener(v -> {
            isCar = false;
            tab_type_bike.setBackgroundResource(R.drawable.bg_chip_selected);
            tab_type_bike.setTextColor(Color.WHITE);
            tab_type_car.setBackgroundResource(R.drawable.bg_chip_unselected);
            tab_type_car.setTextColor(Color.parseColor("#475569"));
            calculateResaleValue();
        });
    }

    private void setupChips() {
        TextView chipP1 = findViewById(R.id.chip_price_1l);
        TextView chipP3 = findViewById(R.id.chip_price_3l);
        TextView chipP6 = findViewById(R.id.chip_price_6l);
        TextView chipP10 = findViewById(R.id.chip_price_10l);
        TextView chipP15 = findViewById(R.id.chip_price_15l);
        priceChips = new TextView[]{chipP1, chipP3, chipP6, chipP10, chipP15};

        chipP1.setOnClickListener(v -> selectPrice("100000", chipP1));
        chipP3.setOnClickListener(v -> selectPrice("300000", chipP3));
        chipP6.setOnClickListener(v -> selectPrice("600000", chipP6));
        chipP10.setOnClickListener(v -> selectPrice("1000000", chipP10));
        chipP15.setOnClickListener(v -> selectPrice("1500000", chipP15));

        TextView chipA1 = findViewById(R.id.chip_age_1);
        TextView chipA2 = findViewById(R.id.chip_age_2);
        TextView chipA3 = findViewById(R.id.chip_age_3);
        TextView chipA5 = findViewById(R.id.chip_age_5);
        TextView chipA7 = findViewById(R.id.chip_age_7);
        ageChips = new TextView[]{chipA1, chipA2, chipA3, chipA5, chipA7};

        chipA1.setOnClickListener(v -> selectAge(1, chipA1));
        chipA2.setOnClickListener(v -> selectAge(2, chipA2));
        chipA3.setOnClickListener(v -> selectAge(3, chipA3));
        chipA5.setOnClickListener(v -> selectAge(5, chipA5));
        chipA7.setOnClickListener(v -> selectAge(7, chipA7));

        TextView chipK1 = findViewById(R.id.chip_km_low);
        TextView chipK2 = findViewById(R.id.chip_km_mid);
        TextView chipK3 = findViewById(R.id.chip_km_high);
        kmChips = new TextView[]{chipK1, chipK2, chipK3};

        chipK1.setOnClickListener(v -> selectKm(0, chipK1));
        chipK2.setOnClickListener(v -> selectKm(1, chipK2));
        chipK3.setOnClickListener(v -> selectKm(2, chipK3));

        TextView chipCG = findViewById(R.id.chip_cond_good);
        TextView chipCE = findViewById(R.id.chip_cond_excel);
        TextView chipCF = findViewById(R.id.chip_cond_fair);
        condChips = new TextView[]{chipCG, chipCE, chipCF};

        chipCG.setOnClickListener(v -> selectCondition(0, chipCG));
        chipCE.setOnClickListener(v -> selectCondition(1, chipCE));
        chipCF.setOnClickListener(v -> selectCondition(2, chipCF));
    }

    private void selectPrice(String price, TextView sel) {
        et_original_price.setText(price);
        highlightChip(priceChips, sel);
        calculateResaleValue();
    }

    private void selectAge(int age, TextView sel) {
        selectedAge = age;
        highlightChip(ageChips, sel);
        calculateResaleValue();
    }

    private void selectKm(int kmTier, TextView sel) {
        selectedKmTier = kmTier;
        highlightChip(kmChips, sel);
        calculateResaleValue();
    }

    private void selectCondition(int cond, TextView sel) {
        selectedCondition = cond;
        highlightChip(condChips, sel);
        calculateResaleValue();
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

    private void calculateResaleValue() {
        String priceStr = et_original_price.getText().toString().trim();
        if (priceStr.isEmpty()) return;

        try {
            double originalPrice = Double.parseDouble(priceStr);
            if (originalPrice <= 0) return;

            // Base age retention (IRDAI schedule)
            double retentionFactor;
            switch (selectedAge) {
                case 1: retentionFactor = 0.85; break;
                case 2: retentionFactor = 0.80; break;
                case 3: retentionFactor = 0.70; break;
                case 5: retentionFactor = 0.50; break;
                case 7: retentionFactor = 0.35; break;
                default: retentionFactor = 0.65; break;
            }

            // KM adjustment
            double kmMultiplier = 1.0;
            if (selectedKmTier == 0) kmMultiplier = 1.04;
            else if (selectedKmTier == 2) kmMultiplier = 0.92;

            // Condition adjustment
            double condMultiplier = 1.0;
            if (selectedCondition == 1) condMultiplier = 1.05; // Excellent
            else if (selectedCondition == 2) condMultiplier = 0.93; // Fair

            // Vehicle type factor
            double typeFactor = isCar ? 1.0 : 0.97;

            double finalRetention = retentionFactor * kmMultiplier * condMultiplier * typeFactor;
            if (finalRetention > 0.95) finalRetention = 0.95;
            if (finalRetention < 0.20) finalRetention = 0.20;

            double expectedPrice = originalPrice * finalRetention;
            double minPrice = expectedPrice * 0.95;
            double maxPrice = expectedPrice * 1.05;

            double deprPercent = (1.0 - finalRetention) * 100.0;

            tv_resale_range.setText(formatRupees(minPrice) + " - " + formatRupees(maxPrice));
            tv_retention_percent.setText(String.format(Locale.getDefault(), "~%.0f%% of original value retained", finalRetention * 100.0));
            tv_expected_price.setText(formatRupees(expectedPrice));
            tv_depreciation_val.setText(String.format(Locale.getDefault(), "%.1f%% lost", deprPercent));

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
