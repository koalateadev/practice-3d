package com.practice3d.ui.display

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraProvider
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.practice3d.databinding.FragmentCameraBinding
import com.practice3d.ui.Controller
import com.practice3d.ui.MainViewModel

class CameraFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var binding: FragmentCameraBinding? = null
    private var cameraProvider: ProcessCameraProvider? = null

    val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            startCamera()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraBinding.inflate(inflater)

        viewModel.state.controller.observe(this) {
            if (it == Controller.CAMERA) {
                binding?.cameraPreview?.visibility = View.VISIBLE
                launcher.launch(Manifest.permission.CAMERA)
            } else {
                binding?.cameraPreview?.visibility = View.GONE

                cameraProvider?.unbindAll()
                cameraProvider = null
            }
        }

        return binding?.root
    }

    private fun startCamera() {
        context?.let {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(it)

            cameraProviderFuture.addListener({
                cameraProvider = cameraProviderFuture.get()
                cameraProvider?.unbindAll()
                bindPreview()
            }, ContextCompat.getMainExecutor(it))
        }
    }

    private fun bindPreview() {
        binding?.let {
            val preview: Preview = Preview.Builder()
                .build()

            val cameraSelector: CameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

            preview.setSurfaceProvider(it.cameraPreview.surfaceProvider)
            cameraProvider?.bindToLifecycle(this, cameraSelector, preview)
        }
    }
}