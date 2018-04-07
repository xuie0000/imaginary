package com.xuie.imaginaryandroid.gui.gank.meizhi;

import com.xuie.imaginaryandroid.data.source.GankRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.xuie.imaginaryandroid.util.Utils.checkNotNull;

/**
 * Created by xuie on 17-7-5.
 */

public class MeizhiPresenter implements MeizhiContract.Presenter {
    private GankRepository gankRepository;
    private MeizhiContract.View gankView;
    private int currentPage = 1;

    public MeizhiPresenter(GankRepository gankRepository, MeizhiContract.View gankView) {
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
