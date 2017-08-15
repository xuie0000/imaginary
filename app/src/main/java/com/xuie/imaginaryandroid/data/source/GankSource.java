package com.xuie.imaginaryandroid.data.source;

import com.xuie.imaginaryandroid.data.GankBean;
import com.xuie.imaginaryandroid.data.福利Bean;

import java.util.List;

import rx.Observable;

/**
 * Created by xuie on 17-7-5.
 */

public interface GankSource {
    Observable<List<福利Bean>> get福利(int page);

    Observable<GankBean> getDay(String date);
}
