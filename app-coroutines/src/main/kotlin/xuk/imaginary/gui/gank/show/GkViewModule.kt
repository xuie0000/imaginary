package xuk.imaginary.gui.gank.show

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
class GkViewModule : ViewModel() {

  private val mutableDate: MutableLiveData<String> = MutableLiveData()

  val gk: LiveData<GkIo.GkBean> = liveData(Dispatchers.IO) {
    mutableDate.value?.let { getDate(it) }
//    emit(test.value)
  }

  fun requestGk(date: String) {
    mutableDate.value = date
  }

  private suspend fun getDate(date: String) = Repository.getDate(date)

}
