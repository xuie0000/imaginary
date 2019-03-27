package com.xuie.imaginary.gui.gank.meizhi

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
 * @date 2019/3/22
 */
const val MEIZHI_MODULE_TAG = "MEIZHI_MODULE"

val meizhiKodeinModel = Kodein.Module(MEIZHI_MODULE_TAG) {
  bind<MeiZhiViewModule>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
    ViewModelProviders.of((context).activity!!, MeiZhiViewModuleFactory.getInstance(instance()))
        .get(MeiZhiViewModule::class.java)
  }
}

