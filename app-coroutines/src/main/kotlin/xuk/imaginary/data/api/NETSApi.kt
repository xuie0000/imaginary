package xuk.imaginary.data.api

import xuk.imaginary.data.NetsDetail
import xuk.imaginary.data.NetsSummary
import xuk.imaginary.data.VideoBean

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Url

/**
 * @author Jie Xu
 * @date 17-8-17
 */
interface NETSApi {

  @GET("nc/article/headline/T1348647853363/{start_page}-20.html")
  fun getNewsList(
      @Header("Cache-Control") cacheControl: String,
      @Path("start_page") startPage: Int): Observable<Map<String, List<NetsSummary>>>

  @GET("nc/article/{postId}/full.html")
  fun getNewDetail(
      @Header("Cache-Control") cacheControl: String,
      @Path("postId") postId: String): Observable<Map<String, NetsDetail>>

  @GET
  fun getNewsBodyHtmlPhoto(
      @Header("Cache-Control") cacheControl: String,
      @Url photoPath: String): Observable<ResponseBody>
  //@Url，它允许我们直接传入一个请求的URL。这样以来我们可以将上一个请求的获得的url直接传入进来，baseUrl将被无视
  // baseUrl 需要符合标准，为空、""、或不合法将会报错


  @Headers("Content-Type: application/json;charset=utf-8")
  @GET("nc/video/list/{type}/n/{startPage}-10.html")
  fun getVideoList(
      @Header("Cache-Control") cacheControl: String,
      @Path("type") type: String,
      @Path("startPage") startPage: Int): Observable<Map<String, List<VideoBean>>>

  companion object {
    const val NETS_API = "http://c.m.163.com/"
  }
}
