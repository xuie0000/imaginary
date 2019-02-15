package com.xuie.imaginary.data.source

import com.xuie.imaginary.data.BaseBean
import com.xuie.imaginary.data.GankBean
import com.xuie.imaginary.data.api.GankApi
import com.xuie.imaginary.data.api.ServiceGenerator
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * @author Jie Xu
 * @date 17-7-5
 */
class GankRepository private constructor() : GankSource {
  private val gankApi = ServiceGenerator.createService(GankApi::class.java)

  override fun get福利(page: Int): Observable<List<BaseBean>> {
    return gankApi.get福利(page)
        .subscribeOn(Schedulers.newThread())
        .map { 福利s ->
          if (福利s.isError) {
            return@map ArrayList<BaseBean>()
          }
          福利s.results
        }
  }

  override fun getDay(date: String): Observable<GankBean> {
    return gankApi.getDay(date)
        .subscribeOn(Schedulers.newThread())
        .map { gankBean ->
          gankBean
        }
  }

  companion object {
    private var INSTANCE: GankRepository? = null

    fun getInstance(): GankRepository {
      return INSTANCE ?: GankRepository().apply { INSTANCE = this }
    }
  }


}
