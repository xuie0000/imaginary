package xuk.imaginary.gui.gank.meizhi

import android.util.Log
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

  private var mutableCurrentPage: MutableLiveData<Int> = MutableLiveData()
  private var isRefresh = false

  init {
    mutableCurrentPage.value = 1
  }

  var items: LiveData<List<GkIo.BaseBean>?> = liveData(Dispatchers.IO) {
    val result = Repository.getCategory("福利", 1)
    emit(result)
  }

  private suspend fun getToday(page: Int) = Repository.getCategory("福利", page)

  internal fun start() {
    refresh(isRefresh)
  }

  internal fun refresh(isRefresh: Boolean) {
    Log.d("MeiZhiViewModule", "refresh: ...")

    this.isRefresh = isRefresh
    if (isRefresh || mutableCurrentPage.value == null) {
      mutableCurrentPage.value = 1
    } else {
      Log.d("MeiZhiViewModule", "3333")
      mutableCurrentPage.value?.plus(1)
      Log.d("MeiZhiViewModule", "3333 ${mutableCurrentPage.value}")
    }
  }

}
