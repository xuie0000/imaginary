package com.xuie.imaginaryandroid.data.source;

import com.xuie.imaginaryandroid.data.福利;
import com.xuie.imaginaryandroid.data.api.GankApi;
import com.xuie.imaginaryandroid.data.api.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by xuie on 17-7-5.
 */

public class GankRepository implements GankSource {
    private static GankRepository INSTANCE;
    private GankApi gankApi = ServiceGenerator.createService(GankApi.class);

    private GankRepository() {
    }

    public static GankRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (GankRepository.class) {
                INSTANCE = new GankRepository();
            }
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<福利>> get福利(int page) {
        return gankApi.get福利(page)
                .subscribeOn(Schedulers.newThread())
                .map(福利 -> {
                    if (福利.isError())
                        return new ArrayList<com.xuie.imaginaryandroid.data.福利>();
                    return 福利.getResults();
                });
    }


}
