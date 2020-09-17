package xuk.imaginary.data.source;

import java.util.List;

import io.reactivex.Observable;
import xuk.imaginary.data.BaseBean;
import xuk.imaginary.data.GankBean;

/**
 * @author xuie
 * @date 17-7-5
 */
public interface GankSource {
    Observable<List<BaseBean>> get福利(int page);

    Observable<GankBean> getDay(String date);
}
