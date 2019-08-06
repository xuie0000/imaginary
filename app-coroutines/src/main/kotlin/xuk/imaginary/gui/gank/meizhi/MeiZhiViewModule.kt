package xuk.imaginary.gui.gank.meizhi

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.AndroidViewModel
import xuk.imaginary.data.BaseBean
import xuk.imaginary.data.source.GankRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * @author Jie Xu
 * @date 2019/1/28
 */
class MeiZhiViewModule(application: Application, private val gankRepository: GankRepository) : AndroidViewModel(application) {

  val items: ObservableList<BaseBean> = ObservableArrayList()
  private var currentPage = 1
  private var disposable: Disposable? = null
  private var isRefresh = true
  private var isRequesting = false

  internal fun start() {
    getList(isRefresh)
    isRefresh = false
  }

  internal fun getList(isRefresh: Boolean) {
    Log.d(TAG, "getList: ...$isRefresh, $isRequesting")
    // get local data
    if (isRequesting) {
      Log.d(TAG, "getList: requesting")
      return
    }
    if (isRefresh) {
      currentPage = 1
    } else {
      currentPage++
    }

    isRequesting = true
    clear()
    disposable = gankRepository.get福利(currentPage)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ meiZhiList ->
          isRequesting = false
          if (isRefresh) {
            items.clear()
          }
          items.addAll(meiZhiList)
        }, { throwable ->
          isRequesting = false
          throwable.printStackTrace()
        })
    // get remote data
  }

  private fun clear() {
    if (disposable != null) {
      disposable!!.dispose()
      disposable = null
    }
  }

  fun end() {
    clear()
  }

  companion object {
    private const val TAG = "MeiZhiViewModule"
  }

}
