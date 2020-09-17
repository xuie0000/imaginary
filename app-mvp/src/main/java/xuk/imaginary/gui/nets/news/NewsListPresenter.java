package xuk.imaginary.gui.nets.news;

import io.reactivex.android.schedulers.AndroidSchedulers;
import xuk.imaginary.data.source.NetsRepository;
import xuk.imaginary.util.Utils;

/**
 * @author xuie
 * @date 17-7-5
 */
public class NewsListPresenter implements NewsListContract.Presenter {
    private NetsRepository netsRepository;
    private NewsListContract.View netsView;
    private int currentPage = 0;

    public NewsListPresenter(NetsRepository netsRepository, NewsListContract.View netsView) {
        this.netsRepository = Utils.checkNotNull(netsRepository);
        this.netsView = Utils.checkNotNull(netsView);
        this.netsView.setPresenter(this);
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
        if (isRefresh) {
            currentPage = 0;
        } else {
            currentPage += 20;
        }
        netsRepository.getNews(currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(netsSummaries -> netsView.addList(isRefresh, netsSummaries)
                        , Throwable::printStackTrace);

    }
}
