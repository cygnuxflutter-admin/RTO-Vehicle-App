package com.vehicle.information.trending.rtoexam.rto.Task_Extra;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;
import com.vehicle.information.trending.rtoexam.rto.R;


public class CustomLoaderScreen extends FrameLayout {

    /* renamed from: a  reason: collision with root package name */
    CircularFillableLoaders f1285a;
    public Callback callback;
    public boolean loadingStarted = false;
    Handler b = new Handler();
    public int progress = 10;
    Runnable e = new Runnable() { // from class: com.vehicle.information.trending.rtoexam.rto.widget.CustomLoaderScreen.1
        @Override // java.lang.Runnable
        public void run() {
            CustomLoaderScreen customLoaderScreen = CustomLoaderScreen.this;
            customLoaderScreen.loadingStarted = true;
            Callback callback = customLoaderScreen.callback;
            if (callback != null) {
                callback.start();
            }
        }
    };
    Runnable c = new Runnable() { // from class: com.vehicle.information.trending.rtoexam.rto.widget.CustomLoaderScreen.2
        @Override // java.lang.Runnable
        public final void run() {
            CustomLoaderScreen customLoaderScreen = CustomLoaderScreen.this;
            int i = customLoaderScreen.progress + 10;
            customLoaderScreen.progress = i;
            if (customLoaderScreen.f1285a != null) {
                customLoaderScreen.f1285a.setProgress(i);
            }
            CustomLoaderScreen.this.d.run();
        }
    };
    Runnable d = new Runnable() { // from class: com.vehicle.information.trending.rtoexam.rto.widget.CustomLoaderScreen.3
        @Override // java.lang.Runnable
        public final void run() {
            CustomLoaderScreen customLoaderScreen = CustomLoaderScreen.this;
            if (customLoaderScreen.progress != 90) {
                customLoaderScreen.b.postDelayed(customLoaderScreen.c, 1000L);
            }
        }
    };


    public interface Callback {
        void start();
    }

    public CustomLoaderScreen(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        try {
            this.f1285a = (CircularFillableLoaders) View.inflate(context, R.layout.view_custom_loader, this).findViewById(R.id.cfl_progress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setVisibilityCustomLoaderScreen(final int i) {
        if (i == 0) {
            this.b.postDelayed(this.e, 2000L);
            this.c.run();
            super.setVisibility(i);
        } else if (i == 8) {
            if (this.f1285a != null) {
                this.f1285a.setProgress(100);
            }
            this.b.removeCallbacksAndMessages(null);
            restartProgress();
            this.b.postDelayed(new Runnable() { // from class: com.vehicle.information.trending.rtoexam.rto.widget.CustomLoaderScreen.4
                @Override // java.lang.Runnable
                public final void run() {
                    CustomLoaderScreen.this.setVisibilityCustomLoaderScreen(i);
                }
            }, 1000L);
        }
    }

    private void restartProgress() {
        this.b.removeCallbacks(this.e);
        this.b.removeCallbacksAndMessages(null);
        this.progress = 0;
        this.loadingStarted = false;
    }

    public void finishLoading() {
        this.b.removeCallbacks(this.e);
        if (this.f1285a != null) {
            this.f1285a.setProgress(100);
        }
        this.b.removeCallbacksAndMessages(null);
        restartProgress();
        this.b.postDelayed(new Runnable() { // from class: com.vehicle.information.trending.rtoexam.rto.widget.CustomLoaderScreen.5
            @Override // java.lang.Runnable
            public final void run() {
                CustomLoaderScreen.this.finishloadingCustomLoaderScreen();
            }
        }, 1000L);
    }

    public void finishloadingCustomLoaderScreen() {
        super.setVisibility(View.GONE);
        if (this.f1285a != null) {
            this.f1285a.setProgress(0);
        }
    }

    public boolean isLoadingStarted() {
        return this.loadingStarted;
    }

    public void setCallback(Callback callback2) {
        this.callback = callback2;
    }
}
