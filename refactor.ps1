$content = Get-Content 'app\src\main\java\com\vehicle\information\trending\rtoexam\rto\Task_adManager\Task_InterstitialAdManager.java' -Raw

# Replace admobInterstitialAd.show(activity);
$content = $content -replace 'admobInterstitialAd\.show\(activity\);', 'showAdWithLoader(activity);'

# Replace fbInterstitialAd.show(); inside the show methods (but not inside fetchFbAd callbacks, wait, fetchFbAd has fbInterstitialAd = new ... but no .show() )
$content = $content -replace 'fbInterstitialAd\.show\(\);', 'showAdWithLoader(activity);'

# Add the showAdWithLoader helper method at the end of the class
$helper = "
    private void showAdWithLoader(final Activity activity) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Loading Ad...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isAdmobAdAvailable()) {
                    admobInterstitialAd.show(activity);
                } else if (isFbAdAvailable()) {
                    fbInterstitialAd.show();
                } else {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
                }
            }
        }, 1200);
    }
}
"
$content = $content -replace '}\s*$', $helper

# Fix the fbInterstitialAd.show() inside the helper itself since it got replaced!
$content = $content -replace '\} else if \(isFbAdAvailable\(\)\) \{\s*showAdWithLoader\(activity\);\s*\} else \{', '} else if (isFbAdAvailable()) { fbInterstitialAd.show(); } else {'
$content = $content -replace 'if \(isAdmobAdAvailable\(\)\) \{\s*showAdWithLoader\(activity\);\s*\} else if \(isFbAdAvailable\(\)\)', 'if (isAdmobAdAvailable()) { admobInterstitialAd.show(activity); } else if (isFbAdAvailable())'

# Add dismiss on FB ad error
$content = $content -replace 'public void onError\(Ad ad, com\.facebook\.ads\.AdError adError\) \{\s*isFailed = true;\s*\}', 'public void onError(Ad ad, com.facebook.ads.AdError adError) { isFailed = true; if (progressDialog != null && progressDialog.isShowing()) { progressDialog.dismiss(); } if (onAdLoadInterface != null) onAdLoadInterface.onAdClose(); }'

Set-Content 'app\src\main\java\com\vehicle\information\trending\rtoexam\rto\Task_adManager\Task_InterstitialAdManager.java' $content
