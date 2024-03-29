package xuk.imaginary.gui.nets.detail

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Pair
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import xuk.imaginary.R
import xuk.imaginary.data.NetsDetail
import xuk.imaginary.data.source.NETSRepository
import xuk.imaginary.util.GlideUtils
import xuk.imaginary.util.TimeUtils
import xuk.imaginary.widget.URLImageGetter
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

  private lateinit var postId: String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_nets_one)

    newsDetailPhotoIv = findViewById(R.id.news_detail_photo_iv)
    maskView = findViewById(R.id.mask_view)
    toolbar = findViewById(R.id.toolbar)
    setSupportActionBar(toolbar)
    toolbarLayout = findViewById(R.id.toolbar_layout)
    appBar = findViewById(R.id.app_bar)
    newsDetailFromTv = findViewById(R.id.news_detail_from_tv)
    newsDetailBodyTv = findViewById(R.id.news_detail_body_tv)
    progressBar = findViewById(R.id.progress_bar)
    fab = findViewById(R.id.fab)
    postId = intent.getStringExtra(POST_ID)

    NetsOnePresenter(NETSRepository.instance, this)

    presenter.getNewsOneRequest(postId)
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
          override fun onComplete() {
            progressBar.visibility = View.GONE
            fab.visibility = View.VISIBLE
          }

          override fun onSubscribe(d: Disposable) {
          }

          override fun onError(e: Throwable) {
            progressBar.visibility = View.GONE
          }

          override fun onNext(netsDetail: NetsDetail) {
            setBody(netsDetail, netsDetail.body!!)
          }
        })

  }

  private fun setToolBarLayout(newsTitle: String) {
    toolbarLayout.title = newsTitle
    toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white))
    toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white))
  }

  private fun setNewsDetailPhotoIv(imgSrc: String) {
    GlideUtils.loadImageNetsPhone(this, imgSrc, newsDetailPhotoIv)
  }

  private fun setBody(netsDetail: NetsDetail, newsBody: String) {
    val imgTotal = netsDetail.img!!.size
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
