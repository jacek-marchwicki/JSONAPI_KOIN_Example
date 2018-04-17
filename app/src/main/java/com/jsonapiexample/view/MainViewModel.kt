package com.jsonapiexample.view

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.jsonapiexample.repository.MainAPI
import com.jsonapiexample.model.Author
import com.jsonapiexample.util.SchedulerProvider
import com.jsonapiexample.util.with

class MainViewModel(private val mainAPI: MainAPI, private val scheduler: SchedulerProvider) : BaseViewModel() {

    val authorsLiveData = MutableLiveData<List<Author>>()

    fun getAuthors() {
        launch {
            mainAPI.authors()
                    .with(scheduler)
                    .subscribe(
                            {
                                try {
                                    val authors: List<Author> = it.data.map { resource -> resource as Author }
                                    authorsLiveData.value = authors
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            },
                            { err ->
                                err.printStackTrace()
                            })
        }
    }
}