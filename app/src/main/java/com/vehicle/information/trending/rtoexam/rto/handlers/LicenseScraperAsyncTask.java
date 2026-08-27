package com.vehicle.information.trending.rtoexam.rto.handlers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
//
//import com.vehicle.information.trending.rtoexam.rto.Utils.VehicleUtils;
//import com.vehicle.information.trending.rtoexam.rto.Task_Model.LicenseDetails;
//import com.vehicle.information.trending.rtoexam.rto.utils.GlobalReferenceEngine;
//import com.vehicle.information.trending.rtoexam.rto.utils.Utils;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.LicenseDetails;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.GlobalReferenceEngine;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Utils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.VehicleUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;
import java.util.Map;


public class LicenseScraperAsyncTask extends AsyncTask<String, Void, Void> {
    private String btnIdStr;
    private IResponseCallback callback;
    private Context context;
    private Map<String, String> cookies;
    private ProgressDialog dProgress;
    private String viewStateStr;


    public interface IResponseCallback {
        void onError(String str);

        void onNotFound();

        void onResponse(LicenseDetails licenseDetails);
    }

    public LicenseScraperAsyncTask(Context context2, String str, IResponseCallback iResponseCallback) {
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
            e.printStackTrace();
        }
    }

    /* JADX WARN: Not initialized variable reg: 27, insn: 0x036b: MOVE  (r2 I:??[OBJECT, ARRAY]) = (r27 I:??[OBJECT, ARRAY]), block:B:67:0x036b */
    private void parseDetailsUrl(String str, String str2) {
        String str3;
        String str4 = null;
        String str5;
        Exception e;
        String text5;
        String str32;
        Document parse;
        String str52 = "";
        Map<String, String> map = this.cookies;
        if (map == null) {
            str3 = "Error in your request, please try again later";
        } else if (map.size() <= 0) {
            str3 = "Error in your request, please try again later";
        } else {
            if (Utils.isNullOrEmpty(this.btnIdStr)) {
                str4 = "Error in your request, please try again later";
            } else if (Utils.isNullOrEmpty(this.viewStateStr)) {
                str4 = "Error in your request, please try again later";
            } else {
                try {
                    Connection connect = Jsoup.connect(GlobalReferenceEngine.licenseLocalSourceFinalUrl);
                    connect.followRedirects(true);
                    connect.timeout(VehicleUtils.SERVER_TIMEOUT);
                    connect.method(Connection.Method.POST);
                    connect.cookies(this.cookies);
                    connect.referrer(GlobalReferenceEngine.licenseLocalSourceInitUrl);
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
                    String str42 = this.btnIdStr;
                    connect.data(str42, str42);
                    connect.data("form_rcdl", "form_rcdl");
                    connect.data("javax.faces.partial.render", "form_rcdl:pnl_show form_rcdl:pg_show form_rcdl:rcdl_pnl");
                    connect.data(GlobalReferenceEngine.licenseLocalSourceField1, str);
                    connect.data(GlobalReferenceEngine.licenseLocalSourceField2, str2);
                    connect.data("javax.faces.ViewState", this.viewStateStr);
                    Connection.Response execute = connect.execute();
                    try {
                        if (execute.statusCode() != 200) {
                            this.callback.onError("Error in your request, please try again later");
                            return;
                        } else if (execute.body().contains("No DL Details Found")) {
                            this.callback.onNotFound();
                            return;
                        } else if (execute.body().contains("Technical error")) {
                            this.callback.onError("Error in your request, please try again later");
                            return;
                        } else if (execute.body().contains("showMessageInDialog")) {
                            this.callback.onError(Utils.extractWarningMessage(execute.body(), "license"));
                            return;
                        } else {
                            String body = execute.body();
                            int indexOf = body.indexOf("<div class=\"font-bold top-space bottom-space text-capitalize center-position text-underline\">") + 93;
                            if (indexOf <= 93) {
                                this.callback.onError("Error in your request, please try again later");
                                return;
                            }
                            String trim = body.substring(indexOf, body.indexOf("</div>", indexOf)).replaceAll("Details Of Driving License:", str52).trim();
                            LicenseDetails licenseDetails = new LicenseDetails();
                            licenseDetails.setLicenseNo(trim);
                            licenseDetails.setDob(str2);
                            String substring = body.substring(body.indexOf("<table"), body.lastIndexOf("</table>"));
                            StringBuilder sb = new StringBuilder();
                            try {
                                sb.append("<!DOCTYPE html><html><body>");
                                sb.append(substring);
                                sb.append("</body></html>");
                                Document parse2 = Jsoup.parse(sb.toString());
                                try {
                                    if (parse2 == null || parse2.select("table") == null) {
                                        str5 = "Error in your request, please try again later";
                                        try {
                                            this.callback.onError(str5);
                                            return;
                                        } catch (Exception e2) {
                                            e = e2;
                                            e.printStackTrace();
                                            this.callback.onError(str5);
                                            return;
                                        }
                                    } else {
                                        Element first = parse2.select("table").first();
                                        String text = first.select("tr").get(0).select("td").get(1).text();
                                        String text2 = first.select("tr").get(1).select("td").get(1).text();
                                        String text3 = first.select("tr").get(2).select("td").get(1).text();
                                        String text4 = first.select("tr").get(3).select("td").get(1).text();
                                        Element element = parse2.select("table").get(1);
                                        String text52 = element.select("tr").get(0).select("td").get(1).text();
                                        String text6 = element.select("tr").get(0).select("td").get(2).text();
                                        if (text52.split(":")[1].equalsIgnoreCase("NA")) {
                                            String text7 = element.select("tr").get(1).select("td").get(1).text();
                                            str32 = element.select("tr").get(1).select("td").get(2).text();
                                            text5 = text7;
                                        } else {
                                            str32 = text6;
                                            text5 = text52;
                                        }
                                        Iterator<Element> it = parse2.select("table").get(3).select("tr").iterator();
                                        while (it.hasNext()) {
                                            Element next = it.next();
                                            if (next.select("td").size() > 0) {
                                                parse = parse2;
                                                str52 = str52.concat(next.select("td").get(1).text()).concat(", ");
                                            } else {
                                                parse = parse2;
                                            }
                                            parse2 = parse;
                                        }
                                        licenseDetails.setCurrentStatus(text);
                                        licenseDetails.setHolderName(text2.trim());
                                        licenseDetails.setDateOfIssue(text3.trim());
                                        licenseDetails.setLastTransactionAt(text4.trim());
                                        licenseDetails.setValidFrom(text5.split(":")[1]);
                                        licenseDetails.setValidTo(str32.split(":")[1]);
                                        if (Utils.isNullOrEmpty(str52)) {
                                            licenseDetails.setVehicleClass(str52);
                                        } else {
                                            licenseDetails.setVehicleClass(str52.trim().substring(0, str52.length() - 2));
                                        }
                                        this.callback.onResponse(licenseDetails);
                                        return;
                                    }
                                } catch (Exception e3) {
                                    e = e3;
                                }
                            } catch (Exception e4) {
                                e = e4;
                                str5 = "Error in your request, please try again later";
                            }
                        }
                    } catch (Exception e5) {
                        e = e5;
                        str5 = "Error in your request, please try again later";
                    }
                } catch (Exception e6) {
                    e = e6;
                    str5 = "Error in your request, please try again later";
                }
            }
            this.callback.onError(str4);
            return;
        }
        this.callback.onError(str3);
    }

    private void parseUrl() {
        try {
            Connection connect = Jsoup.connect(GlobalReferenceEngine.licenseLocalSourceInitUrl);
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
            Log.e("LicenseScraperAsyncTask", " " + e.getMessage());
        }
    }
}
