package com.xuie.imaginary.gui.gank.show

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.xuie.imaginary.R
import com.xuie.imaginary.ViewModelFactory
import com.xuie.imaginary.base.BaseActivity
import com.xuie.imaginary.databinding.ActivityGankBinding

import java.util.ArrayList

/**
 * @author Jie Xu
 */
class GankActivity : BaseActivity() {

  private val adapter = ExpandableItemAdapter(ArrayList())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mBinding = DataBindingUtil.setContentView<ActivityGankBinding>(this, R.layout.activity_gank)

    val date = intent.getStringExtra("date")
    val imageUrl = intent.getStringExtra("image")

    Log.d(TAG, "onCreate: data - $date, image - $imageUrl")

    val gankViewModule = obtainViewModel(this)
    gankViewModule.dateString.set(date)
    gankViewModule.imageUrl.set(imageUrl)
    mBinding.setViewmodule(gankViewModule)

    mBinding.recyclerView.setAdapter(adapter)
    mBinding.recyclerView.setLayoutManager(LinearLayoutManager(this))
    mBinding.recyclerView.setNestedScrollingEnabled(false)
    adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)

    gankViewModule.getGank(date)
  }

  companion object {
    private val TAG = "GankActivity"

    fun obtainViewModel(activity: FragmentActivity): GankViewModule {
      val factory = ViewModelFactory.getInstance(activity.application)
      return ViewModelProviders.of(activity, factory).get(GankViewModule::class.java)
    }
  }
}
