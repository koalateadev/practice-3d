package com.practice3d.layer.model

interface ModelLayerController {

    /* Model interactions */

    fun setModel()

    fun setMaterial()

    fun scaleModel()

    fun moveModel()

    fun rotateModel()

    /* Light interactions */

    fun setLight()

    fun moveLight()

    fun rotateLight()
}