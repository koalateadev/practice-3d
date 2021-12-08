package com.practice3d.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import androidx.constraintlayout.helper.widget.Carousel
import com.google.android.filament.IndirectLight
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.SceneView
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.Light
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ShapeFactory
import com.google.ar.sceneform.ux.*
import com.gorisse.thomas.sceneform.light.build
import com.practice3d.R
import com.practice3d.databinding.ActivityMainBinding
import com.practice3d.infrastructure.model.CustomShapeFactory.makeCone
import com.practice3d.infrastructure.model.CustomShapeFactory.makePyramid
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var sceneView: SceneView? = null
    private var modelNode: Node? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.carousel.setAdapter(object : Carousel.Adapter {
            private val ids = listOf(
                R.drawable.cylinder,
                R.drawable.sphere,
                R.drawable.cube_outline,
                R.drawable.cone,
                R.drawable.pyramid
            )

            override fun count() = ids.size

            override fun populate(view: View, index: Int) {
                (view as ImageView).setImageResource(ids[index])
            }

            override fun onNewItem(index: Int) {
                setModel(ids[(index + 2) % ids.size])
            }
        })

        binding.floatingActionButton.setOnClickListener {
            if (binding.container.visibility == View.VISIBLE) {
                rotateDown(it)
                collapse(binding.container)
            } else {
                rotate(it)
                expand(binding.container)
            }
        }

        binding.shapeSelectionButton.setOnClickListener {
            binding.shapeSelectionButton.isSelected = !binding.shapeSelectionButton.isSelected
            if (binding.shapeSelectionButton.isSelected) {
                collapse(binding.bottomSheet)
            } else {
                expand(binding.bottomSheet)
            }
        }

        binding.lockButton.setOnClickListener {
            if (it.tag == "unlocked") {
                (it as ImageButton).setImageResource(R.drawable.lock)
                it.tag = "locked"
            } else {
                (it as ImageButton).setImageResource(R.drawable.lock_open_outline)
                it.tag = "unlocked"
            }
        }

        sceneView = binding.scene
        loadScene()
        setModel(R.drawable.cube_outline)

        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        sceneView?.resume()
    }

    override fun onPause() {
        super.onPause()
        sceneView?.pause()
    }

    private fun rotate(v: View) {
        v.animate()
            .rotation(180f)
            .setDuration(400)
            .start()
    }

    private fun rotateDown(v: View) {
        v.animate()
            .rotation(0f)
            .setDuration(400)
            .start()
    }

    private fun expand(v: View) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val targetHeight = v.measuredHeight

        v.layoutParams.height = 0
        v.visibility = View.VISIBLE
        v.clipToOutline = true
        v.animate()
            .setUpdateListener {
                v.layoutParams.height = (targetHeight * it.animatedFraction).toInt()
                v.requestLayout()
            }
            .setDuration(targetHeight.toLong())
            .setInterpolator(AccelerateInterpolator())
            .start()
    }

    private fun collapse(v: View) {
        val initialHeight = v.layoutParams.height

        v.animate()
            .setUpdateListener {
                if (it.animatedFraction == 1f) v.visibility = View.GONE

                v.layoutParams.height = (initialHeight * (1 - it.animatedFraction)).toInt()
                v.requestLayout()
            }
            .setDuration((v.measuredHeight).toLong())
            .start()
    }

    fun loadScene() {
        sceneView?.setTransparent(true)
        sceneView?.renderer?.setClearColor(Color(1f, 1f, 1f, 0f))
        sceneView?.renderer?.getEnvironment()?.indirectLight?.let {
            it.intensity = 15_000f
            sceneView?.renderer?.setIndirectLight(it)
        }
        sceneView?.renderer?.setMainLight(null)

        val ts = TransformationSystem(
            resources.displayMetrics,
            FootprintSelectionVisualizer()
        )

        val transformNode = DragTransformableNode(ts)
        transformNode.parent = sceneView?.scene
        transformNode.worldPosition = Vector3(0f, 0f, -0.5f)
        transformNode.select()

        modelNode = Node()
        modelNode?.parent = transformNode
        sceneView?.scene?.addOnPeekTouchListener { hitTestResult, motionEvent ->

            binding.scene.isSelected = !binding.scene.isSelected
            if (binding.scene.isSelected) {
                collapse(binding.bottomSheet)
                collapse(binding.container)
            } else {
                expand(binding.bottomSheet)
                expand(binding.container)
            }

            try {
                ts.onTouch(hitTestResult, motionEvent)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    fun setModel(id: Int) {
        modelNode?.let { node ->
            MaterialFactory.makeTransparentWithColor(this, Color(android.graphics.Color.WHITE))
                .thenAccept {
                    it.setFloat(MaterialFactory.MATERIAL_METALLIC, 0f)
                    it.setFloat(MaterialFactory.MATERIAL_REFLECTANCE, 0f)
                    it.setFloat(MaterialFactory.MATERIAL_ROUGHNESS, 1f)
                    val model = when(id) {
                        R.drawable.cylinder -> {
                            ShapeFactory.makeCylinder(0.15f, 0.3f, Vector3.zero(), it)
                        }
                        R.drawable.sphere -> {
                            ShapeFactory.makeSphere(0.15f, Vector3.zero(), it)
                        }
                        R.drawable.pyramid -> {
                            makePyramid(Vector3(0.3f, 0.3f, 0.3f), Vector3.zero(), it)
                        }
                        R.drawable.cone -> {
                            makeCone(0.15f, 0.3f, Vector3.zero(), it)
                        }
                        else -> {
                            ShapeFactory.makeCube(Vector3(0.3f, 0.3f, 0.3f), Vector3.zero(), it)
                        }
                    }
                    model?.isShadowCaster = true
                    model?.isShadowReceiver = true
                    node.renderable = model
                }
        }
    }

    inner class DragTransformableNode(transformationSystem: TransformationSystem) :
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

    inner class DragRotationController(
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
}