package xuk.imaginary.gui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import xuk.imaginary.R
import xuk.imaginary.gui.gank.meizhi.MeizhiFragment
import xuk.imaginary.gui.nets.news.NewsListFragment
import xuk.imaginary.gui.nets.video.VideoMainFragment
import xuk.imaginary.gui.settings.SettingsFragment

class MainActivity : AppCompatActivity() {
  private lateinit var viewPager: ViewPager
  private lateinit var navigation: BottomNavigationView

  private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    when (item.itemId) {
      R.id.navigation_dashboard -> {
        viewPager.currentItem = 0
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_video -> {
        viewPager.currentItem = 1
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_gank -> {
        viewPager.currentItem = 2
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_settings -> {
        viewPager.currentItem = 3
        return@OnNavigationItemSelectedListener true
      }
    }
    false
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    viewPager = findViewById(R.id.view_pager)
    navigation = findViewById(R.id.navigation)

    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    viewPager.adapter = MyViewPagerAdapter(supportFragmentManager)

    viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
      internal var isUser = false

      override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
      }

      override fun onPageSelected(position: Int) {
        if (isUser) {
          val itemId = navigation.menu.getItem(position).itemId
          navigation.selectedItemId = itemId
          isUser = false
        }
      }

      override fun onPageScrollStateChanged(state: Int) {
        if (state == ViewPager.SCROLL_STATE_DRAGGING) {
          isUser = true
        }
      }
    })
    navigation.selectedItemId = navigation.menu.getItem(1).itemId
  }

  private inner class MyViewPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
      return 4
    }

    override fun getItem(position: Int): Fragment {
      return when (position) {
        0 -> NewsListFragment.instance
        1 -> VideoMainFragment.instance
        2 -> MeizhiFragment.instance
        3 -> SettingsFragment.instance
        else -> NewsListFragment.instance
      }
    }
  }

  override fun onResume() {
    super.onResume()
    val decorView = window.decorView
    val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    decorView.systemUiVisibility = option
    window.statusBarColor = Color.TRANSPARENT
//        window.statusBarColor = getColor(R.color.colorPrimary);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
  }

  override fun onBackPressed() {
    if (JCVideoPlayer.backPress()) {
      return
    }
    super.onBackPressed()
  }

  companion object {
    private val TAG = "MainActivity"
  }
}
