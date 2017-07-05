package com.xuie.imaginaryandroid.data.api;

import com.xuie.imaginaryandroid.data.福利;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by xuie on 17-7-5.
 * http://gank.io/api
 */

public interface GankApi {
    String API = "http://gank.io/api/";

    @GET("data/福利/10/{page}")
    Observable<福利> get福利(@Path("page") int page);
}
