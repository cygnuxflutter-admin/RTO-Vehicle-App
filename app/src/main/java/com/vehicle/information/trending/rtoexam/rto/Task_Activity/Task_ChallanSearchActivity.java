package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

public class Task_ChallanSearchActivity extends AppCompatActivity {

    private EditText etChallanInput;
    private MaterialCardView cardChallanResult;
    private TextView tvResVehNo, tvResRtoLoc, tvResStatusBadge, tvStatusDesc;
    private Button btnCheckChallan, btnOpenStatePortal, btnOpenParivahanModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_activity_challan_search);

        RelativeLayout rl_ad = findViewById(R.id.rl_ad);
        if (Task_NetworkUtils.isNetworkAvailable(this)) {
            Task_PreferenceClass taskPreferenceClass = new Task_PreferenceClass(this);
            if (taskPreferenceClass.getInt("BannerAdShow") == 1) {
                Task_LoadAds.loadAdmobBannerAd(this, rl_ad);
            } else {
                if (rl_ad != null) rl_ad.setVisibility(View.GONE);
                View rlBanner = findViewById(R.id.rlBanner);
                if (rlBanner != null) rlBanner.setVisibility(View.GONE);
            }
        }

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        etChallanInput = findViewById(R.id.et_challan_input);
        cardChallanResult = findViewById(R.id.card_challan_result);
        tvResVehNo = findViewById(R.id.tv_res_veh_no);
        tvResRtoLoc = findViewById(R.id.tv_res_rto_loc);
        tvResStatusBadge = findViewById(R.id.tv_res_status_badge);
        tvStatusDesc = findViewById(R.id.tv_status_desc);
        btnCheckChallan = findViewById(R.id.btn_check_challan);
        btnOpenStatePortal = findViewById(R.id.btn_open_state_portal);
        btnOpenParivahanModal = findViewById(R.id.btn_open_parivahan_modal);

        // Check Challan Button
        btnCheckChallan.setOnClickListener(v -> performChallanSearch());

        // Parivahan Server Button
        btnOpenParivahanModal.setOnClickListener(v -> {
            String veh = etChallanInput.getText().toString().trim().toUpperCase().replace(" ", "");
            copyToClipboard(veh);
            Toast.makeText(this, "વાહન નંબર કોપી થઈ ગયો છે! પોર્ટલ પર 'Vehicle Number' સિલેક્ટ કરો", Toast.LENGTH_LONG).show();
            openBrowser("https://echallan.parivahan.gov.in/index/accused-challan");
        });

        // State police portal links
        findViewById(R.id.card_gujarat_challan).setOnClickListener(v -> {
            copyToClipboard(etChallanInput.getText().toString().trim().toUpperCase().replace(" ", ""));
            Toast.makeText(this, "વાહન નંબર કોપી થઈ ગયો છે!", Toast.LENGTH_SHORT).show();
            openBrowser("https://echallan.gujarat.gov.in");
        });

        findViewById(R.id.card_maha_challan).setOnClickListener(v -> {
            copyToClipboard(etChallanInput.getText().toString().trim().toUpperCase().replace(" ", ""));
            Toast.makeText(this, "વાહન નંબર કોપી થઈ ગયો છે!", Toast.LENGTH_SHORT).show();
            openBrowser("https://mahatrafficticket.gov.in");
        });

        findViewById(R.id.card_delhi_challan).setOnClickListener(v -> {
            copyToClipboard(etChallanInput.getText().toString().trim().toUpperCase().replace(" ", ""));
            Toast.makeText(this, "વાહન નંબર કોપી થઈ ગયો છે!", Toast.LENGTH_SHORT).show();
            openBrowser("https://traffic.delhipolice.gov.in/notice/pay-notice");
        });

        // Initial preview
        performChallanSearch();
    }

    private void performChallanSearch() {
        String veh = etChallanInput.getText().toString().trim().toUpperCase();
        if (veh.isEmpty()) {
            Toast.makeText(this, "કૃપા કરીને સાચો વાહન નંબર દાખલ કરો", Toast.LENGTH_SHORT).show();
            return;
        }

        String cleanVeh = veh.replace(" ", "");
        copyToClipboard(cleanVeh);
        cardChallanResult.setVisibility(View.VISIBLE);
        tvResVehNo.setText(veh);

        // Format RTO location based on prefix
        String rtoLoc = "Official RTO Registered Vehicle";
        String statePortalUrl = "https://echallan.gujarat.gov.in";
        String statePortalTitle = "Check on Gujarat Police E-Challan Portal ➔";

        if (cleanVeh.startsWith("GJ")) {
            statePortalUrl = "https://echallan.gujarat.gov.in";
            statePortalTitle = "Check on Gujarat Police E-Challan Portal ➔";
            if (cleanVeh.startsWith("GJ05") || cleanVeh.startsWith("GJ5")) {
                rtoLoc = "Surat (Pal / Surat City), Gujarat RTO";
            } else if (cleanVeh.startsWith("GJ01") || cleanVeh.startsWith("GJ1")) {
                rtoLoc = "Ahmedabad, Gujarat RTO";
            } else if (cleanVeh.startsWith("GJ03") || cleanVeh.startsWith("GJ3")) {
                rtoLoc = "Rajkot, Gujarat RTO";
            } else if (cleanVeh.startsWith("GJ06") || cleanVeh.startsWith("GJ6")) {
                rtoLoc = "Vadodara, Gujarat RTO";
            } else if (cleanVeh.startsWith("GJ27")) {
                rtoLoc = "Ahmedabad East (Vastral), Gujarat RTO";
            } else if (cleanVeh.startsWith("GJ28")) {
                rtoLoc = "Surat Rural (Pal), Gujarat RTO";
            } else {
                rtoLoc = "Gujarat RTO Registered";
            }
        } else if (cleanVeh.startsWith("MH")) {
            statePortalUrl = "https://mahatrafficticket.gov.in";
            statePortalTitle = "Check on Maharashtra Police Portal ➔";
            rtoLoc = "Maharashtra RTO Registered";
        } else if (cleanVeh.startsWith("DL")) {
            statePortalUrl = "https://traffic.delhipolice.gov.in/notice/pay-notice";
            statePortalTitle = "Check on Delhi Police Portal ➔";
            rtoLoc = "Delhi RTO Registered";
        } else {
            statePortalUrl = "https://echallan.parivahan.gov.in/index/accused-challan";
            statePortalTitle = "Check on Official Parivahan Portal ➔";
        }

        tvResRtoLoc.setText(rtoLoc);
        tvResStatusBadge.setText("Copied: " + cleanVeh + " ✓");
        tvStatusDesc.setText("પોલીસ કેમેરા મેમો અને ચલણ રસીદ જોવા માટે નીચેના બટન પર ક્લિક કરો:");

        final String finalUrl = statePortalUrl;
        btnOpenStatePortal.setText(statePortalTitle);
        btnOpenStatePortal.setOnClickListener(v -> {
            copyToClipboard(cleanVeh);
            Toast.makeText(this, cleanVeh + " કોપી થઈ ગયો છે! પોર્ટલ પર પેસ્ટ કરો", Toast.LENGTH_SHORT).show();
            openBrowser(finalUrl);
        });

        Toast.makeText(this, cleanVeh + " કોપી થઈ ગયો છે!", Toast.LENGTH_SHORT).show();
    }

    private void copyToClipboard(String text) {
        if (text == null || text.isEmpty()) return;
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Vehicle Number", text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
        }
    }

    private void openBrowser(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Could not open browser: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}