package com.xuie.imaginaryandroid.data.source

import com.xuie.imaginaryandroid.data.BaseBean
import com.xuie.imaginaryandroid.data.GankBean
import io.reactivex.Observable

/**
 * Created by xuie on 17-7-5.
 */

interface GankSource {
    fun get福利(page: Int): Observable<List<BaseBean>>

    fun getDay(date: String): Observable<GankBean>
}
