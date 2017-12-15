package com.xuie.imaginaryandroid.gui.nets.video;

import com.xuie.imaginaryandroid.data.source.NETSRepository;

import rx.android.schedulers.AndroidSchedulers;

import static com.xuie.imaginaryandroid.util.Utils.checkNotNull;

/**
 * Created by xuie on 17-7-5.
 */

public class VideosPresenter implements VideosContract.Presenter {
    private NETSRepository netsRepository;
    private VideosContract.View videosView;
    private int currentPage = 0;

    public VideosPresenter(NETSRepository netsRepository, VideosContract.View videosView) {
        this.netsRepository = checkNotNull(netsRepository);
        this.videosView = checkNotNull(videosView);
        this.videosView.setPresenter(this);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getList(String type, boolean isRefresh) {
        if (isRefresh) {
            currentPage = 0;
        } else {
            currentPage += 1;
        }
        netsRepository.getVideoList(type, currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(videoBeen -> videosView.addList(isRefresh, videoBeen)
                        , Throwable::printStackTrace);

    }
}
