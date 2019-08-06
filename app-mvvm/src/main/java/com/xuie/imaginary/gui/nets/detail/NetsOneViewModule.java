package xuk.imaginary.gui.nets.detail;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import xuk.imaginary.data.NetsDetail;
import xuk.imaginary.data.source.NetsRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author xuie
 * @date 2019/1/28
 */
public class NetsOneViewModule extends AndroidViewModel {
    public final ObservableField<NetsDetail> detail = new ObservableField<>();

    private NetsRepository netsRepository;
    private Disposable disposable;

    public NetsOneViewModule(@NonNull Application application, NetsRepository netsRepository) {
        super(application);
        this.netsRepository = netsRepository;
    }

    public void getNewsOneRequest(String postId, String imgRes) {
        clear();
        disposable = netsRepository.getNewDetail(postId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(netsDetail -> {
                    String res = getImgSrcs(netsDetail);
                    if (TextUtils.isEmpty(res)) {
                        res = imgRes;
                    }
                    netsDetail.setImageUrl(res);
                    detail.set(netsDetail);
                }, Throwable::printStackTrace);
    }

    private String getImgSrcs(NetsDetail netsDetail) {
        List<NetsDetail.ImgBean> imgSrcList = netsDetail.getImg();
        String imgSrc = null;
        if (imgSrcList != null && imgSrcList.size() > 0) {
            imgSrc = imgSrcList.get(0).getSrc();
        }
        return imgSrc;
    }

    private void clear() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

}
