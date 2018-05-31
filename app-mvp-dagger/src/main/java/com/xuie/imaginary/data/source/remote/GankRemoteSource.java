package com.xuie.imaginary.data.source.remote;

import android.util.Log;

import com.xuie.imaginary.data.BaseBean;
import com.xuie.imaginary.data.GankBean;
import com.xuie.imaginary.data.api.GankApi;
import com.xuie.imaginary.data.api.ServiceGenerator;
import com.xuie.imaginary.data.source.GankSource;
import com.xuie.imaginary.data.福利s;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xuie on 17-12-28.
 */

@Singleton
public class GankRemoteSource implements GankSource {
    private static final String TAG = "GankRemoteSource";
    private GankApi gankApi = ServiceGenerator.createService(GankApi.class);

    @Inject
    public GankRemoteSource() {
    }

    @Override
    public Observable<List<BaseBean>> get福利(int page) {
        return gankApi.get福利(page)
                .subscribeOn(Schedulers.newThread())
                .map(new Function<福利s, List<BaseBean>>() {
                    @Override
                    public List<BaseBean> apply(福利s 福利s) {
                        if (福利s.isError()) {
                            return new ArrayList<>();
                        }
                        return 福利s.getResults();
                    }
                });
    }

    @Override
    public Observable<GankBean> getDay(String date) {
        return gankApi.getDay(date)
                .subscribeOn(Schedulers.newThread())
                .map(new Function<GankBean, GankBean>() {
                    @Override
                    public GankBean apply(GankBean gankBean) {
                        if (gankBean == null) {
                            Log.d(TAG, "gank is null");
                        }
                        return gankBean;
                    }
                });
    }
}
