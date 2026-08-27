package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_NativeAdUtil;
import com.vehicle.information.trending.rtoexam.rto.Task_interfaces.IDatePicker;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.SpinnerDatePicker;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.VehicleUtils;

import org.joda.time.DateTimeConstants;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


public class DLActivity extends AllBaseActivity implements IDatePicker {
    public static ArrayList<String> arrayListDemo = new ArrayList<>();
    public static ArrayList<String> arrayListDemo2 = new ArrayList<>();
    public static Map<String, String> cookies;
    public static String formNumber;
    public static Activity setting_activity;
    private static String str1;
    public static String viewState;
    LinearLayout j;
    String k;
    String l;
    EditText m;
    TextView n;
    RelativeLayout o;
    TextView p;
    Connection.Response q = null;
    String r = "";
    private Task_PreferenceClass taskPreferenceClass;
    @Override
    public void onDialogDismiss() {
    }

    public boolean checkEntry() {
        return this.m.getText().toString().length() + this.n.getText().toString().length() >= 10;
    }

    public String getAbsoluteURL(String str) {
        return "https://parivahan.gov.in" + str;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_dl_number);

        setting_activity = this;
        taskPreferenceClass = new Task_PreferenceClass(this);
        RelativeLayout native_banner_ad_container = findViewById(R.id.native_banner_ad_container);
        if (taskPreferenceClass.getInt("NativeAdShow") == 1){
            Task_NativeAdUtil.loadNativeAd(native_banner_ad_container, this);
        } else {
            native_banner_ad_container.setVisibility(View.GONE);
            findViewById(R.id.ads).setVisibility(View.GONE);
        }

