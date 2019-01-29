package com.xuie.imaginary.gui.nets.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.xuie.imaginary.R;
import com.xuie.imaginary.ViewModelFactory;
import com.xuie.imaginary.databinding.ActivityNetsOneBinding;

/**
 * @author xuie
 */
public class NetsOneActivity extends AppCompatActivity {
    public static final String POST_ID = "postId";
    public static final String IMG_RES = "image";

    @SuppressLint("ObsoleteSdkInt")
    public static void newIntent(Context mContext, View imageView, View titleView, String postId, String imgUrl) {
        Intent intent = new Intent(mContext, NetsOneActivity.class);
        intent.putExtra(POST_ID, postId);
        intent.putExtra(IMG_RES, imgUrl);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,
                    // 创建多个共享元素
                    Pair.create(imageView, mContext.getString(R.string.transition_image)),
                    Pair.create(titleView, mContext.getString(R.string.transition_title))
            );

            mContext.startActivity(intent, options.toBundle());
        } else {
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(imageView, imageView.getWidth() / 2, imageView.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity(mContext, intent, options.toBundle());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNetsOneBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_nets_one);
        NetsOneViewModule netsOneViewModule = obtainViewModel(this);

        setSupportActionBar(mBinding.toolbar);
        String postId = getIntent().getStringExtra(POST_ID);
        String imgRes = getIntent().getStringExtra(IMG_RES);
        netsOneViewModule.getNewsOneRequest(postId, imgRes);
        mBinding.setViewmodule(netsOneViewModule);
    }

    public static NetsOneViewModule obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(NetsOneViewModule.class);
    }

}
