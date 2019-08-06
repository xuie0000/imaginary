package xuk.imaginary.data.source

import xuk.imaginary.data.BaseBean
import xuk.imaginary.data.GankBean

import io.reactivex.Observable

/**
 * @author Jie Xu
 * @date 17-7-5
 */
interface GankSource {
  /**
   * 获取当前福利
   *
   * @param page page
   * @return 福利
   */
  fun get福利(page: Int): Observable<List<BaseBean>>

  /**
   * 获取GANK一天的信息
   *
   * @param date 日期
   * @return Gank信息
   */
  fun getDay(date: String): Observable<GankBean>
}
