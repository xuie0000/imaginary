package xuk.imaginary.data.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import xuk.imaginary.data.GankBean;
import xuk.imaginary.data.福利s;

/**
 * The interface Gank <a href="http://gank.io/api">api</a>.
 *
 * @author xuie
 * @date 17 -7-5
 */
public interface GankApi {
    /**
     * The constant GANK_API.
     */
    String GANK_API = "http://gank.io/api/";

    /**
     * Get 福利 observable.
     *
     * @param page the page
     * @return the observable
     */
    @GET("data/福利/20/{page}")
    Observable<福利s> get福利(@Path("page") int page);

    /**
     * Gets day.
     *
     * @param date the date
     * @return the day
     */
    @GET("day/{date}")
    Observable<GankBean> getDay(@Path("date") String date);
}
