package com.xuie.imaginary.data.api;

import com.xuie.imaginary.data.GankBean;
import com.xuie.imaginary.data.福利s;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by xuie on 17-12-15.
 * http://gank.io/api
 */

public interface GankApi {
    // GANK
    String GANK_API = "http://gank.io/api/";

    @GET("data/福利/20/{page}")
    Observable<福利s> get福利(@Path("page") int page);

    @GET("day/{date}")
    Observable<GankBean> getDay(@Path("date") String date);
}
