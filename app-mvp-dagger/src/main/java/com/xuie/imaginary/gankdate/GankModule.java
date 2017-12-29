package com.xuie.imaginary.gankdate;

import com.xuie.imaginary.di.ActivityScoped;
import com.xuie.imaginary.gank.MeizhiPresenter;

import dagger.Binds;
import dagger.Module;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link MeizhiPresenter}.
 */
@Module
public abstract class GankModule {
    @ActivityScoped
    @Binds
    abstract GankContract.Presenter meizhiPresenter(GankPresenter presenter);
}
