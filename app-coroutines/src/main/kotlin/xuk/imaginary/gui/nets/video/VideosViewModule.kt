package xuk.imaginary.gui.nets.video

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.AndroidViewModel
import xuk.imaginary.data.VideoBean
import xuk.imaginary.data.source.NetsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * @author Jie Xu
 * @date 2019/1/28
 */
class VideosViewModule(application: Application, private val netsRepository: NetsRepository) : AndroidViewModel(application) {
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
