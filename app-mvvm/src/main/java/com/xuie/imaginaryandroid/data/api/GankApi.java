package com.xuie.imaginaryandroid.data.api;

import com.xuie.imaginaryandroid.data.GankBean;
import com.xuie.imaginaryandroid.data.福利s;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author xuie
 * @date 17-7-5
 * http://gank.io/api
 */
public interface GankApi {
    String GANK_API = "http://gank.io/api/";

    @GET("data/福利/20/{page}")
    Observable<福利s> get福利(@Path("page") int page);

    @GET("day/{date}")
    Observable<GankBean> getDay(@Path("date") String date);
}
