package com.xuie.imaginaryandroid.data

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Created by xuie on 17-8-16.
 */

class NetsSummary : MultiItemEntity {

    /**
     * imgextra : [{"imgsrc":"http://cms-bucket.nosdn.127.net/e2ab56d95eb342e9b2d851bb3885158720170817074439.jpeg"},{"imgsrc":"http://cms-bucket.nosdn.127.net/9a36161866174b1186077a94db40ac2120170817074439.jpeg"}]
     * template : normal1
     * skipID : 00AP0001|2271371
     * lmodify : 2017-08-17 08:43:55
     * postid : PHOT25A4B000100A
     * source : 视觉中国
     * title : 探访合肥被扣共享单车存放点 堆积如山
     * mtime : 2017-08-17 08:43:55
     * hasImg : 1
     * topic_background : http://img2.cache.netease.com/m/newsapp/reading/cover1/C1348646712614.jpg
     * digest :
     * photosetID : 00AP0001|2271371
     * boardid : photoview_bbs
     * alias : Top News
     * hasAD : 1
     * imgsrc : http://cms-bucket.nosdn.127.net/9cfbe0dcab3c475e868ff6a270217e2620170817074438.jpeg
     * ptime : 2017-08-17 07:44:48
     * daynum : 17395
     * hasHead : 1
     * order : 1
     * votecount : 30724
     * hasCover : false
     * docid : 9IG74V5H00963VRO_CS1BB7DNbjjikeupdateDoc
     * tname : 头条
     * priority : 354
     * ads : [{"subtitle":"","skipType":"photoset","skipID":"00AO0001|2271377","tag":"photoset","title":"为防暴乱 美国连夜拆除内战将领雕像","imgsrc":"http://cms-bucket.nosdn.127.net/78585eed3e3546598853f9863d148eae20170817083623.jpeg","url":"00AO0001|2271377"},{"subtitle":"","skipType":"photoset","skipID":"00AO0001|2271376","tag":"photoset","title":"迪拜漂浮别墅悄然走红 售价约2100万元","imgsrc":"http://cms-bucket.nosdn.127.net/24e66ad03a664ed1b796905476cbae2f20170817081824.jpeg","url":"00AO0001|2271376"},{"subtitle":"","skipType":"photoset","skipID":"00AP0001|2271378","tag":"photoset","title":"城市下水管道蛙人 给管道\u201c做胃镜\u201d","imgsrc":"http://cms-bucket.nosdn.127.net/fd7ee7e85f1c45b6a290b1272bab9cdc20170817083720.jpeg","url":"00AP0001|2271378"},{"subtitle":"","skipType":"photoset","skipID":"00AN0001|2271365","tag":"photoset","title":"山东烟台武警开展跨区反恐综合演练","imgsrc":"http://cms-bucket.nosdn.127.net/7b83603ecaae4e3d89e5a1528d3fe36020170817025717.jpeg","url":"00AN0001|2271365"},{"subtitle":"","skipType":"photoset","skipID":"00AN0001|2271364","tag":"photoset","title":"西安一家餐馆起火 附近住户被紧急疏散","imgsrc":"http://cms-bucket.nosdn.127.net/0ca22190e7bb495f8e58f9735907d10420170817021601.jpeg","url":"00AN0001|2271364"}]
     * ename : iosnews
     * replyCount : 31907
     * imgsum : 6
     * hasIcon : true
     * skipType : photoset
     * cid : C1348646712614
     */

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

    class ImgextraBean {
        /**
         * imgsrc : http://cms-bucket.nosdn.127.net/e2ab56d95eb342e9b2d851bb3885158720170817074439.jpeg
         */

        var imgsrc: String? = null

        override fun toString(): String {
            return "ImgextraBean{" +
                    "imgsrc='" + imgsrc + '\''.toString() +
                    '}'.toString()
        }
    }

    class AdsBean {
        /**
         * subtitle :
         * skipType : photoset
         * skipID : 00AO0001|2271377
         * tag : photoset
         * title : 为防暴乱 美国连夜拆除内战将领雕像
         * imgsrc : http://cms-bucket.nosdn.127.net/78585eed3e3546598853f9863d148eae20170817083623.jpeg
         * url : 00AO0001|2271377
         */

        var subtitle: String? = null
        var skipType: String? = null
        var skipID: String? = null
        var tag: String? = null
        var title: String? = null
        var imgsrc: String? = null
        var url: String? = null

        override fun toString(): String {
            return "AdsBean{" +
                    "subtitle='" + subtitle + '\''.toString() +
                    ", skipType='" + skipType + '\''.toString() +
                    ", skipID='" + skipID + '\''.toString() +
                    ", tag='" + tag + '\''.toString() +
                    ", title='" + title + '\''.toString() +
                    ", imgsrc='" + imgsrc + '\''.toString() +
                    ", url='" + url + '\''.toString() +
                    '}'.toString()
        }
    }

    override fun toString(): String {
        return "NetsSummary{" +
                "template='" + template + '\''.toString() +
                ", skipID='" + skipID + '\''.toString() +
                ", lmodify='" + lmodify + '\''.toString() +
                ", postid='" + postid + '\''.toString() +
                ", source='" + source + '\''.toString() +
                ", title='" + title + '\''.toString() +
                ", mtime='" + mtime + '\''.toString() +
                ", hasImg=" + hasImg +
                ", topic_background='" + topic_background + '\''.toString() +
                ", digest='" + digest + '\''.toString() +
                ", photosetID='" + photosetID + '\''.toString() +
                ", boardid='" + boardid + '\''.toString() +
                ", alias='" + alias + '\''.toString() +
                ", hasAD=" + hasAD +
                ", imgsrc='" + imgsrc + '\''.toString() +
                ", ptime='" + ptime + '\''.toString() +
                ", daynum='" + daynum + '\''.toString() +
                ", hasHead=" + hasHead +
                ", order=" + order +
                ", votecount=" + votecount +
                ", hasCover=" + isHasCover +
                ", docid='" + docid + '\''.toString() +
                ", tname='" + tname + '\''.toString() +
                ", priority=" + priority +
                ", ename='" + ename + '\''.toString() +
                ", replyCount=" + replyCount +
                ", imgsum=" + imgsum +
                ", hasIcon=" + isHasIcon +
                ", skipType='" + skipType + '\''.toString() +
                ", cid='" + cid + '\''.toString() +
                ", imgextra=" + imgextra +
                ", ads=" + ads +
                '}'.toString()
    }

    companion object {
        const val IMG_ONE = 1
        const val IMG_MULTI = 2
    }
}
