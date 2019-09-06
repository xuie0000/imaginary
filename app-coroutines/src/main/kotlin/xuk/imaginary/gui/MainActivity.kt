package xuk.imaginary.gui

import android.os.Bundle
import androidx.fragment.app.commit
import xuk.imaginary.R
import xuk.imaginary.gui.gank.meizhi.MeiZhiFragment

/**
 * @author Jie Xu
 */
class MainActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    supportFragmentManager.commit {
      addToBackStack("contentFrame")
      add(R.id.contentFrame, MeiZhiFragment())
    }
  }
}
