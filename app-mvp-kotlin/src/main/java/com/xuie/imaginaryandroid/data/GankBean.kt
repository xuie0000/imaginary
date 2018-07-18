package com.xuie.imaginaryandroid.data

/**
 * Created by xuie on 17-8-15.
 */
data class GankBean(
        var isError: Boolean = false,
        var results: ResultsBean,
        var category: List<String>
)

data class ResultsBean(
        var Android: List<BaseBean>,
        var iOS: List<BaseBean>,
        var 休息视频: List<BaseBean>,
        var 前端: List<BaseBean>,
        var 瞎推荐: List<BaseBean>,
        var 福利: List<BaseBean>,
        var 拓展资源: List<BaseBean>,
        var App: List<BaseBean>
)
