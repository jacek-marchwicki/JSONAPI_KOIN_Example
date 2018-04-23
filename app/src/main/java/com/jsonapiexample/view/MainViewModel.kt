package com.jsonapiexample.view

import android.arch.lifecycle.MutableLiveData
import com.github.davidmoten.rx2.RetryWhen
import com.jsonapiexample.model.Author
import com.jsonapiexample.repository.MainAPI
import com.jsonapiexample.util.SchedulerProvider
import com.jsonapiexample.util.with
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

class MainViewModel(private val mainAPI: MainAPI, private val scheduler: SchedulerProvider) : BaseViewModel() {

    val authorsLiveData = MutableLiveData<Result<List<Author>>>()

    fun getAuthors() {
        launch {
            mainAPI.authors()
                    .with(scheduler)
                    .doOnSubscribe {
                        authorsLiveData.value = Result.Loading()
                    }
                    .retryWhen(RetryWhen.exponentialBackoff(100, TimeUnit.MILLISECONDS)
                            .retryIf {
                                it is HttpException && it.code() == 500
                            }.build())
                    .subscribe(
                            {
                                try {
                                    val authors: List<Author> = it.data.map { resource -> resource as Author }
                                    authorsLiveData.value = Result.Success(authors)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    authorsLiveData.value = Result.Error(e)
                                }
                            },
                            { err ->
                                err.printStackTrace()
                                authorsLiveData.value = Result.Error(err)
                            })
        }
    }
}