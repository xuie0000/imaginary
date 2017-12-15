package com.xuie.imaginary;

import android.app.Application;
import android.content.Context;

import com.xuie.imaginary.data.source.GankRepository;
import com.xuie.imaginary.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * We create a custom {@link Application} class that extends  {@link DaggerApplication}.
 * We then override applicationInjector() which tells Dagger how to make our @Singleton Component
 * We never have to call `component.inject(this)` as {@link DaggerApplication} will do that for us.
 */
public class ToDoApplication extends DaggerApplication {
    @Inject
    GankRepository gankRepository;

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

//    /**
//     * Our Espresso tests need to be able to get an instance of the {@link TasksRepository}
//     * so that we can delete all tasks before running each test
//     */
//    @VisibleForTesting
//    public TasksRepository getTasksRepository() {
//        return tasksRepository;
//    }
}
