package com.jsonapiexample.di

import com.gustavofao.jsonapi.Retrofit.JSONConverterFactory
import com.jsonapiexample.di.DatasourceProperties.SERVER_URL
import com.jsonapiexample.util.ApplicationSchedulerProvider
import com.jsonapiexample.repository.MainAPI
import com.jsonapiexample.view.MainViewModel
import com.jsonapiexample.util.SchedulerProvider
import com.jsonapiexample.model.*
import okhttp3.OkHttpClient
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

object DatasourceProperties {
    const val SERVER_URL = "SERVER_URL"
}

val viewModelModule = applicationContext {
    viewModel { MainViewModel(get(), get()) }
}

val rxModule = applicationContext {
    bean { ApplicationSchedulerProvider() as SchedulerProvider }
}

val networkModule = applicationContext {
    bean { createOkHttpClient() }
    bean { createWebService<MainAPI>(get(), getProperty(SERVER_URL)) }
}

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(JSONConverterFactory.create(Author::class.java, Photo::class.java, Book::class.java, Serie::class.java, Store::class.java, Chapter::class.java))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}

val appModule = listOf(viewModelModule, rxModule, networkModule)