package xuk.imaginary.data.source

import com.xuie.imaginary.data.NetsDetail
import com.xuie.imaginary.data.NetsSummary
import com.xuie.imaginary.data.VideoBean

import io.reactivex.Observable
import io.reactivex.Single

/**
 * @author Jie Xu
 * @date 17-8-17
 */
interface NetsSource {
  fun getNews(page: Int): Single<List<NetsSummary>>

  fun getNewDetail(postId: String): Observable<NetsDetail>

  fun getVideoList(type: String, page: Int): Single<List<VideoBean>>
}
