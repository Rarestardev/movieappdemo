package com.rarestar.empirebesttv.app;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

public class ScreenUtility {
    private final float dpWidth,dpHeight;
    Activity activity;

    public ScreenUtility(Activity activity){
        this.activity=activity;

        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        float density = activity.getResources().getDisplayMetrics().density;
        dpWidth = metrics.widthPixels / density;
        dpHeight = metrics.heightPixels / density;
    }

    public float getDpWidth() {
        return dpWidth;
    }

    public float getDpHeight() {
        return dpHeight;
    }
}
