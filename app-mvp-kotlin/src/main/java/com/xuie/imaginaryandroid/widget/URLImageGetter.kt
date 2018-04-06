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

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

import okhttp3.ResponseBody
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.schedulers.Schedulers

/**
 * @author 咖枯
 * @version 1.0 2016/6/19
 */
class URLImageGetter(private val mTextView: TextView, private val mNewsBody: String, private val mPicTotal: Int) : Html.ImageGetter {
    private val mPicWidth: Int
    private var mPicCount: Int = 0
    var mSubscription: Subscription

    init {
        mPicWidth = mTextView.getWidth()
    }

    @Override
    fun getDrawable(source: String): Drawable? {
        val drawable: Drawable?
        val file = File(mFilePath, source.hashCode().toString() + "")
        if (file.exists()) {
            mPicCount++
            drawable = getDrawableFromDisk(file)
        } else {
            drawable = getDrawableFromNet(source)
        }
        return drawable
    }

    @Nullable
    private fun getDrawableFromDisk(file: File): Drawable? {
        val drawable = Drawable.createFromPath(file.getAbsolutePath())
        if (drawable != null) {
            val picHeight = calculatePicHeight(drawable!!)
            drawable!!.setBounds(0, 0, mPicWidth, picHeight)
        }
        return drawable
    }

    private fun calculatePicHeight(drawable: Drawable): Int {
        val imgWidth = drawable.getIntrinsicWidth()
        val imgHeight = drawable.getIntrinsicHeight()
        val rate = imgHeight / imgWidth
        return (mPicWidth * rate).toInt()
    }

    @NonNull
    private fun getDrawableFromNet(source: String): Drawable {
        mSubscription = ServiceGenerator.createService(NETSApi::class.java, NETSApi.NETS_API)
                .getNewsBodyHtmlPhoto(HttpUtils.getCacheControl(), source)
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(object : Func1<ResponseBody, Boolean>() {
                    @Override
                    fun call(response: ResponseBody): Boolean {
                        return WritePicToDisk(response, source)
                    }
                }).subscribe(object : Subscriber<Boolean>() {
                    @Override
                    fun onCompleted() {
                    }

                    @Override
                    fun onError(e: Throwable) {
                    }

                    @Override
                    fun onNext(isLoadSuccess: Boolean) {
                        mPicCount++
                        if (isLoadSuccess && mPicCount == mPicTotal - 1) {
                            mTextView.setText(Html.fromHtml(mNewsBody, this@URLImageGetter, null))
                        }
                    }
                })

        return createPicPlaceholder()
    }

    @NonNull
    private fun WritePicToDisk(response: ResponseBody, source: String): Boolean {
        val file = File(mFilePath, source.hashCode().toString() + "")
        var `in`: InputStream? = null
        var out: FileOutputStream? = null
        try {
            `in` = response.byteStream()
            out = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var len: Int
            while ((len = `in`!!.read(buffer)) != -1) {
                out!!.write(buffer, 0, len)
            }
            return true
        } catch (e: Exception) {
            return false
        } finally {
            try {
                if (`in` != null) {
                    `in`!!.close()
                }
                if (out != null) {
                    out!!.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    @SuppressWarnings("deprecation")
    @NonNull
    private fun createPicPlaceholder(): Drawable {
        val drawable: Drawable
        val color = R.color.white
        drawable = ColorDrawable(App.getContext().getResources().getColor(color))
        drawable.setBounds(0, 0, mPicWidth, mPicWidth / 3)
        return drawable
    }

    companion object {
        private val mFilePath = App.getContext().getCacheDir().getAbsolutePath()
    }

}