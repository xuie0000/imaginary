package xuk.imaginary.data

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @author Jie Xu
 * @date 17-8-16
 */
class NetsSummary : MultiItemEntity {

  var template: String? = null
  var skipID: String? = null
  var lmodify: String? = null
  var postid: String? = null
  var source: String? = null
  var title: String? = null
  var mtime: String? = null
  var hasImg: Int = 0
  var topic_background: String? = null
  var digest: String? = null
  var photosetID: String? = null
  var boardid: String? = null
  var alias: String? = null
  var hasAD: Int = 0
  var imgsrc: String? = null
  var ptime: String? = null
  var daynum: String? = null
  var hasHead: Int = 0
  var order: Int = 0
  var votecount: Int = 0
  var isHasCover: Boolean = false
  var docid: String? = null
  var tname: String? = null
  var priority: Int = 0
  var ename: String? = null
  var replyCount: Int = 0
  var imgsum: Int = 0
  var isHasIcon: Boolean = false
  var skipType: String? = null
  var cid: String? = null
  var imgextra: List<ImgextraBean>? = null
  var ads: List<AdsBean>? = null
  override fun getItemType(): Int {
    return if (imgextra != null) IMG_MULTI else IMG_ONE
  }

  companion object {
    const val IMG_ONE = 1
    const val IMG_MULTI = 2
  }
}

data class ImgextraBean(
    var imgsrc: String
)

data class AdsBean(
    var subtitle: String,
    var skipType: String,
    var skipID: String,
    var tag: String,
    var title: String,
    var imgsrc: String,
    var url: String
)
