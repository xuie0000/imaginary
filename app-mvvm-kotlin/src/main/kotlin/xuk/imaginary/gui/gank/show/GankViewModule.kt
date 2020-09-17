package xuk.imaginary.gui.gank.show

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.AndroidViewModel
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.xuie.imaginary.data.GankBean
import com.xuie.imaginary.data.source.GankRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * @author Jie Xu
 * @date 2019/1/28
 */
class GankViewModule(application: Application, private val gankRepository: GankRepository)
  : AndroidViewModel(application) {

  val imageUrl = ObservableField<String>()
  val dateString = ObservableField<String>()

  val multiItems: ObservableList<MultiItemEntity> = ObservableArrayList()
  private var disposable: Disposable? = null

  fun getGank(date: String) {
    clear()
    disposable = gankRepository.getDay(date)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ gb ->
          val entities = generateData(gb)
          multiItems.clear()
          multiItems.addAll(entities)
          Log.d(TAG, "entities.size():" + entities.size)
        }, { e -> e.printStackTrace() })
  }

  private fun clear() {
    if (disposable != null) {
      disposable!!.dispose()
      disposable = null
    }
  }


  private fun generateData(gb: GankBean): List<MultiItemEntity> {
    val entities = ArrayList<MultiItemEntity>()
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
          //                bbs = gb.getResults().get福利()
          continue@loop
        "前端" -> bbs = gb.results.前端
        "拓展资源" -> bbs = gb.results.拓展资源
        else -> {
        }
      }
      for (ab in bbs) {
        val lv1 = Level1Item()
        lv1.articleName = ab.desc
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
