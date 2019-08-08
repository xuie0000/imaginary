package xuk.imaginary.gui.gank.show

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import xuk.imaginary.data.GankBean
import xuk.imaginary.data.source.GankRepository
import java.util.*

/**
 * @author Jie Xu
 * @date 2019/1/28
 */
class GankViewModule(application: Application, private val gankRepository: GankRepository)
  : AndroidViewModel(application) {

  val multiItems: MutableLiveData<List<MulItem>> = MutableLiveData()
  private var disposable: Disposable? = null

  fun requestGank(date: String) {
    clear()
    disposable = gankRepository.getDay(date)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ gb ->
          val entities = generateData(gb)
          multiItems.value = entities
        }, { e -> e.printStackTrace() })
  }

  private fun clear() {
    disposable?.dispose()
    disposable = null
  }


  private fun generateData(gb: GankBean): List<MulItem> {
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

  companion object {
    private const val TAG = "GankViewModule"
  }

}
