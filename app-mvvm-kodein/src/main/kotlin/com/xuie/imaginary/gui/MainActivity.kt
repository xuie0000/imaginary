package com.xuie.imaginary.gui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xuie.imaginary.R
import com.xuie.imaginary.base.BaseActivity
import com.xuie.imaginary.databinding.ActivityMainBinding
import com.xuie.imaginary.gui.gank.meizhi.MeizhiFragment
import com.xuie.imaginary.gui.gank.meizhi.meizhiKodeinModel
import com.xuie.imaginary.gui.gank.show.gankKodeinModel
import com.xuie.imaginary.gui.nets.detail.netsOneKodeinModel
import com.xuie.imaginary.gui.nets.news.NewsListFragment
import com.xuie.imaginary.gui.nets.news.newsListKodeinModel
import com.xuie.imaginary.gui.nets.video.VideoMainFragment
import com.xuie.imaginary.gui.nets.video.videosKodeinModel
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import org.kodein.di.Copy
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.android.retainedSubKodein

/**
 * @author Jie Xu
 */
class MainActivity : BaseActivity(), KodeinAware {
  override val kodein by retainedSubKodein(kodein(), copy = Copy.All) {
    import(meizhiKodeinModel)
    import(newsListKodeinModel)
    import(videosKodeinModel)
    import(gankKodeinModel)
    import(netsOneKodeinModel)
  }

  private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    when (item.itemId) {
      R.id.navigation_dashboard -> {
        mBinding.viewPager.currentItem = 0
        true
      }
      R.id.navigation_video -> {
        mBinding.viewPager.currentItem = 1
        true
      }
      R.id.navigation_gank -> {
        mBinding.viewPager.currentItem = 2
        true
      }
//      R.id.navigation_settings -> {
//        mBinding.viewPager.currentItem = 3
//        true
//      }
      else -> true
    }
  }

  private lateinit var mBinding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    mBinding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    mBinding.viewPager.adapter = MyViewPagerAdapter(supportFragmentManager)
    mBinding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
      var isUser = false

      override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

      override fun onPageSelected(position: Int) {
        if (isUser) {
          val itemId = mBinding.navigation.menu.getItem(position).itemId
          mBinding.navigation.selectedItemId = itemId
          isUser = false
        }
      }

      override fun onPageScrollStateChanged(state: Int) {
        if (state == ViewPager.SCROLL_STATE_DRAGGING) {
          isUser = true
        }
      }
    })
    mBinding.navigation.selectedItemId = mBinding.navigation.menu.getItem(1).itemId
  }

  private inner class MyViewPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
      return when (position) {
        0 -> NewsListFragment.instance
        1 -> VideoMainFragment.instance
        2 -> MeizhiFragment.instance
//        3 -> SettingsFragment.instance
        else -> MeizhiFragment.instance
      }
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
}
