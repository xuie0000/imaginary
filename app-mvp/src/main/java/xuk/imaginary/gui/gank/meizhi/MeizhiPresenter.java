package xuk.imaginary.gui.gank.meizhi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import xuk.imaginary.data.source.GankRepository;
import xuk.imaginary.util.Utils;

/**
 * @author xuie
 * @date 17-7-5
 */
public class MeizhiPresenter implements MeizhiContract.Presenter {
    private GankRepository gankRepository;
    private MeizhiContract.View gankView;
    private int currentPage = 1;

    public MeizhiPresenter(GankRepository gankRepository, MeizhiContract.View gankView) {
        this.gankRepository = Utils.checkNotNull(gankRepository);
        this.gankView = Utils.checkNotNull(gankView);
        this.gankView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getList(true);
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getList(boolean isRefresh) {
        // get local data
        if (isRefresh) {
            currentPage = 1;
        } else {
            currentPage++;
        }
        gankRepository.get福利(currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meiZhis -> gankView.addList(isRefresh, meiZhis), Throwable::printStackTrace);
        // get remote data

    }
}
