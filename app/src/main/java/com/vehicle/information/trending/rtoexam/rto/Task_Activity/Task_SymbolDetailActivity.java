package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.Task_SymbolAdpter;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

public class Task_SymbolDetailActivity extends AllBaseActivity {

    Task_SymbolAdpter rtoSymbolDet_rcyAdp;
    RecyclerView rto_list_recycler;
    String str_pass_value = "Mandatory";
    String str_language = "gujarati";

    TextView tvHeaderTitle;
    TextView tabGujarati, tabHindi, tabEnglish;

    // 1. Cautionary
    int[] cautionary_img_ary = {
            R.drawable.caution_1, R.drawable.caution_2, R.drawable.caution_3, R.drawable.caution_4,
            R.drawable.caution_5, R.drawable.caution_6, R.drawable.caution_7, R.drawable.caution_8,
            R.drawable.caution_9, R.drawable.caution_10, R.drawable.caution_11, R.drawable.caution_12,
            R.drawable.caution_13, R.drawable.caution_14, R.drawable.caution_15, R.drawable.caution_16,
            R.drawable.caution_17, R.drawable.caution_18, R.drawable.caution_19, R.drawable.caution_20,
            R.drawable.caution_21
    };
    String[] cautionary_text_ary_en = {
            "Right Hair Pin Bend", "Left Hair Pin Bend", "Right Hand Curve", "Left Hand Curve",
            "Left Reverse Bend", "Right Reverse Bend", "Side Road Left", "Side Road Right",
            "T-intersection", "Major Road Ahead", "Staggered Intersection", "Staggered Intersection",
            "Cross Road Ahead", "Right Y-Intersection", "Left Y-Intersection", "Two Way Traffic",
            "Y-Intersection", "Roundabout Ahead", "Gap In Median", "Pedestrian Crossing",
            "Narrow Bridge Ahead"
    };
    String[] cautionary_text_ary_gu = {
            "જમણી બાજુ તીવ્ર વળાંક (હેર પીન)", "ડાબી બાજુ તીવ્ર વળાંક (હેર પીન)", "જમણી બાજુ વળાંક", "ડાબી બાજુ વળાંક",
            "ડાબી બાજુ ઝિગ-ઝેગ વળાંક", "જમણી બાજુ ઝિગ-ઝેગ વળાંક", "ડાબી બાજુ શાખા રોડ છે", "જમણી બાજુ શાખા રોડ છે",
            "ટી (T) આકારનો રસ્તો", "આગળ મુખ્ય રસ્તો છે", "ડાબે-જમણે વિભાજિત રસ્તો", "ડાબે-જમણે વિભાજિત રસ્તો",
            "આગળ ચાર રસ્તા (ચોક) છે", "જમણી બાજુ વાય (Y) ક્રોસિંગ", "ડાબી બાજુ વાય (Y) ક્રોસિંગ", "બંને બાજુનો વાહન વ્યવહાર",
            "વાય (Y) આકારનો ક્રોસિંગ", "આગળ ગોળ ચક્કર (રાઉન્ડ અબાઉટ) છે", "ડિવાઇડર વચ્ચે જગ્યા (ગેપ)", "પદયાત્રી ક્રોસિંગ (ઝેબ્રા ક્રોસિંગ)",
            "આગળ સાંકડો પુલ છે"
    };
    String[] cautionary_text_ary_hi = {
            "दाएं तरफ तीव्र मोड़ (हेयर पिन)", "बाएं तरफ तीव्र मोड़ (हेयर पिन)", "दाएं घुमावदार मोड़", "बाएं घुमावदार मोड़",
            "बाएं तरफ जिग-जैग मोड़", "दाएं तरफ जिग-जैग मोड़", "बाएं तरफ साइड रोड है", "दाएं तरफ साइड रोड है",
            "टी (T) आकार का चौराहा", "आगे मुख्य सड़क है", "टेढ़े-मेढ़े साइड रोड", "टेढ़े-मेढ़े साइड रोड",
            "आगे चौराहा है", "दाएं वाई (Y) इंटरसेक्शन", "बाएं वाई (Y) इंटरसेक्शन", "दो तरफा यातायात",
            "वाई (Y) इंटरसेक्शन", "आगे गोल चक्कर (राउंडअबाउट) है", "डिवाइडर के बीच जगह (गैप)", "पैदल यात्री क्रॉसिंग",
            "आगे संकरा पुल है"
    };

