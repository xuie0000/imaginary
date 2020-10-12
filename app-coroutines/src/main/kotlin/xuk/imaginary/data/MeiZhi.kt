package xuk.imaginary.data

/**
 * @author Jie Xu
 * @date 2020/10/12
 */
data class MeiZhi(
    var title: String,
    var gk: GkIo.BaseBean?,
    var type: Int = TYPE_TITLE
) {

  companion object {
    const val TYPE_TITLE = 0
    const val TYPE_ITEM = 1
  }

}