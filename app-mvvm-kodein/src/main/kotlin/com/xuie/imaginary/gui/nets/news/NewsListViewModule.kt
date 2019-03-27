package com.xuie.imaginary.gui.nets.news

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xuie.imaginary.data.NetsSummary
import com.xuie.imaginary.data.source.NetsRepository
import com.xuie.imaginary.util.SingletonHolderSingleArg
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * @author Jie Xu
 * @date 2019/1/28
 */
class NewsListViewModule(private val netsRepository: NetsRepository) : ViewModel() {
  val items: ObservableList<NetsSummary> = ObservableArrayList()
  private var currentPage = 0
  private var disposable: Disposable? = null

  fun getList(isRefresh: Boolean) {
    if (isRefresh) {
      currentPage = 0
    } else {
      currentPage += 20
    }

    clear()
    disposable = netsRepository.getNews(currentPage)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ netsSummaries ->
          if (isRefresh) {
            items.clear()
          }
          items.addAll(netsSummaries)
        }, { e -> e.printStackTrace() })

  }

  private fun clear() {
    disposable?.dispose()
    disposable = null
  }

}


class NewsListViewModuleFactory(private val repo: NetsRepository)
  : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T =
      NewsListViewModule(repo) as T

  companion object : SingletonHolderSingleArg<NewsListViewModuleFactory, NetsRepository>(::NewsListViewModuleFactory)
}
