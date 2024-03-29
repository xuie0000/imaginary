package xuk.imaginary.app

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import xuk.imaginary.di.KodeinViewModelFactory
import xuk.imaginary.di.baseModule
import xuk.imaginary.di.dbModule
import xuk.imaginary.di.httpModule

/**
 * @author Jie Xu
 * @date 17-7-5
 */
class App : Application(), KodeinAware {
  override val kodein by Kodein.lazy {
    import(androidXModule(this@App))

    bind() from singleton { KodeinViewModelFactory(kodein) }

    import(baseModule)
    import(httpModule)
    import(dbModule)
  }

  override fun onCreate() {
    super.onCreate()
    if (isMainProcess(this)) {
      context = this
    }
  }

  companion object {
    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context

    fun isMainProcess(context: Context): Boolean {
      val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
      val processInfoList = am.runningAppProcesses
      val mainProcessName = context.packageName
      val myPid = android.os.Process.myPid()
      for (info in processInfoList) {
        if (info.pid == myPid && mainProcessName == info.processName) {
          return true
        }
      }
      return false
    }
  }
}
