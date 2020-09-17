package xuk.imaginary.gank;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import xuk.imaginary.di.ActivityScoped;
import xuk.imaginary.di.FragmentScoped;

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
