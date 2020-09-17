package xuk.imaginary.data.api

import io.reactivex.Observable

import retrofit2.http.GET
import retrofit2.http.Path
import xuk.imaginary.data.GankBean
import xuk.imaginary.data.福利s

/**
 * Created by xuie on 17-7-5.
 * http://gank.io/api
 */

interface GankApi {

  @GET("data/福利/20/{page}")
  fun get福利(@Path("page") page: Int): Observable<福利s>

  @GET("day/{date}")
  fun getDay(@Path("date") date: String): Observable<GankBean>

  companion object {
    // GANK
    const val GANK_API = "http://gank.io/api/"
  }
}
