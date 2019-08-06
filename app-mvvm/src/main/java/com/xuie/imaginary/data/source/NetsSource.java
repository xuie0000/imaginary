package xuk.imaginary.data.source;

import xuk.imaginary.data.NetsDetail;
import xuk.imaginary.data.NetsSummary;
import xuk.imaginary.data.VideoBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * @author xuie
 * @date 17-8-17
 */
public interface NetsSource {
    Single<List<NetsSummary>> getNews(int page);

    Observable<NetsDetail> getNewDetail(String postId);

    Single<List<VideoBean>> getVideoList(String type, int page);
}
