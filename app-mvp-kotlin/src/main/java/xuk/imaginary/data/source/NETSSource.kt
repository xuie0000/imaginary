package xuk.imaginary.data.source

import io.reactivex.Observable
import io.reactivex.Single
import xuk.imaginary.data.NetsDetail
import xuk.imaginary.data.NetsSummary
import xuk.imaginary.data.VideoBean

/**
 * Created by xuie on 17-8-17.
 */

interface NETSSource {
  fun getNews(page: Int): Single<MutableList<NetsSummary>>?

  fun getNewDetail(postId: String): Observable<NetsDetail>

  fun getVideoList(type: String, page: Int): Single<MutableList<VideoBean>>?
}
