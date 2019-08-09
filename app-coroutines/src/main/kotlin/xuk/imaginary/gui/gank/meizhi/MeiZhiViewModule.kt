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
import xuk.imaginary.data.GankIo
import xuk.imaginary.data.Repository
import xuk.imaginary.data.SelectPage

/**
 * @author Jie Xu
 * @date 2019/1/28
 */
@ObsoleteCoroutinesApi
class MeiZhiViewModule : ViewModel() {

  private val mutableItems: MutableLiveData<List<GankIo.BaseBean>> = MutableLiveData()

  var currentPage = 1
  private var isRefresh: MutableLiveData<Boolean> = MutableLiveData(false)

  val items: LiveData<List<GankIo.BaseBean>> = mutableItems

  private val actor = GlobalScope.actor<Action>(Dispatchers.Main, Channel.CONFLATED) {
    for (action in this) when (action) {
      is SelectPage -> {
        currentPage = action.page
        isRefresh.value = action.refresh
//        try {
//          mutableCharts.value = cache.getFreshCharts(action.city) ?: getNewCharts(action.city)
//        } catch (e: Exception) {
//          mutableMessage.value = e.toString()
//        }
        mutableItems.value = getToday()
      }
    }
  }

  private suspend fun getToday() = Repository.get福利(currentPage).apply {
    if (isRefresh.value!!) {
      mutableItems.value = null
    }
  }

  internal fun start() {
    refresh(isRefresh.value ?: false)
    isRefresh.value = false
  }

  internal fun refresh(isRefresh: Boolean) {
    Log.d(TAG, "refresh: ...")

    if (isRefresh) {
      currentPage = 1
    } else {
      currentPage++
    }

    action(SelectPage(currentPage, isRefresh))
  }

  fun action(action: Action) = actor.offer(action)

  override fun onCleared() {
    actor.close()
  }

  companion object {
    private const val TAG = "MeiZhiViewModule"
  }

}
