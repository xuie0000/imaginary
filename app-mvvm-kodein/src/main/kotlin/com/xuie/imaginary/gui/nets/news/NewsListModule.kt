package com.xuie.imaginary.gui.nets.news

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * @author Jie Xu
 * @date 2019/3/27
 */
const val NEWS_LIST_MODULE_TAG = "NEWS_LIST_MODULE"

val newsListModel = Kodein.Module(NEWS_LIST_MODULE_TAG) {
  bind() from singleton { NewsListViewModule(instance()) }

}