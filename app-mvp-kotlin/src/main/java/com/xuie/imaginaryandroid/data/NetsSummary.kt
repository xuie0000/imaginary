package com.xuie.imaginaryandroid.data

/**
 * Created by xuie on 17-8-16.
 */
data class NetsSummary(
        var template: String,
        var skipID: String,
        var lmodify: String,
        var postid: String,
        var source: String,
        var title: String,
        var mtime: String,
        var hasImg: Int = 0,
        var topic_background: String,
        var digest: String,
        var photosetID: String,
        var boardid: String,
        var alias: String,
        var hasAD: Int = 0,
        var imgsrc: String,
        var ptime: String,
        var daynum: String,
        var hasHead: Int = 0,
        var order: Int = 0,
        var votecount: Int = 0,
        var isHasCover: Boolean = false,
        var docid: String,
        var tname: String,
        var priority: Int = 0,
        var ename: String,
        var replyCount: Int = 0,
        var imgsum: Int = 0,
        var isHasIcon: Boolean = false,
        var skipType: String,
        var cid: String,
        var imgextra: List<ImgextraBean>,
        var ads: List<AdsBean>
//    override fun getItemType(): Int (
//        return if (imgextra != null) IMG_MULTI else IMG_ONE
//)
)

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
