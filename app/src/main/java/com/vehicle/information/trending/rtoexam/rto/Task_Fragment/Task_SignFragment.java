package com.vehicle.information.trending.rtoexam.rto.Task_Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.Task_CustomAdpter;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_SignModel;
import com.vehicle.information.trending.rtoexam.rto.R;
import java.util.ArrayList;

public class Task_SignFragment extends Fragment {
    Task_CustomAdpter ctm_adp;
    ListView secondListView;
    String str_language;

    public Task_SignFragment(String str) {
        this.str_language = str;
    }

    @Override
    public String toString() {
        if ("gujarati".equalsIgnoreCase(this.str_language)) {
            return "ચિહ્નો";
        }
        if ("hindi".equalsIgnoreCase(this.str_language)) {
            return "प्रतीक";
        }
        return "Signs";
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.task_sign_fragment, viewGroup, false);
        this.secondListView = inflate.findViewById(R.id.secondListView);
        if ("gujarati".equalsIgnoreCase(this.str_language)) {
            this.ctm_adp = new Task_CustomAdpter(getActivity(), getSigns_gujarati());
        } else if ("hindi".equalsIgnoreCase(this.str_language)) {
            this.ctm_adp = new Task_CustomAdpter(getActivity(), getSigns_hindi());
        } else {
            this.ctm_adp = new Task_CustomAdpter(getActivity(), getSigns_english());
        }
        this.secondListView.setAdapter((ListAdapter) this.ctm_adp);
        return inflate;
    }

    private ArrayList<Task_SignModel> getSigns_gujarati() {
        ArrayList<Task_SignModel> arrayList = new ArrayList<>();
        arrayList.add(new Task_SignModel("વાહન થોભો.", R.drawable.ic_symbol_1));
        arrayList.add(new Task_SignModel("ફરજીયાત ડાબી બાજુ રસ્તો રાખો.", R.drawable.ic_symbol_2));
        arrayList.add(new Task_SignModel("રસ્તો આપો.", R.drawable.ic_symbol_3));
        arrayList.add(new Task_SignModel("નો એન્ટ્રી", R.drawable.ic_symbol_4));
        arrayList.add(new Task_SignModel("જમણી બાજુ તીવ્ર વળાંક.", R.drawable.ic_symbol_5));
        arrayList.add(new Task_SignModel("રાહદારી માટે નું ક્રોસિંગ.", R.drawable.ic_symbol_6));
        arrayList.add(new Task_SignModel("જમણી બાજુ પાર્કિંગ માન્ય છે.", R.drawable.ic_symbol_7));
        arrayList.add(new Task_SignModel("U-Turn મનાઈ છે.", R.drawable.ic_symbol_8));
        arrayList.add(new Task_SignModel("હોર્ન વગાડવાની મનાઈ છે.", R.drawable.ic_symbol_9));
        arrayList.add(new Task_SignModel("આગળ સાંકડો બ્રીજ છે.", R.drawable.ic_symbol_10));
        arrayList.add(new Task_SignModel("ગતિ મર્યાદા નો અંત.", R.drawable.ic_symbol_11));
        arrayList.add(new Task_SignModel("આગળ સાંકડો રસ્તો છે.", R.drawable.ic_symbol_12));
        arrayList.add(new Task_SignModel("ઓવર ટેકિંગ ની મનાઈ છે.", R.drawable.ic_symbol_14));
        arrayList.add(new Task_SignModel("આગળ ક્રોસ રોડ છે (બે રસ્તાઓ ભેગા થાય છે / ચાર રસ્તા છે)", R.drawable.ic_symbol_15));
        arrayList.add(new Task_SignModel("ફરજીયાત આગળ વધો અથવા ડાબી બાજુ વાહન વાળી શકાય.", R.drawable.ic_symbol_16));
        arrayList.add(new Task_SignModel("હોર્ન વગાડવો ફરજીયાત છે.", R.drawable.ic_symbol_17));
        arrayList.add(new Task_SignModel("આગળ જમણી બાજુ રસ્તો છે.", R.drawable.ic_symbol_18));
        arrayList.add(new Task_SignModel("વાહન ને અલ્પ સમય માટે પણ ઉભું રાખવાની મનાઈ છે.", R.drawable.ic_symbol_19));
        arrayList.add(new Task_SignModel("સીધા જાઓ.", R.drawable.ic_symbol_20));
        arrayList.add(new Task_SignModel("બધા પ્રકાર ના વાહનો માટે પ્રવેશ બંધ.", R.drawable.ic_symbol_21));
        arrayList.add(new Task_SignModel("ટ્રક માટે પ્રવેશ ની મનાઈ છે.", R.drawable.ic_symbol_22));
        arrayList.add(new Task_SignModel("રાહદારી માટે મનાઈ છે.", R.drawable.ic_symbol_24));
        arrayList.add(new Task_SignModel("ડાબી બાજુ વાળવાની મનાઈ છે.", R.drawable.ic_symbol_25));
        arrayList.add(new Task_SignModel("૫૦ કિમી/કલાક કરતા વધુ ઝડપે વાહન ચલાવવું નહિ.", R.drawable.ic_symbol_26));
        arrayList.add(new Task_SignModel("ફરજીયાત સીધા જાઓ અથવા right side વાળો.", R.drawable.ic_symbol_27));
        arrayList.add(new Task_SignModel("ડાબી બાજુ નો વળાંક.", R.drawable.ic_symbol_29));
        arrayList.add(new Task_SignModel("જમણી બાજુ ચીપિયા જેવો વળાંક.", R.drawable.ic_symbol_30));
        arrayList.add(new Task_SignModel("ડાબી બાજુ ચીપિયા જેવો વળાંક.", R.drawable.ic_symbol_31));
        arrayList.add(new Task_SignModel("વાંકો ચૂકો રસ્તો જમણી બાજુ.", R.drawable.ic_symbol_32));
        arrayList.add(new Task_SignModel("વાંકો ચૂકો રસ્તો ડાબી બાજુ.", R.drawable.ic_symbol_33));
        arrayList.add(new Task_SignModel("વાય ક્રોસિંગ", R.drawable.ic_symbol_34));
        arrayList.add(new Task_SignModel("એક્ષલ વજન ની મર્યાદા.", R.drawable.ic_symbol_35));
        arrayList.add(new Task_SignModel("ગોળ ફરીને જાઓ.", R.drawable.ic_symbol_37));
        arrayList.add(new Task_SignModel("ભયજનક સુપડી છે.", R.drawable.ic_symbol_38));
        arrayList.add(new Task_SignModel("જમણી બાજુ વાય ક્રોસિંગ.", R.drawable.ic_symbol_39));
        arrayList.add(new Task_SignModel("વાય ક્રોસિંગ ડાબી બાજુ.", R.drawable.ic_symbol_40));
        arrayList.add(new Task_SignModel("વાહન લપસી જાય એવો ચીકણો રસ્તો.", R.drawable.ic_symbol_41));
        arrayList.add(new Task_SignModel("છુટા પથ્થર વાળો રસ્તો.", R.drawable.ic_symbol_42));
        arrayList.add(new Task_SignModel("સાઇકલ માટે નું ક્રોસિંગ.", R.drawable.ic_symbol_43));
        arrayList.add(new Task_SignModel("રસ્તા ઉપર ઢોર-પશુ આવવાની શક્યતા છે.", R.drawable.ic_symbol_44));
        arrayList.add(new Task_SignModel("આગળ સ્કૂલ છે.", R.drawable.ic_symbol_45));
        arrayList.add(new Task_SignModel("રોડ ઉપર માણસ કામ કરે છે.", R.drawable.ic_symbol_46));
        arrayList.add(new Task_SignModel("ખડક ના પથ્થરો ઉપર થી પડે તેમ છે.", R.drawable.ic_symbol_47));
        arrayList.add(new Task_SignModel("નાસ્તા પાણી માટે નો સ્ટોલ છે.", R.drawable.ic_symbol_48));
        arrayList.add(new Task_SignModel("સીધુ ચઢાણ.", R.drawable.ic_symbol_49));
        arrayList.add(new Task_SignModel("સીધુ ઉતરાણ.", R.drawable.ic_symbol_50));
        arrayList.add(new Task_SignModel("આગળ રસ્તો પહોળો છે.", R.drawable.ic_symbol_51));
        return arrayList;
    }

    private ArrayList<Task_SignModel> getSigns_hindi() {
        ArrayList<Task_SignModel> arrayList = new ArrayList<>();
        arrayList.add(new Task_SignModel("रुकिए", R.drawable.ic_symbol_1));
        arrayList.add(new Task_SignModel("बाएं मुड़ना अनिवार्य है", R.drawable.ic_symbol_2));
        arrayList.add(new Task_SignModel("रास्ता दीजिए", R.drawable.ic_symbol_3));
        arrayList.add(new Task_SignModel("प्रवेश निषेध", R.drawable.ic_symbol_4));
        arrayList.add(new Task_SignModel("दाएं तरफ तीव्र मोड़", R.drawable.ic_symbol_5));
        arrayList.add(new Task_SignModel("पैदल यात्री क्रॉसिंग", R.drawable.ic_symbol_6));
        arrayList.add(new Task_SignModel("दाहिनी ओर पार्किंग मान्य है", R.drawable.ic_symbol_7));
        arrayList.add(new Task_SignModel("यू-टर्न वर्जित", R.drawable.ic_symbol_8));
        arrayList.add(new Task_SignModel("हॉर्न बजाना मना है", R.drawable.ic_symbol_9));
        arrayList.add(new Task_SignModel("आगे संकरा पुल है", R.drawable.ic_symbol_10));
        arrayList.add(new Task_SignModel("गति सीमा समाप्त", R.drawable.ic_symbol_11));
        arrayList.add(new Task_SignModel("आगे संकरी सड़क है", R.drawable.ic_symbol_12));
        arrayList.add(new Task_SignModel("ओवरटेकिंग वर्जित", R.drawable.ic_symbol_14));
        arrayList.add(new Task_SignModel("चौराहा (क्रॉस रोड)", R.drawable.ic_symbol_15));
        arrayList.add(new Task_SignModel("अनिवार्य सीधे जाएं या बाएं मुड़ें", R.drawable.ic_symbol_16));
        arrayList.add(new Task_SignModel("हॉर्न बजाना अनिवार्य है", R.drawable.ic_symbol_17));
        arrayList.add(new Task_SignModel("दाएं मोड़ है", R.drawable.ic_symbol_18));
        arrayList.add(new Task_SignModel("गाड़ी खड़ी करना मना है", R.drawable.ic_symbol_19));
        arrayList.add(new Task_SignModel("सीधे जाएं", R.drawable.ic_symbol_20));
        arrayList.add(new Task_SignModel("सभी वाहनों का प्रवेश निषेध", R.drawable.ic_symbol_21));
        arrayList.add(new Task_SignModel("ट्रक का प्रवेश निषेध", R.drawable.ic_symbol_22));
        arrayList.add(new Task_SignModel("पैदल चलना मना है", R.drawable.ic_symbol_24));
        arrayList.add(new Task_SignModel("बाएं मुड़ना मना है", R.drawable.ic_symbol_25));
        arrayList.add(new Task_SignModel("50 किमी/घंटा से अधिक गति वर्जित", R.drawable.ic_symbol_26));
        arrayList.add(new Task_SignModel("अनिवार्य सीधे जाएं या दाएं मुड़ें", R.drawable.ic_symbol_27));
        arrayList.add(new Task_SignModel("बाएं मोड़", R.drawable.ic_symbol_29));
        arrayList.add(new Task_SignModel("दाएं हेयर पिन मोड़", R.drawable.ic_symbol_30));
        arrayList.add(new Task_SignModel("बाएं हेयर पिन मोड़", R.drawable.ic_symbol_31));
        arrayList.add(new Task_SignModel("दाएं घुमावदार सड़क", R.drawable.ic_symbol_32));
        arrayList.add(new Task_SignModel("बाएं घुमावदार सड़क", R.drawable.ic_symbol_33));
        arrayList.add(new Task_SignModel("वाई-इंटरसेक्शन", R.drawable.ic_symbol_34));
        arrayList.add(new Task_SignModel("धुरी भार सीमा", R.drawable.ic_symbol_35));
        arrayList.add(new Task_SignModel("गोल चक्कर", R.drawable.ic_symbol_37));
        arrayList.add(new Task_SignModel("खतरनाक गड्ढा / ढलान", R.drawable.ic_symbol_38));
        arrayList.add(new Task_SignModel("दाएं वाई-इंटरसेक्शन", R.drawable.ic_symbol_39));
        arrayList.add(new Task_SignModel("बाएं वाई-इंटरसेक्शन", R.drawable.ic_symbol_40));
        arrayList.add(new Task_SignModel("फिसलन भरी सड़क", R.drawable.ic_symbol_41));
        arrayList.add(new Task_SignModel("बिखरी बजरी / पत्थर", R.drawable.ic_symbol_42));
        arrayList.add(new Task_SignModel("साइकिल क्रॉसिंग", R.drawable.ic_symbol_43));
        arrayList.add(new Task_SignModel("पशु आने की संभावना", R.drawable.ic_symbol_44));
        arrayList.add(new Task_SignModel("आगे स्कूल है", R.drawable.ic_symbol_45));
        arrayList.add(new Task_SignModel("काम चालू है", R.drawable.ic_symbol_46));
        arrayList.add(new Task_SignModel("चट्टान गिरने की संभावना", R.drawable.ic_symbol_47));
        arrayList.add(new Task_SignModel("जलपान गृह", R.drawable.ic_symbol_48));
        arrayList.add(new Task_SignModel("सीधी चढ़ाई", R.drawable.ic_symbol_49));
        arrayList.add(new Task_SignModel("सीधी ढलान", R.drawable.ic_symbol_50));
        arrayList.add(new Task_SignModel("आगे सड़क चौड़ी है", R.drawable.ic_symbol_51));
        return arrayList;
    }

    private ArrayList<Task_SignModel> getSigns_english() {
        ArrayList<Task_SignModel> arrayList = new ArrayList<>();
        arrayList.add(new Task_SignModel("Stop", R.drawable.ic_symbol_1));
        arrayList.add(new Task_SignModel("Compulsory Keep Left", R.drawable.ic_symbol_2));
        arrayList.add(new Task_SignModel("Give Way", R.drawable.ic_symbol_3));
        arrayList.add(new Task_SignModel("No Entry", R.drawable.ic_symbol_4));
        arrayList.add(new Task_SignModel("Right Hand Curve", R.drawable.ic_symbol_5));
        arrayList.add(new Task_SignModel("Pedestrian Crossing", R.drawable.ic_symbol_6));
        arrayList.add(new Task_SignModel("Parking This Side", R.drawable.ic_symbol_7));
        arrayList.add(new Task_SignModel("U-Turn Prohibited", R.drawable.ic_symbol_8));
        arrayList.add(new Task_SignModel("Horn Prohibited", R.drawable.ic_symbol_9));
        arrayList.add(new Task_SignModel("Narrow Bridge Ahead", R.drawable.ic_symbol_10));
        arrayList.add(new Task_SignModel("Speed Limit Ends", R.drawable.ic_symbol_11));
        arrayList.add(new Task_SignModel("Narrow Road Ahead", R.drawable.ic_symbol_12));
        arrayList.add(new Task_SignModel("Overtaking Prohibited", R.drawable.ic_symbol_14));
        arrayList.add(new Task_SignModel("Cross Road Ahead", R.drawable.ic_symbol_15));
        arrayList.add(new Task_SignModel("Compulsory Ahead or Turn Left", R.drawable.ic_symbol_16));
        arrayList.add(new Task_SignModel("Compulsory Sound Horn", R.drawable.ic_symbol_17));
        arrayList.add(new Task_SignModel("Side Road Right", R.drawable.ic_symbol_18));
        arrayList.add(new Task_SignModel("No Stopping or Standing", R.drawable.ic_symbol_19));
        arrayList.add(new Task_SignModel("Straight Prohibited / Go Straight", R.drawable.ic_symbol_20));
        arrayList.add(new Task_SignModel("All Motor Vehicles Prohibited", R.drawable.ic_symbol_21));
        arrayList.add(new Task_SignModel("Trucks Prohibited", R.drawable.ic_symbol_22));
        arrayList.add(new Task_SignModel("Pedestrians Prohibited", R.drawable.ic_symbol_24));
        arrayList.add(new Task_SignModel("Left Turn Prohibited", R.drawable.ic_symbol_25));
        arrayList.add(new Task_SignModel("Speed Limit 50 km/h", R.drawable.ic_symbol_26));
        arrayList.add(new Task_SignModel("Compulsory Ahead or Turn Right", R.drawable.ic_symbol_27));
        arrayList.add(new Task_SignModel("Left Hand Curve", R.drawable.ic_symbol_29));
        arrayList.add(new Task_SignModel("Right Hairpin Bend", R.drawable.ic_symbol_30));
        arrayList.add(new Task_SignModel("Left Hairpin Bend", R.drawable.ic_symbol_31));
        arrayList.add(new Task_SignModel("Right Reverse Bend", R.drawable.ic_symbol_32));
        arrayList.add(new Task_SignModel("Left Reverse Bend", R.drawable.ic_symbol_33));
        arrayList.add(new Task_SignModel("Y-Intersection", R.drawable.ic_symbol_34));
        arrayList.add(new Task_SignModel("Axle Weight Limit", R.drawable.ic_symbol_35));
        arrayList.add(new Task_SignModel("Roundabout", R.drawable.ic_symbol_37));
        arrayList.add(new Task_SignModel("Dangerous Dip", R.drawable.ic_symbol_38));
        arrayList.add(new Task_SignModel("Right Y-Intersection", R.drawable.ic_symbol_39));
        arrayList.add(new Task_SignModel("Left Y-Intersection", R.drawable.ic_symbol_40));
        arrayList.add(new Task_SignModel("Slippery Road", R.drawable.ic_symbol_41));
        arrayList.add(new Task_SignModel("Loose Gravel", R.drawable.ic_symbol_42));
        arrayList.add(new Task_SignModel("Cycle Crossing", R.drawable.ic_symbol_43));
        arrayList.add(new Task_SignModel("Cattle Crossing", R.drawable.ic_symbol_44));
        arrayList.add(new Task_SignModel("School Ahead", R.drawable.ic_symbol_45));
        arrayList.add(new Task_SignModel("Men at Work", R.drawable.ic_symbol_46));
        arrayList.add(new Task_SignModel("Falling Rocks", R.drawable.ic_symbol_47));
        arrayList.add(new Task_SignModel("Light Refreshment", R.drawable.ic_symbol_48));
        arrayList.add(new Task_SignModel("Steep Ascent", R.drawable.ic_symbol_49));
        arrayList.add(new Task_SignModel("Steep Descent", R.drawable.ic_symbol_50));
        arrayList.add(new Task_SignModel("Road Widens Ahead", R.drawable.ic_symbol_51));
        return arrayList;
    }
}