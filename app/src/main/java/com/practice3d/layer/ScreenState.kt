package com.practice3d.layer

class ScreenState {

    val cameraOpen: Boolean = false
    val menuOpen: Boolean = false
    val locked: Boolean = false
    val controlSelected: ControlType = ControlType.NONE
    val hideControls: Boolean = false
}

enum class ControlType {
    NONE,
    MODEL_SELECTOR,
    MODEL_TRANSFORMER,
    LIGHT_TRANSFORMER,
    CAMERA
}