    // 2. Driving Rules
    int[] drivingrules_img_ary = {
            R.drawable.ic_drive_1, R.drawable.ic_drive_2, R.drawable.ic_drive_3,
            R.drawable.ic_drive_4, R.drawable.ic_drive_5
    };
    String[] drivingrules_text_ary_en = {
            "I intend to move in to the left or turn left.",
            "I intend to move out of the right or changing the lane or turn right.",
            "I intend to stop.",
            "I intend to slow down.",
            "Indicating the car following you to over take."
    };
    String[] drivingrules_text_ary_gu = {
            "હું ડાબી બાજુ જવા અથવા ડાબે વળવા માંગુ છું.",
            "હું જમણી બાજુ જવા, લેન બદલવા અથવા જમણે વળવા માંગુ છું.",
            "હું વાહન રોકવા (થોભવા) માંગુ છું.",
            "હું વાહનની ગતિ ધીમી કરવા માંગુ છું.",
            "પાછળ આવતા વાહનને ઓવરટેક કરવાનો સંકેત આપવો."
    };
    String[] drivingrules_text_ary_hi = {
            "मैं बाईं ओर मुड़ना या बाईं लेन में जाना चाहता हूँ।",
            "मैं दाईं ओर मुड़ना, लेन बदलना या दाईं तरफ जाना चाहता हूँ।",
            "मैं वाहन रोकना चाहता हूँ।",
            "मैं वाहन की गति धीमी करना चाहता हूँ।",
            "पीछे आ रहे वाहन को ओवरटेक करने का इशारा करना।"
    };

    // 3. Informatory
    int[] informatory_img_ary = {
            R.drawable.ic_informatory_1, R.drawable.ic_informatory_2, R.drawable.ic_informatory_3,
            R.drawable.ic_informatory_4, R.drawable.ic_informatory_5, R.drawable.ic_informatory_6,
            R.drawable.ic_informatory_7, R.drawable.ic_informatory_8, R.drawable.ic_informatory_9,
            R.drawable.ic_informatory_10, R.drawable.ic_informatory_11, R.drawable.ic_informatory_12,
            R.drawable.ic_informatory_13, R.drawable.ic_informatory_14
    };
    String[] informatory_text_ary_en = {
            "Public telephone", "Petrol pump", "Hospital", "Eating Place",
            "Light Refreshment", "No Through Road", "No Through Side Road", "First Aid Post",
            "Park This Side", "Parking Both Sides", "Parking Lot Bikes", "Parking Lot Cycles",
            "Parking Lot Taxis", "Parking Lot Auto"
    };
    String[] informatory_text_ary_gu = {
            "સાર્વજનિક ટેલિફોન", "પેટ્રોલ પંપ", "હોસ્પિટલ (દવાખાનું)", "ભોજનાલય / રેસ્ટોરન્ટ",
            "નાસ્તા-પાણીનું સ્થળ", "આગળ રસ્તો બંધ છે", "બાજુનો રસ્તો આગળ બંધ છે", "પ્રાથમિક સારવાર કેન્દ્ર",
            "આ બાજુ પાર્કિંગ કરવું", "બંને બાજુ પાર્કિંગ માન્ય", "સ્કૂટર / બાઇક પાર્કિંગ", "સાયકલ પાર્કિંગ",
            "ટેક્સી સ્ટેન્ડ / પાર્કિંગ", "ઓટો રિક્ષા સ્ટેન્ડ / પાર્કિંગ"
    };
    String[] informatory_text_ary_hi = {
            "सार्वजनिक टेलीफोन", "पेट्रोल पंप", "अस्पताल", "भोजनालय / रेस्टोरेंट",
            "अल्पाहार / जलपान गृह", "आगे रास्ता बंद है", "साइड का रास्ता आगे बंद है", "प्राथमिक उपचार केंद्र",
            "इस तरफ गाड़ी पार्क करें", "दोनों तरफ पार्किंग मान्य", "दोपहिया वाहन पार्किंग", "साइकिल पार्किंग",
            "टैक्सी पार्किंग", "ऑटो रिक्शा पार्किंग"
    };

