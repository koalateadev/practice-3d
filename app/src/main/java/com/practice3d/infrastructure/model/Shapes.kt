package com.practice3d.infrastructure.model

import com.practice3d.R

enum class Shapes(val drawableId: Int) {
    CYLINDER(R.drawable.cylinder),
    SPHERE(R.drawable.sphere),
    CUBE(R.drawable.cube_outline),
    CONE(R.drawable.cone),
    PYRAMID(R.drawable.pyramid)
}