package com.xuie.imaginaryandroid.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by xuie on 17-8-22.
 * https://github.com/bumptech/glide/wiki/Configuration
 */
@GlideModule
public final class MyAppGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 在 Android 中有两个主要的方法对图片进行解码：ARGB8888 和 RGB565(DecodeFormat.PREFER_RGB_565)。前者为每个像素使用了 4 个字节，后者仅为每个像素使用了 2 个字节。ARGB8888 的优势是图像质量更高以及能存储一个 alpha 通道。Picasso 使用 ARGB8888，Glide 默认使用低质量的 RGB565
        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.format(DecodeFormat.PREFER_ARGB_8888);
        builder.setDefaultRequestOptions(requestOptions);
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "com.xuie", 100 * 1024 * 1024));
//        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "com.xuie", 100 * 1024 * 1024));
        builder.setMemoryCache(new LruResourceCache(10 * 1024 * 1024));
    }

}

