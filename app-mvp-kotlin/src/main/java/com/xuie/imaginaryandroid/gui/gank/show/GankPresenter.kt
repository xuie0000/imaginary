package com.xuie.imaginaryandroid.gui.gank.show

import com.xuie.imaginaryandroid.data.source.GankRepository

import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by xuie on 17-7-5.
 */

class GankPresenter(
        private val gankRepository: GankRepository,
        private val meizhiView: GankContract.View
) : GankContract.Presenter {
    private val currentPage = 1

    init {
        this.meizhiView.presenter = this
    }

    override fun start() {
//        getList(true);
    }

    override fun getGank(year: Int, month: Int, day: Int) {

    }

    override fun getGank(date: String) {
        gankRepository.getDay(date)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ gankBean -> meizhiView.refresh(gankBean) },
                        { e -> e.printStackTrace()})
    }
}
