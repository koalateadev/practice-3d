package com.practice3d.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import com.practice3d.R
import com.practice3d.databinding.ActivityMainBinding
import com.practice3d.ui.controller.CameraControllerFragment
import com.practice3d.ui.controller.ModelSelectionFragment
import com.practice3d.ui.controller.ModelTransformationFragment
import com.practice3d.ui.controller.OptionsFragment
import com.practice3d.ui.display.CameraFragment
import com.practice3d.ui.display.ModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private val controllerFragmentTag = "CONTROLLER_FRAGMENT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

//        supportFragmentManager.beginTransaction()
//            .add(R.id.fragmentContainer, CameraFragment())
//            .commit()
//
//        supportFragmentManager.beginTransaction()
//            .add(R.id.fragmentContainer, ModelFragment())
//            .commit()

        supportFragmentManager.commitNow {
            add(R.id.fragmentContainer, OptionsFragment())
        }

        viewModel.observableState.observe(this) {
            supportFragmentManager.findFragmentByTag(controllerFragmentTag)?.let {
                supportFragmentManager
                    .beginTransaction()
                    .remove(it)
                    .commitNow()
            }

            Log.d("Controller Changed", it.controller.name)
            when (it.controller) {
                Controller.CAMERA -> supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, CameraControllerFragment(), controllerFragmentTag)
                    .commitNow()
                Controller.MODEL_SELECTION -> supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, ModelSelectionFragment(), controllerFragmentTag)
                    .commitNow()
                Controller.MODEL_TRANSFORMATION -> supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, ModelTransformationFragment(), controllerFragmentTag)
                    .commitNow()
                else -> {}
            }
        }

        setContentView(binding.root)
    }
}