package com.xuie.imaginary.gui.nets.video

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xuie.imaginary.data.VideoBean
import com.xuie.imaginary.data.source.NetsRepository
import com.xuie.imaginary.util.SingletonHolderSingleArg
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

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


class VideosViewModuleFactory(private val repo: NetsRepository)
  : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T =
      VideosViewModule(repo) as T

  companion object : SingletonHolderSingleArg<VideosViewModuleFactory, NetsRepository>(::VideosViewModuleFactory)
}
