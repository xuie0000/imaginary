package com.xuie.imaginary.gank;

import com.xuie.imaginary.data.BaseBean;
import com.xuie.imaginary.data.source.GankRepository;
import com.xuie.imaginary.di.ActivityScoped;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by xuie on 17-12-15.
 */

@ActivityScoped
public class MeizhiPresenter implements MeizhiContract.Presenter {

    private final GankRepository gankRepository;
    @Nullable
    private MeizhiContract.View meizhiView;
    private int currentPage = 1;

    @Inject
    MeizhiPresenter(GankRepository gankRepository) {
        this.gankRepository = gankRepository;
    }

    @Override
    public void getList(final boolean isRefresh) {
        // get local data
        if (isRefresh) {
            currentPage = 1;
        } else {
            currentPage++;
        }
        gankRepository.get福利(currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<BaseBean>>() {
                    @Override
                    public void call(List<BaseBean> baseBeans) {
                        meizhiView.addList(isRefresh, baseBeans);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
        // get remote data

    }

    @Override
    public void takeView(MeizhiContract.View view) {
        meizhiView = view;
        getList(true);
    }

    @Override
    public void dropView() {

    }
}
