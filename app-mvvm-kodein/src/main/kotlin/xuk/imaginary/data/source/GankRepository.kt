package xuk.imaginary.data.source

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import xuk.imaginary.data.BaseBean
import xuk.imaginary.data.GankBean
import xuk.imaginary.data.api.GankApi
import xuk.imaginary.data.api.ServiceGenerator

/**
 * @author Jie Xu
 * @date 17-7-5
 */
class GankRepository : GankSource {
  private val gankApi = ServiceGenerator.createService(GankApi::class.java)

  override fun get福利(page: Int): Observable<List<BaseBean>> {
    return gankApi.get福利(page)
        .subscribeOn(Schedulers.io())
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
  }

}
