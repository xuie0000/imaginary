package com.xuie.imaginary.gui.nets.news;

import com.xuie.imaginary.data.source.NetsRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.xuie.imaginary.util.Utils.checkNotNull;

/**
 * @author xuie
 * @date 17-7-5
 */
public class NewsListPresenter implements NewsListContract.Presenter {
    private NetsRepository netsRepository;
    private NewsListContract.View netsView;
    private int currentPage = 0;

    public NewsListPresenter(NetsRepository netsRepository, NewsListContract.View netsView) {
        this.netsRepository = checkNotNull(netsRepository);
        this.netsView = checkNotNull(netsView);
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
