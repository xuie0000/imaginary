package xuk.imaginary.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import xuk.imaginary.R

/**
 * Created by xuie on 17-7-5.
 */
class App : Application() {

  override fun onCreate() {
    super.onCreate()
    context = this
    PreferenceManager.setDefaultValues(this, R.xml.fragment_settings, false)
  }

  companion object {
    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context
  }
}
