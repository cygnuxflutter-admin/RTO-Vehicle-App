package com.vehicle.information.trending.rtoexam.rto.handlers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.VehicleDetails;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.GlobalReferenceEngine;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Utils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.VehicleUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;


public class ScraperAsyncTask extends AsyncTask<String, Void, Void> {
    private String btnIdStr;
    private IResponseCallback callback;
    private Context context;
    private Map<String, String> cookies;
    private ProgressDialog dProgress;
    private String viewStateStr;

    
    public interface IResponseCallback {
        void onError(String str);

        void onNotFound();

        void onResponse(VehicleDetails vehicleDetails);
    }

    public ScraperAsyncTask(Context context2, String str, IResponseCallback iResponseCallback) {
        this.context = context2;
        this.callback = iResponseCallback;
        if (str != null && !str.isEmpty() && Utils.isActivityFinished(context2)) {
            ProgressDialog progressDialog = new ProgressDialog(context2);
            this.dProgress = progressDialog;
            progressDialog.setMessage(str);
            this.dProgress.setCancelable(false);
            this.dProgress.setCanceledOnTouchOutside(false);
        }
    }

    @Override // android.os.AsyncTask
    public void onPreExecute() {
        ProgressDialog progressDialog;
        super.onPreExecute();
        if (Utils.isActivityFinished(this.context) && (progressDialog = this.dProgress) != null && !progressDialog.isShowing()) {
            this.dProgress.show();
        }
    }

    public Void doInBackground(String... strArr) {
        parseUrl();
        parseDetailsUrl(strArr[0], strArr[1]);
        return null;
    }

    public void onPostExecute(Void voidR) {
        ProgressDialog progressDialog;
        super.onPostExecute(voidR);
        try {
            if (Utils.isActivityFinished(this.context) && (progressDialog = this.dProgress) != null && progressDialog.isShowing()) {
                this.dProgress.dismiss();
            }
        } catch (Exception e) {
        }
    }

