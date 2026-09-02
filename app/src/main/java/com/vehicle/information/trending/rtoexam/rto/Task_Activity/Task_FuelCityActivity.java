package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.Task_FuelCityAdapter;
import com.vehicle.information.trending.rtoexam.rto.Task_Extra.Task_Constant;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_StateListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Task_FuelCityActivity extends AppCompatActivity {

    private Task_FuelCityAdapter adapter;
    private EditText etSearch;
    private ImageView ivClearSearch;
    private TextView tvHeaderTitle;
    private TextView tvHeaderSubtitle;

    private final Map<String, ArrayList<Task_StateListModel>> stateCityMap = new LinkedHashMap<>();
    private final ArrayList<Task_StateListModel> stateList = new ArrayList<>();
    private final ArrayList<Task_StateListModel> currentDisplayList = new ArrayList<>();

    private String selectedState = null;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.task_activity_fuel);

        tvHeaderTitle = findViewById(R.id.tv_header_title);
        tvHeaderSubtitle = findViewById(R.id.tv_header_subtitle);
        etSearch = findViewById(R.id.editTextTextPersonName);
        ivClearSearch = findViewById(R.id.iv_clear_search);

        if (ivClearSearch != null) {
            ivClearSearch.setOnClickListener(v -> {
                if (etSearch != null) {
                    etSearch.setText("");
                }
            });
        }

        findViewById(R.id.imageView4).setOnClickListener(v -> onBackPressed());

        try {
            getData();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = findViewById(R.id.fuelCityRec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    hideKeyboard();
                }
            }
        });

        adapter = new Task_FuelCityAdapter(this, currentDisplayList, this::onItemClicked);
        recyclerView.setAdapter(adapter);

        showStateList();

        if (etSearch != null) {
            etSearch.addTextChangedListener(new android.text.TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String query = s != null ? s.toString() : "";
                    if (ivClearSearch != null) {
                        ivClearSearch.setVisibility(query.isEmpty() ? View.GONE : View.VISIBLE);
                    }
                    filterText(query);
                }

                @Override
                public void afterTextChanged(android.text.Editable s) {}
            });
        }
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager) getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
        if (etSearch != null) {
            etSearch.clearFocus();
        }
    }

    private void showStateList() {
        selectedState = null;
        if (tvHeaderTitle != null) tvHeaderTitle.setText("Select State");
        if (tvHeaderSubtitle != null) tvHeaderSubtitle.setText("Choose your state to view cities");
        if (etSearch != null) {
            etSearch.setHint("Search State or City...");
            etSearch.setText("");
            etSearch.clearFocus();
        }
        currentDisplayList.clear();
        currentDisplayList.addAll(stateList);
        if (adapter != null) {
            adapter.setFilteredList(currentDisplayList);
        }
    }

    private void showCityList(String stateName) {
        selectedState = stateName;
        if (tvHeaderTitle != null) tvHeaderTitle.setText("Select City");
        if (tvHeaderSubtitle != null) tvHeaderSubtitle.setText(stateName + " - Choose City");
        if (etSearch != null) {
            etSearch.setHint("Search city in " + stateName + "...");
            etSearch.setText("");
            etSearch.clearFocus();
        }
        currentDisplayList.clear();
        ArrayList<Task_StateListModel> cities = stateCityMap.get(stateName);
        if (cities != null) {
            currentDisplayList.addAll(cities);
        }
        if (adapter != null) {
            adapter.setFilteredList(currentDisplayList);
        }
    }

    private void onItemClicked(Task_StateListModel item) {
        if (item.isState()) {
            showCityList(item.getStateName());
        } else {
            // City selected
            SharedPreferences.Editor edit = getSharedPreferences(Task_Constant.MY_PREFS_NAME, 0).edit();
            edit.putString("cityName", item.getStateName());
            edit.putString("cityId", item.getId());
            edit.apply();
            finish();
        }
    }

    private void filterText(String query) {
        String trimmed = query != null ? query.trim().toLowerCase() : "";
        ArrayList<Task_StateListModel> filtered = new ArrayList<>();

        if (selectedState == null) {
            // In State Selection Mode
            if (trimmed.isEmpty()) {
                filtered.addAll(stateList);
            } else {
                // 1. First add matching states
                for (Task_StateListModel state : stateList) {
                    if (state.getStateName().toLowerCase().contains(trimmed)) {
                        filtered.add(state);
                    }
                }
                // 2. Also search matching cities across all states for direct pick
                for (Map.Entry<String, ArrayList<Task_StateListModel>> entry : stateCityMap.entrySet()) {
                    String stateName = entry.getKey();
                    for (Task_StateListModel city : entry.getValue()) {
                        if (city.getStateName().toLowerCase().contains(trimmed)) {
                            filtered.add(new Task_StateListModel(city.getStateName(), city.getId(), stateName, false));
                        }
                    }
                }
            }
        } else {
            // In City Selection Mode for selectedState
            ArrayList<Task_StateListModel> cities = stateCityMap.get(selectedState);
            if (cities != null) {
                if (trimmed.isEmpty()) {
                    filtered.addAll(cities);
                } else {
                    for (Task_StateListModel city : cities) {
                        if (city.getStateName().toLowerCase().contains(trimmed)) {
                            filtered.add(city);
                        }
                    }
                }
            }
        }

        if (adapter != null) {
            adapter.setFilteredList(filtered);
        }
    }

    @Override
    public void onBackPressed() {
        if (selectedState != null) {
            // Return back to state selection
            showStateList();
        } else {
            super.onBackPressed();
        }
    }

    public String readFile(String str) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open(str), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String readLine;
        while ((readLine = bufferedReader.readLine()) != null) {
            sb.append(readLine);
        }
        bufferedReader.close();
        return sb.toString();
    }

    public void getData() throws JSONException, IOException {
        stateCityMap.clear();
        stateList.clear();

        JSONObject jsonObject = new JSONObject(readFile("allcitystate.json"));
        JSONArray names = jsonObject.names();
        if (names == null) return;

        List<String> stateNamesSorted = new ArrayList<>();
        for (int i = 0; i < names.length(); i++) {
            stateNamesSorted.add(names.getString(i));
        }
        Collections.sort(stateNamesSorted);

        for (String stateName : stateNamesSorted) {
            JSONArray cityArray = jsonObject.getJSONArray(stateName);
            ArrayList<Task_StateListModel> cityList = new ArrayList<>();

            for (int j = 0; j < cityArray.length(); j++) {
                JSONObject cityObj = cityArray.getJSONObject(j);
                String cName = cityObj.getString("cityName");
                String cId = cityObj.getString("id");
                cityList.add(new Task_StateListModel(cName, cId, stateName, false));
            }

            stateCityMap.put(stateName, cityList);
            stateList.add(new Task_StateListModel(stateName, "STATE", cityList.size() + " cities available", true));
        }
    }
}
