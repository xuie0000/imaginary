package com.xuie.imaginary.gui.nets.video

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * @author Jie Xu
 * @date 2019/3/27
 */
const val VIDEOS_MODULE_TAG = "VIDEOS_MODULE"

val videosKodeinModel = Kodein.Module(VIDEOS_MODULE_TAG) {
  bind() from singleton { VideosViewModule(instance()) }
}