        this.o = (RelativeLayout) findViewById(R.id.progres_layour);
        EditText editText = (EditText) findViewById(R.id.dl_number);
        this.m = editText;
        editText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        this.p = (TextView) findViewById(R.id.search_txt);
        this.j = (LinearLayout) findViewById(R.id.cal_open);
        this.n = (TextView) findViewById(R.id.dob);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.fillnum);
        ImageView imageView = (ImageView) findViewById(R.id.help_no);
        ((ImageView) findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DLActivity.this.onBackPressed();
            }
        });
        this.j.setOnClickListener(new View.OnClickListener() {
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SpinnerDatePicker.getInstance(DLActivity.this).setMaxDate(System.currentTimeMillis()).callback(DLActivity.this).setOkButtonText("Ok").show();
            }
        });
        this.p.setOnClickListener(new View.OnClickListener() {
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!DLActivity.this.isOnline()) {
                    VehicleUtils.showSnake(VehicleUtils.CONNECT, DLActivity.this);
                } else if (!DLActivity.this.checkEntry()) {
                    Toast.makeText(DLActivity.this.getApplicationContext(), "License Number is invalid", Toast.LENGTH_SHORT).show();
                } else if (DLActivity.this.n.getText().toString().length() > 4) {
                    DLActivity dLActivity = DLActivity.this;
                    dLActivity.l = dLActivity.m.getText().toString();
                    DLActivity dLActivity2 = DLActivity.this;
                    dLActivity2.k = dLActivity2.n.getText().toString();
                    new LongOperation().execute("");
                } else {
                    Toast.makeText(DLActivity.this.getApplicationContext(), "Invalid date of Birth", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isOnline() {
        try {
            return ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo().isConnectedOrConnecting();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.o.setVisibility(View.GONE);
    }

    @Override
    public void onOkClick(String str) {
        this.n.setText(str);
    }

    public void MyClick1() {
        Intent intent = new Intent(getApplicationContext(), DLDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("ARRAY", arrayListDemo);
        bundle.putStringArrayList("ARRAY2", arrayListDemo2);
        intent.putExtras(bundle);
        Go_ad_Page(intent);
        overridePendingTransition(0, 0);
    }


    private class LongOperation extends AsyncTask<String, Void, String> {
        public void onProgressUpdate(Void... voidArr) {
        }

        private LongOperation() {
        }

        public String doInBackground(String... strArr) {
            try {
                DLActivity.arrayListDemo.clear();
                DLActivity.arrayListDemo2.clear();
                DLActivity dLActivity = DLActivity.this;
                dLActivity.q = Jsoup.connect(dLActivity.getAbsoluteURL("/rcdlstatus/vahan/rcDlHome.xhtml?pur_cd=101")).method(Connection.Method.GET).userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.101 Safari/537.36").execute();
                DLActivity.cookies = DLActivity.this.q.cookies();
                if (DLActivity.this.q.statusCode() != 200) {
                    return "Executed";
                }
                Document parse = DLActivity.this.q.parse();
                DLActivity.formNumber = parse.select("button[class=ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only]").attr(AppMeasurementSdk.ConditionalUserProperty.NAME);
                DLActivity.viewState = parse.select("input[name=javax.faces.ViewState]").attr(AppMeasurementSdk.ConditionalUserProperty.VALUE);
                parse.getElementsByTag("img").get(1).attr("src");
                DLActivity dLActivity2 = DLActivity.this;
                Connection data = Jsoup.connect(VehicleUtils.SUB_URL).followRedirects(true).timeout(DateTimeConstants.MILLIS_PER_MINUTE).method(Connection.Method.POST).cookies(DLActivity.cookies).referrer("https://parivahan.gov.in").header(HttpConnection.CONTENT_TYPE, "application/x-www-form-urlencoded").header("Host", "parivahan.gov.in").header("Accept", "application/xml, text/xml, */*; q=0.01").header("Accept-Language", "en-US,en;q=0.5").header("Accept-Encoding", "gzip, deflate, br").header("X-Requested-With", "XMLHttpRequest").header("Faces-Request", "partial/ajax").header("Origin", "https://parivahan.gov.in").userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36").data("javax.faces.partial.ajax", "true").data("javax.faces.source", DLActivity.formNumber).data("javax.faces.partial.execute", "@all").data("javax.faces.partial.render", "form_rcdl:pnl_show form_rcdl:pg_show form_rcdl:rcdl_pnl");
                String str = DLActivity.formNumber;
                dLActivity2.r = data.data(str, str).data("form_rcdl", "form_rcdl").data("form_rcdl:tf_dlNO", DLActivity.this.l).data("form_rcdl:tf_dob_input", DLActivity.this.k).data("javax.faces.ViewState", DLActivity.viewState).execute().body();
                StringBuilder sb = new StringBuilder();
                sb.append("<!DOCTYPE html><html><body>");
                String str2 = DLActivity.this.r;
                sb.append(str2.substring(str2.indexOf("<table"), DLActivity.this.r.lastIndexOf("</table>")));
                sb.append("</body></html>");
                String valueOf = String.valueOf(Jsoup.parse(sb.toString()));
                int indexOf = DLActivity.this.r.indexOf("<div class=\"font-bold top-space bottom-space text-capitalize\">") + 62;
                DLActivity dLActivity22 = DLActivity.this;
                dLActivity22.r = dLActivity22.r.substring(indexOf, dLActivity22.r.indexOf("</div>", indexOf)).replaceAll("Registering Authority:", "").trim();
                Log.e("okkk", valueOf);
                Document parse2 = Jsoup.parse(valueOf);
                Element first = parse2.select("table").first();
                String trim = first.select("tr").get(0).select("td").get(1).text().trim();
                DLActivity.arrayListDemo.add(trim);
                String trim2 = first.select("tr").get(1).select("td").get(1).text().trim();
                DLActivity.arrayListDemo.add(trim2);
                Log.e("Status", trim);
                Log.e("Name", trim2);
                Element element = parse2.select("table").get(1);
                DLActivity.arrayListDemo.add(element.select("tr").get(0).select("td").get(1).text());
                DLActivity.arrayListDemo.add(element.select("tr").get(1).select("td").get(1).text());
                Element element2 = parse2.select("table").get(3);
                String replace = element2.select("tr").get(0).select("td").text().replace("Non-Transport", "");
                DLActivity.arrayListDemo.add(replace);
                String replace2 = element2.select("tr").get(1).select("td").text().replace("Transport", "");
                DLActivity.arrayListDemo.add(replace2);
                Log.e("NON_Tran", replace);
                Log.e("TRAN", replace2);
                Iterator<Element> it = parse2.select("table").get(4).select("tr").iterator();
                String str3 = null;
                String str22 = null;
                while (true) {
                    Element element22 = element2;
                    if (!it.hasNext()) {
                        break;
                    }
                    Elements select = it.next().select("td");
                    Iterator<Element> it2 = it;
                    select.get(0).text();
                    String text = select.get(1).text();
                    select.get(2).text();
                    str22 = select.get(3).text();
                    str3 = text;
                    it = it2;
                    element2 = element22;
                    replace = replace;
                }
                DLActivity.arrayListDemo.add(str3);
                DLActivity.arrayListDemo.add(str22);
                DLActivity.arrayListDemo.add(DLActivity.this.l);
                parse2.select("div").get(1);
                parse2.select("div").get(2);
                Element element3 = parse2.select("table").get(5);
                Elements select2 = element3.select("tr");
                element3.select("tr").get(0).select("th").get(0).select("span").text();
                element3.select("tr").get(0).select("th").get(1).select("span").text();
                element3.select("tr").get(0).select("th").get(2).select("span").text();
                for (int i = 1; i < select2.size(); i++) {
                    select2.select("tr").get(i).text();
                    Elements select3 = select2.get(i).select("tr");
                    for (int i2 = 0; i2 < 3; i2++) {
                        String text2 = select3.select("td").get(i2).text();
                        Log.e("ALLTD", text2);
                        DLActivity.arrayListDemo2.add(text2);
                    }
                }
                Log.e("ALLTD11", String.valueOf(DLActivity.arrayListDemo));
                Log.e("ALLTD12", String.valueOf(DLActivity.arrayListDemo2));
                return "Executed";
            } catch (Exception e) {
                e.printStackTrace();
                Intent intent = new Intent(DLActivity.this.getApplicationContext(), DLErrorActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("DLNO", DLActivity.this.l);
                intent.putExtras(bundle);
                DLActivity.this.startActivity(intent);
                return "Executed";
            }
        }

        public void onPostExecute(String str) {
            if (DLActivity.arrayListDemo.size() != 0 && DLActivity.arrayListDemo2.size() != 0) {
                DLActivity.this.o.setVisibility(View.GONE);
                DLActivity.this.MyClick1();
            }
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            DLActivity.this.o.setVisibility(View.VISIBLE);
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
