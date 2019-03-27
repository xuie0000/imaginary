package com.xuie.imaginary.data

/**
 * @author Jie Xu
 * @date 17-8-8
 */
data class BaseBean(
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

    var _id: String,
    var createdAt: String,
    var desc: String,
    var publishedAt: String,
    var source: String,
    var type: String,
    var url: String,
    var isUsed: Boolean = false,
    var who: String,
    var images: List<String>? = null
)
