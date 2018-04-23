package com.jsonapiexample

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.jsonapiexample.di.appModule
import com.jsonapiexample.repository.MainAPI
import com.jsonapiexample.util.SchedulerProvider
import com.jsonapiexample.view.MainViewModel
import com.jsonapiexample.view.Result
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import okhttp3.MediaType
import okhttp3.Protocol
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import retrofit2.HttpException
import retrofit2.Response
import okhttp3.ResponseBody
import org.junit.*
import org.junit.rules.TestRule


class MainViewModelTest : KoinTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        startKoin(appModule)
    }

    @After
    fun after() {
        closeKoin()
    }

    @Test
    fun `check error handling`() {
        val mainAPI: MainAPI = mock()
        val io = TestScheduler()
        val ui = TestScheduler()
        val mainViewModel = MainViewModel(mainAPI, object : SchedulerProvider {
            override fun io() = io
            override fun ui() = ui
            override fun computation() = Schedulers.io()
        })

        whenever(mainAPI.authors()).thenReturn(Single.error(HttpException(Response.error<Any>(
                403, ResponseBody.create(MediaType.parse("application/json"), "")
        ))))

        mainViewModel.getAuthors()

        io.triggerActions()
        ui.triggerActions()
//        Thread.sleep(1000)
//        verify(mainAPI).authors()
        Assert.assertTrue(mainViewModel.authorsLiveData.value is Result.Error
                && (mainViewModel.authorsLiveData.value as Result.Error).throwable is HttpException
                && ((mainViewModel.authorsLiveData.value as Result.Error).throwable as HttpException).code() == 403)
    }
}