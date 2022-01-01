package com.practice3d.ui.display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.ar.sceneform.HitTestResult
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.SceneView
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.ux.FootprintSelectionVisualizer
import com.google.ar.sceneform.ux.TransformationSystem
import com.practice3d.databinding.FragmentModelBinding
import com.practice3d.ui.DragTransformableNode
import com.practice3d.ui.MainViewModel

class ModelFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var binding: FragmentModelBinding? = null
    private var modelNode: Node? = null
    private lateinit var ts: TransformationSystem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModelBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ts = TransformationSystem(
            resources.displayMetrics,
            FootprintSelectionVisualizer()
        )

        binding?.scene?.let {
            loadScene(it)
        }
        viewModel.state.model.observe(this) {
            modelNode?.renderable = it
        }
    }

    override fun onResume() {
        super.onResume()

        binding?.scene?.resume()
    }

    override fun onPause() {
        super.onPause()

        binding?.scene?.pause()
    }

    fun loadScene(scene: SceneView) {
        scene.setTransparent(true)
        scene.renderer?.setClearColor(Color(1f, 1f, 1f, 0f))
        scene.setZOrderMediaOverlay(true)

        scene.renderer?.getEnvironment()?.indirectLight?.let {
            it.intensity = 10_000f
            scene.renderer?.setIndirectLight(it)
        }
//        scene.renderer?.setIndirectLight(null)
//        scene.renderer?.setMainLight(null)

        val transformNode = DragTransformableNode(ts)
        transformNode.parent = scene.scene
        transformNode.worldPosition = Vector3(0f, 0f, -0.5f)
        transformNode.select()
        viewModel.state.locked.observe(this) {
            val listener =
                Scene.OnPeekTouchListener { hitTestResult, motionEvent ->
                    try {
                        ts.onTouch(hitTestResult, motionEvent)
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
            binding?.scene?.scene?.addOnPeekTouchListener(listener)
            transformNode.setOnTapListener { hitTestResult, motionEvent -> }
            transformNode.setOnTouchListener { hitTestResult, motionEvent -> true }
            if (it) {
                ts.selectNode(null)
            } else {
                ts.selectNode(transformNode)
            }
        }

        modelNode = Node()
        modelNode?.parent = transformNode
    }
}