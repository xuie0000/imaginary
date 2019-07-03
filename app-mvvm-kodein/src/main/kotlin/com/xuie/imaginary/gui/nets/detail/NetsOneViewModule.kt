package com.xuie.imaginary.gui.nets.detail

import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.xuie.imaginary.data.NetsDetail
import com.xuie.imaginary.data.source.NetsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

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

const val NETS_ONE_MODULE_TAG = "NETS_ONE_MODULE"

val netsOneModel = Kodein.Module(NETS_ONE_MODULE_TAG) {
  bind() from singleton { NetsOneViewModule(instance()) }
}
