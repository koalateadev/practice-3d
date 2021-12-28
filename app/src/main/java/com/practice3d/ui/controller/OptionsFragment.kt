package com.practice3d.ui.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.practice3d.R
import com.practice3d.databinding.FragmentOptionsBinding
import com.practice3d.extensions.collapse
import com.practice3d.extensions.expand
import com.practice3d.extensions.rotate
import com.practice3d.extensions.rotateDown
import com.practice3d.ui.MainViewModel

class OptionsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOptionsBinding.inflate(inflater)

        binding.shapeSelectionButton.setOnClickListener {
            viewModel.controlShapeSelection()
        }

        binding.cameraButton.setOnClickListener {
            viewModel.controlCamera()
        }

        binding.lockButton.setOnClickListener {
            viewModel.toggleLock()
        }

        binding.floatingActionButton.setOnClickListener {
            if (binding.container.visibility == View.VISIBLE) {
                it.rotate()
                binding.container.collapse()
            } else {
                it.rotateDown()
                binding.container.expand()
            }
        }

        viewModel.observableState.observe(this) {
            if (it.locked) {
                binding.lockButton.setImageResource(R.drawable.lock)
            } else {
                binding.lockButton.setImageResource(R.drawable.lock_open_outline)
            }
        }

        return binding.root
    }
}