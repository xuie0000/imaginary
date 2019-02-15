package com.xuie.imaginary.gui.nets.detail

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders

import com.xuie.imaginary.R
import com.xuie.imaginary.ViewModelFactory
import com.xuie.imaginary.databinding.ActivityNetsOneBinding

/**
 * @author Jie Xu
 */
class NetsOneActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mBinding = DataBindingUtil.setContentView<ActivityNetsOneBinding>(this, R.layout.activity_nets_one)
    val netsOneViewModule = obtainViewModel(this)

    setSupportActionBar(mBinding.toolbar)
    val postId = intent.getStringExtra(POST_ID)
    val imgRes = intent.getStringExtra(IMG_RES)
    netsOneViewModule.getNewsOneRequest(postId, imgRes)
    mBinding.viewmodule = netsOneViewModule
  }

  companion object {
    const val POST_ID = "postId"
    const val IMG_RES = "image"

    @SuppressLint("ObsoleteSdkInt")
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
            .makeScaleUpAnimation(imageView, imageView.width / 2, imageView.height / 2, 0, 0)
        ActivityCompat.startActivity(mContext, intent, options.toBundle())
      }
    }

    fun obtainViewModel(activity: FragmentActivity): NetsOneViewModule {
      val factory = ViewModelFactory.getInstance(activity.application)
      return ViewModelProviders.of(activity, factory).get(NetsOneViewModule::class.java)
    }
  }

}
