package xuk.imaginary.app;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;

import java.util.List;

import xuk.imaginary.R;

/**
 * @author xuie
 * @date 17-7-5
 */
public class App extends Application {

    @SuppressLint("StaticFieldLeak") private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        if (isMainProcess(this)) {
            context = this;
            PreferenceManager.setDefaultValues(this, R.xml.fragment_settings, false);
        }
    }

    public static boolean isMainProcess(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfoList = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfoList) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static Context getContext() {
        return context;
    }
}
