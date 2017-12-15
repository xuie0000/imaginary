package com.xuie.imaginaryandroid.data.source;

import android.util.Log;

import com.xuie.imaginaryandroid.data.BaseBean;
import com.xuie.imaginaryandroid.data.GankBean;
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
    private static final String TAG = "GankRepository";
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
    public Observable<List<BaseBean>> get福利(int page) {
        return gankApi.get福利(page)
                .subscribeOn(Schedulers.newThread())
                .map(福利s -> {
                    if (福利s.isError())
                        return new ArrayList<BaseBean>();
                    return 福利s.getResults();
                });
    }

    @Override
    public Observable<GankBean> getDay(String date) {
        return gankApi.getDay(date)
                .subscribeOn(Schedulers.newThread())
                .map(gankBean -> {
                    if (gankBean == null)
                        Log.d(TAG, "gank is null");
                    return gankBean;
                });
    }


}