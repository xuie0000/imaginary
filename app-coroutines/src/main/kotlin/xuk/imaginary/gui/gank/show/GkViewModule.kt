package xuk.imaginary.gui.gank.show

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import xuk.imaginary.data.Action
import xuk.imaginary.data.GkIo
import xuk.imaginary.data.Repository
import xuk.imaginary.data.SelectDate

/**
 * @author Jie Xu
 * @date 2019/1/28
 */
@ObsoleteCoroutinesApi
class GkViewModule : ViewModel() {

  private val mutableGk: MutableLiveData<GkIo.GkBean> = MutableLiveData()
  val gk: LiveData<GkIo.GkBean> = mutableGk

  private val actor = GlobalScope.actor<Action>(Dispatchers.Main, Channel.CONFLATED) {
    for (action in this) when (action) {
      is SelectDate -> {
        mutableGk.value = getDate(action.date)
      }
    }
  }

  fun requestGk(date: String) {
    action(SelectDate(date))
  }

  fun action(action: Action) = actor.offer(action)

  override fun onCleared() {
    actor.close()
  }

  private suspend fun getDate(date: String) = Repository.getDate(date)

}
