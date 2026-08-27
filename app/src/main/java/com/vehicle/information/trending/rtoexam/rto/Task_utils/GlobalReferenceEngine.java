package com.vehicle.information.trending.rtoexam.rto.Task_utils;

import android.util.Log;

import org.json.JSONObject;


public class GlobalReferenceEngine {
    private static String API_BASE_URL = "https://www.tradetu.com/bus_api/public/api/v1/vaahan/";
    private static String APPS_URL = "https://www.dtashion.com/api/v1.0/shopping/in/";
    public static String APP_LINKS_NODE = "top_apps_categories";
    private static String BASE_URL = "https://www.tradetu.com/bus_api/public/api/v1/vaahan/";
    public static String BIKE_ACCESSORIES_NODE = "bike_accessories?brandId=";
    public static String BIKE_BRANDS_NODE = "bike_brands";
    public static String BIKE_DEALERS_NODE = "bike_dealers?brandId=";
    public static String BIKE_MODELS_NODE = "bike_models?brandId=";
    public static String BIKE_MODEL_DETAILS_NODE = "bike_model?modelId=";
    public static String BIKE_SERVICE_CENTERS_NODE = "bike_service_centers?brandId=";
    public static String BIKE_VARIANTS_NODE = "bike_variants?modelId=";
    public static String BIKE_VARIANT_DETAILS_NODE = "bike_variant?variantId=";
    public static String CALCULATE_RESALE_VALUE_NODE = "resale_value_vehicles_data/calculateResaleValue";
    public static String CAR_ACCESSORIES_NODE = "car_accessories?brandId=";
    public static String CAR_BRANDS_NODE = "car_brands";
    public static String CAR_DEALERS_NODE = "car_dealers?brandId=";
    public static String CAR_MODELS_NODE = "car_models?brandId=";
    public static String CAR_MODEL_DETAILS_NODE = "car_model?modelId=";
    public static String CAR_SERVICE_CENTERS_NODE = "car_service_centers?brandId=";
    public static String CAR_VARIANTS_NODE = "car_variants?modelId=";
    public static String CAR_VARIANT_DETAILS_NODE = "car_variant?variantId=";
    public static String CITIES_NODE = "cities/";
    public static String COMPARE_BIKE_VARIANTS_NODE = "compare_bike_variants";
    public static String COMPARE_CAR_VARIANTS_NODE = "compare_car_variants";
    public static String DRIVING_SCHOOLS_NODE = "drivingSchools";
    public static String FUEL_CITIES_NODE = "fuelCities";
    public static String FUEL_PRICES_NODE = "fuelPrices/";
    public static String INIT_DATA_NODE = "init_data";
    public static String POPULAR_CAR_BIKE_NODE = "popular_cars_bikes";
    public static String PUSH_BIKE_DEALERS_ENQUIRY_NODE = "push_bike_dealers_enquiry";
    public static String PUSH_CAR_DEALERS_ENQUIRY_NODE = "push_car_dealers_enquiry";
    public static String PUSH_LICENSE_DETAILS_NODE = "pushLicenseDetails";
    public static String PUSH_VEHICLE_DETAILS_NODE = "pushVehicleDetails";
    public static String RESALE_VALUE_VEHICLE_DATA_NODE = "resale_value_vehicles_data";
    public static String RTO_INFORMATION_NODE = "rtoInformation";
    public static String RTO_QUESTIONS_NODE = "rtoQuestions";
    public static String SEARCH_CHALLAN_DETAILS_NODE = "searchChallanDetails";
    public static String SEARCH_LICENSE_DETAILS_NODE = "searchLicenseDetails";
    public static String SEARCH_VEHICLE_DETAILS_NODE = "searchVehicleDetails";
    private static String SITE_URL = "https://www.tradetu.com/rto/api/v1.0/";
    public static String TRAFFIC_SIGNALS_NODE = "trafficSignals";
    public static boolean catchExternalSiteLink = true;
    public static String dataAccessKey = "";
    public static String dataAccessParams = "";
    public static String dataAccessPoint = "LOCAL";
    public static String dataAccessRouter = "";
    public static String dataAccessUrl = "";
    public static JSONObject innerPromotionContent = null;
    public static boolean isLogLicenseServerData = false;
    public static boolean isLogServerData = false;
    public static boolean isPromotionAvailable = false;
    private static JSONObject jsonObject = null;
    public static String licenseLocalSourceField1 = "";
    public static String licenseLocalSourceField2 = "";
    public static String licenseLocalSourceFinalUrl = "";
    public static String licenseLocalSourceInitUrl = "";
    public static String licenseScreen = "OLD";
    public static String localSourceField1 = "";
    public static String localSourceField2 = "";
    public static String localSourceFinalUrl = "";
    public static String localSourceHostUrl = "";
    public static String localSourceInitUrl = "";
    private static Callback mCallback = null;
    private static JSONObject migrateAppConfig = null;
    public static String playGameUrl = "";
    public static JSONObject promotionContent = null;
    public static String sellCarUrl = "";
    public static boolean serverUnderMaintenance = false;
    public static boolean showDealerOffersButton = false;
    public static boolean showPlayGameButton = true;
    public static boolean showRatingDialog = false;
    public static boolean showSellCarButton = false;
    private static String updateChangeLog = "";
    private static boolean updateHasChangeLog = false;
    private static int updateMinVersion;
    private static int updateNewVersion;

    
    public interface Callback {
        void onConfigLoaded();
    }

    public static String prependSiteUrl(String str) {
        if (!Utils.isNullOrEmpty(SITE_URL) && !SITE_URL.endsWith("/")) {
            SITE_URL += "/";
        }
        return SITE_URL + str;
    }

