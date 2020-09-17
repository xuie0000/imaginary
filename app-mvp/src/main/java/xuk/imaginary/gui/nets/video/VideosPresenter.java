package xuk.imaginary.gui.nets.video;

import io.reactivex.android.schedulers.AndroidSchedulers;
import xuk.imaginary.data.source.NetsRepository;
import xuk.imaginary.util.Utils;

/**
 * @author xuie
 * @date 17-7-5
 */
public class VideosPresenter implements VideosContract.Presenter {
    private NetsRepository netsRepository;
    private VideosContract.View videosView;
    private int currentPage = 0;

    public VideosPresenter(NetsRepository netsRepository, VideosContract.View videosView) {
        this.netsRepository = Utils.checkNotNull(netsRepository);
        this.videosView = Utils.checkNotNull(videosView);
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
