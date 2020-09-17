package xuk.imaginary.gui.gank.show;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.AndroidViewModel;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import xuk.imaginary.data.BaseBean;
import xuk.imaginary.data.GankBean;
import xuk.imaginary.data.source.GankRepository;

/**
 * @author xuie
 * @date 2019/1/28
 */
public class GankViewModule extends AndroidViewModel {
    private static final String TAG = "GankViewModule";

    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> dateString = new ObservableField<>();

    public final ObservableList<MultiItemEntity> multiItems = new ObservableArrayList<>();

    private GankRepository gankRepository;
    private Disposable disposable;

    public GankViewModule(@NonNull Application application, GankRepository gankRepository) {
        super(application);
        this.gankRepository = gankRepository;
    }

    public void getGank(String date) {
        clear();
        disposable = gankRepository.getDay(date)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gb -> {
                    List<MultiItemEntity> entities = generateData(gb);
                    multiItems.clear();
                    multiItems.addAll(entities);
                    Log.d(TAG, "entities.size():" + entities.size());
                }, Throwable::printStackTrace);
    }

    private void clear() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }


    private List<MultiItemEntity> generateData(GankBean gb) {
        List<MultiItemEntity> entities = new ArrayList<>();
        for (String s : gb.getCategory()) {
            Level0Item lv0 = new Level0Item();
            lv0.setType(s);
            List<BaseBean> bbs = gb.getResults().getAndroid();
            switch (s) {
                case "Android":
                    bbs = gb.getResults().getAndroid();
                    break;
                case "瞎推荐":
                    bbs = gb.getResults().get瞎推荐();
                    break;
                case "iOS":
                    bbs = gb.getResults().getiOS();
                    break;
                case "休息视频":
                    bbs = gb.getResults().get休息视频();
                    break;
                case "福利":
//                bbs = gb.getResults().get福利()
                    continue;
                case "前端":
                    bbs = gb.getResults().get前端();
                    break;
                case "拓展资源":
                    bbs = gb.getResults().get拓展资源();
                    break;
                default:
                    break;
            }
            for (BaseBean ab : bbs) {
                Level1Item lv1 = new Level1Item();
                lv1.setArticleName(ab.getDesc());
                lv1.setArticleUrl(ab.getUrl());
                lv1.setAuthor(ab.getWho());
                if (ab.getImages() != null && ab.getImages().size() > 0) {
                    lv1.setImageUrl(ab.getImages().get(0));
                }
                lv0.addSubItem(lv1);
            }
            entities.add(lv0);
        }
        return entities;
    }

}
