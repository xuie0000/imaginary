package com.xuie.imaginary.gui.nets.video

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

/**
 * @author Jie Xu
 * @date 2019/3/27
 */
const val VIDEOS_MODULE_TAG = "VIDEOS_MODULE"

val videosKodeinModel = Kodein.Module(VIDEOS_MODULE_TAG) {
  bind<VideosViewModule>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
    ViewModelProviders.of((context).activity!!, VideosViewModuleFactory.getInstance(instance()))
        .get(VideosViewModule::class.java)
  }
}