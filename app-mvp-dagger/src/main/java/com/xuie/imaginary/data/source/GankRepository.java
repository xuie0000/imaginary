package com.xuie.imaginary.data.source;

import com.xuie.imaginary.data.BaseBean;
import com.xuie.imaginary.data.GankBean;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by xuie on 17-12-15.
 */

@Singleton
public class GankRepository implements GankSource {
    private static final String TAG = "GankRepository";

    private final GankSource mRemoteDataSource;

//    private final GankSource mLocalDataSource;

    @Inject
    GankRepository(@Remote GankSource remoteSource/*, @Local GankSource localDataSource*/) {
        this.mRemoteDataSource = remoteSource;
//        this.mLocalDataSource = localDataSource;
    }

    @Override
    public Observable<List<BaseBean>> get福利(int page) {
        return mRemoteDataSource.get福利(page);
    }

    @Override
    public Observable<GankBean> getDay(String date) {
        return mRemoteDataSource.getDay(date);
    }


}
