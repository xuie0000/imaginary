package com.xuie.imaginary.gui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.xuie.imaginary.R
import com.xuie.imaginary.base.BaseActivity
import com.xuie.imaginary.databinding.ActivityMainBinding
import com.xuie.imaginary.gui.gank.meizhi.meiZhiModel
import com.xuie.imaginary.gui.gank.show.gankModel
import com.xuie.imaginary.gui.nets.detail.netsOneKodeinModel
import com.xuie.imaginary.gui.nets.news.newsListModel
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
    import(meiZhiModel)
    import(newsListModel)
    import(videosKodeinModel)
    import(gankModel)
    import(netsOneKodeinModel)
  }

  private lateinit var mBinding: ActivityMainBinding
  private var currentNavController: LiveData<NavController>? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    val navGraphIds = listOf(R.navigation.nets, R.navigation.videos, R.navigation.gank)

    // Setup the bottom navigation view with a list of navigation graphs
    val controller = mBinding.navigation.setupWithNavController(
        navGraphIds = navGraphIds,
        fragmentManager = supportFragmentManager,
        containerId = R.id.nav_host_container,
        intent = intent
    )
    // Whenever the selected controller changes, setup the action bar.
    controller.observe(this, Observer { navController ->
//      setupActionBarWithNavController(navController)
    })
    currentNavController = controller
  }

  override fun onSupportNavigateUp(): Boolean {
    return currentNavController?.value?.navigateUp() ?: false
  }

  /**
   * Overriding popBackStack is necessary in this case if the app is started from the deep link.
   */
  override fun onBackPressed() {
    if (currentNavController?.value?.popBackStack() != true) {
      if (JCVideoPlayer.backPress()) {
        return
      }
      super.onBackPressed()
    }
  }
}
