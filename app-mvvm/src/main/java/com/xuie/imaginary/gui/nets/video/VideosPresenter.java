package com.xuie.imaginary.gui.nets.video;

import com.xuie.imaginary.data.source.NetsRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.xuie.imaginary.util.Utils.checkNotNull;

/**
 * @author xuie
 * @date 17-7-5
 */
public class VideosPresenter implements VideosContract.Presenter {
    private NetsRepository netsRepository;
    private VideosContract.View videosView;
    private int currentPage = 0;

    public VideosPresenter(NetsRepository netsRepository, VideosContract.View videosView) {
        this.netsRepository = checkNotNull(netsRepository);
        this.videosView = checkNotNull(videosView);
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