    private void parseDetailsUrl(String str, String str2) {
        Map<String, String> map = this.cookies;
        if (map != null && map.size() > 0) {
            if (!Utils.isNullOrEmpty(this.btnIdStr) && !Utils.isNullOrEmpty(this.viewStateStr)) {
                try {
                    Connection connect = Jsoup.connect(GlobalReferenceEngine.localSourceFinalUrl);
                    connect.followRedirects(true);
                    connect.timeout(VehicleUtils.SERVER_TIMEOUT);
                    connect.method(Connection.Method.POST);
                    connect.cookies(this.cookies);
                    connect.referrer(GlobalReferenceEngine.localSourceInitUrl);
                    connect.header(HttpConnection.CONTENT_TYPE, "application/x-www-form-urlencoded");
                    connect.header("Host", GlobalReferenceEngine.localSourceHostUrl);
                    connect.header("Accept", "application/xml, text/xml, */*; q=0.01");
                    connect.header("Accept-Language", "en-US,en;q=0.5");
                    connect.header("Accept-Encoding", "gzip, deflate, br");
                    connect.header("X-Requested-With", "XMLHttpRequest");
                    connect.header("Faces-Request", "partial/ajax");
                    connect.header("Origin", "https://" + GlobalReferenceEngine.localSourceHostUrl);
                    connect.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36");
                    connect.data("javax.faces.partial.ajax", "true");
                    connect.data("javax.faces.source", this.btnIdStr);
                    connect.data("javax.faces.partial.execute", "@all");
                    String str3 = this.btnIdStr;
                    connect.data(str3, str3);
                    connect.data("form_rcdl", "form_rcdl");
                    connect.data("javax.faces.partial.render", "form_rcdl:pnl_show form_rcdl:pg_show form_rcdl:rcdl_pnl");
                    connect.data(GlobalReferenceEngine.localSourceField1, str);
                    connect.data(GlobalReferenceEngine.localSourceField2, str2);
                    connect.data("javax.faces.ViewState", this.viewStateStr);
                    Log.e("MYTAG", "ErrorNo: parseDetailsUrl:" + connect.get());
                    Connection.Response execute = connect.execute();
                    try {
                        if (execute.statusCode() != 200) {
                            this.callback.onError("Error in your request, please try again later");
                            return;
                        } else if (execute.body().contains(VehicleUtils.REG_NOT_EXIT)) {
                            this.callback.onNotFound();
                            return;
                        } else if (execute.body().contains("Technical error")) {
                            this.callback.onError("Error in your request, please try again later");
                            return;
                        } else if (execute.body().contains("showMessageInDialog")) {
                            this.callback.onError(Utils.extractWarningMessage(execute.body(), "registration"));
                            return;
                        } else {
                            String body = execute.body();
                            int indexOf = body.indexOf("<div class=\"font-bold top-space bottom-space text-capitalize\">") + 62;
                            if (indexOf <= 62) {
                                this.callback.onError("Error in your request, please try again later");
                                return;
                            }
                            String trim = body.substring(indexOf, body.indexOf("</div>", indexOf)).replaceAll("Registering Authority:", "").trim();
                            VehicleDetails vehicleDetails = new VehicleDetails();
                            vehicleDetails.setRegistrationAuthority(trim);
                            String substring = body.substring(body.indexOf("<table"), body.lastIndexOf("</table>"));
                            StringBuilder sb = new StringBuilder();
                            try {
                                sb.append("<!DOCTYPE html><html><body>");
                                sb.append(substring);
                                sb.append("</body></html>");
                                Document parse = Jsoup.parse(sb.toString());
                                if (parse == null || parse.select("table") == null) {
                                    this.callback.onError("Error in your request, please try again later");
                                    this.callback.onError("Error in your request, please try again later");
                                    return;
                                }
                                Element first = parse.select("table").first();
                                first.select("tr").get(0).select("td").get(1).text();
                                String text = first.select("tr").get(0).select("td").get(3).text();
                                String text2 = first.select("tr").get(1).select("td").get(1).text();
                                String text3 = first.select("tr").get(1).select("td").get(3).text();
                                String text4 = first.select("tr").get(2).select("td").get(1).text();
                                String text5 = first.select("tr").get(3).select("td").get(1).text();
                                String trim2 = first.select("tr").get(3).select("td").get(3).text().trim();
                                String text6 = first.select("tr").get(4).select("td").get(1).text();
                                Element element = first.select("tr").get(5);
                                if (element != null) {
                                    if (element.select("td").get(1) != null) {
                                        vehicleDetails.setFitnessUpto(element.select("td").get(1).text());
                                    }
                                    if (element.select("td").get(3) != null) {
                                        vehicleDetails.setInsuranceUpto(element.select("td").get(3).text());
                                    }
                                }
                                Element element2 = first.select("tr").get(6);
                                if (element2 != null) {
                                    if (element2.select("td").get(1) != null) {
                                        vehicleDetails.setFuelNorms(element2.select("td").get(1).text());
                                    }
                                    if (element2.select("td").get(3) != null) {
                                        vehicleDetails.setRoadTaxPaidUpto(element2.select("td").get(3).text());
                                    }
                                }
                                vehicleDetails.setRegistrationNo(null);
                                vehicleDetails.setRegistrationDate(text);
                                vehicleDetails.setChassisNo(text2);
                                vehicleDetails.setEngineNo(text3);
                                vehicleDetails.setOwnerName(text4);
                                vehicleDetails.setFuelType(trim2);
                                vehicleDetails.setMakerModel(text6);
                                vehicleDetails.setVehicleClass(text5);
                                this.callback.onResponse(vehicleDetails);
                                return;
                            } catch (Exception e) {
                                this.callback.onError("Error in your request, please try again later");
                                return;
                            }
                        }
                    } catch (Exception e2) {
                    }
                } catch (Exception e3) {
                    Log.e("TAG", "parseDetailsUrl: " + e3.getMessage());
                }
            }
            this.callback.onError("Error in your request, please try again later");
            return;
        }
        this.callback.onError("Error in your request, please try again later");
    }

    private void parseUrl() {
        try {
            Connection connect = Jsoup.connect(GlobalReferenceEngine.localSourceInitUrl);
            connect.followRedirects(true);
            connect.ignoreHttpErrors(true);
            connect.method(Connection.Method.GET);
            connect.timeout(VehicleUtils.SERVER_TIMEOUT);
            Connection.Response execute = connect.execute();
            this.cookies = execute.cookies();
            Document parse = Jsoup.parse(execute.body());
            Elements elementsByAttributeValue = parse.getElementsByAttributeValue(AppMeasurementSdk.ConditionalUserProperty.NAME, "javax.faces.ViewState");
            if (elementsByAttributeValue.size() <= 0) {
                elementsByAttributeValue = parse.getElementsByAttributeValue("id", "j_id1:javax.faces.ViewState:0");
            }
            this.viewStateStr = elementsByAttributeValue.attr(AppMeasurementSdk.ConditionalUserProperty.VALUE);
            Elements select = parse.getElementsByAttributeValueStarting("id", "form_rcdl:j_idt").select("button");
            if (select != null && select.size() > 0) {
                this.btnIdStr = select.get(0).attr("id").trim();
            }
        } catch (Exception e) {
            Log.e("ScraperAsyncTask", " " + e.getMessage());
        }
    }
}
