package xuk.imaginary.gui.gank

import android.os.Bundle
import androidx.fragment.app.commit
import xuk.imaginary.R
import xuk.imaginary.gui.BaseActivity
import xuk.imaginary.gui.gank.meizhi.MeiZhiFragment

class MainActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    supportFragmentManager.commit {
      add(R.id.contentFrame, MeiZhiFragment())
    }
  }
}
