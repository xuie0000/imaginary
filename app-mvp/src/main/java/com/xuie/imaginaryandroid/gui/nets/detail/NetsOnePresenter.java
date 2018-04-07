package com.xuie.imaginaryandroid.gui.nets.detail;

import com.xuie.imaginaryandroid.data.source.NETSRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.xuie.imaginaryandroid.util.Utils.checkNotNull;

/**
 * Created by xuie on 17-7-5.
 */

public class NetsOnePresenter implements NetsOneContract.Presenter {
    private NETSRepository netsRepository;
    private NetsOneContract.View newsOneView;
    private int currentPage = 1;

    public NetsOnePresenter(NETSRepository netsRepository, NetsOneContract.View newsOneView) {
        this.netsRepository = checkNotNull(netsRepository);
        this.newsOneView = checkNotNull(newsOneView);
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
