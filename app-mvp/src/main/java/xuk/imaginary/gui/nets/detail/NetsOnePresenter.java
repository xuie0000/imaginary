package xuk.imaginary.gui.nets.detail;

import io.reactivex.android.schedulers.AndroidSchedulers;
import xuk.imaginary.data.source.NetsRepository;
import xuk.imaginary.util.Utils;

/**
 * @author xuie
 * @date 17-7-5
 */
public class NetsOnePresenter implements NetsOneContract.Presenter {
    private NetsRepository netsRepository;
    private NetsOneContract.View newsOneView;
    private int currentPage = 1;

    public NetsOnePresenter(NetsRepository netsRepository, NetsOneContract.View newsOneView) {
        this.netsRepository = Utils.checkNotNull(netsRepository);
        this.newsOneView = Utils.checkNotNull(newsOneView);
        this.newsOneView.setPresenter(this);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getNewsOneRequest(String postId) {
        netsRepository.getNewDetail(postId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(netsDetail -> newsOneView.refreshNewsOne(netsDetail),
                        Throwable::printStackTrace);
    }
}
