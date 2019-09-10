package xuk.imaginary.data.source;

import xuk.imaginary.data.NetsDetail;
import xuk.imaginary.data.NetsSummary;
import xuk.imaginary.data.VideoBean;
import xuk.imaginary.data.api.NETSApi;
import xuk.imaginary.data.api.ServiceGenerator;
import xuk.imaginary.common.HttpUtils;
import xuk.imaginary.common.TimeUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author xuie
 * @date 17-8-17
 */
public class NetsRepository implements NetsSource {
    private static NetsRepository INSTANCE;
    private NETSApi netsApi = ServiceGenerator.createService(NETSApi.class, NETSApi.NETS_API);

    private NetsRepository() {
    }

    public static NetsRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (GankRepository.class) {
                INSTANCE = new NetsRepository();
            }
        }
        return INSTANCE;
    }

    @Override
    public Single<List<NetsSummary>> getNews(int page) {
        return netsApi.getNewsList(HttpUtils.getCacheControl(), page)
                .subscribeOn(Schedulers.newThread())
//                .map(new Function<Map<String, List<NetsSummary>>, List<NetsSummary>>() {
//                    @Override
//                    public List<NetsSummary> apply(Map<String, List<NetsSummary>> stringListMap){
//                        return stringListMap.get("T1348647853363");
//                    }
//                });
                .flatMap(new Function<Map<String, List<NetsSummary>>, ObservableSource<NetsSummary>>() {
                    @Override
                    public ObservableSource<NetsSummary> apply(Map<String, List<NetsSummary>> stringListMap) {
                        List<NetsSummary> ns = stringListMap.get("T1348647853363");
                        return Observable.fromIterable(ns);
                    }
                })
                .map(new Function<NetsSummary, NetsSummary>() {
                    @Override
                    public NetsSummary apply(NetsSummary netsSummary) {
                        netsSummary.setPtime(TimeUtils.formatDate(netsSummary.getPtime()));
                        return netsSummary;
                    }
                })
                .distinct()
                .toSortedList((netsSummary, netsSummary2) ->
                        netsSummary2.getPtime().compareTo(netsSummary.getPtime()));
    }

    @Override
    public Observable<NetsDetail> getNewDetail(String postId) {
        return netsApi.getNewDetail(HttpUtils.getCacheControl(), postId)
                .subscribeOn(Schedulers.newThread())
                .map(stringNetsDetailMap -> stringNetsDetailMap.get(postId));
    }

    @Override
    public Single<List<VideoBean>> getVideoList(String type, int page) {
        return netsApi.getVideoList(HttpUtils.getCacheControl(), type, page)
                .subscribeOn(Schedulers.newThread())
//                .map(new Function<Map<String, List<VideoBean>>, List<VideoBean>>() {
//                    @Override
//                    public List<VideoBean> apply(Map<String, List<VideoBean>> stringListMap){
//                        return stringListMap.get(type);
//                    }
//                });
                .flatMap(new Function<Map<String, List<VideoBean>>, Observable<VideoBean>>() {
                    @Override
                    public Observable<VideoBean> apply(Map<String, List<VideoBean>> stringListMap) {
                        return Observable.fromIterable(stringListMap.get(type));
                    }
                })
                .distinct()
                .toSortedList((videoBean, videoBean2) -> videoBean2.getPtime().compareTo(videoBean.getPtime()));
    }
}
