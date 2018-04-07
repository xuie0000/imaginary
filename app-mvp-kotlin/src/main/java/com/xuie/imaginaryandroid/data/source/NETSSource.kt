package com.xuie.imaginaryandroid.data.source

import com.xuie.imaginaryandroid.data.NetsDetail
import com.xuie.imaginaryandroid.data.NetsSummary
import com.xuie.imaginaryandroid.data.VideoBean
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by xuie on 17-8-17.
 */

interface NETSSource {
    fun getNews(page: Int): Single<MutableList<NetsSummary>>?

    fun getNewDetail(postId: String): Observable<NetsDetail>

    fun getVideoList(type: String, page: Int): Single<MutableList<VideoBean>>?
}