    // 4. Mandatory
    int[] mandatory_img_ary = {
            R.drawable.ic_mandtory_1, R.drawable.ic_mandtory_2, R.drawable.ic_mandtory_3,
            R.drawable.ic_mandtory_4, R.drawable.ic_mandtory_5, R.drawable.ic_mandtory_6,
            R.drawable.ic_mandtory_7, R.drawable.ic_mandtory_8, R.drawable.ic_mandtory_9,
            R.drawable.ic_mandtory_10, R.drawable.ic_mandtory_11, R.drawable.ic_mandtory_12,
            R.drawable.ic_mandtory_13, R.drawable.ic_mandtory_14, R.drawable.ic_mandtory_15,
            R.drawable.ic_mandtory_16, R.drawable.ic_mandtory_17, R.drawable.ic_mandtory_18,
            R.drawable.ic_mandtory_19, R.drawable.ic_mandtory_20, R.drawable.ic_mandtory_21,
            R.drawable.ic_mandtory_22, R.drawable.ic_mandtory_23, R.drawable.ic_mandtory_24,
            R.drawable.ic_mandtory_25
    };
    String[] mandatory_text_ary_en = {
            "Speed Limit", "No Entry", "One Way Sign, Entry allowed", "Right Turn Prohibited",
            "Left Turn Prohibited", "One Way, Entry Prohibited", "Load Limit",
            "Vehicle Prohibited in Both Directions", "Horn Prohibited", "U-Turn Prohibited",
            "Overtaking Prohibited", "No Parking", "Width Limit", "Height Limit",
            "No Stopping or Standing", "Restriction Ends", "Stop", "Compulsory Turn Right",
            "Compulsory Turn Left", "Compulsory Ahead Only", "Compulsory Keep Left",
            "Compulsory Ahead or Turn Left", "Compulsory Ahead or Turn Right", "Compulsory Sound Horn",
            "Give Way"
    };
    String[] mandatory_text_ary_gu = {
            "ગતિ મર્યાદા (સ્પીડ લિમિટ)", "પ્રવેશ બંધ / મનાઈ", "એક તરફી રસ્તો, પ્રવેશ માન્ય", "જમણી બાજુ વળવાની મનાઈ",
            "ડાબી બાજુ વળવાની મનાઈ", "એક તરફી રસ્તો, પ્રવેશ બંધ", "ભાર મર્યાદા (વજન)",
            "બંને દિશામાં વાહન પ્રવેશ બંધ", "હોર્ન વગાડવાની મનાઈ", "યુ-ટર્ન લેવાની મનાઈ",
            "ઓવરટેક કરવાની મનાઈ", "વાહન પાર્ક કરવાની મનાઈ (નો પાર્કિંગ)", "પહોળાઈ મર્યાદા", "ઊંચાઈ મર્યાદા",
            "વાહન થોભાવવાની કે ઊભા રાખવાની મનાઈ", "પ્રતિબંધ સમાપ્તિ", "થોભો / વાહન રોકો", "ફરજિયાત જમણી બાજુ વળો",
            "ફરજિયાત ડાબી બાજુ વળો", "ફરજિયાત ફક્ત આગળ વધો", "ફરજિયાત ડાબી બાજુ રાખો",
            "ફરજિયાત આગળ વધો અથવા ડાબે વળો", "ફરજિયાત આગળ વધો અથવા જમણે વળો", "ફરજિયાત હોર્ન વગાડો",
            "રસ્તો આપો"
    };
    String[] mandatory_text_ary_hi = {
            "गति सीमा", "प्रवेश निषेध", "एक तरफा रास्ता, प्रवेश मान्य", "दाएं मुड़ना मना है",
            "बाएं मुड़ना मना है", "एक तरफा रास्ता, प्रवेश निषेध", "भार सीमा (वजन)",
            "दोनों दिशाओं में वाहन प्रवेश निषेध", "हॉर्न बजाना मना है", "यू-टर्न वर्जित",
            "ओवरटेकिंग वर्जित", "पार्किंग मना है (नो पार्किंग)", "चौड़ाई सीमा", "ऊंचाई सीमा",
            "गाड़ी रोकना या खड़ा करना मना है", "प्रतिबंध समाप्त", "रुकिए", "अनिवार्य दाएं मुड़ें",
            "अनिवार्य बाएं मुड़ें", "अनिवार्य केवल आगे बढ़ें", "अनिवार्य बाएं रखें",
            "अनिवार्य सीधे जाएं या बाएं मुड़ें", "अनिवार्य सीधे जाएं या दाएं मुड़ें", "हॉर्न बजाना अनिवार्य है",
            "रास्ता दीजिए"
    };

