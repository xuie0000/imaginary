package com.xuie.imaginaryandroid.data

/**
 * Created by xuie on 17-8-8.
 */

class BaseBean {

    /**
     * _id : 59818615421aa90ca209c547
     * createdAt : 2017-08-02T15:58:13.659Z
     * desc : 垃圾回收算法与 JVM 垃圾回收器综述
     * publishedAt : 2017-08-15T13:32:36.998Z
     * source : chrome
     * type : Android
     * url : https://zhuanlan.zhihu.com/p/28258571
     * used : true
     * who : 王下邀月熊
     * images : ["http://img.gank.io/e8529e62-606c-44f3-b10d-963d58a2ef83"]
     */

    var _id: String? = null
    var createdAt: String? = null
    var desc: String? = null
    var publishedAt: String? = null
    var source: String? = null
    var type: String? = null
    var url: String? = null
    var isUsed: Boolean = false
    var who: String? = null
    var images: List<String>? = null

    override fun toString(): String {
        return "BaseBean{" +
                "_id='" + _id + '\''.toString() +
                ", createdAt='" + createdAt + '\''.toString() +
                ", desc='" + desc + '\''.toString() +
                ", publishedAt='" + publishedAt + '\''.toString() +
                ", source='" + source + '\''.toString() +
                ", type='" + type + '\''.toString() +
                ", url='" + url + '\''.toString() +
                ", used=" + isUsed +
                ", who='" + who + '\''.toString() +
                ", images=" + images +
                '}'.toString()
    }
}
