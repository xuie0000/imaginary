/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xuk.imaginary.data;

import xuk.imaginary.data.source.GankRepository;
import xuk.imaginary.data.source.GankSource;
import xuk.imaginary.data.source.NetsRepository;

/**
 * Enables injection of mock implementations for
 * {@link GankSource} at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 *
 * @author xuie
 */
public class Injection {

//    public static TasksRepository provideTasksRepository(@NonNull Context context) {
//        checkNotNull(context);
//        return TasksRepository.getInstance(FakeTasksRemoteDataSource.getInstance(),
//                TasksLocalDataSource.getInstance(context, provideSchedulerProvider()));
//    }

    public static GankRepository provideGankRepository() {
        return GankRepository.getInstance();
    }

    public static NetsRepository provideNetsRepository() {
        return NetsRepository.getInstance();
    }

}
