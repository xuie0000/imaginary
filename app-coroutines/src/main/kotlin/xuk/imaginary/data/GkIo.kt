package xuk.imaginary.data

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Jie Xu
 * @date 2019/8/9
 */
object GkIo {
  private const val URL = "http://gank.io/api/"

  data class GkBean(
      var isError: Boolean = false,
      /** key type : 'Android', 'iOS', '休息视频', '前端', '瞎推荐', '福利', '拓展资源', 'App'*/
      var results: Map<String, List<BaseBean>>,
      var category: List<String>
  )

  data class CategoryBean(

      /**
       * error : false
       * results : [{"_id":"595c2f23421aa90ca209c3f0","createdAt":"2017-07-05T08:13:23.237Z","desc":"2017-07-5","publishedAt":"2017-07-05T11:15:30.556Z","source":"chrome","type":"福利s","url":"https://ws1.sinaimg.cn/large/610dc034ly1fh8ox6bmjlj20u00u0mz7.jpg","used":true,"who":"daimajia"},{"_id":"595ad246421aa90ca3bb6a91","createdAt":"2017-07-04T07:24:54.820Z","desc":"7-4","publishedAt":"2017-07-04T11:50:36.484Z","source":"chrome","type":"福利s","url":"https://ws1.sinaimg.cn/large/610dc034ly1fh7hwi9lhzj20u011hqa9.jpg","used":true,"who":"daimajia"},{"_id":"5941db7b421aa92c794633cd","createdAt":"2017-06-15T08:57:31.47Z","desc":"6-15","publishedAt":"2017-06-15T13:55:57.947Z","source":"chrome","type":"福利s","url":"https://ws1.sinaimg.cn/large/610dc034ly1fgllsthvu1j20u011in1p.jpg","used":true,"who":"代码家"},{"_id":"593f1ff7421aa92c73b64803","createdAt":"2017-06-13T07:12:55.795Z","desc":"6-13","publishedAt":"2017-06-14T11:34:54.556Z","source":"chrome","type":"福利s","url":"https://ws1.sinaimg.cn/large/610dc034ly1fgj7jho031j20u011itci.jpg","used":true,"who":"daimajia"},{"_id":"593dde44421aa92c73b647f5","createdAt":"2017-06-12T08:20:20.475Z","desc":"6-12","publishedAt":"2017-06-12T11:11:11.25Z","source":"chrome","type":"福利s","url":"https://ws1.sinaimg.cn/large/610dc034ly1fgi3vd6irmj20u011i439.jpg","used":true,"who":"代码家"},{"_id":"5939fcb1421aa92c7be61bd5","createdAt":"2017-06-09T09:41:05.305Z","desc":"6-9","publishedAt":"2017-06-09T12:50:03.131Z","source":"chrome","type":"福利s","url":"https://ws1.sinaimg.cn/large/610dc034ly1fgepc1lpvfj20u011i0wv.jpg","used":true,"who":"dmj"},{"_id":"5938c377421aa92c7be61bcb","createdAt":"2017-06-08T11:24:39.838Z","desc":"6-8","publishedAt":"2017-06-08T11:27:47.21Z","source":"chrome","type":"福利s","url":"https://ws1.sinaimg.cn/large/610dc034ly1fgdmpxi7erj20qy0qyjtr.jpg","used":true,"who":"daimajia"},{"_id":"593774e7421aa92c79463375","createdAt":"2017-06-07T11:37:11.749Z","desc":"6-7","publishedAt":"2017-06-07T11:43:31.396Z","source":"chrome","type":"福利s","url":"https://ws1.sinaimg.cn/large/610dc034ly1fgchgnfn7dj20u00uvgnj.jpg","used":true,"who":"daimajia"},{"_id":"5936223c421aa92c73b647c7","createdAt":"2017-06-06T11:32:12.609Z","desc":"6-6","publishedAt":"2017-06-06T11:36:13.568Z","source":"chrome","type":"福利s","url":"https://ws1.sinaimg.cn/large/610dc034ly1fgbbp94y9zj20u011idkf.jpg","used":true,"who":"dmj"},{"_id":"5934d287421aa92c73b647ba","createdAt":"2017-06-05T11:39:51.13Z","desc":"6-5","publishedAt":"2017-06-05T11:44:53.909Z","source":"chrome","type":"福利s","url":"https://ws1.sinaimg.cn/large/610dc034ly1fga6auw8ycj20u00u00uw.jpg","used":true,"who":"daimajia"}]
       */
      var isError: Boolean = false,
      var results: List<BaseBean>
  )

  data class HistoryBean(
      var error: Boolean = false,
      var results: List<String>
  )

  data class BaseBean(
      /**
       * _id : 59818615421aa90ca209c547
       * createdAt : 2017-08-02T15:58:13.659Z
       * desc : 垃圾回收算法与 JVM 垃圾回收器综述
       * publishedAt : 2017-08-15T13:32:36.998Z
       * source : chrome
       * type : Android
       * url : https://zhuanlan.zhihu.com/p/28258571
       * used : true
       * who : 王下邀月熊
       * images : ["http://img.gk.io/e8529e62-606c-44f3-b10d-963d58a2ef83"]
       */

      var _id: String = "",
      var createdAt: String = "",
      var desc: String = "",
      var publishedAt: String = "",
      var source: String = "",
      var type: String = "",
      var url: String = "",
      var isUsed: Boolean = false,
      var who: String = "",
      var images: List<String>? = null
  )

  interface Service {

    /**
     * 获取最新一天的干货
     */
    @GET("today")
    suspend fun getToday(): Response<GkBean>

    /**
     * 搜索 API
     * search/query/listview/category/Android/count/10/page/1
     * category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * count 最大 50
     */
    @GET("search/query/listview/category/{type}/count/{countSize}/page/{page}")
    suspend fun getListView(
        @Path("type") type: String,
        @Path("countSize") countSize: Int,
        @Path("page") page: Int
    ): Response<CategoryBean>

    /**
     * 获取发过干货日期接口
     * http://gk.io/api/day/history
     */
    @GET("day/history")
    suspend fun getHistory(): Response<HistoryBean>

    /**
     * 分类数据: http://gk.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     */
    @GET("data/{category}/20/{page}")
    suspend fun getCategory(
        @Path("category") category: String,
        @Path("page") page: Int
    ): Response<CategoryBean>

    @GET("data/{category}/20/{page}")
    fun getCategoryTest(
        @Path("category") category: String,
        @Path("page") page: Int
    ): Response<CategoryBean>

    /**
     * 每日数据： http://gk.io/api/day/年/月/日
     */
    @GET("day/{date}")
    suspend fun getDate(
        @Path("date") date: String
    ): Response<GkBean>

    /**
     * 每日数据： http://gk.io/api/day/年/月/日
     */
    @GET("day/{year}/{month}/{day}")
    suspend fun getDate(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("day") day: Int
    ): Response<GkBean>
  }

  private val retrofit = Retrofit.Builder()
      .baseUrl(URL)
//      .addConverterFactory(GsonConverterFactory.create(gson))
      .addConverterFactory(GsonConverterFactory.create(Gson()))
//      .addConverterFactory(MoshiConverterFactory.create())
      .client(
          OkHttpClient.Builder()
//              .readTimeout(8000, TimeUnit.MILLISECONDS)
//              .connectTimeout(8000, TimeUnit.MILLISECONDS)
              .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
              })
              .build()
      )
      .build()

  val service: Service = retrofit.create(GkIo.Service::class.java)

}