package com.practice3d.ui

import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.ux.*

class DragTransformableNode(transformationSystem: TransformationSystem) :
    TransformableNode(transformationSystem) {

    private val dragRotationController = DragRotationController(
        this,
        transformationSystem.dragRecognizer
    )

    init {
        translationController.isEnabled = false
        removeTransformationController(translationController)
        removeTransformationController(rotationController)
        addTransformationController(dragRotationController)
    }
}

class DragRotationController(
    transformableNode: BaseTransformableNode,
    gestureRecognizer: DragGestureRecognizer
) :
    BaseTransformationController<DragGesture>(transformableNode, gestureRecognizer) {

    // Rate that the node rotates in degrees per degree of twisting.
    private var rotationRateDegrees = 0.5f

    public override fun canStartTransformation(gesture: DragGesture): Boolean {
        return transformableNode.isSelected
    }

    public override fun onContinueTransformation(gesture: DragGesture) {
        var localRotation = transformableNode.localRotation

        val rotationAmountX = gesture.delta.x * rotationRateDegrees
        val up = transformableNode.worldToLocalDirection(Vector3.up())
        val rotationDeltaX = Quaternion.axisAngle(up, rotationAmountX)

        val rotationAmountY = gesture.delta.y * rotationRateDegrees
        val right = transformableNode.worldToLocalDirection(Vector3.right())
        val rotationDeltaY = Quaternion.axisAngle(right, rotationAmountY)

        val change = Quaternion.multiply(rotationDeltaX, rotationDeltaY)
        localRotation = Quaternion.multiply(localRotation, change)

        transformableNode.localRotation = localRotation
    }

    public override fun onEndTransformation(gesture: DragGesture) {}
}