package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Task_DetailActivity extends AppCompatActivity {

    private ImageView iv_back;
    private ImageView iv_share_rule;
    private TextView txt_header;
    private TextView tv_sub_header;
    private WebView txt_desc;
    private ProgressBar pb_loader;

    private String ruleTitle = "Rules of RTO";
    private String rawTextContent = "";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_detail);

        RelativeLayout rl_ad = this.findViewById(R.id.rl_ad);
        if (Task_NetworkUtils.isNetworkAvailable(this)) {
            Task_PreferenceClass taskPreferenceClass = new Task_PreferenceClass(this);
            if (taskPreferenceClass.getInt("BannerAdShow") == 1) {
                Task_LoadAds.loadAdmobBannerAd(this, rl_ad);
            } else {
                rl_ad.setVisibility(View.GONE);
                findViewById(R.id.rlBanner).setVisibility(View.GONE);
            }
        }

        iv_back = findViewById(R.id.iv_back);
        iv_share_rule = findViewById(R.id.iv_share_rule);
        txt_header = findViewById(R.id.txt_header);
        tv_sub_header = findViewById(R.id.tv_sub_header);
        txt_desc = findViewById(R.id.txt_desc);
        pb_loader = findViewById(R.id.pb_loader);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String titleExtra = getIntent().getStringExtra("position");
        String categoryExtra = getIntent().getStringExtra("category");
        String urlExtra = getIntent().getStringExtra("main_url");

        if (titleExtra != null && !titleExtra.isEmpty()) {
            this.ruleTitle = titleExtra;
            txt_header.setText(this.ruleTitle);
        }
        if (categoryExtra != null && !categoryExtra.isEmpty()) {
            tv_sub_header.setText(categoryExtra + " • Guidelines & Forms");
        } else {
            tv_sub_header.setText("Official Guidelines & Procedures");
        }

        iv_share_rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareRuleContent();
            }
        });

        setupWebView(urlExtra);
    }

    private void setupWebView(String mainUrl) {
        WebSettings settings = txt_desc.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        txt_desc.setBackgroundColor(Color.parseColor("#F8FAFC"));

        txt_desc.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                if (uri != null && (uri.getScheme().startsWith("http") || uri.getScheme().startsWith("https"))) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (pb_loader != null) {
                    pb_loader.setVisibility(View.GONE);
                }
            }
        });

        if (mainUrl != null && mainUrl.startsWith("file:///android_asset/")) {
            String assetName = mainUrl.replace("file:///android_asset/", "");
            loadAndStyleAsset(assetName);
        } else if (mainUrl != null) {
            txt_desc.loadUrl(mainUrl);
        }
    }

    private void loadAndStyleAsset(String assetFileName) {
        try {
            InputStream is = getAssets().open(assetFileName);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String rawHtml = new String(buffer, StandardCharsets.UTF_8);

            String styledHtml = injectModernCss(rawHtml);
            txt_desc.loadDataWithBaseURL("file:///android_asset/", styledHtml, "text/html", "UTF-8", null);
        } catch (Exception e) {
            e.printStackTrace();
            txt_desc.loadUrl("file:///android_asset/" + assetFileName);
        }
    }

    private String injectModernCss(String html) {
        String modernCss = "<style>"
                + "* { box-sizing: border-box; margin: 0; padding: 0; }"
                + "body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; "
                + "  background-color: #F8FAFC; color: #1E293B; padding: 16px 14px 28px 14px; font-size: 14.5px; line-height: 1.6; -webkit-text-size-adjust: 100%; }"
                + "#content { max-width: 100%; }"
                + "h3 { display: none; }"
                + "h4 { color: #1E40AF; font-size: 16.5px; font-weight: 700; margin-top: 20px; margin-bottom: 10px; padding-bottom: 6px; border-bottom: 1.5px solid #E2E8F0; display: flex; align-items: center; }"
                + "h4::before { content: ''; display: inline-block; width: 4px; height: 16px; background: #2563EB; border-radius: 2px; margin-right: 8px; }"
                + "p, div { color: #334155; margin-bottom: 10px; }"
                + ".font-bold { font-weight: 600; color: #0F172A; display: inline-block; margin-top: 10px; margin-bottom: 4px; }"
                + "ul { margin-left: 0; padding-left: 0; list-style: none; margin-bottom: 14px; }"
                + "li { position: relative; padding-left: 20px; margin-bottom: 9px; color: #334155; line-height: 1.55; }"
                + "li::before { content: '•'; position: absolute; left: 4px; color: #2563EB; font-size: 20px; line-height: 1; top: -2px; }"
                + "a { color: #1D4ED8; text-decoration: none; background: #EFF6FF; padding: 3px 10px; border-radius: 6px; border: 1px solid #BFDBFE; font-weight: 600; font-size: 13px; display: inline-block; margin: 2px 0; }"
                + "a:active { background: #DBEAFE; }"
                + ".red-color { background: #FFFBEB; border-left: 4px solid #F59E0B; color: #92400E; padding: 12px 14px; border-radius: 8px; margin-top: 16px; font-size: 13px; line-height: 1.5; font-weight: 500; }"
                + "table { width: 100%; border-collapse: collapse; margin: 12px 0; background: #FFFFFF; border-radius: 8px; overflow: hidden; box-shadow: 0 1px 3px rgba(0,0,0,0.05); }"
                + "th, td { padding: 10px 12px; border: 1px solid #E2E8F0; font-size: 13px; text-align: left; }"
                + "th { background: #F1F5F9; color: #1E293B; font-weight: 600; }"
                + "</style>";

        if (html.contains("<head>")) {
            return html.replace("<head>", "<head><meta name='viewport' content='width=device-width, initial-scale=1.0'>" + modernCss);
        } else {
            return "<html><head><meta name='viewport' content='width=device-width, initial-scale=1.0'>" + modernCss + "</head><body>" + html + "</body></html>";
        }
    }

    private void shareRuleContent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBody = "RTO Rules & Procedures: " + this.ruleTitle + "\n\nCheck full guidelines and download official forms on RTO Vehicle App!";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, this.ruleTitle);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(shareIntent, "Share Rule"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}