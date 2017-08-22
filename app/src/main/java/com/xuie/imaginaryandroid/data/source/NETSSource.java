package com.xuie.imaginaryandroid.data.source;

import com.xuie.imaginaryandroid.data.NetsDetail;
import com.xuie.imaginaryandroid.data.NetsSummary;

import java.util.List;

import rx.Observable;

/**
 * Created by xuie on 17-8-17.
 */

public interface NETSSource {
    Observable<List<NetsSummary>> getNews(int page);

    Observable<NetsDetail> getNewDetail(String postId);
}
