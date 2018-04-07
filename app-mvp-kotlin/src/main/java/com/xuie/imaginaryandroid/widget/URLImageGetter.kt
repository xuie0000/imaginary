package com.xuie.imaginaryandroid.widget

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import com.xuie.imaginaryandroid.R
import com.xuie.imaginaryandroid.app.App
import com.xuie.imaginaryandroid.data.api.NETSApi
import com.xuie.imaginaryandroid.data.api.ServiceGenerator
import com.xuie.imaginaryandroid.util.HttpUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.reactivestreams.Subscription
import java.io.File
import java.io.InputStream

class URLImageGetter(
        private val mTextView: TextView,
        private val mNewsBody: String,
        private val mPicTotal: Int
) : Html.ImageGetter {
    private val mPicWidth: Int = mTextView.width
    private var mPicCount: Int = 0
    private lateinit var mSubscription: Subscription

    override fun getDrawable(source: String): Drawable? {
        val drawable: Drawable?
        val file = File(mFilePath, source.hashCode().toString() + "")
        drawable = if (file.exists()) {
            mPicCount++
            getDrawableFromDisk(file)
        } else {
            getDrawableFromNet(source)
        }
        return drawable
    }

    private fun getDrawableFromDisk(file: File): Drawable? {
        val drawable = Drawable.createFromPath(file.absolutePath)
        if (drawable != null) {
            val picHeight = calculatePicHeight(drawable)
            drawable.setBounds(0, 0, mPicWidth, picHeight)
        }
        return drawable
    }

    private fun calculatePicHeight(drawable: Drawable): Int {
        val imgWidth = drawable.intrinsicWidth
        val imgHeight = drawable.intrinsicHeight
        val rate = imgHeight / imgWidth
        return mPicWidth * rate
    }

    private fun getDrawableFromNet(source: String): Drawable {
        ServiceGenerator.createService(NETSApi::class.java, NETSApi.NETS_API)
                .getNewsBodyHtmlPhoto(HttpUtils.cacheControl, source)
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { response ->
                    writePicToDisk(response, source)
                }
                .subscribe(object : Observer<Boolean> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onComplete() {
                    }

                    override fun onNext(isLoadSuccess: Boolean) {
                        mPicCount++
                        if (isLoadSuccess && mPicCount == mPicTotal - 1) {
                            mTextView.text = Html.fromHtml(mNewsBody, this@URLImageGetter, null)
                        }
                    }
                })

        return createPicPlaceholder()
    }

    private fun writePicToDisk(response: ResponseBody, source: String): Boolean {
        val file = File(mFilePath, source.hashCode().toString() + "")
        val input: InputStream = response.byteStream()
        file.copyInputStreamToFile(input)

//        val inputAsString = input.bufferedReader().use { it.readText() }
        return true
    }

    private fun File.copyInputStreamToFile(inputStream: InputStream) {
        inputStream.use { input ->
            this.outputStream().use { fileOut ->
                input.copyTo(fileOut)
            }
        }
    }

    @SuppressWarnings("deprecation")
    private fun createPicPlaceholder(): Drawable {
        val drawable: Drawable
        val color = R.color.white
        drawable = ColorDrawable(App.context.resources.getColor(color))
        drawable.setBounds(0, 0, mPicWidth, mPicWidth / 3)
        return drawable
    }

    companion object {
        private val mFilePath = App.context.cacheDir.absolutePath
        private const val TAG = "URLImageGetter"
    }

}
