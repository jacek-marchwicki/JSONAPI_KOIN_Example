package com.jsonapiexample.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.jsonapiexample.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.architecture.ext.viewModel


class MainActivity : AppCompatActivity() {

    val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.authorsLiveData.observe(this, Observer { authors ->
            textview.text = authors?.toTypedArray()?.joinToString("\n\n")
        })

        mainViewModel.getAuthors()
    }


}
