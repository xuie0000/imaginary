package com.xuie.imaginaryandroid.glide

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

/**
 * Created by xuie on 17-8-22.
 * https://github.com/bumptech/glide/wiki/Configuration
 */
@GlideModule
class MyAppGlideModule : AppGlideModule() {
//    override fun applyOptions(context: Context, builder: GlideBuilder) {
//        // 在 Android 中有两个主要的方法对图片进行解码：ARGB8888 和 RGB565(DecodeFormat.PREFER_RGB_565)。前者为每个像素使用了 4 个字节，后者仅为每个像素使用了 2 个字节。ARGB8888 的优势是图像质量更高以及能存储一个 alpha 通道。Picasso 使用 ARGB8888，Glide 默认使用低质量的 RGB565
//        val requestOptions = RequestOptions()
////        requestOptions.format(DecodeFormat.PREFER_ARGB_8888);
//        builder.setDefaultRequestOptions(requestOptions)
//        builder.setDiskCache(InternalCacheDiskCacheFactory(context, "com.xuie", 100 * 1024 * 1024))
////        builder.setDiskCache(new ExternalCacheDiskCacheFactory (context, "com.xuie", 100 * 1024 * 1024));
//        builder.setMemoryCache(LruResourceCache(10 * 1024 * 1024))
//        builder.setLogLevel(Log.ERROR)
//    }

}

