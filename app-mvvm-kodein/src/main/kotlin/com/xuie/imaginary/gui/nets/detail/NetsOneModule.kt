package com.xuie.imaginary.gui.nets.detail

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
const val NETS_ONE_MODULE_TAG = "NETS_ONE_MODULE"

val netsOneKodeinModel = Kodein.Module(NETS_ONE_MODULE_TAG) {
  bind<NetsOneViewModule>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
    ViewModelProviders.of(context, NetsOneViewModuleFactory.getInstance(instance()))
        .get(NetsOneViewModule::class.java)
  }
}