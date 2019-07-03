package com.xuie.imaginary.gui.gank.show

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * @author Jie Xu
 * @date 2019/3/27
 */
const val GANK_MODULE_TAG = "GANK_MODULE"

val gankModel = Kodein.Module(GANK_MODULE_TAG) {
  bind() from singleton { GankViewModule(instance()) }
}