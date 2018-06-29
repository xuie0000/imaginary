package com.xuie.imaginaryandroid.gui.nets.detail;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.anzewei.parallaxbacklayout.ParallaxBack;
import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.data.NetsDetail;
import com.xuie.imaginaryandroid.data.source.NetsRepository;
import com.xuie.imaginaryandroid.util.GlideUtils;
import com.xuie.imaginaryandroid.util.TimeUtils;
import com.xuie.imaginaryandroid.widget.UrlImageGetter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.xuie.imaginaryandroid.util.Utils.checkNotNull;

/**
 * @author xuie
 */
@ParallaxBack
public class NetsOneActivity extends AppCompatActivity implements NetsOneContract.View {
    public static final String POST_ID = "postId";
    public static final String IMG_RES = "image";

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


    @BindView(R.id.news_detail_photo_iv) ImageView newsDetailPhotoIv;
    @BindView(R.id.mask_view) View maskView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_layout) CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar) AppBarLayout appBar;
    @BindView(R.id.news_detail_from_tv) TextView newsDetailFromTv;
    @BindView(R.id.news_detail_body_tv) TextView newsDetailBodyTv;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.fab) FloatingActionButton fab;

    private NetsOneContract.Presenter mPresenter;
    private String postId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nets_one);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        postId = getIntent().getStringExtra(POST_ID);
        new NetsOnePresenter(NetsRepository.getInstance(), this);
    }

    @Override
    public void setPresenter(NetsOneContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
        mPresenter.getNewsOneRequest(postId);
    }

    @Override
    public void refreshNewsOne(NetsDetail netsDetail) {
        String mShareLink = netsDetail.getShareLink();
        String mNewsTitle = netsDetail.getTitle();
        String newsSource = netsDetail.getSource();
        String newsTime = TimeUtils.formatDate(netsDetail.getPtime());
        String newsBody = netsDetail.getBody();
        String NewsImgSrc = getImgSrcs(netsDetail);

        setToolBarLayout(mNewsTitle);
        //mNewsDetailTitleTv.setText(newsTitle);
        newsDetailFromTv.setText(getString(R.string.news_from, newsSource, newsTime));
        setNewsDetailPhotoIv(NewsImgSrc);

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
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                        fab.setVisibility(View.VISIBLE);
                    }
                });

    }

    private void setToolBarLayout(String newsTitle) {
        toolbarLayout.setTitle(newsTitle);
        toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));
        toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white));
    }

    private void setNewsDetailPhotoIv(String imgSrc) {
        GlideUtils.loadImage(this, imgSrc, newsDetailPhotoIv);
    }

    private void setBody(NetsDetail netsDetail, String newsBody) {
        int imgTotal = netsDetail.getImg().size();
        if (isShowBody(newsBody, imgTotal)) {
//              mNewsDetailBodyTv.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效,实测经常卡机崩溃
            UrlImageGetter urlImageGetter = new UrlImageGetter(newsDetailBodyTv, newsBody, imgTotal);
            newsDetailBodyTv.setText(Html.fromHtml(newsBody, urlImageGetter, null));
        } else {
            newsDetailBodyTv.setText(Html.fromHtml(newsBody));
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
