package com.xuie.imaginary.data.source;

import android.util.Log;

import com.xuie.imaginary.data.BaseBean;
import com.xuie.imaginary.data.GankBean;
import com.xuie.imaginary.data.api.GankApi;
import com.xuie.imaginary.data.api.ServiceGenerator;
import com.xuie.imaginary.data.福利s;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xuie on 17-12-15.
 */

@Singleton
public class GankRepository implements GankSource {
    private static final String TAG = "GankRepository";
    private GankApi gankApi = ServiceGenerator.createService(GankApi.class);

    private final GankSource mRemoteDataSource;

    private final GankSource mLocalDataSource;

    @Inject
    public GankRepository(@Remote GankSource remoteDataSource, @Local GankSource localDataSource) {
        this.mRemoteDataSource = remoteDataSource;
        this.mLocalDataSource = localDataSource;
    }

    @Override
    public Observable<List<BaseBean>> get福利(int page) {
        return gankApi.get福利(page)
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<福利s, List<BaseBean>>() {
                    @Override
                    public List<BaseBean> call(福利s 福利s) {
                        if (福利s.isError())
                            return new ArrayList<BaseBean>();
                        return 福利s.getResults();
                    }
                });
    }

    @Override
    public Observable<GankBean> getDay(String date) {
        return gankApi.getDay(date)
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<GankBean, GankBean>() {
                    @Override
                    public GankBean call(GankBean gankBean) {
                        if (gankBean == null)
                            Log.d(TAG, "gank is null");
                        return gankBean;
                    }
                });
    }


}
