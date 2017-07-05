package com.xuie.imaginaryandroid.data.source;

import com.xuie.imaginaryandroid.data.MeiZhi;

import rx.Observable;

/**
 * Created by xuie on 17-7-5.
 */

public interface GankSource {
    Observable<MeiZhi> get福利(int page);
}
