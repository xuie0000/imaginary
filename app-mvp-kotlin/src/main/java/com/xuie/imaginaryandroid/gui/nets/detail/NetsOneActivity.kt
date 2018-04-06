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
import com.xuie.imaginaryandroid.util.TimeUtils
import com.xuie.imaginaryandroid.widget.URLImageGetter
import io.reactivex.android.schedulers.AndroidSchedulers
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class NetsOneActivity : AppCompatActivity(), NetsOneContract.View {
    override lateinit var presenter: NetsOneContract.Presenter

    private lateinit var newsDetailPhotoIv: ImageView
    private lateinit var maskView: View
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarLayout: CollapsingToolbarLayout
    private lateinit var appBar: AppBarLayout
    private lateinit var newsDetailFromTv: TextView
    private lateinit var newsDetailBodyTv: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var fab: FloatingActionButton

    private lateinit var mPresenter: NetsOneContract.Presenter
    private lateinit var postId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nets_one)
        newsDetailPhotoIv = findViewById(R.id.news_detail_photo_iv)
        maskView = findViewById(R.id.mask_view)
        toolbar = findViewById(R.id.toolbar)
        toolbarLayout = findViewById(R.id.toolbar_layout)
        appBar = findViewById(R.id.app_bar)
        newsDetailFromTv = findViewById(R.id.news_detail_from_tv)
        newsDetailBodyTv = findViewById(R.id.news_detail_body_tv)
        progressBar = findViewById(R.id.progress_bar)
        fab = findViewById(R.id.fab)

        setSupportActionBar(toolbar)

        postId = intent.getStringExtra(POST_ID)
        presenter.getNewsOneRequest(postId)
        NetsOnePresenter(NETSRepository.instance, this)
    }

    override fun refreshNewsOne(netsDetail: NetsDetail) {
        val mShareLink = netsDetail.shareLink
        val mNewsTitle = netsDetail.title
        val newsSource = netsDetail.source
        val newsTime = TimeUtils.formatDate(netsDetail.ptime)
        val newsBody = netsDetail.body
        val newsImgSrc = getImgSrcs(netsDetail)

        if (mNewsTitle != null) {
            setToolBarLayout(mNewsTitle)
        }
        newsDetailFromTv.text = String.format(getString(R.string.news_from), newsSource, newsTime)
        setNewsDetailPhotoIv(newsImgSrc)

        Observable.just(netsDetail)
                .delay(500, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<NetsDetail> {
                    override fun onCompleted() {
                        progressBar.visibility = View.GONE
                        fab.visibility = View.VISIBLE
                    }

                    override fun onError(e: Throwable) {
                        progressBar.visibility = View.GONE
                    }

                    override fun onNext(netsDetail: NetsDetail) {
                        setBody(netsDetail, netsDetail.body)
                    }
                })

    }

    private fun setToolBarLayout(newsTitle: String) {
        toolbarLayout.title = newsTitle
        toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white))
        toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white))
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
//            mNewsDetailBodyTv.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效,实测经常卡机崩溃
            val urlImageGetter = URLImageGetter(newsDetailBodyTv, newsBody, imgTotal)
            newsDetailBodyTv.text = Html.fromHtml(newsBody, urlImageGetter, null)
        } else {
            newsDetailBodyTv.text = Html.fromHtml(newsBody)
        }
    }


    private fun isShowBody(newsBody: String?, imgTotal: Int): Boolean {
        return imgTotal >= 2 && newsBody != null
    }

    private fun getImgSrcs(netsDetail: NetsDetail): String {
        val imgSrcs = netsDetail.img
        val imgSrc: String
        if (imgSrcs != null && imgSrcs.isNotEmpty()) {
            imgSrc = imgSrcs[0].src!!
        } else {
            imgSrc = getIntent().getStringExtra(IMG_RES)
        }
        return imgSrc
    }

    companion object {
        private const val POST_ID = "postId"
        private const val IMG_RES = "image"

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
                        .makeScaleUpAnimation(
                                imageView,
                                imageView.width / 2,
                                imageView.height / 2,
                                0,
                                0
                        )
                ActivityCompat.startActivity(mContext, intent, options.toBundle())
            }
        }
    }
}