    // 5. Road & Signals
    int[] roadsignals_img_ary = {
            R.drawable.ic_road_1, R.drawable.ic_road_2, R.drawable.ic_road_3,
            R.drawable.ic_road_4, R.drawable.ic_road_5, R.drawable.ic_road_6,
            R.drawable.ic_road_7, R.drawable.ic_road_8, R.drawable.ic_road_9,
            R.drawable.ic_road_10, R.drawable.ic_road_11, R.drawable.ic_road_12
    };
    String[] roadsignals_text_ary_en = {
            "Centre Line Marking For A Two Lane Road",
            "Lane Line And Broken Centre Line",
            "Centre Barrier Line Marking For A Four Lane Road",
            "Centre Barrier Line Marking For A Six Lane Road",
            "Double White/Yellow Lines:\nUsed where visibility is restricted in both directions.",
            "Combination Of Solid And Broken Lines:\nIf the line on your side is broken, you may cross or straddle it. OverTake - but only if it is safe to do so.\nIf the line on your side is continious you must not cross or straddle it.",
            "Stop Line:\nA stop line is a single solid transverse line painted before the intersecting edge of the road junction/ intersection.",
            "Give Way Line:\nThe give way line is usually a double dotted line marked transversely at junctions.",
            "Border or Edge Lines:\nThese are continuous lines at the edge of the carriageway and mark the limits of the main carriageway upto which a driver can safely venture.",
            "Parking Prohibited Lines:\nA solid continuous yellow line painted on the kerb or edge of the carriageway along with a \"No-parking\" sign indicates the extent of no-parking area.",
            "Yellow Box Junctions or Keep Clear:\nThese are yellow crossed diagonal lines within the box. The vehicles should cross it only if they have a clear space available ahead of the yellow box. In this marked area vehicles must not stop even briefly.",
            "Pedestrian Crossings\nThese are alternate black and white stripes painted parallel to the road generally known as zebra crossing. Pedestrians must cross only at the point where these lines are provided and when the signal is in their favour at controlled crossings. You must stop and give way to pedestrians at these crossings."
    };
    String[] roadsignals_text_ary_gu = {
            "બે લેન વાળા રસ્તા માટે મધ્ય રેખા (સેન્ટર લાઇન) માર્કિંગ",
            "લેન લાઇન અને તૂટક મધ્ય રેખા (બ્રોકન સેન્ટર લાઇન)",
            "ચાર લેન વાળા રસ્તા માટે મધ્ય બેરિયર રેખા માર્કિંગ",
            "છ લેન વાળા રસ્તા માટે મધ્ય બેરિયર રેખા માર્કિંગ",
            "બેવડી સફેદ / પીળી રેખાઓ:\nજ્યાં બંને દિશામાં વિઝિબિલિટી (જોવાનું) મર્યાદિત હોય ત્યાં વપરાય છે.",
            "સળંગ અને તૂટક રેખાઓનું સંયોજન:\nજો તમારી બાજુની રેખા તૂટક હોય તો તમે સુરક્ષિત રીતે ઓવરટેક કરી શકો છો.\nજો તમારી તરફ સળંગ રેખા હોય તો તેને ક્રોસ કરવાની મનાઈ છે.",
            "સ્ટોપ લાઇન:\nજંક્શન કે ચાર રસ્તા પહેલા દોરેલી સળંગ રેખા જ્યાં વાહન થોભાવવું ફરજિયાત છે.",
            "ગિવ વે (રસ્તો આપો) લાઇન:\nજંક્શન પર દોરેલી બેવડી ટપકાંવાળી રેખા જે અન્ય વાહનોને પ્રાથમિકતા આપવાનો સંકેત આપે છે.",
            "કિનારીની બોર્ડર રેખાઓ:\nરસ્તાની બંને કિનારે દોરેલી સળંગ રેખાઓ જે રસ્તાની સુરક્ષિત હદ દર્શાવે છે.",
            "પાર્કિંગ પ્રતિબંધ રેખા:\nરસ્તાના કિનારે દોરેલી સળંગ પીળી રેખા જે દર્શાવે છે કે અહીં વાહન પાર્ક કરવું મનાઈ છે.",
            "યલો બોક્સ જંક્શન:\nચોકડી આકારની પીળી રેખાઓવાળો બોક્સ. આગળ રસ્તો ક્લિયર હોય તો જ પ્રવેશ કરવો, બોક્સમાં વાહન થોભાવવાની મનાઈ છે.",
            "પદયાત્રી (ઝેબ્રા) ક્રોસિંગ:\nકાળા અને સફેદ પટ્ટાવાળો રસ્તો. વાહનચાલકોએ અહીં વાહન ધીમું પાડીને અથવા થોભાવીને રાહદારીઓને રસ્તો આપવો ફરજિયાત છે."
    };
    String[] roadsignals_text_ary_hi = {
            "दो लेन वाली सड़क के लिए मध्य रेखा मार्किंग",
            "लेन लाइन और टूटी हुई मध्य रेखा",
            "चार लेन वाली सड़क के लिए मध्य बैरियर लाइन मार्किंग",
            "छह लेन वाली सड़क के लिए मध्य बैरियर लाइन मार्किंग",
            "डबल सफेद / पीली लाइनें:\nजहाँ दोनों दिशाओं में दृश्यता सीमित हो वहाँ उपयोग की जाती हैं।",
            "ठोस और टूटी हुई लाइनों का संयोजन:\nयदि आपकी तरफ की लाइन टूटी हुई है, तो आप सुरक्षित होने पर ओवरटेक कर सकते हैं।\nयदि आपकी तरफ सीधी ठोस लाइन है, तो उसे पार न करें।",
            "स्टॉप लाइन:\nचौराहे या जंक्शन से पहले खींची गई ठोस रेखा जहाँ वाहन रोकना अनिवार्य होता है।",
            "रास्ता दें लाइन:\nजंक्शनों पर बनी डबल डॉटेड लाइन जो मुख्य सड़क के वाहनों को पहले जाने का रास्ता देने का संकेत देती है।",
            "किनारे की बॉर्डर लाइनें:\nसड़क के दोनों किनारों पर खींची गई रेखाएं जो सुरक्षित ड्राइविंग सीमा को दर्शाती हैं।",
            "पार्किंग निषेध रेखा:\nसड़क किनारे बनी पीली लाइन जो नो-पार्किंग क्षेत्र की सीमा को दर्शाती है।",
            "येलो बॉक्स जंक्शन:\nपीली क्रॉस लाइनों वाला बॉक्स। जब आगे रास्ता साफ हो तभी प्रवेश करें, बॉक्स के अंदर गाड़ी रोकना मना है।",
            "पैदल यात्री (ज़ेबरा) क्रॉसिंग:\nसड़क पर बने काले और सफेद पट्टे। यहाँ वाहन चालकों को रुककर पैदल चलने वालों को पहले रास्ता देना अनिवार्य है।"
    };

