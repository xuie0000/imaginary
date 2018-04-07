package com.xuie.imaginaryandroid.gui.nets.news;

import com.xuie.imaginaryandroid.data.source.NETSRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.xuie.imaginaryandroid.util.Utils.checkNotNull;

/**
 * Created by xuie on 17-7-5.
 */

public class NewsListPresenter implements NewsListContract.Presenter {
    private NETSRepository netsRepository;
    private NewsListContract.View netsView;
    private int currentPage = 0;

    public NewsListPresenter(NETSRepository netsRepository, NewsListContract.View netsView) {
        this.netsRepository = checkNotNull(netsRepository);
        this.netsView = checkNotNull(netsView);
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
