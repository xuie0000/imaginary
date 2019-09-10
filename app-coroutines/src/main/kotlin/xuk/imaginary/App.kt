package xuk.imaginary

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * @author Jie Xu
 * @date 17-7-5
 */
class App : Application() {

  override fun onCreate() {
    super.onCreate()
    context = this
  }

  companion object {
    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context
      private set
  }
}