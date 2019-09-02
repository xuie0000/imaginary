package xuk.imaginary.gui.gank.show

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import xuk.imaginary.data.GkIo
import xuk.imaginary.data.Repository

/**
 * @author Jie Xu
 * @date 2019/1/28
 */
class GkViewModule : ViewModel() {

  private val mutableDate: MutableLiveData<String> = MutableLiveData()
  val gk: LiveData<GkIo.GkBean?> = Transformations.switchMap(mutableDate) { date ->
    liveData(Dispatchers.IO) {
      val gkItems = Repository.getDate(date)
      emit(gkItems)
    }
  }

  fun requestGk(date: String) {
    mutableDate.value = date
  }

}
