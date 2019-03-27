package com.xuie.imaginary.app

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.preference.PreferenceManager

import com.xuie.imaginary.R

/**
 * @author Jie Xu
 * @date 17-7-5
 */
class App : Application() {

  override fun onCreate() {
    super.onCreate()
    if (isMainProcess(this)) {
      context = this
      PreferenceManager.setDefaultValues(this, R.xml.fragment_settings, false)
    }
  }

  companion object {

    @SuppressLint("StaticFieldLeak")
    var context: Context? = null
      private set

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
