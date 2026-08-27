package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.Task_FuelCityAdapter;
import com.vehicle.information.trending.rtoexam.rto.R;

/*import com.vehicle.information.trending.rtoexam.rto.databinding.Task_ActivityFuelBinding;*/
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_StateListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

public class Task_FuelCityActivity extends AppCompatActivity {

    Task_FuelCityAdapter adapter;
/*    Task_ActivityFuelBinding binding;*/
    SearchView editText;
    ArrayList<Task_StateListModel> stateList;

    SearchView editTextTextPersonName;
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.task_activity_fuel);
       /* Task_ActivityFuelBinding inflate = Task_ActivityFuelBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());*/

//        this.editTextTextPersonName = (ImageView) findViewById(R.id.editTextTextPersonName);

        this.stateList = new ArrayList<>();
        SearchView searchView = (SearchView) findViewById(R.id.editTextTextPersonName);
        this.editText = searchView;
        searchView.clearFocus();
        this.editText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextSubmit(String str) {
                return false;
            }

            @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextChange(String str) {
                Task_FuelCityActivity.this.filterText(str);
                return true;
            }
        });

        findViewById(R.id.imageView4).setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Task_FuelCityActivity.this.onBackPressed();
            }
        });
        try {
            getdata();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.fuelCityRec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Task_FuelCityAdapter taskFuelCityAdapter = new Task_FuelCityAdapter(this, this.stateList);
        this.adapter = taskFuelCityAdapter;
        recyclerView.setAdapter(taskFuelCityAdapter);
    }

    public String readFile(String str) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open(str), StandardCharsets.UTF_8));
        String str2 = "";
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return str2;
            }
            str2 = str2 + readLine;
        }
    }

    public void getdata() throws JSONException, IOException {
        JSONObject jSONObject = new JSONObject(readFile("allcitystate.json"));
        for (int i = 0; i < jSONObject.length(); i++) {
            String valueOf = String.valueOf(jSONObject.names().get(i));
            this.stateList.add(new Task_StateListModel(valueOf, "null"));
            JSONArray jSONArray = jSONObject.getJSONArray(valueOf);
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                this.stateList.add(new Task_StateListModel(jSONObject2.getString("cityName"), jSONObject2.getString("id")));
            }
        }
    }

    private void filterText(String str) {
        ArrayList<Task_StateListModel> arrayList = new ArrayList<>();
        Iterator<Task_StateListModel> it = this.stateList.iterator();
        while (it.hasNext()) {
            Task_StateListModel next = it.next();
            if (next.getStateName().toLowerCase().contains(str.toLowerCase())) {
                arrayList.add(next);
            }
        }
        if (!arrayList.isEmpty()) {
            this.adapter.setFilteredList(arrayList);
        }
    }
}
