package com.xuie.imaginary.gui.gank.show;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xuie.imaginary.R;
import com.xuie.imaginary.ViewModelFactory;
import com.xuie.imaginary.databinding.ActivityGankBinding;

import java.util.ArrayList;

/**
 * @author xuie
 */
public class GankActivity extends AppCompatActivity {
    private static final String TAG = "GankActivity";

    private ExpandableItemAdapter adapter = new ExpandableItemAdapter(new ArrayList<>());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGankBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_gank);

        String date = getIntent().getStringExtra("date");
        String imageUrl = getIntent().getStringExtra("image");

        Log.d(TAG, "onCreate: data - " + date + ", image - " + imageUrl);

        GankViewModule gankViewModule = obtainViewModel(this);
        gankViewModule.dateString.set(date);
        gankViewModule.imageUrl.set(imageUrl);
        mBinding.setViewmodule(gankViewModule);

        mBinding.recyclerView.setAdapter(adapter);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setNestedScrollingEnabled(false);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        gankViewModule.getGank(date);
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );
        }
    }

    public static GankViewModule obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(GankViewModule.class);
    }
}
