package com.xuie.imaginary.data.source;

import com.xuie.imaginary.data.source.remote.GankRemoteSource;
import com.xuie.imaginary.util.AppExecutors;
import com.xuie.imaginary.util.DiskIOThreadExecutor;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * This is used by Dagger to inject the required arguments into the {@link GankRepository}.
 */
@Module
abstract public class GankRepositoryModule {

    private static final int THREAD_COUNT = 3;

//    @Singleton
//    @Binds
//    @Local
//    abstract GankSource provideGankLocalDataSource(TasksLocalDataSource dataSource);

    @Singleton
    @Binds
    @Remote
    abstract GankSource provideGankRemoteSource(GankRemoteSource remoteSource);
//
//    @Singleton
//    @Provides
//    static ToDoDatabase provideDb(Application context) {
//        return Room.databaseBuilder(context.getApplicationContext(), ToDoDatabase.class, "Tasks.db")
//                .build();
//    }
//
//    @Singleton
//    @Provides
//    static TasksDao provideTasksDao(ToDoDatabase db) {
//        return db.taskDao();
//    }

    @Singleton
    @Provides
    static AppExecutors provideAppExecutors() {
        return new AppExecutors(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }
}
