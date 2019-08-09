package xuk.imaginary.data

import retrofit2.await

/**
 * @author Jie Xu
 * @date 2019/8/9
 */

object Repository {
  suspend fun get福利(page: Int): List<GankIo.BaseBean> {
    return GankIo.service.get福利(page).await().results ?: return emptyList()
  }

  suspend fun getDay(date: String): GankIo.GankBean {
    return GankIo.service.getDay(date).await()
  }
}