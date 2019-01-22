package com.xuie.imaginary.gui.gank.meizhi;

import android.util.Log;

import com.xuie.imaginary.data.source.GankRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.xuie.imaginary.util.Utils.checkNotNull;

/**
 * @author xuie
 * @date 17-7-5
 */
public class MeizhiPresenter implements MeizhiContract.Presenter {
    private static final String TAG = "MeizhiPresenter";
    private GankRepository gankRepository;
    private MeizhiContract.View gankView;
    private int currentPage = 1;
    private CompositeDisposable mSubscriptions = new CompositeDisposable();

    MeizhiPresenter(GankRepository gankRepository, MeizhiContract.View gankView) {
        this.gankRepository = checkNotNull(gankRepository);
        this.gankView = checkNotNull(gankView);
    }

    @Override
    public void subscribe() {
        getList(true);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.dispose();
    }

    @Override
    public void getList(boolean isRefresh) {
        Log.d(TAG, "getList: ..." + isRefresh);
        // get local data
        if (isRefresh) {
            currentPage = 1;
        } else {
            currentPage++;
        }
        Disposable disposable = gankRepository.get福利(currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meiZhis -> gankView.addList(isRefresh, meiZhis), Throwable::printStackTrace);
        // get remote data
        mSubscriptions.add(disposable);
    }
}
