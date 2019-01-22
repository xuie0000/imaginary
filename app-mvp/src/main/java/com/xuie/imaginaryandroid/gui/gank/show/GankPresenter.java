package com.xuie.imaginary.gui.gank.show;

import com.xuie.imaginary.data.source.GankRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.xuie.imaginary.util.Utils.checkNotNull;

/**
 * @author xuie
 * @date 17-7-5
 */
public class GankPresenter implements GankContract.Presenter {
    private GankRepository gankRepository;
    private GankContract.View meizhiView;
    private int currentPage = 1;

    public GankPresenter(GankRepository gankRepository, GankContract.View meizhiView) {
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
