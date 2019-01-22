package com.xuie.imaginary.gui.nets.detail;

import com.xuie.imaginary.data.source.NetsRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.xuie.imaginary.util.Utils.checkNotNull;

/**
 * @author xuie
 * @date 17-7-5
 */
public class NetsOnePresenter implements NetsOneContract.Presenter {
    private NetsRepository netsRepository;
    private NetsOneContract.View newsOneView;
    private int currentPage = 1;

    public NetsOnePresenter(NetsRepository netsRepository, NetsOneContract.View newsOneView) {
        this.netsRepository = checkNotNull(netsRepository);
        this.newsOneView = checkNotNull(newsOneView);
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
