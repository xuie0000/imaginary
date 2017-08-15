package com.xuie.imaginaryandroid.gui.show;

import com.xuie.imaginaryandroid.data.source.GankRepository;

import rx.android.schedulers.AndroidSchedulers;

import static com.xuie.imaginaryandroid.util.Utils.checkNotNull;

/**
 * Created by xuie on 17-7-5.
 */

public class MeizhiPresenter implements MeizhiContract.Presenter {
    private GankRepository gankRepository;
    private MeizhiContract.View meizhiView;
    private int currentPage = 1;

    public MeizhiPresenter(GankRepository gankRepository, MeizhiContract.View meizhiView) {
        this.gankRepository = checkNotNull(gankRepository);
        this.meizhiView = checkNotNull(meizhiView);
        this.meizhiView.setPresenter(this);
    }

    @Override
    public void subscribe() {
//        getList(true);
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getGank(int year, int month, int day) {

    }

    @Override
    public void getGank(String date) {
        gankRepository.getDay(date)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gankBean -> meizhiView.refresh(gankBean), Throwable::printStackTrace);
    }
}
