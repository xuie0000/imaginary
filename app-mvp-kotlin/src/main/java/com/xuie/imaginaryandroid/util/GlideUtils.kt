package com.xuie.imaginaryandroid.util

import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.xuie.imaginaryandroid.R

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

/**
 * @author xuie
 * @date 2018-6-28
 */
object GlideUtils {

    fun loadImageNetsVideo(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions().fitCenter()
                        .placeholder(R.mipmap.ic_empty_picture)
                        .error(R.mipmap.ic_empty_picture)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .centerCrop()
                )
                .transition(withCrossFade())
                .into(imageView)
    }

    fun loadImageNetsList(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions()
                        .placeholder(R.mipmap.ic_empty_picture)
                        .error(R.mipmap.ic_empty_picture)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .override(300, 300)
                        .centerInside()
                )
                .transition(withCrossFade())
                .into(imageView)
    }

    fun loadImageNetsPhone(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions().fitCenter()
                        .placeholder(R.mipmap.ic_empty_picture)
                        .error(R.mipmap.ic_empty_picture)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .centerCrop()
                )
                .transition(withCrossFade())
                .into(imageView)
    }

    fun loadImageMeizhiDetail(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions()
                        .placeholder(R.mipmap.meizhi_sample)
                        .error(R.mipmap.meizhi_sample)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                )
                .transition(withCrossFade())
                .into(imageView)
    }

    fun loadImageMeizhi(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions().fitCenter()
                        .placeholder(R.mipmap.meizhi_sample)
                        .error(R.mipmap.meizhi_sample)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                )
                .transition(withCrossFade())
                .into(imageView)
    }

    fun loadImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions().fitCenter()
                        .placeholder(R.mipmap.ic_empty_picture)
                        .error(R.mipmap.ic_empty_picture)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .circleCrop()
                )
                .transition(withCrossFade())
                .into(imageView)
    }

    fun loadRoundImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(RequestOptions().centerCrop()
                        .error(R.mipmap.ic_empty_picture)
                )
                .into(object : BitmapImageViewTarget(imageView) {
                    @Override
                    protected fun setResource(resource: Bitmap) {
                        val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource)
                        circularBitmapDrawable.setCircular(true)
                        imageView.setImageDrawable(circularBitmapDrawable)
                    }
                })

    }

    fun loadAvatar(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(RequestOptions().centerCrop().skipMemoryCache(true).error(R.mipmap.ic_empty_picture))
                .into(object : BitmapImageViewTarget(imageView) {
                    @Override
                    protected fun setResource(resource: Bitmap) {
                        val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource)
                        circularBitmapDrawable.setCircular(true)
                        imageView.setImageDrawable(circularBitmapDrawable)
                    }
                })
    }


}