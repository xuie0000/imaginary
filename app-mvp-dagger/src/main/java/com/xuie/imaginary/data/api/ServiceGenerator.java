package com.xuie.imaginary.data.api;

import android.text.TextUtils;
import android.webkit.WebSettings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xuie.imaginary.ToDoApplication;
import com.xuie.imaginary.util.NetWorkUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author xuie
 */
public class ServiceGenerator {
    /**
     * 读超时长，单位：毫秒
     */
    private static final int READ_TIME_OUT = 7676;
    /**
     * 连接时长，单位：毫秒
     */
    private static final int CONNECT_TIME_OUT = 7676;

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(GankApi.GANK_API)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson));
        return createService(serviceClass, builder, null);
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));
        return createService(serviceClass, builder, null);
    }

    public static <S> S createService(Class<S> serviceClass, Retrofit.Builder builder, final String authToken) {
        //开启Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //缓存 100Mb
        File cacheFile = new File(ToDoApplication.getContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
        //增加头部信息
        Interceptor headerInterceptor = chain -> {
            Request build = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    // 移除旧的 http://www.jianshu.com/p/4132b381f07e
                    .removeHeader("User-Agent")
                    //添加真正的头部
                    .addHeader("User-Agent", WebSettings.getDefaultUserAgent(ToDoApplication.getContext()))
                    .build();
            return chain.proceed(build);
        };

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(headerInterceptor)
                .addInterceptor(logInterceptor)
                .cache(cache)
                .build();

        if (authToken != null) {
            client.interceptors().add(chain -> {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", authToken)
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            });
        }

        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static Interceptor mRewriteCacheControlInterceptor = chain -> {
        Request request = chain.request();
        String cacheControl = request.cacheControl().toString();
        if (!NetWorkUtils.isNetConnected(ToDoApplication.getContext())) {
            request = request.newBuilder()
                    .cacheControl(TextUtils.isEmpty(cacheControl) ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if (NetWorkUtils.isNetConnected(ToDoApplication.getContext())) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置

            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                    .removeHeader("Pragma")
                    .build();
        }
    };

}
