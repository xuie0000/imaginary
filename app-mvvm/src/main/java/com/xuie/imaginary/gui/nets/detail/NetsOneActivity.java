package com.xuie.imaginary.gui.nets.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
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
import com.xuie.imaginary.data.NetsDetail;
import com.xuie.imaginary.databinding.ActivityNetsOneBinding;
import com.xuie.imaginary.util.GlideUtils;
import com.xuie.imaginary.widget.UrlImageGetter;

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

    private String postId;
    private String imgRes;
    private ActivityNetsOneBinding mBinding;
    private NetsOneViewModule netsOneViewModule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_nets_one);
        netsOneViewModule = obtainViewModel(this);

        setSupportActionBar(mBinding.toolbar);
        postId = getIntent().getStringExtra(POST_ID);
        imgRes = getIntent().getStringExtra(IMG_RES);
        netsOneViewModule.getNewsOneRequest(postId, imgRes);
        mBinding.setViewmodule(netsOneViewModule);
    }

    public static NetsOneViewModule obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(NetsOneViewModule.class);
    }

//    @Override
//    public void refreshNewsOne(NetsDetail netsDetail) {
//        String newsSource = netsDetail.getSource();
//        String newsTime = TimeUtils.formatDate(netsDetail.getPtime());
//
//
//        mBinding.content.newsDetailFromTv.setText(getString(R.string.news_from, newsSource, newsTime));
//        setNewsDetailPhotoIv(newsImgSrc);
//
//    }

    private void setNewsDetailPhotoIv(String imgSrc) {
        GlideUtils.loadImageNetsPhone(this, imgSrc, mBinding.newsDetailPhotoIv);
    }

    private void setBody(NetsDetail netsDetail, String newsBody) {
        int imgTotal = netsDetail.getImg().size();
        if (isShowBody(newsBody, imgTotal)) {
//              mNewsDetailBodyTv.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效,实测经常卡机崩溃
            UrlImageGetter urlImageGetter = new UrlImageGetter(mBinding.newsDetailBodyTv, newsBody, imgTotal);
            mBinding.newsDetailBodyTv.setText(Html.fromHtml(newsBody, urlImageGetter, null));
        } else {
            mBinding.newsDetailBodyTv.setText(Html.fromHtml(newsBody));
        }
    }


    private boolean isShowBody(String newsBody, int imgTotal) {
        return imgTotal >= 2 && newsBody != null;
    }

}
