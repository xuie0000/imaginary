package xuk.imaginary.gui.nets.video

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import xuk.imaginary.data.VideoBean
import xuk.imaginary.data.source.NetsRepository

/**
 * @author Jie Xu
 * @date 2019/1/28
 */
class VideosViewModule(private val netsRepository: NetsRepository) : ViewModel() {
  val items: ObservableList<VideoBean> = ObservableArrayList()
  private var disposable: Disposable? = null
  private var currentPage = 0

  fun getList(type: String, isRefresh: Boolean) {
    if (isRefresh) {
      currentPage = 0
    } else {
      currentPage += 1
    }
    clear()
    disposable = netsRepository.getVideoList(type, currentPage)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ videoBeen ->
          if (isRefresh) {
            items.clear()
          }
          items.addAll(videoBeen)
        }, { e -> e.printStackTrace() })

  }

  private fun clear() {
    if (disposable != null) {
      disposable!!.dispose()
      disposable = null
    }
  }
}

const val VIDEOS_MODULE_TAG = "VIDEOS_MODULE"

val videosModel = Kodein.Module(VIDEOS_MODULE_TAG) {
  bind() from singleton { VideosViewModule(instance()) }
}

