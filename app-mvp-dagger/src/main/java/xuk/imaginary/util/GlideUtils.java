package xuk.imaginary.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import xuk.imaginary.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author xuie
 * @date 2018-6-28
 */
public class GlideUtils {

    public static void loadImageNetsVideo(final Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().fitCenter()
                        .placeholder(R.mipmap.ic_empty_picture)
                        .error(R.mipmap.ic_empty_picture)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .centerCrop()
                )
                .transition(withCrossFade())
                .into(imageView);
    }

    public static void loadImageNetsList(final Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_empty_picture)
                        .error(R.mipmap.ic_empty_picture)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .override(300, 300)
                        .centerInside()
                )
                .transition(withCrossFade())
                .into(imageView);
    }

    public static void loadImageNetsPhone(final Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().fitCenter()
                        .placeholder(R.mipmap.ic_empty_picture)
                        .error(R.mipmap.ic_empty_picture)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .centerCrop()
                )
                .transition(withCrossFade())
                .into(imageView);
    }

    public static void loadImageMeizhiDetail(final Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
//                        .placeholder(R.mipmap.meizhi_sample)
                                .error(R.mipmap.meizhi_sample)
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                )
                .transition(withCrossFade())
                .into(imageView);
    }

    public static void loadImageMeizhi(final Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().fitCenter()
//                        .placeholder(R.mipmap.meizhi_sample)
                                .error(R.mipmap.meizhi_sample)
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                )
                .transition(withCrossFade())
                .into(imageView);
    }

    public static void loadImage(final Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().fitCenter()
//                        .placeholder(R.mipmap.ic_empty_picture)
                                .error(R.mipmap.ic_empty_picture)
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                .circleCrop()
                )
                .transition(withCrossFade())
                .into(imageView);
    }

    public static void loadRoundImage(final Context context, String url, final ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(new RequestOptions().centerCrop()
                        .error(R.mipmap.ic_empty_picture)
                )
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });

    }

    public static void loadAvatar(final Context context, String url, final ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(new RequestOptions().centerCrop().skipMemoryCache(true).error(R.mipmap.ic_empty_picture))
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }


}
