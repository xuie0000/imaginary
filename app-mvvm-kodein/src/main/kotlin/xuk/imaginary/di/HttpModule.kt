package xuk.imaginary.di

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import xuk.imaginary.data.source.GankRepository
import xuk.imaginary.data.source.NetsRepository

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
