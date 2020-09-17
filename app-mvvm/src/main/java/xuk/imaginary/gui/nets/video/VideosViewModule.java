package xuk.imaginary.gui.nets.video;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.AndroidViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import xuk.imaginary.data.VideoBean;
import xuk.imaginary.data.source.NetsRepository;

/**
 * @author xuie
 * @date 2019/1/28
 */
public class VideosViewModule extends AndroidViewModel {
    public final ObservableList<VideoBean> items = new ObservableArrayList<>();

    private NetsRepository netsRepository;
    private Disposable disposable;
    private int currentPage = 0;

    public VideosViewModule(@NonNull Application application, NetsRepository netsRepository) {
        super(application);
        this.netsRepository = netsRepository;
    }

    public void getList(String type, boolean isRefresh) {
        if (isRefresh) {
            currentPage = 0;
        } else {
            currentPage += 1;
        }
        clear();
        disposable = netsRepository.getVideoList(type, currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(videoBeen -> {
                    if (isRefresh) {
                        items.clear();
                    }
                    items.addAll(videoBeen);
                }, Throwable::printStackTrace);

    }

    private void clear() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

}
