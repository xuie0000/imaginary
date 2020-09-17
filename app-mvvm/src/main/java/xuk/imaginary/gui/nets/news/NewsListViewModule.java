package xuk.imaginary.gui.nets.news;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.AndroidViewModel;

import xuk.imaginary.data.NetsSummary;
import xuk.imaginary.data.source.NetsRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author xuie
 * @date 2019/1/28
 */
public class NewsListViewModule extends AndroidViewModel {
    public final ObservableList<NetsSummary> items = new ObservableArrayList<>();

    private NetsRepository netsRepository;
    private int currentPage = 0;
    private Disposable disposable;

    public NewsListViewModule(@NonNull Application application, NetsRepository netsRepository) {
        super(application);
        this.netsRepository = netsRepository;
    }

    public void getList(boolean isRefresh) {
        if (isRefresh) {
            currentPage = 0;
        } else {
            currentPage += 20;
        }

        clear();
        disposable = netsRepository.getNews(currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(netsSummaries -> {
                    if (isRefresh) {
                        items.clear();
                    }
                    items.addAll(netsSummaries);
                }, Throwable::printStackTrace);

    }

    private void clear() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

}
