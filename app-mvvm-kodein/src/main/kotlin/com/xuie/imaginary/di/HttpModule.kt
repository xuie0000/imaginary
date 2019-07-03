package com.xuie.imaginary.di

import com.xuie.imaginary.data.source.GankRepository
import com.xuie.imaginary.data.source.NetsRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

/**
 * @author Jie Xu
 * @date 2019/3/22
 */

const val HTTP_MODULE_TAT = "HTTP_MODULE"

val httpModule = Kodein.Module(HTTP_MODULE_TAT) {

  bind<GankRepository>() with singleton {
    GankRepository()
  }

  bind<NetsRepository>() with singleton {
    NetsRepository()
  }

}
