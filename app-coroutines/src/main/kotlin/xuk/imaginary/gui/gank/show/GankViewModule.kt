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
import xuk.imaginary.data.GankIo
import xuk.imaginary.data.Repository
import xuk.imaginary.data.SelectDate
import java.util.*

/**
 * @author Jie Xu
 * @date 2019/1/28
 */
@ObsoleteCoroutinesApi
class GankViewModule : ViewModel() {

  private val mutableItems: MutableLiveData<List<MulItem>> = MutableLiveData()
  val items: LiveData<List<MulItem>> = mutableItems

  private val actor = GlobalScope.actor<Action>(Dispatchers.Main, Channel.CONFLATED) {
    for (action in this) when (action) {
      is SelectDate -> {
        mutableItems.value = generateData(getDay(action.date))
      }
    }
  }

  fun requestGank(date: String) {
    action(SelectDate(date))
  }

  fun action(action: Action) = actor.offer(action)

  override fun onCleared() {
    actor.close()
  }


  private fun generateData(gb: GankIo.GankBean): List<MulItem> {
    val entities = ArrayList<MulItem>()
    loop@ for (s in gb.category) {
      val lv0 = Level0Item()
      lv0.type = s
      var bbs = gb.results.Android
      when (s) {
        "Android" -> bbs = gb.results.Android
        "瞎推荐" -> bbs = gb.results.瞎推荐
        "iOS" -> bbs = gb.results.iOS
        "休息视频" -> bbs = gb.results.休息视频
        "福利" ->
          // bbs = gb.getResults().get福利()
          continue@loop
        "前端" -> bbs = gb.results.前端
        "拓展资源" -> bbs = gb.results.拓展资源
        else -> {
        }
      }
      Log.d(TAG, "V0:$s")
      for (ab in bbs) {
        val lv1 = Level1Item()
        lv1.articleName = ab.desc
        Log.d(TAG, "  V1: " + ab.desc)
        lv1.articleUrl = ab.url
        lv1.author = ab.who
        if (ab.images != null && ab.images!!.isNotEmpty()) {
          lv1.imageUrl = ab.images!![0]
        }
        lv0.addSubItem(lv1)
      }
      entities.add(lv0)
    }
    return entities
  }

  private suspend fun getDay(date: String) = Repository.getDay(date)

  companion object {
    private const val TAG = "GankViewModule"
  }

}
