package com.vehicle.information.trending.rtoexam.rto.handlers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;

import com.bumptech.glide.load.Key;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Extra.EncryptionHandler;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.BikeBrandsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.BikeDealersResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.BikeModelDetailsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.BikeModelsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.BikeServiceCentersResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.BikeVariantDetailsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.BikeVariantsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.CarBikeAccessoriesResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.CarBrandsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.CarDealersResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.CarModelDetailsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.CarModelsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.CarServiceCentersResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.CarVariantDetailsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.CarVariantsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.CategoriesResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.ChallanDetailsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.CompareBikeVariantsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.CompareCarVariantsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.DrivingSchoolsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.FuelCityResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.FuelPricesResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.PopularCarBikeResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.RTOInformationResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.RTOQuestionResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.ResaleValueVehicleDataResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.TrafficSignalResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.VehicleValuationDataResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.GlobalContext;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.GlobalReferenceEngine;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.GlobalTracker;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Utils;
import com.vehicle.information.trending.rtoexam.rto.handlers.request.RequestLoader;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.BikeBrandsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.BikeDealersResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.BikeModelDetailsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.BikeModelsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.BikeServiceCentersResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.BikeVariantDetailsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.BikeVariantsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.CarBikeAccessoriesResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.CarBrandsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.CarDealersResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.CarModelDetailsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.CarModelsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.CarServiceCentersResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.CarVariantDetailsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.CarVariantsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.CategoriesResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.ChallanDetailsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.CompareBikeVariantsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.CompareCarVariantsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.DrivingSchoolsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.ExternalVehicleDetailsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.FuelCitiesResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.FuelPricesResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.LicenseDetailsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.LogDealersEnquiryResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.LogLicenseDetailsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.LogVehicleDetailsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.PopularCarBikeResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.RTOInformationResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.RTOQuestionsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.ResaleValueVehicleDataResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.TrafficSignalsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.VehicleDetailsResponseHandler;
import com.vehicle.information.trending.rtoexam.rto.handlers.response.VehicleValuationDataResponseHandler;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TaskHandler {
    private static TaskHandler mInstance;

    public interface JsonResponseHandler {
        void onError(String str);

        void onResponse(JSONObject jSONObject);
    }

    public interface ResponseHandler<T> {
        void onError(String str);

        void onResponse(T t);
    }

    public static TaskHandler newInstance() {
        if (mInstance == null) {
            mInstance = new TaskHandler();
        }
        return mInstance;
    }

    public void fetchVehicleDetails(Context context, String str, boolean z, boolean z2, boolean z3, ResponseHandler<JSONObject> responseHandler) {
        HashMap hashMap = new HashMap();
        hashMap.put("registrationNo", str);
        hashMap.put("key_skip_db", Boolean.valueOf(z));
        hashMap.put("extra", Boolean.valueOf(z2));
        requestUrl(context, 1, GlobalReferenceEngine.prependAPIBaseUrl(GlobalReferenceEngine.SEARCH_VEHICLE_DETAILS_NODE), "tag_vehicle_details", hashMap, z3 ? context.getString(R.string.loading) : null, new VehicleDetailsResponseHandler(responseHandler));
    }

    public void fetchVehicleDetails(Context context, String str, String str2, boolean z, ResponseHandler<JSONObject> responseHandler) {
        HashMap hashMap = new HashMap();
        hashMap.put("registrationNo", str);
        hashMap.put("data", str2);
        requestUrl(context, 1, GlobalReferenceEngine.prependAPIBaseUrl("compileVehicleDetails"), "tag_compile_vehicle_details", hashMap, z ? context.getString(R.string.loading) : null, new VehicleDetailsResponseHandler(responseHandler));
    }

    public void fetchVehicleDetails(Context context, String str, String[] strArr, boolean z, ResponseHandler<String> responseHandler) {
        HashMap hashMap = new HashMap();
        for (String str2 : strArr) {
            String[] split = str2.split("=");
            if (split.length > 1) {
                if (split[1].equalsIgnoreCase("REG_NO")) {
                    hashMap.put(split[0], split[1].replaceFirst("REG_NO", EncryptionHandler.encrypt(str)));
                } else if (split[1].equalsIgnoreCase("OS")) {
                    hashMap.put(split[0], split[1].replaceFirst("OS", EncryptionHandler.encrypt(Build.VERSION.RELEASE)));
                } else if (split[1].equalsIgnoreCase("MO")) {
                    hashMap.put(split[0], split[1].replaceFirst("MO", EncryptionHandler.encrypt(Build.MODEL)));
                } else if (split[1].equalsIgnoreCase("DI")) {
                    hashMap.put(split[0], split[1].replaceFirst("DI", EncryptionHandler.encrypt(UUID.randomUUID().toString())));
                } else {
                    hashMap.put(split[0], EncryptionHandler.encrypt(split[1]));
                }
            }
        }
        requestExternalUrl(context, 1, GlobalReferenceEngine.dataAccessUrl, "tag_external_vehicle_details", hashMap, z ? context.getString(R.string.loading) : null, new ExternalVehicleDetailsResponseHandler(responseHandler));
    }

    public void fetchLicenseDetails(Context context, String str, String str2, boolean z, boolean z2, ResponseHandler<JSONObject> responseHandler) {
        HashMap hashMap = new HashMap();
        hashMap.put("licenseNo", str);
        if (!Utils.isNullOrEmpty(str2)) {
            hashMap.put("dob", str2);
        }
        hashMap.put("key_skip_db", Boolean.valueOf(z));
        requestUrl(context, 1, GlobalReferenceEngine.prependAPIBaseUrl(GlobalReferenceEngine.SEARCH_LICENSE_DETAILS_NODE), "tag_license_details", hashMap, z2 ? context.getString(R.string.loading) : null, new LicenseDetailsResponseHandler(responseHandler));
    }

    public void fetchChallanDetails(Context context, String str, boolean z, boolean z2, ResponseHandler<ChallanDetailsResponse> responseHandler) {
        HashMap hashMap = new HashMap();
        hashMap.put("registrationNo", str);
        hashMap.put("key_skip_db", Boolean.valueOf(z));
        requestUrl(context, 1, GlobalReferenceEngine.prependAPIBaseUrl(GlobalReferenceEngine.SEARCH_CHALLAN_DETAILS_NODE), "tag_challan_details", hashMap, z2 ? context.getString(R.string.loading) : null, new ChallanDetailsResponseHandler(responseHandler));
    }

    public void pushVehicleDetails(Context context, Map<String, Object> map, ResponseHandler<JSONObject> responseHandler) {
        requestUrl(context, 1, GlobalReferenceEngine.prependAPIBaseUrl(GlobalReferenceEngine.PUSH_VEHICLE_DETAILS_NODE), "tag_push_vehicle_details", map, "", new LogVehicleDetailsResponseHandler(responseHandler));
    }

    public void pushLicenseDetails(Context context, Map<String, Object> map, ResponseHandler<JSONObject> responseHandler) {
        requestUrl(context, 1, GlobalReferenceEngine.prependAPIBaseUrl(GlobalReferenceEngine.PUSH_LICENSE_DETAILS_NODE), "tag_push_license_details", map, "", new LogLicenseDetailsResponseHandler(responseHandler));
    }

    public void fetchRTOInformation(Context context, boolean z, ResponseHandler<RTOInformationResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependAPIBaseUrl(GlobalReferenceEngine.RTO_INFORMATION_NODE), "tag_rto_information", z ? context.getString(R.string.loading) : null, new RTOInformationResponseHandler(responseHandler));
    }

    public void fetchCarBrands(Context context, boolean z, ResponseHandler<CarBrandsResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.CAR_BRANDS_NODE), "tag_car_brands", z ? context.getString(R.string.loading) : null, new CarBrandsResponseHandler(responseHandler));
    }

    public void fetchCarModels(Context context, String str, String str2, boolean z, ResponseHandler<CarModelsResponse> responseHandler) {
        String str3 = GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.CAR_MODELS_NODE) + str;
        if (!Utils.isNullOrEmpty(str2)) {
            str3 = str3.concat("&price=").concat(str2);
        }
        beforeRequestUrl(context, 0, str3, "tag_car_models", z ? context.getString(R.string.loading) : null, new CarModelsResponseHandler(responseHandler));
    }

    public void fetchCarModelDetails(Context context, String str, boolean z, ResponseHandler<CarModelDetailsResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.CAR_MODEL_DETAILS_NODE) + str, "tag_car_model_details", z ? context.getString(R.string.loading) : null, new CarModelDetailsResponseHandler(responseHandler));
    }

    public void fetchCarVariants(Context context, String str, boolean z, ResponseHandler<CarVariantsResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.CAR_VARIANTS_NODE) + str, "tag_car_variants", z ? context.getString(R.string.loading) : null, new CarVariantsResponseHandler(responseHandler));
    }

    public void fetchCarVariantDetails(Context context, String str, boolean z, ResponseHandler<CarVariantDetailsResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.CAR_VARIANT_DETAILS_NODE) + str, "tag_car_variant_details", z ? context.getString(R.string.loading) : null, new CarVariantDetailsResponseHandler(responseHandler));
    }

    public void fetchCarVariantsComparison(Context context, String str, String str2, boolean z, ResponseHandler<CompareCarVariantsResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.COMPARE_CAR_VARIANTS_NODE).concat("?variantId_x=").concat(str).concat("&variantId_y=").concat(str2), "tag_compare_car_variants", z ? context.getString(R.string.loading) : null, new CompareCarVariantsResponseHandler(responseHandler));
    }

    public void fetchCarDealers(Context context, String str, String str2, String str3, String str4, boolean z, ResponseHandler<CarDealersResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.CAR_DEALERS_NODE) + str + "&cityId=" + str2 + "&offset=" + str3 + "&count=" + str4, "tag_car_dealers", z ? context.getString(R.string.loading) : null, new CarDealersResponseHandler(responseHandler));
    }

    public void fetchCarServiceCenters(Context context, String str, String str2, String str3, String str4, boolean z, ResponseHandler<CarServiceCentersResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.CAR_SERVICE_CENTERS_NODE) + str + "&cityId=" + str2 + "&offset=" + str3 + "&count=" + str4, "tag_car_service_centers", z ? context.getString(R.string.loading) : null, new CarServiceCentersResponseHandler(responseHandler));
    }

    public void fetchAccessories(Context context, String str, String str2, boolean z, ResponseHandler<CarBikeAccessoriesResponse> responseHandler) {
        String str3;
        if (str.equalsIgnoreCase(GlobalTracker.CAR)) {
            str3 = GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.CAR_ACCESSORIES_NODE) + str2;
        } else if (str.equalsIgnoreCase(GlobalTracker.BIKE)) {
            str3 = GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.BIKE_ACCESSORIES_NODE) + str2;
        } else {
            str3 = "";
        }
        beforeRequestUrl(context, 0, str3, "tag_car_bike_accessories", z ? context.getString(R.string.loading) : null, new CarBikeAccessoriesResponseHandler(responseHandler));
    }

    public void fetchBikeBrands(Context context, boolean z, ResponseHandler<BikeBrandsResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.BIKE_BRANDS_NODE), "tag_bike_brands", z ? context.getString(R.string.loading) : null, new BikeBrandsResponseHandler(responseHandler));
    }

    public void fetchBikeModels(Context context, String str, String str2, boolean z, ResponseHandler<BikeModelsResponse> responseHandler) {
        String str3 = GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.BIKE_MODELS_NODE) + str;
        if (!Utils.isNullOrEmpty(str2)) {
            str3 = str3.concat("&price=").concat(str2);
        }
        beforeRequestUrl(context, 0, str3, "tag_bike_models", z ? context.getString(R.string.loading) : null, new BikeModelsResponseHandler(responseHandler));
    }

    public void fetchBikeModelDetails(Context context, String str, boolean z, ResponseHandler<BikeModelDetailsResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.BIKE_MODEL_DETAILS_NODE) + str, "tag_bike_model_details", z ? context.getString(R.string.loading) : null, new BikeModelDetailsResponseHandler(responseHandler));
    }

    public void fetchBikeVariants(Context context, String str, boolean z, ResponseHandler<BikeVariantsResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.BIKE_VARIANTS_NODE) + str, "tag_bike_variants", z ? context.getString(R.string.loading) : null, new BikeVariantsResponseHandler(responseHandler));
    }

    public void fetchBikeVariantDetails(Context context, String str, boolean z, ResponseHandler<BikeVariantDetailsResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.BIKE_VARIANT_DETAILS_NODE) + str, "tag_bike_variant_details", z ? context.getString(R.string.loading) : null, new BikeVariantDetailsResponseHandler(responseHandler));
    }

    public void fetchBikeVariantsComparison(Context context, String str, String str2, boolean z, ResponseHandler<CompareBikeVariantsResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.COMPARE_BIKE_VARIANTS_NODE).concat("?variantId_x=").concat(str).concat("&variantId_y=").concat(str2), "tag_compare_bike_variants", z ? context.getString(R.string.loading) : null, new CompareBikeVariantsResponseHandler(responseHandler));
    }

    public void fetchBikeDealers(Context context, String str, String str2, String str3, String str4, boolean z, ResponseHandler<BikeDealersResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.BIKE_DEALERS_NODE) + str + "&cityId=" + str2 + "&offset=" + str3 + "&count=" + str4, "tag_bike_dealers", z ? context.getString(R.string.loading) : null, new BikeDealersResponseHandler(responseHandler));
    }

    public void pushDealersEnquiry(Context context, String str, Map<String, Object> map, boolean z, ResponseHandler<JSONObject> responseHandler) {
        requestUrl(context, 1, str, "tag_push_dealer_enquiries", map, z ? context.getString(R.string.submitting) : null, new LogDealersEnquiryResponseHandler(responseHandler));
    }

    public void fetchBikeServiceCenters(Context context, String str, String str2, String str3, String str4, boolean z, ResponseHandler<BikeServiceCentersResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.BIKE_SERVICE_CENTERS_NODE) + str + "&cityId=" + str2 + "&offset=" + str3 + "&count=" + str4, "tag_bike_service_centers", z ? context.getString(R.string.loading) : null, new BikeServiceCentersResponseHandler(responseHandler));
    }

    public void fetchPopularCarsBikes(Context context, boolean z, ResponseHandler<PopularCarBikeResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.POPULAR_CAR_BIKE_NODE), "tag_popular_car_bike", z ? context.getString(R.string.loading) : null, new PopularCarBikeResponseHandler(responseHandler));
    }

    public void fetchFuelPrices(Context context, String str, boolean z, ResponseHandler<FuelPricesResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.FUEL_PRICES_NODE) + str, "tag_fuel_prices", z ? context.getString(R.string.loading) : null, new FuelPricesResponseHandler(responseHandler));
    }

    public void fetchFuelCities(Context context, boolean z, ResponseHandler<FuelCityResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.FUEL_CITIES_NODE), "tag_fuel_cities", z ? context.getString(R.string.loading) : null, new FuelCitiesResponseHandler(responseHandler));
    }

    public void fetchRTOQuestions(Context context, boolean z, ResponseHandler<RTOQuestionResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.RTO_QUESTIONS_NODE).concat("?lang=en"), "tag_rto_questions", z ? context.getString(R.string.loading) : null, new RTOQuestionsResponseHandler(responseHandler));
    }

    public void fetchTrafficSignals(Context context, boolean z, ResponseHandler<TrafficSignalResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.TRAFFIC_SIGNALS_NODE).concat("?lang=en"), "tag_traffic_signals", z ? context.getString(R.string.loading) : null, new TrafficSignalsResponseHandler(responseHandler));
    }

    public void fetchDrivingSchools(Context context, String str, String str2, String str3, boolean z, ResponseHandler<DrivingSchoolsResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.DRIVING_SCHOOLS_NODE) + "?cityId=" + str + "&offset=" + str2 + "&count=" + str3, "tag_driving_schools", z ? context.getString(R.string.loading) : null, new DrivingSchoolsResponseHandler(responseHandler));
    }

    public void fetchTopApps(Context context, boolean z, ResponseHandler<CategoriesResponse> responseHandler) {
        beforeRequestUrl(context, 0, GlobalReferenceEngine.prependShoppingUrl(GlobalReferenceEngine.APP_LINKS_NODE), "tag_top_apps_categories", z ? context.getString(R.string.loading) : null, new CategoriesResponseHandler(responseHandler));
    }

    public void fetchResaleValueVehiclesData(Context context, String str, boolean z, ResponseHandler<ResaleValueVehicleDataResponse> responseHandler) {
        beforeRequestUrl(context, 0, str, "tag_resale_value_vehicles_data", z ? context.getString(R.string.loading) : null, new ResaleValueVehicleDataResponseHandler(responseHandler));
    }

    public void fetchVehicleValuationData(Context context, Map<String, Object> map, boolean z, ResponseHandler<VehicleValuationDataResponse> responseHandler) {
        requestUrl(context, 1, GlobalReferenceEngine.prependSiteUrl(GlobalReferenceEngine.CALCULATE_RESALE_VALUE_NODE), "tag_calculate_resale_value", map, z ? context.getString(R.string.loading) : null, new VehicleValuationDataResponseHandler(responseHandler));
    }

    private void beforeRequestUrl(Context context, int i, String str, String str2, String str3, JsonResponseHandler jsonResponseHandler) {
        requestUrl(context, i, str, str2, new HashMap(), str3, jsonResponseHandler);
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x0034  */
    private void requestUrl(Context context, int i, String str, String str2, Map<String, Object> map, String str3, JsonResponseHandler jsonResponseHandler) {
        boolean z;
        ProgressDialog progressDialog;
        Context context2 = context;
        if (!Utils.isNullOrEmpty(str3)) {
            if (Utils.isActivityFinished(context)) {
                ProgressDialog progressDialog2 = new ProgressDialog(context);
                progressDialog2.setMessage(str3);
                progressDialog2.setCancelable(false);
                progressDialog2.setCanceledOnTouchOutside(false);
                progressDialog2.show();
                progressDialog = progressDialog2;
                z = true;
                if (context2 == null) {
                    context2 = GlobalContext.getInstance().getContext();
                }
                new RequestLoader(this, i, map, str, z, context2, progressDialog, jsonResponseHandler, str2).request();
            }
        }
        progressDialog = null;
        z = false;
        if (context2 == null) {
        }
        new RequestLoader(this, i, map, str, z, context2, progressDialog, jsonResponseHandler, str2).request();
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x0034  */
    private void requestExternalUrl(Context context, int i, String str, String str2, Map<String, Object> map, String str3, ResponseHandler<String> responseHandler) {
        boolean z;
        ProgressDialog progressDialog;
        Context context2 = context;
        if (!Utils.isNullOrEmpty(str3)) {
            if (Utils.isActivityFinished(context)) {
                ProgressDialog progressDialog2 = new ProgressDialog(context);
                progressDialog2.setMessage(str3);
                progressDialog2.setCancelable(false);
                progressDialog2.setCanceledOnTouchOutside(false);
                progressDialog2.show();
                progressDialog = progressDialog2;
                z = true;
                if (context2 == null) {
                    context2 = GlobalContext.getInstance().getContext();
                }
                new RequestLoader(this, i, map, str, z, context2, progressDialog, responseHandler, str2).requestExternal();
            }
        }
        progressDialog = null;
        z = false;
        if (context2 == null) {
        }
        new RequestLoader(this, i, map, str, z, context2, progressDialog, responseHandler, str2).requestExternal();
    }

    public String encodeString(String str) {
        if (str == null) {
            return "";
        }
        try {
            return URLEncoder.encode(str, Key.STRING_CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public void cancelProgressDialog(ProgressDialog progressDialog) {
        if (progressDialog != null) {
            try {
                if (progressDialog.isShowing()) {
                    progressDialog.cancel();
                    progressDialog.dismiss();
                }
            } catch (Exception e) {
            }
        }
    }
}
