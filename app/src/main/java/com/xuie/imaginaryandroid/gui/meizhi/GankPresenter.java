package com.xuie.imaginaryandroid.gui.meizhi;

import com.xuie.imaginaryandroid.data.MeiZhi;
import com.xuie.imaginaryandroid.data.source.GankRepository;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
                .subscribe(new Action1<List<MeiZhi>>() {
                    @Override
                    public void call(List<MeiZhi> meiZhis) {
                        gankView.addList(isRefresh, meiZhis);
                    }
                }, Throwable::printStackTrace);
        // get remote data

    }
}
