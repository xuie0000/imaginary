package xuk.imaginary.data.source

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import xuk.imaginary.data.NetsDetail
import xuk.imaginary.data.NetsSummary
import xuk.imaginary.data.VideoBean
import xuk.imaginary.data.api.NETSApi
import xuk.imaginary.data.api.ServiceGenerator
import xuk.imaginary.util.HttpUtils

/**
 * @author Jie Xu
 * @date 17-8-17
 */
class NetsRepository : NetsSource {
  private val netsApi = ServiceGenerator.createService(NETSApi::class.java, NETSApi.NETS_API)

  override fun getNews(page: Int): Single<List<NetsSummary>> {
    return netsApi.getNewsList(HttpUtils.cacheControl, page)
        .subscribeOn(Schedulers.newThread())
        .flatMap { stringListMap ->
          val ns = stringListMap["T1348647853363"]
          Observable.fromIterable(ns!!)
        }
        .map { netsSummary ->
          netsSummary.ptime = TimeUtils.formatDate(netsSummary.ptime!!)
          netsSummary
        }
        .distinct()
        .toSortedList { netsSummary, netsSummary2 -> netsSummary2.ptime!!.compareTo(netsSummary.ptime!!) }
  }

  override fun getNewDetail(postId: String): Observable<NetsDetail> {
    return netsApi.getNewDetail(HttpUtils.cacheControl, postId)
        .subscribeOn(Schedulers.newThread())
        .map { stringNetsDetailMap -> stringNetsDetailMap[postId] }
  }

  override fun getVideoList(type: String, page: Int): Single<List<VideoBean>> {
    return netsApi.getVideoList(HttpUtils.cacheControl, type, page)
        .subscribeOn(Schedulers.newThread())
        .flatMap { stringListMap ->
          Observable.fromIterable(stringListMap.getValue(type))
        }
        .distinct()
        .toSortedList { videoBean, videoBean2 ->
          videoBean2.ptime!!.compareTo(videoBean.ptime!!)
        }
  }
}
