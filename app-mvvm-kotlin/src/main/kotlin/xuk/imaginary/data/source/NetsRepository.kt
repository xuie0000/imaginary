package xuk.imaginary.data.source

import com.xuie.imaginary.data.NetsDetail
import com.xuie.imaginary.data.NetsSummary
import com.xuie.imaginary.data.VideoBean
import com.xuie.imaginary.data.api.NETSApi
import com.xuie.imaginary.data.api.ServiceGenerator
import com.xuie.imaginary.util.HttpUtils
import com.xuie.imaginary.util.TimeUtils
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * @author Jie Xu
 * @date 17-8-17
 */
class NetsRepository private constructor() : NetsSource {
  private val netsApi = ServiceGenerator.createService(NETSApi::class.java, NETSApi.NETS_API)

  override fun getNews(page: Int): Single<List<NetsSummary>> {
    return netsApi.getNewsList(HttpUtils.cacheControl, page)
        .subscribeOn(Schedulers.newThread())
        //                .map(new Function<Map<String, List<NetsSummary>>, List<NetsSummary>>() {
        //                    @Override
        //                    public List<NetsSummary> apply(Map<String, List<NetsSummary>> stringListMap){
        //                        return stringListMap.get("T1348647853363");
        //                    }
        //                });
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
        //                .map(new Function<Map<String, List<VideoBean>>, List<VideoBean>>() {
        //                    @Override
        //                    public List<VideoBean> apply(Map<String, List<VideoBean>> stringListMap){
        //                        return stringListMap.get(type);
        //                    }
        //                });
        .flatMap { stringListMap -> Observable.fromIterable(stringListMap[type]!!) }
        .distinct()
        .toSortedList { videoBean, videoBean2 -> videoBean2.ptime!!.compareTo(videoBean.ptime!!) }
  }

  companion object {
    private var INSTANCE: NetsRepository? = null

    fun getInstance(): NetsRepository {
      return INSTANCE ?: NetsRepository().apply { INSTANCE = this }
    }
  }
}
