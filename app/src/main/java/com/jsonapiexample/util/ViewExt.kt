package com.jsonapiexample.util

import android.animation.Animator
import android.view.View
import android.animation.ObjectAnimator


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}