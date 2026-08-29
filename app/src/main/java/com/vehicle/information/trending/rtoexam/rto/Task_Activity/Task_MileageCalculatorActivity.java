package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class Task_MileageCalculatorActivity extends AppCompatActivity {

    private TextView tab_trip_cost;
    private TextView tab_actual_mileage;
    private LinearLayout ll_inputs_trip;
    private LinearLayout ll_inputs_mileage;

    // Header results
    private TextView tv_result_header;
    private TextView tv_primary_result;
    private TextView tv_primary_subtext;
    private TextView tv_sec_label_1;
    private TextView tv_sec_val_1;
    private TextView tv_sec_label_2;
    private TextView tv_sec_val_2;

    // Inputs Trip
    private EditText et_trip_distance;
    private EditText et_trip_fuel_price;
    private EditText et_trip_mileage;
    private Button btn_calc_trip;
    private TextView[] mileageChips;

    // Inputs Tank
    private EditText et_odo_start;
    private EditText et_odo_end;
    private EditText et_fuel_filled;
    private EditText et_actual_fuel_price;
    private Button btn_calc_mileage;

    private boolean isTripMode = true;
    private NumberFormat indianFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_mileage_calculator);
        RelativeLayout rl_ad = findViewById(R.id.rl_ad);
        if (rl_ad != null) { com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds.loadAdmobBannerAd(this, rl_ad); }

        indianFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        indianFormat.setMaximumFractionDigits(0);

        

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        tab_trip_cost = findViewById(R.id.tab_trip_cost);
        tab_actual_mileage = findViewById(R.id.tab_actual_mileage);
        ll_inputs_trip = findViewById(R.id.ll_inputs_trip);
        ll_inputs_mileage = findViewById(R.id.ll_inputs_mileage);

        tv_result_header = findViewById(R.id.tv_result_header);
        tv_primary_result = findViewById(R.id.tv_primary_result);
        tv_primary_subtext = findViewById(R.id.tv_primary_subtext);
        tv_sec_label_1 = findViewById(R.id.tv_sec_label_1);
        tv_sec_val_1 = findViewById(R.id.tv_sec_val_1);
        tv_sec_label_2 = findViewById(R.id.tv_sec_label_2);
        tv_sec_val_2 = findViewById(R.id.tv_sec_val_2);

        et_trip_distance = findViewById(R.id.et_trip_distance);
        et_trip_fuel_price = findViewById(R.id.et_trip_fuel_price);
        et_trip_mileage = findViewById(R.id.et_trip_mileage);
        btn_calc_trip = findViewById(R.id.btn_calc_trip);

        et_odo_start = findViewById(R.id.et_odo_start);
        et_odo_end = findViewById(R.id.et_odo_end);
        et_fuel_filled = findViewById(R.id.et_fuel_filled);
        et_actual_fuel_price = findViewById(R.id.et_actual_fuel_price);
        btn_calc_mileage = findViewById(R.id.btn_calc_mileage);

        setupTabs();
        setupChips();

        btn_calc_trip.setOnClickListener(v -> calculateTripCost());
        btn_calc_mileage.setOnClickListener(v -> calculateActualMileage());

        calculateTripCost();
    }

    private void setupTabs() {
        tab_trip_cost.setOnClickListener(v -> {
            isTripMode = true;
            tab_trip_cost.setBackgroundResource(R.drawable.bg_chip_selected);
            tab_trip_cost.setTextColor(Color.WHITE);
            tab_actual_mileage.setBackgroundResource(R.drawable.bg_chip_unselected);
            tab_actual_mileage.setTextColor(Color.parseColor("#475569"));

            ll_inputs_trip.setVisibility(View.VISIBLE);
            ll_inputs_mileage.setVisibility(View.GONE);
            calculateTripCost();
        });

        tab_actual_mileage.setOnClickListener(v -> {
            isTripMode = false;
            tab_actual_mileage.setBackgroundResource(R.drawable.bg_chip_selected);
            tab_actual_mileage.setTextColor(Color.WHITE);
            tab_trip_cost.setBackgroundResource(R.drawable.bg_chip_unselected);
            tab_trip_cost.setTextColor(Color.parseColor("#475569"));

            ll_inputs_trip.setVisibility(View.GONE);
            ll_inputs_mileage.setVisibility(View.VISIBLE);
            calculateActualMileage();
        });
    }

    private void setupChips() {
        TextView chip15 = findViewById(R.id.chip_mil_15);
        TextView chip20 = findViewById(R.id.chip_mil_20);
        TextView chip35 = findViewById(R.id.chip_mil_35);
        TextView chip50 = findViewById(R.id.chip_mil_50);
        mileageChips = new TextView[]{chip15, chip20, chip35, chip50};

        chip15.setOnClickListener(v -> selectMileageChip("15", chip15));
        chip20.setOnClickListener(v -> selectMileageChip("20", chip20));
        chip35.setOnClickListener(v -> selectMileageChip("35", chip35));
        chip50.setOnClickListener(v -> selectMileageChip("50", chip50));
    }

    private void selectMileageChip(String mileage, TextView selected) {
        et_trip_mileage.setText(mileage);
        for (TextView t : mileageChips) {
            if (t == selected) {
                t.setBackgroundResource(R.drawable.bg_chip_selected);
                t.setTextColor(Color.WHITE);
            } else {
                t.setBackgroundResource(R.drawable.bg_chip_unselected);
                t.setTextColor(Color.parseColor("#475569"));
            }
        }
        calculateTripCost();
    }

    private void calculateTripCost() {
        String distStr = et_trip_distance.getText().toString().trim();
        String priceStr = et_trip_fuel_price.getText().toString().trim();
        String milStr = et_trip_mileage.getText().toString().trim();

        if (distStr.isEmpty() || priceStr.isEmpty() || milStr.isEmpty()) return;

        try {
            double dist = Double.parseDouble(distStr);
            double price = Double.parseDouble(priceStr);
            double mil = Double.parseDouble(milStr);

            if (dist <= 0 || price <= 0 || mil <= 0) return;

            double litersNeeded = dist / mil;
            double totalCost = litersNeeded * price;
            double costPerKm = totalCost / dist;

            tv_result_header.setText("TOTAL ESTIMATED TRIP COST");
            tv_primary_result.setText(formatRupees(totalCost));
            tv_primary_subtext.setText("estimated fuel expense for " + String.format(Locale.getDefault(), "%.0f km", dist));

            tv_sec_label_1.setText("Fuel Needed");
            tv_sec_val_1.setText(String.format(Locale.getDefault(), "%.2f Litres", litersNeeded));

            tv_sec_label_2.setText("Cost Per KM");
            tv_sec_val_2.setText(String.format(Locale.getDefault(), "₹ %.2f / km", costPerKm));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculateActualMileage() {
        String startStr = et_odo_start.getText().toString().trim();
        String endStr = et_odo_end.getText().toString().trim();
        String fuelStr = et_fuel_filled.getText().toString().trim();
        String priceStr = et_actual_fuel_price.getText().toString().trim();

        if (startStr.isEmpty() || endStr.isEmpty() || fuelStr.isEmpty()) return;

        try {
            double startKm = Double.parseDouble(startStr);
            double endKm = Double.parseDouble(endStr);
            double fuelLiters = Double.parseDouble(fuelStr);
            double price = priceStr.isEmpty() ? 0.0 : Double.parseDouble(priceStr);

            if (endKm <= startKm || fuelLiters <= 0) {
                Toast.makeText(this, "End Odometer must be greater than Start Odometer", Toast.LENGTH_SHORT).show();
                return;
            }

            double distTravelled = endKm - startKm;
            double actualMileage = distTravelled / fuelLiters;
            double costPerKm = (price > 0) ? ((fuelLiters * price) / distTravelled) : 0.0;

            tv_result_header.setText("ACTUAL VEHICLE MILEAGE");
            tv_primary_result.setText(String.format(Locale.getDefault(), "%.2f km/L", actualMileage));
            tv_primary_subtext.setText("based on " + String.format(Locale.getDefault(), "%.0f km", distTravelled) + " driven with " + fuelLiters + "L fuel");

            tv_sec_label_1.setText("Distance Driven");
            tv_sec_val_1.setText(String.format(Locale.getDefault(), "%.0f km", distTravelled));

            tv_sec_label_2.setText("Cost Per KM");
            if (costPerKm > 0) {
                tv_sec_val_2.setText(String.format(Locale.getDefault(), "₹ %.2f / km", costPerKm));
            } else {
                tv_sec_val_2.setText(String.format(Locale.getDefault(), "%.2f km/L", actualMileage));
            }

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
