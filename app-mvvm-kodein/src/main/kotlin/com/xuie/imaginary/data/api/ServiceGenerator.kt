package com.xuie.imaginary.data.api

import android.text.TextUtils
import android.webkit.WebSettings
import com.google.gson.GsonBuilder
import com.xuie.imaginary.app.App
import com.xuie.imaginary.util.NetWorkUtils
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author Jie Xu
 */
object ServiceGenerator {
  /**
   * 读超时长，单位：毫秒
   */
  private const val READ_TIME_OUT = 7676
  /**
   * 连接时长，单位：毫秒
   */
  private const val CONNECT_TIME_OUT = 7676

  private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create()

  /**
   * 设缓存有效期为两天
   */
  private const val CACHE_STALE_SEC = (60 * 60 * 24 * 2).toLong()

  /**
   * 云端响应头拦截器，用来配置缓存策略
   * Dangerous interceptor that rewrites the server's cache-control header.
   */
  private val mRewriteCacheControlInterceptor = Interceptor { chain ->
    var request = chain.request()
    val cacheControl = request.cacheControl().toString()
    if (!NetWorkUtils.isNetConnected(App.context!!)) {
      request = request.newBuilder()
          .cacheControl(if (TextUtils.isEmpty(cacheControl)) CacheControl.FORCE_NETWORK else CacheControl.FORCE_CACHE)
          .build()
    }
    val originalResponse = chain.proceed(request)
    if (NetWorkUtils.isNetConnected(App.context!!)) {
      //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
      originalResponse.newBuilder()
          .header("Cache-Control", cacheControl)
          .removeHeader("Pragma")
          .build()
    } else {
      originalResponse.newBuilder()
          .header("Cache-Control", "public, only-if-cached, max-stale=$CACHE_STALE_SEC")
          .removeHeader("Pragma")
          .build()
    }
  }

  fun <S> createService(serviceClass: Class<S>): S {
    val builder = Retrofit.Builder()
        .baseUrl(GankApi.GANK_API)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
    return createService(serviceClass, builder, null)
  }

  fun <S> createService(serviceClass: Class<S>, baseUrl: String): S {
    val builder = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
    return createService(serviceClass, builder, null)
  }

  fun <S> createService(serviceClass: Class<S>, builder: Retrofit.Builder, authToken: String?): S {
    //开启Log
    val logInterceptor = HttpLoggingInterceptor()
    logInterceptor.level = HttpLoggingInterceptor.Level.BODY
    //缓存
    val cacheFile = File(App.context!!.cacheDir, "cache")
    //100Mb
    val cache = Cache(cacheFile, (1024 * 1024 * 100).toLong())
    //增加头部信息
    val headerInterceptor = Interceptor { chain ->
      val build = chain.request().newBuilder()
          .addHeader("Content-Type", "application/json")
          // http://www.jianshu.com/p/4132b381f07e
          //移除旧的
          .removeHeader("User-Agent")
          //添加真正的头部
          .addHeader("User-Agent", WebSettings.getDefaultUserAgent(App.context))
          .build()
      chain.proceed(build)
    }

    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder()
        .readTimeout(READ_TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
        .connectTimeout(CONNECT_TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
        .addInterceptor(mRewriteCacheControlInterceptor)
        .addNetworkInterceptor(mRewriteCacheControlInterceptor)
        .addInterceptor(headerInterceptor)
        .addInterceptor(logInterceptor)
        .cache(cache)
        .build()

    if (authToken != null) {
      client.interceptors().add(Interceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Authorization", authToken)
            .method(original.method(), original.body())

        val request = requestBuilder.build()
        chain.proceed(request)
      })
    }

    val retrofit = builder.client(client).build()
    return retrofit.create(serviceClass)
  }

}
