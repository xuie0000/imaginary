/*
 *  Copyright 2017 Google Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.xuie.imaginary

import android.annotation.SuppressLint
import android.app.Application

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.xuie.imaginary.data.Injection
import com.xuie.imaginary.data.source.GankRepository
import com.xuie.imaginary.data.source.NetsRepository
import com.xuie.imaginary.gui.gank.meizhi.MeiZhiViewModule
import com.xuie.imaginary.gui.gank.show.GankViewModule
import com.xuie.imaginary.gui.nets.detail.NetsOneViewModule
import com.xuie.imaginary.gui.nets.news.NewsListViewModule
import com.xuie.imaginary.gui.nets.video.VideosViewModule

/**
 * A creator is used to inject the product ID into the ViewModel
 *
 *
 * This creator is to showcase how to inject dependencies into ViewModels. It's not
 * actually necessary in this case, as the product ID can be passed in a public method.
 *
 * @author google
 */
class ViewModelFactory private constructor(private val mApplication: Application, private val mGankRepository: GankRepository, private val mNetsRepository: NetsRepository) : ViewModelProvider.NewInstanceFactory() {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(MeiZhiViewModule::class.java)) {

      return MeiZhiViewModule(mApplication, mGankRepository) as T
    } else if (modelClass.isAssignableFrom(GankViewModule::class.java)) {

      return GankViewModule(mApplication, mGankRepository) as T
    } else if (modelClass.isAssignableFrom(NewsListViewModule::class.java)) {

      return NewsListViewModule(mApplication, mNetsRepository) as T
    } else if (modelClass.isAssignableFrom(NetsOneViewModule::class.java)) {

      return NetsOneViewModule(mApplication, mNetsRepository) as T
    } else if (modelClass.isAssignableFrom(VideosViewModule::class.java)) {

      return VideosViewModule(mApplication, mNetsRepository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
  }

  companion object {

    @SuppressLint("StaticFieldLeak")
    @Volatile
    private var INSTANCE: ViewModelFactory? = null

    fun getInstance(application: Application): ViewModelFactory? {

      if (INSTANCE == null) {
        synchronized(ViewModelFactory::class.java) {
          if (INSTANCE == null) {
            INSTANCE = ViewModelFactory(application,
                Injection.provideGankRepository(),
                Injection.provideNetsRepository())
          }
        }
      }
      return INSTANCE
    }

    @VisibleForTesting
    fun destroyInstance() {
      INSTANCE = null
    }
  }
}
