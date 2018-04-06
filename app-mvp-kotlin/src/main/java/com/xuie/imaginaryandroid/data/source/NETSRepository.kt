package com.xuie.imaginaryandroid.data.source

import com.xuie.imaginaryandroid.data.NetsDetail
import com.xuie.imaginaryandroid.data.NetsSummary
import com.xuie.imaginaryandroid.data.VideoBean
import com.xuie.imaginaryandroid.data.api.NETSApi
import com.xuie.imaginaryandroid.data.api.ServiceGenerator
import com.xuie.imaginaryandroid.util.HttpUtils
import com.xuie.imaginaryandroid.util.TimeUtils
import rx.Observable
import rx.schedulers.Schedulers

/**
 * Created by xuie on 17-8-17.
 */
class NETSRepository private constructor() : NETSSource {
    private val netsApi = ServiceGenerator.createService(NETSApi::class.java, NETSApi.NETS_API)

    override fun getNews(page: Int): Observable<List<NetsSummary>> {
        return netsApi.getNewsList(HttpUtils.cacheControl, page)
                .subscribeOn(Schedulers.newThread())
                .flatMap { stringListMap -> Observable.from(stringListMap["T1348647853363"]) }
                .map({ netsSummary ->
                    netsSummary.ptime = TimeUtils.formatDate(netsSummary.ptime!!)
                    netsSummary
                })
                .distinct()
                .toSortedList({ netsSummary, netsSummary2 -> netsSummary2.ptime!!.compareTo(netsSummary.ptime!!) })
    }

    override fun getNewDetail(postId: String): Observable<NetsDetail> {
        return netsApi.getNewDetail(HttpUtils.cacheControl, postId)
                .subscribeOn(Schedulers.newThread())
                .map({ stringNetsDetailMap -> stringNetsDetailMap.get(postId) })
    }

    override fun getVideoList(type: String, page: Int): Observable<List<VideoBean>> {
        return netsApi.getVideoList(HttpUtils.cacheControl, type, page)
                .subscribeOn(Schedulers.newThread())
                .flatMap { stringListMap -> Observable.from(stringListMap[type]) }
                .distinct()
                .toSortedList({ videoBean, videoBean2 -> videoBean2.ptime!!.compareTo(videoBean.ptime!!) })
    }

    companion object {
        private var INSTANCE: NETSRepository? = null
        private val lock = Any()

        val instance: NETSRepository
            get() {
                if (INSTANCE == null) {
                    synchronized(lock) {
                        INSTANCE = NETSRepository()
                    }
                }
                return INSTANCE!!
            }
    }
}
