package com.xuie.imaginaryandroid.data.source;

import com.xuie.imaginaryandroid.data.BaseBean;
import com.xuie.imaginaryandroid.data.GankBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by xuie on 17-7-5.
 */

public interface GankSource {
    Observable<List<BaseBean>> get福利(int page);

    Observable<GankBean> getDay(String date);
}
