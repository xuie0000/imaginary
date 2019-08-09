package xuk.imaginary.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import xuk.imaginary.R

/**
 * @author Jie Xu
 * @date 2018-6-28
 */
object GlideUtils {

  fun loadImageMeizhiDetail(context: Context, url: String, imageView: ImageView) {
    Glide.with(context)
        .load(url)
        .apply(RequestOptions()
            //.placeholder(R.mipmap.meizhi_sample)
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
            //.placeholder(R.mipmap.meizhi_sample)
            .error(R.mipmap.meizhi_sample)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        )
        .transition(withCrossFade())
        .into(imageView)
  }


}
