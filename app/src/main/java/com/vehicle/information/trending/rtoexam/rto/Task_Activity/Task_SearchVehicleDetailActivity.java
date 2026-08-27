package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.vehicle.information.trending.rtoexam.rto.Task_ListenerData.Task_TaskHandler;
import com.google.gson.Gson;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_VehiclseDetailRespondModel;
import com.vehicle.information.trending.rtoexam.rto.R;
import org.json.JSONObject;


public class Task_SearchVehicleDetailActivity extends AppCompatActivity {
    private String actionName;
    private String registrationNo;
    private String type;
    public Task_VehiclseDetailRespondModel vehicleDetailsResponse;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.task_activity_search_results_loader);
        this.registrationNo = getIntent().getStringExtra("REGISTRATION_NO");
        this.actionName = getIntent().getStringExtra("ACTION");
        this.type = getIntent().getStringExtra("TYPE");
        Log.d("VehicleDetailsAPI", "Task_SearchVehicleDetailActivity: onCreate | Reg No: " + this.registrationNo);
        loadWebServerData(true);
    }

    private void loadWebServerData(boolean z) {
        Log.d("VehicleDetailsAPI", "Task_SearchVehicleDetailActivity: loadWebServerData() called for Reg No: " + this.registrationNo);
        Task_TaskHandler.newInstance().fetchVehicleDetails(this, this.registrationNo, false, z, false, new Task_TaskHandler.ResponseHandler<JSONObject>() {
            @Override
            public void onError(String str) {
                Log.e("VehicleDetailsAPI", "Task_SearchVehicleDetailActivity: API Error Callback -> " + str);
                Toast.makeText(Task_SearchVehicleDetailActivity.this, "web Data Not Found!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONObject jSONObject) {
                Log.d("VehicleDetailsAPI", "Task_SearchVehicleDetailActivity: API Response Callback -> " + (jSONObject != null ? jSONObject.toString() : "null"));
                Task_SearchVehicleDetailActivity.this.manipulateJsonResponse(jSONObject);
            }
        });
    }

    public void manipulateJsonResponse(JSONObject jSONObject) {
        Task_VehiclseDetailRespondModel m_rtoVehiclseDetailRespond = (Task_VehiclseDetailRespondModel) new Gson().fromJson(jSONObject.toString(), Task_VehiclseDetailRespondModel.class);
        this.vehicleDetailsResponse = m_rtoVehiclseDetailRespond;
        if (m_rtoVehiclseDetailRespond.getStatusCode() != 200) {
            finish();
            Toast.makeText(this, "Vehicle Details Not Found!!", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(new Intent(this, Task_VehiclesDetailActivity.class).putExtra("detail", (Parcelable) this.vehicleDetailsResponse));
        finish();
    }
}
