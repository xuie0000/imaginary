package com.xuie.imaginaryandroid.data.source;

import com.xuie.imaginaryandroid.data.MeiZhi;
import com.xuie.imaginaryandroid.data.api.GankApi;
import com.xuie.imaginaryandroid.data.api.ServiceGenerator;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by xuie on 17-7-5.
 */

public class GankRepository implements GankSource {
    private GankApi gankApi = ServiceGenerator.createService(GankApi.class);

    public GankRepository() {
    }

    @Override
    public Observable<MeiZhi> get福利(int page) {
        gankApi.get福利(page)
                .subscribeOn(Schedulers.newThread())
                .map(福利 -> {
                    if (福利.isError())
                        return null;
                    return 福利.getResults();
                });
        return null;
    }


}
