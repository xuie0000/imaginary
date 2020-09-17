package xuk.imaginary.data.source

import io.reactivex.Observable
import xuk.imaginary.data.BaseBean
import xuk.imaginary.data.GankBean

/**
 * Created by xuie on 17-7-5.
 */

interface GankSource {
  fun get福利(page: Int): Observable<List<BaseBean>>

  fun getDay(date: String): Observable<GankBean>
}
