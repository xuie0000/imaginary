package com.xuie.imaginary.gank;

import com.xuie.imaginary.data.source.GankRepository;
import com.xuie.imaginary.di.ActivityScoped;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * Created by xuie on 17-12-15.
 */

@ActivityScoped
public class MeizhiPresenter implements MeizhiContract.Presenter {

    private final GankRepository gankRepository;
    @Nullable
    private MeizhiContract.View meizhiView;

    @Inject
    public MeizhiPresenter(GankRepository gankRepository) {
        this.gankRepository = gankRepository;
    }

    @Override
    public void getList(boolean isRefresh) {

    }

    @Override
    public void takeView(MeizhiContract.View view) {

    }

    @Override
    public void dropView() {

    }
}
