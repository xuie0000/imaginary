package xuk.imaginary.gankdate;

import xuk.imaginary.di.ActivityScoped;
import xuk.imaginary.gank.MeizhiPresenter;

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
