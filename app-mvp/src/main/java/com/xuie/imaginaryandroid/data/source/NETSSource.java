package com.xuie.imaginaryandroid.data.source;

import com.xuie.imaginaryandroid.data.NetsDetail;
import com.xuie.imaginaryandroid.data.NetsSummary;
import com.xuie.imaginaryandroid.data.VideoBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by xuie on 17-8-17.
 */

public interface NETSSource {
    Single<List<NetsSummary>> getNews(int page);

    Observable<NetsDetail> getNewDetail(String postId);

    Single<List<VideoBean>> getVideoList(String type, int page);
}
