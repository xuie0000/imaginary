package xuk.imaginary.gui.gank.meizhi;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.AndroidViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import xuk.imaginary.data.BaseBean;
import xuk.imaginary.data.source.GankRepository;

/**
 * @author xuie
 * @date 2019/1/28
 */
public class MeiZhiViewModule extends AndroidViewModel {
    private static final String TAG = "MeiZhiViewModule";

    public final ObservableList<BaseBean> items = new ObservableArrayList<>();
    private int currentPage = 1;
    private Disposable disposable;
    private boolean isRefresh = true;
    private boolean isRequesting = false;

    private GankRepository gankRepository;

    public MeiZhiViewModule(@NonNull Application application, final GankRepository gankRepository) {
        super(application);
        this.gankRepository = gankRepository;
    }

    void start() {
        getList(isRefresh);
        isRefresh = false;
    }

    void getList(final boolean isRefresh) {
        Log.d(TAG, "getList: ..." + isRefresh + ", " + isRequesting);
        // get local data
        if (isRequesting) {
            Log.d(TAG, "getList: requesting");
            return;
        }
        if (isRefresh) {
            currentPage = 1;
        } else {
            currentPage++;
        }

        isRequesting = true;
        clear();
        disposable = gankRepository.get福利(currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meiZhiList -> {
                    isRequesting = false;
                    if (isRefresh) {
                        items.clear();
                    }
                    items.addAll(meiZhiList);
                }, throwable -> {
                    isRequesting = false;
                    throwable.printStackTrace();
                });
        // get remote data
    }

    private void clear() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    public void end() {
        clear();
    }

}
