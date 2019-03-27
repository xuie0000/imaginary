package com.xuie.imaginary.gui.gank.show

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
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
const val GANK_MODULE_TAG = "GANK_MODULE"

val gankKodeinModel = Kodein.Module(GANK_MODULE_TAG) {
  bind<GankViewModule>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
    ViewModelProviders.of(context, GankViewModuleFactory.getInstance(instance()))
        .get(GankViewModule::class.java)
  }
}