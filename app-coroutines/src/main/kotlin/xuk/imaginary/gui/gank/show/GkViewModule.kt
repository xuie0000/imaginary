package xuk.imaginary.gui.gank.show

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
import xuk.imaginary.data.SelectDate
import java.util.*

/**
 * @author Jie Xu
 * @date 2019/1/28
 */
@ObsoleteCoroutinesApi
class GkViewModule : ViewModel() {

  private val mutableItems: MutableLiveData<List<MulItem>> = MutableLiveData()
  val items: LiveData<List<MulItem>> = mutableItems

  private val actor = GlobalScope.actor<Action>(Dispatchers.Main, Channel.CONFLATED) {
    for (action in this) when (action) {
      is SelectDate -> {
        mutableItems.value = generateData(getDay(action.date))
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

  private fun generateData(gb: GkIo.GkBean): List<MulItem> {
    val entities = ArrayList<MulItem>()
    for (type in gb.results) {
      val lv0 = Level0Item()
      Log.d(TAG, "V0:${type.key}")
      lv0.type = type.key
      for (bb in type.value) {
        val lv1 = Level1Item()
        lv1.articleName = bb.desc
        Log.d(TAG, "  V1: " + bb.desc)
        lv1.articleUrl = bb.url
        lv1.author = bb.who
        if (bb.images != null && bb.images!!.isNotEmpty()) {
          lv1.imageUrl = bb.images!![0]
        }
        lv0.addSubItem(lv1)
      }
      entities.add(lv0)
    }
    return entities
  }

  private suspend fun getDay(date: String) = Repository.getDate(date)

  companion object {
    private const val TAG = "GkViewModule"
  }

}
