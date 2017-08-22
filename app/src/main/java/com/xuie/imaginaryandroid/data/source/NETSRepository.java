package com.xuie.imaginaryandroid.data.source;

import com.xuie.imaginaryandroid.data.NetsDetail;
import com.xuie.imaginaryandroid.data.NetsSummary;
import com.xuie.imaginaryandroid.data.api.NETSApi;
import com.xuie.imaginaryandroid.data.api.ServiceGenerator;
import com.xuie.imaginaryandroid.util.HttpUtils;
import com.xuie.imaginaryandroid.util.TimeUtils;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xuie on 17-8-17.
 */

public class NETSRepository implements NETSSource {
    private static NETSRepository INSTANCE;
    private NETSApi netsApi = ServiceGenerator.createService(NETSApi.class, NETSApi.NETS_API);

    private NETSRepository() {
    }

    public static NETSRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (GankRepository.class) {
                INSTANCE = new NETSRepository();
            }
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<NetsSummary>> getNews(int page) {
        return netsApi.getNewsList(HttpUtils.getCacheControl(), page)
                .subscribeOn(Schedulers.newThread())
                .flatMap(new Func1<Map<String, List<NetsSummary>>, Observable<NetsSummary>>() {
                    @Override
                    public Observable<NetsSummary> call(Map<String, List<NetsSummary>> stringListMap) {
                        return Observable.from(stringListMap.get("T1348647853363"));
                    }
                })
                .map(netsSummary -> {
                    netsSummary.setPtime(TimeUtils.formatDate(netsSummary.getPtime()));
                    return netsSummary;
                })
                .distinct()
                .toSortedList((netsSummary, netsSummary2) -> netsSummary2.getPtime().compareTo(netsSummary.getPtime()));
    }

    @Override
    public Observable<NetsDetail> getNewDetail(String postId) {
        return netsApi.getNewDetail(HttpUtils.getCacheControl(), postId)
                .subscribeOn(Schedulers.newThread())
                .map(stringNetsDetailMap -> stringNetsDetailMap.get(postId));
    }
}
