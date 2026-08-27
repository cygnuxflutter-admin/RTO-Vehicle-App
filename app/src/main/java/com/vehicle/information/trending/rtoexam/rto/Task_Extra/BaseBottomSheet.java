package com.vehicle.information.trending.rtoexam.rto.Task_Extra;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vehicle.information.trending.rtoexam.rto.R;


public class BaseBottomSheet extends BottomSheetDialogFragment {
    public Context context;
    private String message;
    private String title;

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(2, R.style.CustomBottomSheetDialogTheme);
        this.title = getArguments().getString("TITLE");
        this.message = getArguments().getString("MESSAGE");
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.bottom_sheet_acknowledgement, viewGroup, false);
        TextView textView = (TextView) inflate.findViewById(R.id.cta);
        TextView textView2 = (TextView) inflate.findViewById(R.id.secondaryBtn);
        ((TextView) inflate.findViewById(R.id.title)).setText(this.title);
        ((TextView) inflate.findViewById(R.id.message)).setText(this.message);
        textView2.setText(R.string.txt_not_now);
        textView.setText(R.string.txt_open_settings);
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.vehicle.information.trending.rtoexam.rto.widget.BaseBottomSheet.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BaseBottomSheet.this.dismiss();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.vehicle.information.trending.rtoexam.rto.widget.BaseBottomSheet.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", BaseBottomSheet.this.context.getPackageName(), null));
                BaseBottomSheet.this.context.startActivity(intent);
                BaseBottomSheet.this.dismiss();
            }
        });
        return inflate;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context2) {
        super.onAttach(context2);
        this.context = context2;
    }
}
