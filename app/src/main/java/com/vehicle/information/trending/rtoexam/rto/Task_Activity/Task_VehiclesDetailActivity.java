package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_VehiclseDetailRespondModel;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

import java.util.Calendar;


public class Task_VehiclesDetailActivity extends AllBaseActivity {
    int final_date;
    int final_month;
    int final_year;
    int finalmonth;
    String rgdate;
    String rgmonth;
    TextView txt_bodytype;
    TextView txt_chssno;
    TextView txt_color;
    TextView txt_engno;
    TextView txt_fitnessupto;
    TextView txt_fuelnorms;
    TextView txt_fueltype;
    TextView txt_inscom;
    TextView txt_insupto;
    TextView txt_makmodel;
    TextView txt_manufa;
    TextView txt_oname;
    TextView txt_ownserno;
    TextView txt_pucupto;
    TextView txt_rcstatus;
    TextView txt_regaut;
    TextView txt_regdate;
    TextView txt_regno;
    TextView txt_roadtext;
    TextView txt_seatcap;
    TextView txt_upload_weight;
    TextView txt_vehage;
    TextView txt_vehclass;
    private Task_VehiclseDetailRespondModel vehicleDetailsResponse;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.task_activity_vehicles_details);
       //AdsManager.getInstance().loadBanner(this);

        RelativeLayout rl_ad = this.findViewById(R.id.rl_ad);
        if (Task_NetworkUtils.isNetworkAvailable(this)) {
            Task_PreferenceClass taskPreferenceClass = new Task_PreferenceClass(this);
            if (taskPreferenceClass.getInt("BannerAdShow") == 1){
                Task_LoadAds.loadAdmobBannerAd(this, rl_ad);
            } else {
                rl_ad.setVisibility(View.GONE);
            findViewById(R.id.rlBanner).setVisibility(View.GONE);
            }
        }


        ((ImageView) findViewById(R.id.iv_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task_VehiclesDetailActivity.this.onBackPressed();
            }
        });

        this.vehicleDetailsResponse = (Task_VehiclseDetailRespondModel) getIntent().getParcelableExtra("detail");
        this.txt_oname = (TextView) findViewById(R.id.oname);
        this.txt_regno = (TextView) findViewById(R.id.regno);
        this.txt_regdate = (TextView) findViewById(R.id.regdate);
        this.txt_ownserno = (TextView) findViewById(R.id.owserno);
        this.txt_inscom = (TextView) findViewById(R.id.inscompany);
        this.txt_regaut = (TextView) findViewById(R.id.regauthority);
        this.txt_makmodel = (TextView) findViewById(R.id.makermodel);
        this.txt_engno = (TextView) findViewById(R.id.engno);
        this.txt_chssno = (TextView) findViewById(R.id.chassno);
        this.txt_fueltype = (TextView) findViewById(R.id.fueltype);
        this.txt_vehclass = (TextView) findViewById(R.id.vehclass);
        this.txt_fuelnorms = (TextView) findViewById(R.id.fuelnorms);
        this.txt_vehage = (TextView) findViewById(R.id.vehage);
        this.txt_insupto = (TextView) findViewById(R.id.insupto);
        this.txt_fitnessupto = (TextView) findViewById(R.id.fitupto);
        this.txt_roadtext = (TextView) findViewById(R.id.roadtaxupto);
        this.txt_pucupto = (TextView) findViewById(R.id.pucupto);
        this.txt_color = (TextView) findViewById(R.id.color);
        this.txt_seatcap = (TextView) findViewById(R.id.setcap);
        this.txt_upload_weight = (TextView) findViewById(R.id.upweight);
        this.txt_bodytype = (TextView) findViewById(R.id.bodytype);
        this.txt_manufa = (TextView) findViewById(R.id.manufacture);
        this.txt_rcstatus = (TextView) findViewById(R.id.rcstatus);
        if (this.vehicleDetailsResponse.getDetails().getRegistrationDate() != null) {
            String registrationDate = this.vehicleDetailsResponse.getDetails().getRegistrationDate();
            this.rgdate = registrationDate.substring(0, 2);
            this.rgmonth = registrationDate.substring(3, 6);
        }
        if (this.rgmonth.equals("Jan")) {
            this.finalmonth = 1;
        } else if (this.rgmonth.equals("Feb")) {
            this.finalmonth = 2;
        } else if (this.rgmonth.equals("Mar")) {
            this.finalmonth = 3;
        } else if (this.rgmonth.equals("Apr")) {
            this.finalmonth = 4;
        } else if (this.rgmonth.equals("May")) {
            this.finalmonth = 5;
        } else if (this.rgmonth.equals("Jun")) {
            this.finalmonth = 6;
        } else if (this.rgmonth.equals("Jul")) {
            this.finalmonth = 7;
        } else if (this.rgmonth.equals("Aug")) {
            this.finalmonth = 8;
        } else if (this.rgmonth.equals("Sep")) {
            this.finalmonth = 9;
        } else if (this.rgmonth.equals("Oct")) {
            this.finalmonth = 10;
        } else if (this.rgmonth.equals("Nov")) {
            this.finalmonth = 11;
        } else if (this.rgmonth.equals("Dec")) {
            this.finalmonth = 12;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.get(5);
        calendar.get(2);
        calendar.get(1);
        this.txt_oname.setText(this.vehicleDetailsResponse.getDetails().getOwnerName());
        this.txt_regno.setText(this.vehicleDetailsResponse.getDetails().getRegistrationNo());
        this.txt_regdate.setText(this.vehicleDetailsResponse.getDetails().getRegistrationDate());
        this.txt_ownserno.setText(this.vehicleDetailsResponse.getDetails().getOwnershipDesc());
        this.txt_inscom.setText(this.vehicleDetailsResponse.getDetails().getInsuranceCompany());
        this.txt_regaut.setText(this.vehicleDetailsResponse.getDetails().getRegistrationAuthority());
        this.txt_makmodel.setText(this.vehicleDetailsResponse.getDetails().getMakerModel());
        this.txt_engno.setText(this.vehicleDetailsResponse.getDetails().getEngineNo());
        this.txt_chssno.setText(this.vehicleDetailsResponse.getDetails().getChassisNo());
        this.txt_fueltype.setText(this.vehicleDetailsResponse.getDetails().getFuelType());
        this.txt_vehclass.setText(this.vehicleDetailsResponse.getDetails().getVehicleClass());
        this.txt_fuelnorms.setText(this.vehicleDetailsResponse.getDetails().getFuelNorms());
        TextView textView = this.txt_vehage;
        textView.setText(this.final_year + " Years " + this.final_month + " Months " + this.final_date + " Days");
        this.txt_insupto.setText(this.vehicleDetailsResponse.getDetails().getInsuranceUpto());
        this.txt_fitnessupto.setText(this.vehicleDetailsResponse.getDetails().getFitnessUpto());
        this.txt_roadtext.setText(this.vehicleDetailsResponse.getDetails().getRoadTaxPaidUpto());
        this.txt_pucupto.setText(this.vehicleDetailsResponse.getDetails().getPucUpto());
        this.txt_color.setText(this.vehicleDetailsResponse.getDetails().getVehicleColor());
        this.txt_seatcap.setText(this.vehicleDetailsResponse.getDetails().getSeatCapacity());
        this.txt_upload_weight.setText(this.vehicleDetailsResponse.getDetails().getUnloadWeight());
        this.txt_bodytype.setText(this.vehicleDetailsResponse.getDetails().getBodyTypeDesc());
        this.txt_manufa.setText(this.vehicleDetailsResponse.getDetails().getManufactureMonthYear());
        this.txt_rcstatus.setText(this.vehicleDetailsResponse.getDetails().getRcStatus());
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }



    public void age(int i, int i2, int i3, int i4, int i5, int i6) {
        int[] iArr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (i4 > i) {
            i += iArr[i5 - 1];
            i2--;
        }
        if (i5 > i2) {
            i3--;
            i2 += 12;
        }
        this.final_date = i - i4;
        this.final_month = i2 - i5;
        this.final_year = i3 - i6;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
