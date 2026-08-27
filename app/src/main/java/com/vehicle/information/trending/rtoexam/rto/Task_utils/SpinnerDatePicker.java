package com.vehicle.information.trending.rtoexam.rto.Task_utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_interfaces.IDatePicker;

import java.util.Calendar;


public class SpinnerDatePicker implements DatePicker.OnDateChangedListener {
    static String h;
    private static SpinnerDatePicker instance;

    /* renamed from: a  reason: collision with root package name */
    Button f1291a;
    Button b;
    Context c;
    Dialog d;
    IDatePicker e;
    TextView f;
    DatePicker g;

    public SpinnerDatePicker(Context context2) {
        this.c = context2;
        this.d = new Dialog(context2);
        openSpinnerDatePicker();
    }

    public SpinnerDatePicker show() {
        this.d.show();
        return instance;
    }

    public static SpinnerDatePicker getInstance(Context context2) {
        SpinnerDatePicker spinnerDatePicker = new SpinnerDatePicker(context2);
        instance = spinnerDatePicker;
        return spinnerDatePicker;
    }

    public SpinnerDatePicker callback(IDatePicker iDatePicker2) {
        this.e = iDatePicker2;
        return instance;
    }

    public SpinnerDatePicker setTitle(String str) {
        TextView textView = this.f;
        if (textView != null) {
            textView.setText(str);
        }
        return instance;
    }

    public SpinnerDatePicker setMaxDate(long j) {
        this.g.setMaxDate(j);
        return instance;
    }

    public SpinnerDatePicker setOkButtonText(String str) {
        Button button = this.f1291a;
        if (button != null) {
            button.setText(str);
        }
        return instance;
    }

    @Override // android.widget.DatePicker.OnDateChangedListener
    public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        sb.append(i3 + "-");
        sb.append((i2 + 1) + "-");
        sb.append(i);
        h = sb.toString();
    }

    private void openSpinnerDatePicker() {
        Dialog dialog = new Dialog(this.c);
        this.d = dialog;
        dialog.requestWindowFeature(1);
        this.d.setContentView(R.layout.picker);
        this.g = (DatePicker) this.d.findViewById(R.id.datePicker);
        this.f1291a = (Button) this.d.findViewById(R.id.okbutton);
        this.f = (TextView) this.d.findViewById(R.id.title);
        this.b = (Button) this.d.findViewById(R.id.cancel);
        LinearLayout linearLayout = (LinearLayout) this.d.findViewById(R.id.buttonLayout);
        CardView cardView = (CardView) this.d.findViewById(R.id.cardView);
        this.f1291a.setOnClickListener(new View.OnClickListener() {
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String str;
                if (SpinnerDatePicker.this.e == null || (str = SpinnerDatePicker.h) == null || str.isEmpty()) {
                    SpinnerDatePicker spinnerDatePicker = SpinnerDatePicker.this;
                    if (!(spinnerDatePicker.e == null || spinnerDatePicker.g == null)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(SpinnerDatePicker.this.g.getDayOfMonth() + "-");
                        sb.append((SpinnerDatePicker.this.g.getMonth() + 1) + "-");
                        sb.append(SpinnerDatePicker.this.g.getYear());
                        String sb2 = sb.toString();
                        SpinnerDatePicker.h = sb2;
                        SpinnerDatePicker.this.e.onOkClick(sb2);
                    }
                } else {
                    SpinnerDatePicker.this.e.onOkClick(SpinnerDatePicker.h);
                }
                SpinnerDatePicker.this.d.dismiss();
            }
        });
        this.b.setOnClickListener(new View.OnClickListener() {
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SpinnerDatePicker.this.d.dismiss();
            }
        });
        Calendar calendar = Calendar.getInstance();
        this.g.init(calendar.get(1), calendar.get(2), calendar.get(5), this);
        this.d.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                IDatePicker iDatePicker = SpinnerDatePicker.this.e;
                if (iDatePicker != null) {
                    iDatePicker.onDialogDismiss();
                }
            }
        });
    }
}
