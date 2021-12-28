package com.practice3d.ui.display

import androidx.fragment.app.Fragment

class CameraFragment : Fragment() {
    //
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String?>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 1) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startCamera()
//            }
//        }
//    }
//
//    private fun startCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//
//        cameraProviderFuture.addListener({
//            val cameraProvider = cameraProviderFuture.get()
//            bindPreview(cameraProvider)
//        }, ContextCompat.getMainExecutor(this))
//    }
//
//    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
//        val preview: Preview = Preview.Builder()
//            .build()
//
//        val cameraSelector: CameraSelector = CameraSelector.Builder()
//            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
//            .build()
//
//        preview.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
//        cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, preview)
//    }
}