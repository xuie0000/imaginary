package com.xuie.imaginary.gankdate;

import com.xuie.imaginary.data.GankBean;
import com.xuie.imaginary.data.source.GankRepository;
import com.xuie.imaginary.di.ActivityScoped;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * Created by xuie on 17-7-5.
 */

@ActivityScoped
public class GankPresenter implements GankContract.Presenter {
    private GankRepository gankRepository;
    private GankContract.View meizhiView;
    private int currentPage = 1;

    @Inject
    GankPresenter(GankRepository gankRepository) {
        this.gankRepository = gankRepository;
    }

    @Override
    public void getGank(int year, int month, int day) {

    }

    @Override
    public void getGank(String date) {
        gankRepository.getDay(date)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<GankBean>() {
                    @Override
                    public void call(GankBean gankBean) {
                        meizhiView.refresh(gankBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void takeView(GankContract.View view) {
        meizhiView = view;
//        getList(true);
    }

    @Override
    public void dropView() {

    }
}
