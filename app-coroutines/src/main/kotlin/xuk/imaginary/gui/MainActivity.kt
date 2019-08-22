package xuk.imaginary.gui

import android.os.Bundle
import xuk.imaginary.R
import xuk.imaginary.base.BaseActivity
import xuk.imaginary.gui.gank.meizhi.MeiZhiFragment

/**
 * @author Jie Xu
 */
class MainActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    supportFragmentManager.beginTransaction()
        .add(R.id.contentFrame, MeiZhiFragment())
        .commit()
  }
}
