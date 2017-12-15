package com.xuie.imaginaryandroid.app;

import android.app.Application;
import android.content.Context;
import android.support.v7.preference.PreferenceManager;

import com.xuie.imaginaryandroid.R;

/**
 * Created by xuie on 17-7-5.
 */

public class App extends Application {
    private static final String TAG = "App";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        PreferenceManager.setDefaultValues(this, R.xml.fragment_settings, false);
    }

    public static Context getContext() {
        return context;
    }
}
