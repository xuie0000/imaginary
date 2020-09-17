/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com | 3772304@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package xuk.imaginary.widget

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import xuk.imaginary.R
import xuk.imaginary.app.App
import xuk.imaginary.data.api.NETSApi
import xuk.imaginary.data.api.ServiceGenerator
import xuk.imaginary.util.HttpUtils
import java.io.File
import java.io.InputStream

/**
 * @author 咖枯
 * @version 1.0 2016/6/19
 */
class UrlImageGetter(private val mTextView: TextView, private val mNewsBody: String, private val mPicTotal: Int) : Html.ImageGetter {
  private val mPicWidth: Int = mTextView.width
  private var mPicCount: Int = 0

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
    val imgWidth = drawable.intrinsicWidth.toFloat()
    val imgHeight = drawable.intrinsicHeight.toFloat()
    val rate = imgHeight / imgWidth
    return (mPicWidth * rate).toInt()
  }

  private fun getDrawableFromNet(source: String): Drawable {
    ServiceGenerator.createService(NETSApi::class.java, NETSApi.NETS_API)
        .getNewsBodyHtmlPhoto(HttpUtils.cacheControl, source)
        .unsubscribeOn(Schedulers.io())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map { responseBody -> writePicToDisk(responseBody, source) }
        .subscribe(object : Observer<Boolean> {
          override fun onSubscribe(d: Disposable) {

          }

          override fun onNext(t: Boolean) {
            mPicCount++
            if (t && mPicCount == mPicTotal - 1) {
              mTextView.text = Html.fromHtml(mNewsBody, this@UrlImageGetter, null)
            }
          }

          override fun onError(e: Throwable) {

          }

          override fun onComplete() {

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

  private fun createPicPlaceholder(): Drawable {
    val drawable: Drawable
    val color = R.color.white
    drawable = ColorDrawable(App.context!!.resources.getColor(color))
    drawable.setBounds(0, 0, mPicWidth, mPicWidth / 3)
    return drawable
  }

  companion object {
    private val mFilePath = App.context!!.cacheDir.absolutePath
  }

}