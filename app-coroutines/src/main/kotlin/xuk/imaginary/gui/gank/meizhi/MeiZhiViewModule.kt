package xuk.imaginary.gui.gank.meizhi

import android.util.Log
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
import xuk.imaginary.data.SelectPage

/**
 * @author Jie Xu
 * @date 2019/1/28
 */
@ObsoleteCoroutinesApi
class MeiZhiViewModule : ViewModel() {

  private val mutableItems: MutableLiveData<List<GkIo.BaseBean>> = MutableLiveData()

  private var currentPage = 1
  private var isRefresh = false

  val items: LiveData<List<GkIo.BaseBean>> = mutableItems

  private val actor = GlobalScope.actor<Action>(Dispatchers.Main, Channel.CONFLATED) {
    for (action in this) when (action) {
      is SelectPage -> {
//        try {
//          mutableCharts.value = cache.getFreshCharts(action.city) ?: getNewCharts(action.city)
//        } catch (e: Exception) {
//          mutableMessage.value = e.toString()
//        }
        if (action.refresh) {
          mutableItems.value = null
        }
        mutableItems.value = getToday(action.page)
      }
    }
  }

  private suspend fun getToday(page: Int) = Repository.getCategory("福利", page)

  internal fun start() {
    refresh(isRefresh)
    isRefresh = false
  }

  internal fun refresh(isRefresh: Boolean) {
    Log.d(TAG, "refresh: ...")

    if (isRefresh) {
      currentPage = 1
    } else {
      currentPage++
    }
    this.isRefresh = isRefresh

    action(SelectPage(currentPage, this.isRefresh))
  }

  fun action(action: Action) = actor.offer(action)

  override fun onCleared() {
    actor.close()
  }

  companion object {
    private const val TAG = "MeiZhiViewModule"
  }

}
