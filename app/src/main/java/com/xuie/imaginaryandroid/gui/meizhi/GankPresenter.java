package com.xuie.imaginaryandroid.gui.meizhi;

import com.xuie.imaginaryandroid.data.source.GankRepository;

import rx.android.schedulers.AndroidSchedulers;

import static com.xuie.imaginaryandroid.util.Utils.checkNotNull;

/**
 * Created by xuie on 17-7-5.
 */

public class GankPresenter implements GankContract.Presenter {
    private GankRepository gankRepository;
    private GankContract.View gankView;
    private int currentPage = 1;

    public GankPresenter(GankRepository gankRepository, GankContract.View gankView) {
        this.gankRepository = checkNotNull(gankRepository);
        this.gankView = checkNotNull(gankView);
        this.gankView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getList(true);
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getList(boolean isRefresh) {
        // get local data
        if (isRefresh) {
            currentPage = 1;
        } else {
            currentPage++;
        }
        gankRepository.get福利(currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meiZhis -> gankView.addList(isRefresh, meiZhis), Throwable::printStackTrace);
        // get remote data

    }
}
