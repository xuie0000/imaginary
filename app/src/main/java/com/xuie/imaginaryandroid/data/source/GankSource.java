package com.xuie.imaginaryandroid.data.source;

import com.xuie.imaginaryandroid.data.福利;

import java.util.List;

import rx.Observable;

/**
 * Created by xuie on 17-7-5.
 */

public interface GankSource {
    Observable<List<福利>> get福利(int page);
}
