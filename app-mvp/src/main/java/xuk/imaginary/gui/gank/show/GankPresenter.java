package xuk.imaginary.gui.gank.show;

import io.reactivex.android.schedulers.AndroidSchedulers;
import xuk.imaginary.data.source.GankRepository;
import xuk.imaginary.util.Utils;

/**
 * @author xuie
 * @date 17-7-5
 */
public class GankPresenter implements GankContract.Presenter {
    private GankRepository gankRepository;
    private GankContract.View meizhiView;
    private int currentPage = 1;

    public GankPresenter(GankRepository gankRepository, GankContract.View meizhiView) {
        this.gankRepository = Utils.checkNotNull(gankRepository);
        this.meizhiView = Utils.checkNotNull(meizhiView);
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
