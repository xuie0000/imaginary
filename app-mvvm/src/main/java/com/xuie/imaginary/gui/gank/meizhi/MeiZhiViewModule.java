package com.xuie.imaginary.gui.gank.meizhi;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.AndroidViewModel;

import com.xuie.imaginary.data.BaseBean;
import com.xuie.imaginary.data.source.GankRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author xuie
 * @date 2019/1/28
 */
public class MeiZhiViewModule extends AndroidViewModel {
    private static final String TAG = "MeiZhiViewModule";

    public final ObservableList<BaseBean> items = new ObservableArrayList<>();
    private int currentPage = 1;
    private Disposable disposable;

    private GankRepository gankRepository;

    public MeiZhiViewModule(@NonNull Application application, final GankRepository gankRepository) {
        super(application);
        this.gankRepository = gankRepository;
    }

    public void start() {
        getList(true);
    }

    public void getList(final boolean isRefresh) {
        Log.d(TAG, "getList: ..." + isRefresh);
        // get local data
        if (isRefresh) {
            currentPage = 1;
        } else {
            currentPage++;
        }

        clear();
        disposable = gankRepository.get福利(currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meiZhis -> {
                    if (isRefresh) {
                        items.clear();
                    }
                    items.addAll(meiZhis);
                }, Throwable::printStackTrace);
        // get remote data
    }

    private void clear() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    public void end() {
        clear();
    }

}
