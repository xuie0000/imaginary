package com.xuie.imaginary.gank;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xuie.imaginary.R;
import com.xuie.imaginary.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * @author xuie
 */
public class MeizhiActivity extends DaggerAppCompatActivity {
    private static final String TAG = "MeizhiActivity";

    @Inject MeizhiPresenter meizhiPresenter;
    @Inject MeizhiFragment meizhiFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MeizhiFragment meizhiFragment = (MeizhiFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (meizhiFragment == null) {
            meizhiFragment = this.meizhiFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    meizhiFragment, R.id.contentFrame);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
//        getWindow().setStatusBarColor(getColor(R.color.colorPrimary))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );
        }
    }

}
