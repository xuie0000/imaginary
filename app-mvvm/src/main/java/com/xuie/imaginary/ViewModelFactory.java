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

package com.xuie.imaginary;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.xuie.imaginary.data.Injection;
import com.xuie.imaginary.data.source.GankRepository;
import com.xuie.imaginary.data.source.NetsRepository;
import com.xuie.imaginary.gui.gank.meizhi.MeiZhiViewModule;
import com.xuie.imaginary.gui.gank.show.GankViewModule;
import com.xuie.imaginary.gui.nets.detail.NetsOneViewModule;
import com.xuie.imaginary.gui.nets.news.NewsListViewModule;
import com.xuie.imaginary.gui.nets.video.VideosViewModule;

/**
 * A creator is used to inject the product ID into the ViewModel
 * <p>
 * This creator is to showcase how to inject dependencies into ViewModels. It's not
 * actually necessary in this case, as the product ID can be passed in a public method.
 *
 * @author google
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak")
    private static volatile ViewModelFactory INSTANCE;

    private final Application mApplication;

    private final GankRepository mGankRepository;
    private final NetsRepository mNetsRepository;

    public static ViewModelFactory getInstance(Application application) {

        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application,
                            Injection.provideGankRepository(),
                            Injection.provideNetsRepository());
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private ViewModelFactory(Application application, GankRepository gankRepository, NetsRepository netsRepository) {
        mApplication = application;
        mGankRepository = gankRepository;
        mNetsRepository = netsRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MeiZhiViewModule.class)) {
            //noinspection unchecked
            return (T) new MeiZhiViewModule(mApplication, mGankRepository);
        } else if (modelClass.isAssignableFrom(GankViewModule.class)) {
            //noinspection unchecked
            return (T) new GankViewModule(mApplication, mGankRepository);
        } else if (modelClass.isAssignableFrom(NewsListViewModule.class)) {
            //noinspection unchecked
            return (T) new NewsListViewModule(mApplication, mNetsRepository);
        } else if (modelClass.isAssignableFrom(NetsOneViewModule.class)) {
            //noinspection unchecked
            return (T) new NetsOneViewModule(mApplication, mNetsRepository);
        } else if (modelClass.isAssignableFrom(VideosViewModule.class)) {
            //noinspection unchecked
            return (T) new VideosViewModule(mApplication, mNetsRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
