package xuk.imaginary.data.source;

import xuk.imaginary.data.NetsDetail;
import xuk.imaginary.data.NetsSummary;
import xuk.imaginary.data.VideoBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * The interface Nets source.
 *
 * @author xuie
 * @date 17 -8-17
 */
public interface NetsSource {
    /**
     * Gets news.
     *
     * @param page the page
     * @return the news
     */
    Single<List<NetsSummary>> getNews(int page);

    /**
     * Gets new detail.
     *
     * @param postId the post id
     * @return the new detail
     */
    Observable<NetsDetail> getNewDetail(String postId);

    /**
     * Gets video list.
     *
     * @param type the type
     * @param page the page
     * @return the video list
     */
    Single<List<VideoBean>> getVideoList(String type, int page);
}
