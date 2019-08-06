package xuk.imaginary.gank;

import android.support.annotation.Nullable;

import xuk.imaginary.data.source.GankRepository;
import xuk.imaginary.di.ActivityScoped;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author xuie
 * @date 17-12-15
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
                .subscribe(baseBeans -> meizhiView.addList(isRefresh, baseBeans), Throwable::printStackTrace);
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
