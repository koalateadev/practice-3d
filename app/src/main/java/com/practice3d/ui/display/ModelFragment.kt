package com.practice3d.ui.display

import androidx.fragment.app.Fragment

class ModelFragment : Fragment() {

    override fun onResume() {
        super.onResume()
//        binding.scene.resume()
    }

    override fun onPause() {
        super.onPause()
//        binding.scene.pause()
    }

    //    fun loadScene() {
//        binding.scene.setTransparent(true)
//        binding.scene.renderer?.setClearColor(Color(1f, 1f, 1f, 1f))
//        binding.scene.setZOrderMediaOverlay(true)
//        binding.scene.renderer?.getEnvironment()?.indirectLight?.let {
//            it.intensity = 15_000f
//            binding.scene.renderer?.setIndirectLight(it)
//        }
//        binding.scene.renderer?.setMainLight(null)
//
//        val ts = TransformationSystem(
//            resources.displayMetrics,
//            FootprintSelectionVisualizer()
//        )
//
//        val transformNode = DragTransformableNode(ts)
//        transformNode.parent = binding.scene.scene
//        transformNode.worldPosition = Vector3(0f, 0f, -0.5f)
//        transformNode.select()
//
//        modelNode = Node()
//        modelNode?.parent = transformNode
//        binding.scene.scene.addOnPeekTouchListener { hitTestResult, motionEvent ->
//            try {
//                ts.onTouch(hitTestResult, motionEvent)
//            } catch (ex: Exception) {
//                ex.printStackTrace()
//            }
//        }
//    }
//
//    fun setModel(shape: Shapes) {
//        modelNode?.let { node ->
//            MaterialFactory.makeTransparentWithColor(this, Color(android.graphics.Color.WHITE))
//                .thenAccept {
//                    it.setFloat(MaterialFactory.MATERIAL_METALLIC, 0f)
//                    it.setFloat(MaterialFactory.MATERIAL_REFLECTANCE, 0f)
//                    it.setFloat(MaterialFactory.MATERIAL_ROUGHNESS, 1f)
//                    val model = when(shape) {
//                        Shapes.CYLINDER -> {
//                            CustomShapeFactory.makeCylinder(0.15f, 0.3f, Vector3.zero(), it)
//                        }
//                        Shapes.SPHERE -> {
//                            CustomShapeFactory.makeSphere(0.15f, Vector3.zero(), it)
//                        }
//                        Shapes.PYRAMID -> {
//                            CustomShapeFactory.makePyramid(Vector3(0.3f, 0.3f, 0.3f), Vector3.zero(), it)
//                        }
//                        Shapes.CONE -> {
//                            CustomShapeFactory.makeCone(0.15f, 0.3f, Vector3.zero(), it)
//                        }
//                        else -> {
//                            CustomShapeFactory.makeCube(Vector3(0.3f, 0.3f, 0.3f), Vector3.zero(), it)
//                        }
//                    }
//                    model?.isShadowCaster = true
//                    model?.isShadowReceiver = true
//                    node.renderable = model
//                }
//        }
//    }
}