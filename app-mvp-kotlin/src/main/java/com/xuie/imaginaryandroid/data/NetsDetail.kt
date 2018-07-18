package com.xuie.imaginaryandroid.data

/**
 * Created by xuie on 17-8-22.
 */
data class NetsDetail(
        var template: String,
        var sourceinfo: SourceinfoBean,
        var shareLink: String,
        var source: String,
        var threadVote: Int = 0,
        var title: String,
        var body: String,
        var tid: String,
        var isPicnews: Boolean = false,
        var digest: String,
        var ptime: String,
        var extraCard: ExtraCardBean,
        var ec: String,
        var docid: String,
        var threadAgainst: Int = 0,
        var isHasNext: Boolean = false,
        var dkeys: String,
        var replyCount: Int = 0,
        var voicecomment: String,
        var replyBoard: String,
        var img: List<ImgBean>,
        var autoRecomends: List<AutoRecomendsBean>,
        var topiclist_news: List<TopiclistNewsBean>,
        var book: List<*>,
        var link: List<LinkBean>,
        var boboList: List<*>,
        var ydbaike: List<*>,
        var askbar: List<AskbarBean>,
        var votes: List<*>,
        var topiclist: List<TopiclistBean>
)

class SourceinfoBean(
        var ename: String,
        var alias: String,
        var tname: String,
        var topic_icons: String,
        var tid: String
)

data class ExtraCardBean(
        var extraurl: String,
        var title: String,
        var list: List<ListBean>
)

data class ListBean(
        var name: String,
        var digest: String,
        var imgsrc: String,
        var url: String
)

data class ImgBean(
        var ref: String,
        var src: String,
        var alt: String,
        var pixel: String
)


data class AutoRecomendsBean(
        var youhui: Double = 0.toDouble(),
        var name: String,
        var chexi_id: String,
        var imgsrc: String,
        var youhuinew: String,
        var url: String
)


data class LinkBean(
        var ref: String,
        var digest: String,
        var href: String,
        var title: String,
        var imgsrc: String,
        var type: String
)

data class TopiclistNewsBean(
        var ename: String,
        var isHasCover: Boolean = false,
        var tname: String,
        var alias: String,
        var subnum: String,
        var tid: String,
        var cid: String
)

data class AskbarBean(
        var expertId: String,
        var concernCount: Int = 0,
        var name: String,
        var alias: String,
        var headpicurl: String,
        var title: String
)

data class TopiclistBean(
        var ename: String,
        var isHasCover: Boolean = false,
        var tname: String,
        var alias: String,
        var subnum: String,
        var tid: String,
        var cid: String
)
