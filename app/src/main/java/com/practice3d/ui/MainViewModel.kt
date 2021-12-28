package com.practice3d.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.ar.sceneform.rendering.ModelRenderable
import com.practice3d.infrastructure.model.Shapes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private val state = ViewState()
    val observableState = MutableLiveData(state)

    fun toggleLock() {
        state.locked = !state.locked
        observableState.postValue(state)
    }

    fun controlCamera() {
        state.controller = Controller.CAMERA
        observableState.postValue(state)
    }

    fun controlShapeSelection() {
        state.controller = Controller.MODEL_SELECTION
        observableState.postValue(state)
    }

    fun controlShapeTransformation() {
        state.controller = Controller.MODEL_TRANSFORMATION
        observableState.postValue(state)
    }

    fun setShape(shape: Shapes) {
        state.shape = shape
        observableState.postValue(state)
    }

    inner class ViewState {
        var controller: Controller = Controller.NONE
            internal set
        var locked: Boolean = false
            internal set
        var optionsVisibility: VisibilityState = VisibilityState.VISIBLE
            internal set
        var bottomSheetVisibility: VisibilityState = VisibilityState.VISIBLE
            internal set

        var shape: Shapes = Shapes.CUBE
            internal set
        var model: ModelRenderable? = null
            internal set
    }
}

enum class VisibilityState {
    VISIBLE,
    TEMP_HIDDEN,
    HIDDEN
}

enum class Controller {
    NONE,
    MODEL_SELECTION,
    MODEL_TRANSFORMATION,
    CAMERA,
}