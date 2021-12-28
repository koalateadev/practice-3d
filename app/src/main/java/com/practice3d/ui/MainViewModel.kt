package com.practice3d.ui

import android.app.Application
import android.graphics.Color.WHITE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ModelRenderable
import com.practice3d.infrastructure.model.CustomShapeFactory
import com.practice3d.infrastructure.model.Shapes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    val state = ViewState()

    init {
        setModel(state.shape.value!!, state.color.value!!)
    }

    fun toggleLock() {
        state._locked.postValue(state.locked.value?.not())
    }

    fun controlCamera() {
        state._controller.postValue(Controller.CAMERA)
    }

    fun controlShapeSelection() {
        state._controller.postValue(Controller.MODEL_SELECTION)
    }

    fun controlShapeTransformation() {
        state._controller.postValue(Controller.MODEL_TRANSFORMATION)
    }

    fun setShape(shape: Shapes) {
        state._shape.postValue(shape)
        setModel(shape, state.color.value!!)
    }

    fun setModelTransparency(alpha: Float) {
        val prevColor = state.color.value!!
        val newColor = Color(alpha, prevColor.r, prevColor.g, prevColor.b)
        state._color.postValue(newColor)
        setModel(state.shape.value!!, newColor)
    }

    private fun setModel(shape: Shapes, color: Color) {
        MaterialFactory.makeTransparentWithColor(getApplication(),
            color
        )
            .thenAccept {
//                it.setFloat(MaterialFactory.MATERIAL_METALLIC, 0f)
//                it.setFloat(MaterialFactory.MATERIAL_REFLECTANCE, 0f)
//                it.setFloat(MaterialFactory.MATERIAL_ROUGHNESS, 1f)
                val model = when(shape) {
                    Shapes.CYLINDER -> {
                        CustomShapeFactory.makeCylinder(0.15f, 0.3f, Vector3.zero(), it)
                    }
                    Shapes.SPHERE -> {
                        CustomShapeFactory.makeSphere(0.15f, Vector3.zero(), it)
                    }
                    Shapes.PYRAMID -> {
                        CustomShapeFactory.makePyramid(Vector3(0.3f, 0.3f, 0.3f), Vector3.zero(), it)
                    }
                    Shapes.CONE -> {
                        CustomShapeFactory.makeCone(0.15f, 0.3f, Vector3.zero(), it)
                    }
                    else -> {
                        CustomShapeFactory.makeCube(Vector3(0.3f, 0.3f, 0.3f), Vector3.zero(), it)
                    }
                }
                state._model.postValue(model)
            }
    }

    inner class ViewState {
        internal val _controller = MutableLiveData(Controller.NONE)
        val controller: LiveData<Controller> = _controller

        internal val _locked = MutableLiveData(false)
        val locked: LiveData<Boolean> = _locked

        internal val _shape = MutableLiveData(Shapes.CUBE)
        val shape: LiveData<Shapes> = _shape
        internal val _color = MutableLiveData(Color(WHITE))
        val color: LiveData<Color> = _color
        internal val _model: MutableLiveData<ModelRenderable> = MutableLiveData()
        val model: LiveData<ModelRenderable> = _model

        var optionsVisibility: VisibilityState = VisibilityState.VISIBLE
            internal set
        var bottomSheetVisibility: VisibilityState = VisibilityState.VISIBLE
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