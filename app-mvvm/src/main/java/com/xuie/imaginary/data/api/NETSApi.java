package com.xuie.imaginary.data.api;

import com.xuie.imaginary.data.NetsDetail;
import com.xuie.imaginary.data.NetsSummary;
import com.xuie.imaginary.data.VideoBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * @author xuie
 * @date 17-8-17
 */
public interface NETSApi {
    String NETS_API = "http://c.m.163.com/";

    @GET("nc/article/headline/T1348647853363/{start_page}-20.html")
    Observable<Map<String, List<NetsSummary>>> getNewsList(
            @Header("Cache-Control") String cacheControl,
            @Path("start_page") int startPage);

    @GET("nc/article/{postId}/full.html")
    Observable<Map<String, NetsDetail>> getNewDetail(
            @Header("Cache-Control") String cacheControl,
            @Path("postId") String postId);

    @GET
    Observable<ResponseBody> getNewsBodyHtmlPhoto(
            @Header("Cache-Control") String cacheControl,
            @Url String photoPath);
    //@Url，它允许我们直接传入一个请求的URL。这样以来我们可以将上一个请求的获得的url直接传入进来，baseUrl将被无视
    // baseUrl 需要符合标准，为空、""、或不合法将会报错


    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("nc/video/list/{type}/n/{startPage}-10.html")
    Observable<Map<String, List<VideoBean>>> getVideoList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type,
            @Path("startPage") int startPage);
}
