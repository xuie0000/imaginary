package xuk.imaginary.data.source

import android.util.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import xuk.imaginary.data.BaseBean
import xuk.imaginary.data.GankBean
import xuk.imaginary.data.api.GankApi
import xuk.imaginary.data.api.ServiceGenerator

/**
 * Created by xuie on 17-7-5.
 */

class GankRepository private constructor() : GankSource {
  private val gankApi = ServiceGenerator.createService(GankApi::class.java)

  override fun get福利(page: Int): Observable<List<BaseBean>> {
    return gankApi.get福利(page)
        .subscribeOn(Schedulers.newThread())
        .map({ 福利s ->
          if (福利s.isError) {
//                    return ArrayList<BaseBean>()
          }
          福利s.results
        })
  }

  override fun getDay(date: String): Observable<GankBean> {
    return gankApi.getDay(date)
        .subscribeOn(Schedulers.newThread())
        .map({ gankBean ->
          if (gankBean == null)
            Log.d(TAG, "gank is null")
          gankBean
        })
  }

  companion object {
    private const val TAG = "GankRepository"
    private var INSTANCE: GankRepository? = null
    private val lock = Any()

    val instance: GankRepository
      get() {
        synchronized(lock) {
          if (INSTANCE == null) {
            INSTANCE = GankRepository()
          }
        }
        return INSTANCE!!
      }
  }


}
