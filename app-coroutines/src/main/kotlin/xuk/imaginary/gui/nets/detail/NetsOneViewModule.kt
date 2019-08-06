package xuk.imaginary.gui.nets.detail

import android.app.Application
import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import xuk.imaginary.data.NetsDetail
import xuk.imaginary.data.source.NetsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * @author Jie Xu
 * @date 2019/1/28
 */
class NetsOneViewModule(application: Application, private val netsRepository: NetsRepository) : AndroidViewModel(application) {
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