    // 6. Traffic Police Signals
    int[] trafficpolicesignals_img_ary = {
            R.drawable.traffic_police_1, R.drawable.traffic_police_2, R.drawable.traffic_police_3,
            R.drawable.traffic_police_4, R.drawable.traffic_police_5, R.drawable.traffic_police_6,
            R.drawable.traffic_police_7, R.drawable.traffic_police_8, R.drawable.traffic_police_9
    };
    String[] trafficpolicesignals_text_ary_en = {
            "To stop vehicles approaching simultaneously from front and behind",
            "To allow vehicles coming from right and turning right by stopping traffic approaching from the left",
            "To beckon the vehicles approaching from right",
            "To beckon the vehicles approaching from left",
            "To stop vehicles approaching from left and waiting to turn right",
            "To stop vehicles coming from front",
            "To stop vehicles approaching from behind",
            "To stop vehicles approaching from right to allow vehicles from the left to turn right",
            "Warning signal closing all vehicles"
    };
    String[] trafficpolicesignals_text_ary_gu = {
            "સામેથી અને પાછળથી એકસાથે આવતા વાહનોને રોકવા માટે",
            "ડાબી બાજુનો ટ્રાફિક રોકીને જમણી બાજુથી આવતા વાહનોને જમણે વળવા દેવા માટે",
            "જમણી બાજુથી આવતા વાહનોને આગળ વધવા માટેનો ઈશારો",
            "ડાબી બાજુથી આવતા વાહનોને આગળ વધવા માટેનો ઈશારો",
            "ડાબી બાજુથી આવતા અને જમણે વળવા માંગતા વાહનોને રોકવા માટે",
            "સામેથી આવતા વાહનોને રોકવા માટે",
            "પાછળથી આવતા વાહનોને રોકવા માટે",
            "ડાબેથી જમણે વળતા વાહનો માટે જમણી બાજુથી આવતા વાહનોને રોકવા",
            "બધા જ વાહનોને થોભાવી દેવા માટેની ચેતવણીનો ઈશારો"
    };
    String[] trafficpolicesignals_text_ary_hi = {
            "सामने और पीछे से एक साथ आने वाले वाहनों को रोकने के लिए",
            "बाईं ओर के ट्रैफिक को रोककर दाईं ओर से आने वाले वाहनों को जाने की अनुमति देना",
            "दाईं ओर से आने वाले वाहनों को आगे बढ़ने का इशारा",
            "बाईं ओर से आने वाले वाहनों को आगे बढ़ने का इशारा",
            "बाईं तरफ से आने वाले और दाएं मुड़ने वाले वाहनों को रोकने के लिए",
            "सामने से आ रहे वाहनों को रोकने के लिए",
            "पीछे से आ रहे वाहनों को रोकने के लिए",
            "बाएं से दाएं मुड़ने वाले वाहनों को रास्ता देने के लिए दाईं ओर के वाहनों को रोकना",
            "सभी वाहनों को रोकने के लिए चेतावनी संकेत"
    };

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_symbolic_detail);

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

        ImageView ivBack = findViewById(R.id.iv_back);
        if (ivBack != null) {
            ivBack.setOnClickListener(view -> onBackPressed());
        }

        this.str_pass_value = getIntent().getStringExtra("passvalue");
        if (this.str_pass_value == null) {
            this.str_pass_value = "Mandatory";
        }

        String lang = getIntent().getStringExtra("language");
        if (lang != null && !lang.isEmpty()) {
            this.str_language = lang;
        }

        tvHeaderTitle = findViewById(R.id.tv_header_title);
        tabGujarati = findViewById(R.id.tab_gujarati);
        tabHindi = findViewById(R.id.tab_hindi);
        tabEnglish = findViewById(R.id.tab_english);

        if (tabGujarati != null) {
            tabGujarati.setOnClickListener(v -> {
                this.str_language = "gujarati";
                loadSymbolData();
            });
        }
        if (tabHindi != null) {
            tabHindi.setOnClickListener(v -> {
                this.str_language = "hindi";
                loadSymbolData();
            });
        }
        if (tabEnglish != null) {
            tabEnglish.setOnClickListener(v -> {
                this.str_language = "english";
                loadSymbolData();
            });
        }

        this.rto_list_recycler = findViewById(R.id.rto_list_recycler);
        this.rto_list_recycler.setHasFixedSize(true);
        this.rto_list_recycler.setLayoutManager(new LinearLayoutManager(this));
        this.rto_list_recycler.setItemAnimator(new DefaultItemAnimator());

        loadSymbolData();
    }

    private void loadSymbolData() {
        // Update Tabs Styling
        if (tabGujarati != null && tabHindi != null && tabEnglish != null) {
            if ("hindi".equalsIgnoreCase(str_language)) {
                tabHindi.setBackgroundResource(R.drawable.bg_tab_active);
                tabHindi.setTextColor(Color.parseColor("#1E40AF"));
                tabGujarati.setBackgroundResource(R.drawable.bg_tab_inactive);
                tabGujarati.setTextColor(Color.parseColor("#64748B"));
                tabEnglish.setBackgroundResource(R.drawable.bg_tab_inactive);
                tabEnglish.setTextColor(Color.parseColor("#64748B"));
            } else if ("english".equalsIgnoreCase(str_language)) {
                tabEnglish.setBackgroundResource(R.drawable.bg_tab_active);
                tabEnglish.setTextColor(Color.parseColor("#1E40AF"));
                tabGujarati.setBackgroundResource(R.drawable.bg_tab_inactive);
                tabGujarati.setTextColor(Color.parseColor("#64748B"));
                tabHindi.setBackgroundResource(R.drawable.bg_tab_inactive);
                tabHindi.setTextColor(Color.parseColor("#64748B"));
            } else {
                tabGujarati.setBackgroundResource(R.drawable.bg_tab_active);
                tabGujarati.setTextColor(Color.parseColor("#1E40AF"));
                tabHindi.setBackgroundResource(R.drawable.bg_tab_inactive);
                tabHindi.setTextColor(Color.parseColor("#64748B"));
                tabEnglish.setBackgroundResource(R.drawable.bg_tab_inactive);
                tabEnglish.setTextColor(Color.parseColor("#64748B"));
            }
        }

        if (tvHeaderTitle != null) {
            tvHeaderTitle.setText(getHeaderTitleText(this.str_pass_value, this.str_language));
        }

        String[] selectedTextArray;
        int[] selectedImageArray;

        if (this.str_pass_value.equals("Mandatory")) {
            selectedImageArray = this.mandatory_img_ary;
            if ("gujarati".equalsIgnoreCase(str_language)) {
                selectedTextArray = this.mandatory_text_ary_gu;
            } else if ("hindi".equalsIgnoreCase(str_language)) {
                selectedTextArray = this.mandatory_text_ary_hi;
            } else {
                selectedTextArray = this.mandatory_text_ary_en;
            }
        } else if (this.str_pass_value.equals("Cautionary")) {
            selectedImageArray = this.cautionary_img_ary;
            if ("gujarati".equalsIgnoreCase(str_language)) {
                selectedTextArray = this.cautionary_text_ary_gu;
            } else if ("hindi".equalsIgnoreCase(str_language)) {
                selectedTextArray = this.cautionary_text_ary_hi;
            } else {
                selectedTextArray = this.cautionary_text_ary_en;
            }
        } else if (this.str_pass_value.equals("Informatory")) {
            selectedImageArray = this.informatory_img_ary;
            if ("gujarati".equalsIgnoreCase(str_language)) {
                selectedTextArray = this.informatory_text_ary_gu;
            } else if ("hindi".equalsIgnoreCase(str_language)) {
                selectedTextArray = this.informatory_text_ary_hi;
            } else {
                selectedTextArray = this.informatory_text_ary_en;
            }
        } else if (this.str_pass_value.equals("Road & Signals")) {
            selectedImageArray = this.roadsignals_img_ary;
            if ("gujarati".equalsIgnoreCase(str_language)) {
                selectedTextArray = this.roadsignals_text_ary_gu;
            } else if ("hindi".equalsIgnoreCase(str_language)) {
                selectedTextArray = this.roadsignals_text_ary_hi;
            } else {
                selectedTextArray = this.roadsignals_text_ary_en;
            }
        } else if (this.str_pass_value.equals("Driving Rules")) {
            selectedImageArray = this.drivingrules_img_ary;
            if ("gujarati".equalsIgnoreCase(str_language)) {
                selectedTextArray = this.drivingrules_text_ary_gu;
            } else if ("hindi".equalsIgnoreCase(str_language)) {
                selectedTextArray = this.drivingrules_text_ary_hi;
            } else {
                selectedTextArray = this.drivingrules_text_ary_en;
            }
        } else if (this.str_pass_value.equals("Traffic Police Signals")) {
            selectedImageArray = this.trafficpolicesignals_img_ary;
            if ("gujarati".equalsIgnoreCase(str_language)) {
                selectedTextArray = this.trafficpolicesignals_text_ary_gu;
            } else if ("hindi".equalsIgnoreCase(str_language)) {
                selectedTextArray = this.trafficpolicesignals_text_ary_hi;
            } else {
                selectedTextArray = this.trafficpolicesignals_text_ary_en;
            }
        } else {
            selectedImageArray = this.mandatory_img_ary;
            selectedTextArray = "gujarati".equalsIgnoreCase(str_language) ? this.mandatory_text_ary_gu : this.mandatory_text_ary_en;
        }

        this.rtoSymbolDet_rcyAdp = new Task_SymbolAdpter(this, selectedTextArray, selectedImageArray);
        this.rto_list_recycler.setAdapter(this.rtoSymbolDet_rcyAdp);
    }

    private String getHeaderTitleText(String category, String language) {
        if ("gujarati".equalsIgnoreCase(language)) {
            switch (category) {
                case "Mandatory": return "ફરજિયાત સંકેતો";
                case "Cautionary": return "ચેતવણી સંકેતો";
                case "Informatory": return "માહિતીદર્શક સંકેતો";
                case "Road & Signals": return "રોડ માર્કિંગ અને સિગ્નલ";
                case "Driving Rules": return "ડ્રાઇવિંગના નિયમો";
                case "Traffic Police Signals": return "ટ્રાફિક પોલીસના સંકેતો";
                default: return category;
            }
        } else if ("hindi".equalsIgnoreCase(language)) {
            switch (category) {
                case "Mandatory": return "अनिवार्य संकेत";
                case "Cautionary": return "चेतावनी संकेत";
                case "Informatory": return "सूचनात्मक संकेत";
                case "Road & Signals": return "सड़क और सिग्नल";
                case "Driving Rules": return "ड्राइविंग के नियम";
                case "Traffic Police Signals": return "ट्रैफिक पुलिस के संकेत";
                default: return category;
            }
        } else {
            return category;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}