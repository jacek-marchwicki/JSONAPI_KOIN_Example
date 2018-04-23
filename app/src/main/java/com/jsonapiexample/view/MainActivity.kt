package com.jsonapiexample.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.jsonapiexample.R
import com.jsonapiexample.util.gone
import com.jsonapiexample.util.visible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_error.*
import org.koin.android.architecture.ext.viewModel


class MainActivity : AppCompatActivity() {

    val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = AuthorsRecyclerAdapter()
        recyclerView.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener {
            mainViewModel.getAuthors()
        }
        btnRefresh.setOnClickListener {
            mainViewModel.getAuthors()
        }

        mainViewModel.authorsLiveData.observe(this, Observer { result ->
            when(result) {
                is Result.Loading -> {
                    swipeRefreshLayout.isRefreshing = true
                    errorLayout.gone()
                }
                is Result.Error -> {
                    swipeRefreshLayout.isRefreshing = false
                    errorLayout.visible()
                    swipeRefreshLayout.gone()
                }
                is Result.Success -> {
                    swipeRefreshLayout.isRefreshing = false
                    if (result.data != null) {
                        swipeRefreshLayout.visible()
                        adapter.items = result.data
                    }
                }
            }
        })

        mainViewModel.getAuthors()
    }
}
