package com.xuie.imaginary.data.source;

import com.xuie.imaginary.data.BaseBean;
import com.xuie.imaginary.data.GankBean;

import java.util.List;

import rx.Observable;

/**
 * Created by xuie on 17-7-5.
 */

public interface GankSource {
    Observable<List<BaseBean>> get福利(int page);

    Observable<GankBean> getDay(String date);
}
