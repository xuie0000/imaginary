package xuk.imaginary.gui

import android.os.Build
import android.view.View

import androidx.appcompat.app.AppCompatActivity

/**
 * @author Jie Xu
 */
abstract class BaseActivity : AppCompatActivity() {

  override fun onResume() {
    super.onResume()
    val decorView = window.decorView
    var option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
    decorView.systemUiVisibility = option
  }
}
