package xuk.imaginary.gui.nets.news

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import xuk.imaginary.data.NetsSummary
import xuk.imaginary.data.source.NetsRepository

/**
 * @author Jie Xu
 * @date 2019/1/28
 */
class NewsListViewModule(private val netsRepository: NetsRepository) : ViewModel() {
  val items: ObservableList<NetsSummary> = ObservableArrayList()
  private val currentPage: MutableLiveData<Int> = MutableLiveData()
  private var disposable: Disposable? = null
  private var isRefresh = true

  init {
    currentPage.observeForever { value ->
      clear()
      disposable = netsRepository.getNews(value)
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe({ netsSummaries ->
            if (isRefresh) {
              items.clear()
            }
            items.addAll(netsSummaries)
          }, { e -> e.printStackTrace() })
    }
  }

  fun getList(isRefresh: Boolean) {
    this.isRefresh = isRefresh
    if (isRefresh) {
      currentPage.value = 0
    } else {
      val v = currentPage.value
      if (v != null) {
        currentPage.postValue(v + 20)
      } else {
        currentPage.value = 0
      }
    }
  }

  private fun clear() {
    disposable?.dispose()
    disposable = null
  }
}

const val NEWS_LIST_MODULE_TAG = "NEWS_LIST_MODULE"

val newsListModel = Kodein.Module(NEWS_LIST_MODULE_TAG) {
  bind() from singleton { NewsListViewModule(instance()) }

}
