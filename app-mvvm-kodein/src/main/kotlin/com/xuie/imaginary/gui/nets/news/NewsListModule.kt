package com.xuie.imaginary.gui.nets.news

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
const val NEWS_LIST_MODULE_TAG = "NEWS_LIST_MODULE"

val newsListKodeinModel = Kodein.Module(NEWS_LIST_MODULE_TAG) {
  bind<NewsListViewModule>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
    ViewModelProviders.of((context).activity!!, NewsListViewModuleFactory.getInstance(instance()))
        .get(NewsListViewModule::class.java)
  }
}