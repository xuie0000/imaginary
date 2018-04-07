package com.xuie.imaginaryandroid.gui.gank.meizhi

import com.xuie.imaginaryandroid.data.source.GankRepository
import io.reactivex.android.schedulers.AndroidSchedulers

//import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by xuie on 17-7-5.
 */

class MeizhiPresenter(
        private val gankRepository: GankRepository,
        private val gankView: MeizhiContract.View
) : MeizhiContract.Presenter {
    init {
        this.gankView.presenter = this
    }

    private var currentPage = 1

    override fun subscribe() {
        getList(true)
    }

    override fun unsubscribe() {
    }

    override fun getList(isRefresh: Boolean) {
        // get local data
        if (isRefresh) {
            currentPage = 1
        } else {
            currentPage++
        }
        gankRepository.get福利(currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ meiZhis -> gankView.addList(isRefresh, meiZhis) },
                        { e -> e.printStackTrace()})
        // get remote data

    }
}
