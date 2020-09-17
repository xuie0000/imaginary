package xuk.imaginary.gui.nets.news

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.AndroidViewModel

import com.xuie.imaginary.data.NetsSummary
import com.xuie.imaginary.data.source.NetsRepository

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * @author Jie Xu
 * @date 2019/1/28
 */
class NewsListViewModule(application: Application, private val netsRepository: NetsRepository) : AndroidViewModel(application) {
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
