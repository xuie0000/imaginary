package xuk.imaginary.data.source.remote;

import android.util.Log;

import xuk.imaginary.data.BaseBean;
import xuk.imaginary.data.GankBean;
import xuk.imaginary.data.api.GankApi;
import xuk.imaginary.data.api.ServiceGenerator;
import xuk.imaginary.data.source.GankSource;
import xuk.imaginary.data.福利s;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author xuie
 * @date 17-12-28
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
                .map(福利s -> {
                    if (福利s.isError()) {
                        return new ArrayList<>();
                    }
                    return 福利s.getResults();
                });
    }

    @Override
    public Observable<GankBean> getDay(String date) {
        return gankApi.getDay(date)
                .subscribeOn(Schedulers.newThread())
                .map(gankBean -> {
                    if (gankBean == null) {
                        Log.d(TAG, "gank is null");
                    }
                    return gankBean;
                });
    }
}
