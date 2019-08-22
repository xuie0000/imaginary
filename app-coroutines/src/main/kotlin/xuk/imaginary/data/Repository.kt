package xuk.imaginary.data

import retrofit2.await

/**
 * @author Jie Xu
 * @date 2019/8/9
 */
object Repository {

  suspend fun getToday(): GkIo.GkBean {
    return GkIo.service.getToday().await()
  }

  suspend fun getListView(type: String, countSize: Int, page: Int): List<GkIo.BaseBean> {
    return GkIo.service.getListView(type, countSize, page).await().results
  }

  suspend fun getHistory(): List<String> {
    return GkIo.service.getHistory().await().results
  }

  suspend fun getCategory(category: String, page: Int): List<GkIo.BaseBean> {
    return GkIo.service.getCategory(category, page).await().results
  }

  suspend fun getDate(date: String): GkIo.GkBean {
    return GkIo.service.getDate(date).await()
  }

  suspend fun getDate(year: Int, month: Int, day: Int): GkIo.GkBean {
    return GkIo.service.getDate(year, month, day).await()
  }
}