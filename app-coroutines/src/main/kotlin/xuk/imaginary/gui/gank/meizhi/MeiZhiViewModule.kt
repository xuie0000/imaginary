package xuk.imaginary.gui.gank.meizhi

import androidx.lifecycle.*
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

  var items: LiveData<List<GkIo.BaseBean>?> = MutableLiveData(emptyList())

  init {
    items = Transformations.switchMap(mutableCurrentPage) { page ->
      liveData(Dispatchers.IO) {
        println("111111 $page")
        val result = Repository.getCategory("福利", page)
        println("isRefresh:$isRefresh, page:$page")
        if (isRefresh) {
          emit(result)
        } else {
          emit(if (items.value != null) items.value?.plus(result) else result)
        }
      }
    }
  }

  fun refresh(isRefresh: Boolean) {
    this.isRefresh = isRefresh
    if (isRefresh || mutableCurrentPage.value == null) {
      mutableCurrentPage.value = 1
    } else {
      mutableCurrentPage.value?.plus(1)
    }
    println("refresh: ... refresh:$isRefresh, page:${mutableCurrentPage.value}")
  }

}