    public static String prependShoppingUrl(String str) {
        if (!Utils.isNullOrEmpty(APPS_URL) && !APPS_URL.endsWith("/")) {
            APPS_URL += "/";
        }
        return APPS_URL + str;
    }

    public static String prependAPIBaseUrl(String str) {
        if (!Utils.isNullOrEmpty(API_BASE_URL) && !API_BASE_URL.endsWith("/")) {
            API_BASE_URL += "/";
        }
        return API_BASE_URL + str;
    }

    public static void updateConfig() {
        Log.d("GlobalReferenceEngine", "Remote config");
        if (!Utils.isNullOrEmpty(Urls.base_url_v2)) {
            String str = Urls.base_url_v2;
            BASE_URL = str;
            API_BASE_URL = str;
        }
        if (!Utils.isNullOrEmpty(Urls.site_url)) {
            SITE_URL = Urls.site_url;
        }
        if (!Utils.isNullOrEmpty(Urls.apps_url)) {
            APPS_URL = Urls.apps_url;
        }
        if (!Utils.isNullOrEmpty(Urls.data_access_point_v2)) {
            dataAccessPoint = Urls.data_access_point_v2;
        }
        if (!Utils.isNullOrEmpty(Urls.data_access_url_v2)) {
            dataAccessUrl = Urls.data_access_url_v2;
        }
        if (!Utils.isNullOrEmpty(Urls.data_access_key_v2)) {
            dataAccessKey = Urls.data_access_key_v2;
        }
        if (!Utils.isNullOrEmpty(Urls.data_access_params_v2)) {
            dataAccessParams = Urls.data_access_params_v2;
        }
        if (!Utils.isNullOrEmpty(Urls.local_source_init_url)) {
            localSourceInitUrl = Urls.local_source_init_url;
        }
        if (!Utils.isNullOrEmpty(Urls.local_source_final_url)) {
            localSourceFinalUrl = Urls.local_source_final_url;
        }
        if (!Utils.isNullOrEmpty(Urls.local_source_host_url)) {
            localSourceHostUrl = Urls.local_source_host_url;
        }
        if (!Utils.isNullOrEmpty(Urls.local_source_field_1)) {
            localSourceField1 = Urls.local_source_field_1;
        }
        if (!Utils.isNullOrEmpty(Urls.local_source_field_2)) {
            localSourceField2 = Urls.local_source_field_2;
        }
        isLogServerData = Urls.log_server_data;
        if (!Utils.isNullOrEmpty(Urls.license_screen)) {
            licenseScreen = Urls.license_screen;
        }
        if (!Utils.isNullOrEmpty(Urls.license_local_source_init_url)) {
            licenseLocalSourceInitUrl = Urls.license_local_source_init_url;
        }
        if (!Utils.isNullOrEmpty(Urls.license_local_source_final_url)) {
            licenseLocalSourceFinalUrl = Urls.license_local_source_final_url;
        }
        if (!Utils.isNullOrEmpty(Urls.license_local_source_field_1)) {
            licenseLocalSourceField1 = Urls.license_local_source_field_1;
        }
        if (!Utils.isNullOrEmpty(Urls.license_local_source_field_2)) {
            licenseLocalSourceField2 = Urls.license_local_source_field_2;
        }
        isLogLicenseServerData = Urls.log_license_server_data;
        if (!Utils.isNullOrEmpty(Urls.update_min_version)) {
            updateMinVersion = Integer.parseInt(Urls.update_min_version);
        }
        if (!Utils.isNullOrEmpty(Urls.update_new_version)) {
            updateNewVersion = Integer.parseInt(Urls.update_new_version);
        }
        updateChangeLog = Urls.update_change_log;
        updateHasChangeLog = Urls.update_has_change_log;
        serverUnderMaintenance = Urls.server_under_maintenance;
        showRatingDialog = Urls.show_rating_dialog;
        showDealerOffersButton = Urls.show_dealer_offers_button;
        showSellCarButton = Urls.show_sell_car_button;
        catchExternalSiteLink = Urls.catch_external_site_link;
        showPlayGameButton = Urls.show_play_game_button;
        if (!Utils.isNullOrEmpty(Urls.play_game_url)) {
            playGameUrl = Urls.play_game_url;
        }
        if (!Utils.isNullOrEmpty(Urls.sell_car_url)) {
            sellCarUrl = Urls.sell_car_url;
        }
        if (!Utils.isNullOrEmpty(Urls.contact_us_email)) {
            String str2 = Urls.contact_us_email;
        }
        if (!Utils.isNullOrEmpty(Urls.cue_links_base_url)) {
            String str3 = Urls.cue_links_base_url;
        }
        if (!Utils.isNullOrEmpty(Urls.flipkart_affiliate_id)) {
            String str4 = Urls.flipkart_affiliate_id;
        }
        if (!Utils.isNullOrEmpty(Urls.amazon_affiliate_id)) {
            String str5 = Urls.amazon_affiliate_id;
        }
        if (!Utils.isNullOrEmpty(Urls.update_bad_versions)) {
            try {
                jsonObject = new JSONObject(Urls.update_bad_versions);
            } catch (Exception e) {
            }
        }
        isPromotionAvailable = Urls.is_promotion_available;
        if (!Utils.isNullOrEmpty(Urls.promotion_content)) {
            try {
                promotionContent = new JSONObject(Urls.promotion_content);
            } catch (Exception e2) {
            }
        }
        if (!Utils.isNullOrEmpty(Urls.inner_promotion_content)) {
            try {
                innerPromotionContent = new JSONObject(Urls.inner_promotion_content);
            } catch (Exception e3) {
            }
        }
        Callback callback = mCallback;
        if (callback != null) {
            callback.onConfigLoaded();
        }
    }

    public static void setCallback(Callback callback) {
        mCallback = callback;
    }
}
