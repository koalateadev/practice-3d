package com.practice3d.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.practice3d.R
import com.practice3d.databinding.ActivityMainBinding
import com.practice3d.ui.control.CameraControllerFragment
import com.practice3d.ui.control.ModelSelectionFragment
import com.practice3d.ui.control.ModelTransformationFragment
import com.practice3d.ui.control.OptionsFragment
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

        supportFragmentManager.commitNow {
            add(R.id.fragmentContainer, CameraFragment())
        }

        supportFragmentManager.commitNow {
            add(R.id.fragmentContainer, ModelFragment())
        }

        supportFragmentManager.commitNow {
            add(R.id.fragmentContainer, OptionsFragment())
        }

        viewModel.state.controller.observe(this) {
            supportFragmentManager.findFragmentByTag(controllerFragmentTag)?.let {
                supportFragmentManager.commitNow {
                    remove(it)
                }
            }

            when (it) {
                Controller.CAMERA -> supportFragmentManager.commitNow {
                    add(R.id.fragmentContainer, CameraControllerFragment(), controllerFragmentTag)
                }
                Controller.MODEL_SELECTION -> supportFragmentManager.commitNow {
                    add(R.id.fragmentContainer, ModelSelectionFragment(), controllerFragmentTag)
                }
                Controller.MODEL_TRANSFORMATION -> supportFragmentManager.commitNow {
                    add(R.id.fragmentContainer, ModelTransformationFragment(), controllerFragmentTag)
                }
                else -> {}
            }
        }

        setContentView(binding.root)
    }
}