package com.xuie.imaginary.gui.nets.detail

import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xuie.imaginary.data.NetsDetail
import com.xuie.imaginary.data.source.NetsRepository
import com.xuie.imaginary.util.SingletonHolderSingleArg
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * @author Jie Xu
 * @date 2019/1/28
 */
class NetsOneViewModule(private val netsRepository: NetsRepository) : ViewModel() {
  val detail = ObservableField<NetsDetail>()
  private var disposable: Disposable? = null

  fun getNewsOneRequest(postId: String, imgRes: String) {
    clear()
    disposable = netsRepository.getNewDetail(postId)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ netsDetail ->
          var res = getImgSrcs(netsDetail)
          if (TextUtils.isEmpty(res)) {
            res = imgRes
          }
          // TODO
          netsDetail.imageUrl = res
          detail.set(netsDetail)
        }, { e -> e.printStackTrace() })
  }

  private fun getImgSrcs(netsDetail: NetsDetail): String? {
    val imgSrcList = netsDetail.img
    var imgSrc: String? = null
    if (imgSrcList != null && imgSrcList.isNotEmpty()) {
      imgSrc = imgSrcList[0].src
    }
    return imgSrc
  }

  private fun clear() {
    if (disposable != null) {
      disposable!!.dispose()
      disposable = null
    }
  }

}


class NetsOneViewModuleFactory(private val repo: NetsRepository)
  : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T =
      NetsOneViewModule(repo) as T

  companion object : SingletonHolderSingleArg<NetsOneViewModuleFactory, NetsRepository>(::NetsOneViewModuleFactory)
}
