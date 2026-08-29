package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.Task_ExpiryAdapter;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_VehicleDocumentModel;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_ReminderStorage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Task_ExpiryReminderActivity extends AppCompatActivity {

    private RecyclerView rv_vehicles;
    private LinearLayout ll_empty_state;
    private Button btn_add_vehicle;
    private Task_ExpiryAdapter adapter;
    private List<Task_VehicleDocumentModel> vehicleList;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_expiry_reminder);

        RelativeLayout rl_ad = findViewById(R.id.rl_ad);
        if (rl_ad != null) {
            com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds.loadAdmobBannerAd(this, rl_ad);
        }

        dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        rv_vehicles = findViewById(R.id.rv_vehicles);
        ll_empty_state = findViewById(R.id.ll_empty_state);
        btn_add_vehicle = findViewById(R.id.btn_add_vehicle);

        rv_vehicles.setLayoutManager(new LinearLayoutManager(this));
        rv_vehicles.setHasFixedSize(true);

        btn_add_vehicle.setOnClickListener(v -> showAddVehicleDialog());

        loadVehicles();
    }

    private void loadVehicles() {
        vehicleList = Task_ReminderStorage.getSavedVehicles(this);
        adapter = new Task_ExpiryAdapter(this, vehicleList, model -> {
            Task_ReminderStorage.deleteVehicle(Task_ExpiryReminderActivity.this, model.getId());
            Toast.makeText(Task_ExpiryReminderActivity.this, "Vehicle removed from vault", Toast.LENGTH_SHORT).show();
            loadVehicles();
        });
        rv_vehicles.setAdapter(adapter);
        updateEmptyState();
    }

    private void updateEmptyState() {
        if (ll_empty_state != null && adapter != null) {
            ll_empty_state.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        }
    }

    private void showAddVehicleDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_vehicle_document);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        EditText etNumber = dialog.findViewById(R.id.et_dialog_veh_number);
        EditText etName = dialog.findViewById(R.id.et_dialog_veh_name);
        EditText etInsurance = dialog.findViewById(R.id.et_dialog_insurance_date);
        EditText etPuc = dialog.findViewById(R.id.et_dialog_puc_date);
        Button btnSave = dialog.findViewById(R.id.btn_dialog_save_vehicle);
        ImageView ivClose = dialog.findViewById(R.id.iv_dialog_close);

        ivClose.setOnClickListener(v -> dialog.dismiss());

        etInsurance.setOnClickListener(v -> pickDate(etInsurance));
        etPuc.setOnClickListener(v -> pickDate(etPuc));

        btnSave.setOnClickListener(v -> {
            String num = etNumber.getText().toString().trim().toUpperCase();
            String name = etName.getText().toString().trim();
            String ins = etInsurance.getText().toString().trim();
            String puc = etPuc.getText().toString().trim();

            if (num.isEmpty()) {
                Toast.makeText(this, "Please enter vehicle registration number", Toast.LENGTH_SHORT).show();
                return;
            }

            String id = "veh_" + System.currentTimeMillis();
            Task_VehicleDocumentModel model = new Task_VehicleDocumentModel(id, num, name.isEmpty() ? "Vehicle" : name, ins, puc, "15 Mar 2027");
            Task_ReminderStorage.addVehicle(this, model);

            Toast.makeText(this, "Vehicle saved successfully!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            loadVehicles();
        });

        dialog.show();
    }

    private void pickDate(EditText target) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            Calendar selected = Calendar.getInstance();
            selected.set(year, month, dayOfMonth);
            target.setText(dateFormat.format(selected.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }
}
