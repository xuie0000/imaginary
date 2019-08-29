package xuk.imaginary.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * @author Jie Xu
 * @date 2019/8/9
 */
object Repository {

  suspend fun getToday(): LiveData<GkIo.GkBean> {
    val liveData = MutableLiveData<GkIo.GkBean>()
    GkIo.service.getToday().enqueue(object : Callback<GkIo.GkBean> {
      override fun onFailure(call: Call<GkIo.GkBean>, t: Throwable) {
        t.printStackTrace()
      }

      override fun onResponse(call: Call<GkIo.GkBean>, response: Response<GkIo.GkBean>) {
        if (response.isSuccessful) {
          liveData.value = response.body()
        }
      }

    })

    return liveData
  }

  suspend fun getListView(type: String, countSize: Int, page: Int): LiveData<List<GkIo.BaseBean>> {
    val liveData = MutableLiveData<List<GkIo.BaseBean>>()
    GkIo.service.getListView(type, countSize, page).enqueue(object : Callback<GkIo.CategoryBean> {
      override fun onFailure(call: Call<GkIo.CategoryBean>, t: Throwable) {
        t.printStackTrace()
      }

      override fun onResponse(call: Call<GkIo.CategoryBean>, response: Response<GkIo.CategoryBean>) {
        if (response.isSuccessful) {
          liveData.value = response.body()?.results
        }
      }
    })

    return liveData
  }

  suspend fun getHistory(): LiveData<List<String>> {
    val liveData = MutableLiveData<List<String>>()
    GkIo.service.getHistory().enqueue(object : Callback<GkIo.HistoryBean> {
      override fun onFailure(call: Call<GkIo.HistoryBean>, t: Throwable) {
        t.printStackTrace()
      }

      override fun onResponse(call: Call<GkIo.HistoryBean>, response: Response<GkIo.HistoryBean>) {
        if (response.isSuccessful) {
          liveData.value = response.body()?.results
        }
      }

    })
    return liveData
  }

  suspend fun getCategory(category: String, page: Int): List<GkIo.BaseBean>? {
    val response = GkIo.service.getCategory(category, page)
    if (response.isSuccessful) {
      return response.body()?.results
    }
    return null
  }

  suspend fun getDate(date: String): LiveData<GkIo.GkBean> {
    val liveData = MutableLiveData<GkIo.GkBean>()
    GkIo.service.getDate(date).enqueue(object : Callback<GkIo.GkBean> {
      override fun onFailure(call: Call<GkIo.GkBean>, t: Throwable) {
        t.printStackTrace()
      }

      override fun onResponse(call: Call<GkIo.GkBean>, response: Response<GkIo.GkBean>) {
        if (response.isSuccessful) {
          liveData.value = response.body()
        }
      }

    })
    return liveData
  }

  suspend fun getDate(year: Int, month: Int, day: Int): LiveData<GkIo.GkBean> {
    val liveData = MutableLiveData<GkIo.GkBean>()
    GkIo.service.getDate(year, month, day).enqueue(object : Callback<GkIo.GkBean> {
      override fun onFailure(call: Call<GkIo.GkBean>, t: Throwable) {
        t.printStackTrace()
      }

      override fun onResponse(call: Call<GkIo.GkBean>, response: Response<GkIo.GkBean>) {
        if (response.isSuccessful) {
          liveData.value = response.body()
        }
      }

    })
    return liveData
  }
}