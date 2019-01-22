package com.xuie.imaginary.gui.nets.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Pair;
import android.view.View;

import com.xuie.imaginary.R;
import com.xuie.imaginary.base.BaseActivity;
import com.xuie.imaginary.data.NetsDetail;
import com.xuie.imaginary.data.source.NetsRepository;
import com.xuie.imaginary.databinding.ActivityNetsOneBinding;
import com.xuie.imaginary.util.GlideUtils;
import com.xuie.imaginary.util.TimeUtils;
import com.xuie.imaginary.widget.UrlImageGetter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author xuie
 */
public class NetsOneActivity extends BaseActivity implements NetsOneContract.View {
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

    private NetsOneContract.Presenter mPresenter = new NetsOnePresenter(NetsRepository.getInstance(), this);
    private String postId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nets_one;
    }

    private ActivityNetsOneBinding mBinding;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        mBinding = getDataBinding();
        setSupportActionBar(mBinding.toolbar);
        postId = getIntent().getStringExtra(POST_ID);
        mPresenter.getNewsOneRequest(postId);
    }

    @Override
    protected View[] setImmersiveView() {
        return new View[0];
    }

    @Override
    public void refreshNewsOne(NetsDetail netsDetail) {
        String mShareLink = netsDetail.getShareLink();
        String mNewsTitle = netsDetail.getTitle();
        String newsSource = netsDetail.getSource();
        String newsTime = TimeUtils.formatDate(netsDetail.getPtime());
        String newsBody = netsDetail.getBody();
        String newsImgSrc = getImgSrcs(netsDetail);

        setToolBarLayout(mNewsTitle);
        mBinding.content.newsDetailFromTv.setText(getString(R.string.news_from, newsSource, newsTime));
        setNewsDetailPhotoIv(newsImgSrc);

        Observable.just(netsDetail)
                .delay(500, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NetsDetail>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NetsDetail netsDetail) {
                        setBody(netsDetail, netsDetail.getBody());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mBinding.content.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                        mBinding.content.progressBar.setVisibility(View.GONE);
                        mBinding.fab.setVisibility(View.VISIBLE);
                    }
                });

    }

    private void setToolBarLayout(String newsTitle) {
        mBinding.toolbarLayout.setTitle(newsTitle);
        mBinding.toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));
        mBinding.toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white));
    }

    private void setNewsDetailPhotoIv(String imgSrc) {
        GlideUtils.loadImageNetsPhone(this, imgSrc, mBinding.newsDetailPhotoIv);
    }

    private void setBody(NetsDetail netsDetail, String newsBody) {
        int imgTotal = netsDetail.getImg().size();
        if (isShowBody(newsBody, imgTotal)) {
//              mNewsDetailBodyTv.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效,实测经常卡机崩溃
            UrlImageGetter urlImageGetter = new UrlImageGetter(mBinding.content.newsDetailBodyTv, newsBody, imgTotal);
            mBinding.content.newsDetailBodyTv.setText(Html.fromHtml(newsBody, urlImageGetter, null));
        } else {
            mBinding.content.newsDetailBodyTv.setText(Html.fromHtml(newsBody));
        }
    }


    private boolean isShowBody(String newsBody, int imgTotal) {
        return imgTotal >= 2 && newsBody != null;
    }

    private String getImgSrcs(NetsDetail netsDetail) {
        List<NetsDetail.ImgBean> imgSrcs = netsDetail.getImg();
        String imgSrc;
        if (imgSrcs != null && imgSrcs.size() > 0) {
            imgSrc = imgSrcs.get(0).getSrc();
        } else {
            imgSrc = getIntent().getStringExtra(IMG_RES);
        }
        return imgSrc;
    }
}
