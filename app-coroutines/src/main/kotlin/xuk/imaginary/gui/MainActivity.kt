package xuk.imaginary.gui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import xuk.imaginary.R
import xuk.imaginary.ViewModelFactory
import xuk.imaginary.base.BaseActivity
import xuk.imaginary.gui.gank.meizhi.MeiZhiViewModule
import xuk.imaginary.gui.gank.meizhi.MeizhiFragment

/**
 * @author Jie Xu
 */
class MainActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    supportFragmentManager.beginTransaction()
        .add(R.id.contentFrame, MeizhiFragment.instance)
        .commit()
  }

  companion object {

    fun obtainViewModel(activity: FragmentActivity): MeiZhiViewModule {
      // Use a Factory to inject dependencies into the ViewModel
      val factory = ViewModelFactory.getInstance(activity.application)
      return ViewModelProviders.of(activity, factory).get(MeiZhiViewModule::class.java)
    }
  }
}
