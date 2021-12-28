package com.practice3d.extensions

import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator


fun View.rotate() {
    animate()
        .rotation(180f)
        .setDuration(400)
        .start()
}

fun View.rotateDown() {
    animate()
        .rotation(0f)
        .setDuration(400)
        .start()
}

fun View.expand() {
    measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val targetHeight = measuredHeight

    layoutParams.height = 0
    visibility = View.VISIBLE
    clipToOutline = true
    animate()
        .setUpdateListener {
            layoutParams.height = (targetHeight * it.animatedFraction).toInt()
            requestLayout()
        }
        .setDuration(targetHeight.toLong())
        .setInterpolator(AccelerateInterpolator())
        .start()
}

fun View.collapse() {
    val initialHeight = layoutParams.height

    animate()
        .setUpdateListener {
            if (it.animatedFraction == 1f) visibility = View.GONE

            layoutParams.height = (initialHeight * (1 - it.animatedFraction)).toInt()
            requestLayout()
        }
        .setDuration((measuredHeight).toLong())
        .start()
}