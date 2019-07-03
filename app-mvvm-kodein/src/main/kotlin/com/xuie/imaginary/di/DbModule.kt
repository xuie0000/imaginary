package com.xuie.imaginary.di

import org.kodein.di.Kodein

/**
 * @author Jie Xu
 * @date 2019/3/25
 */
const val DB_MODULE_TAG = "DB_MODULE"

val dbModule = Kodein.Module(DB_MODULE_TAG) {

//  bind<WeatherDao>() with singleton {
//    Room.databaseBuilder(App.context, WeatherDatabase::class.java, "weather").build().weatherDao()
//  }

}