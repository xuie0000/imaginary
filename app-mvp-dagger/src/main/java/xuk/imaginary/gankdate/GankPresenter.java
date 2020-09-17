package xuk.imaginary.gankdate;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import xuk.imaginary.data.source.GankRepository;
import xuk.imaginary.di.ActivityScoped;

/**
 * @author xuie
 * @date 17-7-5
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
                .subscribe(gank -> meizhiView.refresh(gank), Throwable::printStackTrace);
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
