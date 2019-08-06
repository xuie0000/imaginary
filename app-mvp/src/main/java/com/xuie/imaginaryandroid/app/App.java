package xuk.imaginary.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.v7.preference.PreferenceManager;

import com.github.anzewei.parallaxbacklayout.ParallaxHelper;
import xuk.imaginary.R;
/**
 * @author xuie
 * @date 17-7-5
 */
public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        PreferenceManager.setDefaultValues(this, R.xml.fragment_settings, false);

        registerActivityLifecycleCallbacks(ParallaxHelper.getInstance());
    }

    public static Context getContext() {
        return context;
    }
}
