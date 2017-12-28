package com.xuie.imaginary.gank;

import com.xuie.imaginary.di.ActivityScoped;
import com.xuie.imaginary.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link MeizhiPresenter}.
 */
@Module
public abstract class MeizhiModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MeizhiFragment meizhiFragment();

    @ActivityScoped
    @Binds
    abstract MeizhiContract.Presenter meizhiPresenter(MeizhiPresenter presenter);
}
