package com.xuie.imaginaryandroid.data.api

import com.xuie.imaginaryandroid.data.NetsDetail
import com.xuie.imaginaryandroid.data.NetsSummary
import com.xuie.imaginaryandroid.data.VideoBean
import okhttp3.ResponseBody
import retrofit2.http.*
import rx.Observable

/**
 * Created by xuie on 17-8-17.
 */

interface NETSApi {

    @GET("nc/article/headline/T1348647853363/{start_page}-20.html")
    fun getNewsList(
            @Header("Cache-Control") cacheControl: String,
            @Path("start_page") start_page: Int): Observable<Map<String, List<NetsSummary>>>

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
        // NETS
        const val NETS_API = "http://c.m.163.com/"
    }
}
