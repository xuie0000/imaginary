package com.xuie.imaginaryandroid.app;

import android.app.Application;
import android.content.Context;

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
    }

    public static Context getContext() {
        return context;
    }
}
