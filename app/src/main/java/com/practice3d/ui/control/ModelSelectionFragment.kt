package com.practice3d.ui.control

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.helper.widget.Carousel
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.practice3d.databinding.FragmentModelSelectionBinding
import com.practice3d.infrastructure.model.Shapes
import com.practice3d.ui.MainViewModel

class ModelSelectionFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var binding: FragmentModelSelectionBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModelSelectionBinding.inflate(inflater)

        binding?.carousel?.setAdapter(object : Carousel.Adapter {

            override fun count() = Shapes.values().size

            override fun populate(view: View, index: Int) {
                Log.d("Index", index.toString())
                (view as ImageView).setImageResource(Shapes.values()[index].drawableId)
            }

            override fun onNewItem(index: Int) {
                Log.d("New Index", index.toString())
                viewModel.setShape(Shapes.values()[(index + 2) % Shapes.values().size])
            }
        })

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentIndex = Shapes.values().indexOf(viewModel.state.shape.value)
        if (currentIndex != -1) {
            val shapeIndex = (currentIndex + (Shapes.values().size - 2)) % Shapes.values().size
            binding?.carousel?.jumpToIndex(shapeIndex)
        }
    }
}