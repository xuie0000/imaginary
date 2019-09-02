package xuk.imaginary.data

/**
 * @author Jie Xu
 * @date 2019/8/9
 */
object Repository {

  suspend fun getToday(): GkIo.GkBean? {
    val response = GkIo.service.getToday()
    if (response.isSuccessful) {
      return response.body()
    }
    return null
  }

  suspend fun getListView(type: String, countSize: Int, page: Int): List<GkIo.BaseBean> {
    val response = GkIo.service.getListView(type, countSize, page)
    if (response.isSuccessful) {
      return response.body()?.results!!
    }
    return emptyList()
  }

  suspend fun getHistory(): List<String> {
    val response = GkIo.service.getHistory()
    if (response.isSuccessful) {
      return response.body()?.results!!
    }
    return emptyList()
  }

  suspend fun getCategory(category: String, page: Int): List<GkIo.BaseBean> {
    val response = GkIo.service.getCategory(category, page)
    println("getCategory ${response.isSuccessful}")
    if (response.isSuccessful) {
      println("size:${response.body()?.results?.size}")
      return response.body()?.results!!
    }
    return emptyList()
  }

  suspend fun getDate(date: String): GkIo.GkBean? {
    val response = GkIo.service.getDate(date)
    if (response.isSuccessful) {
      return response.body()
    }
    return null
  }

  suspend fun getDate(year: Int, month: Int, day: Int): GkIo.GkBean? {
    val response = GkIo.service.getDate(year, month, day)
    if (response.isSuccessful) {
      return response.body()
    }
    return null
  }
}