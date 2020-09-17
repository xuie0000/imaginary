package xuk.imaginary.data.source;

import xuk.imaginary.data.BaseBean;
import xuk.imaginary.data.GankBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author xuie
 * @date 17-7-5
 */
public interface GankSource {
    /**
     * 获取当前福利
     *
     * @param page page
     * @return 福利
     */
    Observable<List<BaseBean>> get福利(int page);

    /**
     * 获取GANK一天的信息
     *
     * @param date 日期
     * @return Gank信息
     */
    Observable<GankBean> getDay(String date);
}
