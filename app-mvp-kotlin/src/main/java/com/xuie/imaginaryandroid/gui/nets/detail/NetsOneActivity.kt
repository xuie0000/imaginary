package com.xuie.imaginaryandroid.gui.nets.detail

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import android.util.Pair
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

import com.xuie.imaginaryandroid.R
import com.xuie.imaginaryandroid.data.NetsDetail
import com.xuie.imaginaryandroid.data.source.NETSRepository
import com.xuie.imaginaryandroid.glide.GlideApp
import com.xuie.imaginaryandroid.util.TimeUtils
import com.xuie.imaginaryandroid.widget.URLImageGetter
import java.util.concurrent.TimeUnit

import butterknife.BindView
import butterknife.ButterKnife
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

import com.xuie.imaginaryandroid.util.Utils.checkNotNull

class NetsOneActivity : AppCompatActivity(), NetsOneContract.View {


    @BindView(R.id.news_detail_photo_iv)
    internal var newsDetailPhotoIv: ImageView? = null
    @BindView(R.id.mask_view)
    internal var maskView: View? = null
    @BindView(R.id.toolbar)
    internal var toolbar: Toolbar? = null
    @BindView(R.id.toolbar_layout)
    internal var toolbarLayout: CollapsingToolbarLayout? = null
    @BindView(R.id.app_bar)
    internal var appBar: AppBarLayout? = null
    @BindView(R.id.news_detail_from_tv)
    internal var newsDetailFromTv: TextView? = null
    @BindView(R.id.news_detail_body_tv)
    internal var newsDetailBodyTv: TextView? = null
    @BindView(R.id.progress_bar)
    internal var progressBar: ProgressBar? = null
    @BindView(R.id.fab)
    internal var fab: FloatingActionButton? = null

    private var mPresenter: NetsOneContract.Presenter? = null
    private var postId: String? = null


    @Override
    protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nets_one)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)

        postId = getIntent().getStringExtra(POST_ID)
        NetsOnePresenter(NETSRepository.getInstance(), this)
    }

    @Override
    fun setPresenter(presenter: NetsOneContract.Presenter) {
        mPresenter = checkNotNull(presenter)
        mPresenter!!.getNewsOneRequest(postId)
    }

    @Override
    fun refreshNewsOne(netsDetail: NetsDetail) {
        val mShareLink = netsDetail.getShareLink()
        val mNewsTitle = netsDetail.getTitle()
        val newsSource = netsDetail.getSource()
        val newsTime = TimeUtils.formatDate(netsDetail.getPtime())
        val newsBody = netsDetail.getBody()
        val NewsImgSrc = getImgSrcs(netsDetail)

        setToolBarLayout(mNewsTitle)
        //mNewsDetailTitleTv.setText(newsTitle);
        newsDetailFromTv!!.setText(getString(R.string.news_from, newsSource, newsTime))
        setNewsDetailPhotoIv(NewsImgSrc)

        Observable.just(netsDetail)
                .delay(500, TimeUnit.MILLISECONDS)
                //                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<NetsDetail>() {
                    @Override
                    fun onCompleted() {
                        progressBar!!.setVisibility(View.GONE)
                        fab!!.setVisibility(View.VISIBLE)
                    }

                    @Override
                    fun onError(e: Throwable) {
                        progressBar!!.setVisibility(View.GONE)
                    }

                    @Override
                    fun onNext(netsDetail: NetsDetail) {
                        setBody(netsDetail, netsDetail.getBody())
                    }
                })

    }

    private fun setToolBarLayout(newsTitle: String) {
        toolbarLayout!!.setTitle(newsTitle)
        toolbarLayout!!.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white))
        toolbarLayout!!.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white))
    }

    private fun setNewsDetailPhotoIv(imgSrc: String) {
        GlideApp.with(this)
                .load(imgSrc)
                .centerCrop()
                .placeholder(R.mipmap.ic_empty_picture)
                .error(R.mipmap.ic_empty_picture)
                .into(newsDetailPhotoIv)
    }

    private fun setBody(netsDetail: NetsDetail, newsBody: String) {
        val imgTotal = netsDetail.getImg().size()
        if (isShowBody(newsBody, imgTotal)) {
            //              mNewsDetailBodyTv.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效,实测经常卡机崩溃
            val urlImageGetter = URLImageGetter(newsDetailBodyTv, newsBody, imgTotal)
            newsDetailBodyTv!!.setText(Html.fromHtml(newsBody, urlImageGetter, null))
        } else {
            newsDetailBodyTv!!.setText(Html.fromHtml(newsBody))
        }
    }


    private fun isShowBody(newsBody: String?, imgTotal: Int): Boolean {
        return imgTotal >= 2 && newsBody != null
    }

    private fun getImgSrcs(netsDetail: NetsDetail): String {
        val imgSrcs = netsDetail.getImg()
        val imgSrc: String
        if (imgSrcs != null && imgSrcs!!.size() > 0) {
            imgSrc = imgSrcs!!.get(0).getSrc()
        } else {
            imgSrc = getIntent().getStringExtra(IMG_RES)
        }
        return imgSrc
    }

    companion object {
        val POST_ID = "postId"
        val IMG_RES = "image"

        fun newIntent(mContext: Context, imageView: View, titleView: View, postId: String, imgUrl: String) {
            val intent = Intent(mContext, NetsOneActivity::class.java)
            intent.putExtra(POST_ID, postId)
            intent.putExtra(IMG_RES, imgUrl)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val options = ActivityOptions.makeSceneTransitionAnimation(mContext as Activity,
                        // 创建多个共享元素
                        Pair.create(imageView, mContext.getString(R.string.transition_image)),
                        Pair.create(titleView, mContext.getString(R.string.transition_title))
                )

                mContext.startActivity(intent, options.toBundle())
            } else {
                val options = ActivityOptionsCompat
                        .makeScaleUpAnimation(imageView, imageView.getWidth() / 2, imageView.getHeight() / 2, 0, 0)
                ActivityCompat.startActivity(mContext, intent, options.toBundle())
            }
        }
    }
}
