package xuk.imaginary.gui.gank.meizhi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import xuk.imaginary.data.GkIo
import xuk.imaginary.data.Repository

/**
 * @author Jie Xu
 * @date 2019/1/28
 */
class MeiZhiViewModule : ViewModel() {

  private var mutableCurrentPage: MutableLiveData<Int> = MutableLiveData(1)
  private var isRefresh = false

  lateinit var items: LiveData<List<GkIo.BaseBean>>

  init {
    mutableCurrentPage.observeForever { page ->
      items = liveData(Dispatchers.IO) {
        val result = Repository.getCategory("福利", page)
//        var originItems: List<GkIo.BaseBean> = items.value?.plus(result) as List<GkIo.BaseBean>
        emit(result)
      }
    }
  }

  internal fun refresh(isRefresh: Boolean) {
    println("refresh: ...")

    this.isRefresh = isRefresh
    if (isRefresh) {
      println("2222")
      mutableCurrentPage.value = 1
    } else {
      println("3333")
      mutableCurrentPage.value?.plus(1)
      println("3333 ${mutableCurrentPage.value}")
    }
  }

}
