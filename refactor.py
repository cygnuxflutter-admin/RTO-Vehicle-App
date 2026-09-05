import re

with open('app/src/main/java/com/vehicle/information/trending/rtoexam/rto/Task_adManager/Task_InterstitialAdManager.java', 'r', encoding='utf-8') as f:
    content = f.read()

content = content.replace('admobInterstitialAd.show(activity);', 'showAdWithLoader(activity);')
content = content.replace('fbInterstitialAd.show();', 'showAdWithLoader(activity);')

helper_method = '''
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
'''
content = re.sub(r'}\s*$', helper_method, content)

error_replacement = '''public void onError(Ad ad, com.facebook.ads.AdError adError) {
                isFailed = true;
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            }'''
content = re.sub(r'public void onError\(Ad ad, com\.facebook\.ads\.AdError adError\)\s*\{\s*isFailed = true;\s*\}', error_replacement, content)

with open('app/src/main/java/com/vehicle/information/trending/rtoexam/rto/Task_adManager/Task_InterstitialAdManager.java', 'w', encoding='utf-8') as f:
    f.write(content)
