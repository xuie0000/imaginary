package com.xuie.imaginaryandroid.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.support.v7.preference.PreferenceManager

import com.xuie.imaginaryandroid.R

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
