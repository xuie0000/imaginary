package com.xuie.imaginary.gui.gank.meizhi

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * @author Jie Xu
 * @date 2019/3/22
 */
const val MEI_ZHI_MODULE_TAG = "MEI_ZHI_MODULE"

val meiZhiModel = Kodein.Module(MEI_ZHI_MODULE_TAG) {

  bind() from singleton { MeiZhiViewModule(instance()) }

}

