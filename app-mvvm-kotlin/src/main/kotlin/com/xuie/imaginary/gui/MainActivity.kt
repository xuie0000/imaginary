package com.xuie.imaginary.gui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xuie.imaginary.R
import com.xuie.imaginary.ViewModelFactory
import com.xuie.imaginary.base.BaseActivity
import com.xuie.imaginary.databinding.ActivityMainBinding
import com.xuie.imaginary.gui.gank.meizhi.MeiZhiViewModule
import com.xuie.imaginary.gui.gank.meizhi.MeizhiFragment
import com.xuie.imaginary.gui.nets.news.NewsListFragment
import com.xuie.imaginary.gui.nets.news.NewsListViewModule
import com.xuie.imaginary.gui.nets.video.VideoMainFragment
import com.xuie.imaginary.gui.nets.video.VideosViewModule
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer

/**
 * @author Jie Xu
 */
class MainActivity : BaseActivity() {
  private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    when (item.itemId) {
      R.id.navigation_dashboard -> {
        mBinding!!.viewPager.setCurrentItem(0)
        true
      }
      R.id.navigation_video -> {
        mBinding!!.viewPager.setCurrentItem(1)
        true
      }
      R.id.navigation_gank -> {
        mBinding!!.viewPager.setCurrentItem(2)
        true
      }
      //                case R.id.navigation_settings:
      //                    mBinding.viewPager.setCurrentItem(3)
      //                    return true;
      else -> true
    }
  }

  private var mBinding: ActivityMainBinding? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    mBinding!!.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    mBinding!!.viewPager.setAdapter(MyViewPagerAdapter(supportFragmentManager))
    mBinding!!.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
      internal var isUser = false

      override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

      override fun onPageSelected(position: Int) {
        if (isUser) {
          val itemId = mBinding!!.navigation.getMenu().getItem(position).getItemId()
          mBinding!!.navigation.setSelectedItemId(itemId)
          isUser = false
        }
      }

      override fun onPageScrollStateChanged(state: Int) {
        if (state == ViewPager.SCROLL_STATE_DRAGGING) {
          isUser = true
        }
      }
    })
    mBinding!!.navigation.setSelectedItemId(mBinding!!.navigation.getMenu().getItem(1).getItemId())
  }

  private inner class MyViewPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
      when (position) {
        0 -> return NewsListFragment.instance
        1 -> return VideoMainFragment.instance
        2 -> return MeizhiFragment.instance
        else -> return MeizhiFragment.instance
      }//                case 3:
      //                    return SettingsFragment.getInstance();
    }

    override fun getCount(): Int {
      return 3
    }
  }

  override fun onBackPressed() {
    if (JCVideoPlayer.backPress()) {
      return
    }
    super.onBackPressed()
  }

  companion object {

    fun obtainViewModel(activity: FragmentActivity): MeiZhiViewModule {
      // Use a Factory to inject dependencies into the ViewModel
      val factory = ViewModelFactory.getInstance(activity.application)
      return ViewModelProviders.of(activity, factory).get(MeiZhiViewModule::class.java)
    }

    fun obtainNewsViewModel(activity: FragmentActivity): NewsListViewModule {
      val factory = ViewModelFactory.getInstance(activity.application)
      return ViewModelProviders.of(activity, factory).get(NewsListViewModule::class.java)
    }

    fun obtainVideosViewModel(activity: FragmentActivity): VideosViewModule {
      val factory = ViewModelFactory.getInstance(activity.application)
      return ViewModelProviders.of(activity, factory).get(VideosViewModule::class.java)
    }
  }
}
