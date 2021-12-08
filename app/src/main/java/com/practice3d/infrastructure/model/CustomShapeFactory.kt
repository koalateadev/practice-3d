package com.practice3d.infrastructure.model

import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Material
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.RenderableDefinition
import com.google.ar.sceneform.rendering.Vertex
import java.util.*
import java.util.concurrent.ExecutionException
import kotlin.collections.ArrayList

object CustomShapeFactory {

    /**
     * Creates a [ModelRenderable] in the shape of a cube with the give specifications.
     *
     * @param size the size of the constructed cube
     * @param center the center of the constructed cube
     * @param material the material to use for rendering the cube
     * @return renderable representing a cube with the given parameters
     */
    fun makePyramid(size: Vector3, center: Vector3?, material: Material?): ModelRenderable? {
        val extents = size.scaled(0.5f)
        val p0 = Vector3.add(center, Vector3(-extents.x, -extents.y, extents.z))
        val p1 = Vector3.add(center, Vector3(extents.x, -extents.y, extents.z))
        val p2 = Vector3.add(center, Vector3(extents.x, -extents.y, -extents.z))
        val p3 = Vector3.add(center, Vector3(-extents.x, -extents.y, -extents.z))
        val p4 = Vector3.add(center, Vector3(0f, extents.y, 0f))
        val p5 = Vector3.add(center, Vector3(0f, extents.y, 0f))
        val p6 = Vector3.add(center, Vector3(0f, extents.y, 0f))
        val p7 = Vector3.add(center, Vector3(0f, extents.y, 0f))
        val up = Vector3.up()
        val down = Vector3.down()
        val front = Vector3.forward()
        val back = Vector3.back()
        val left = Vector3.left()
        val right = Vector3.right()
        val uv00 = Vertex.UvCoordinate(0.0f, 0.0f)
        val uv10 = Vertex.UvCoordinate(1.0f, 0.0f)
        val uv01 = Vertex.UvCoordinate(0.0f, 1.0f)
        val uv11 = Vertex.UvCoordinate(1.0f, 1.0f)
        val vertices = ArrayList(
            listOf(
                // Bottom
                Vertex.builder().setPosition(p0).setNormal(down).setUvCoordinate(uv01).build(),
                Vertex.builder().setPosition(p1).setNormal(down).setUvCoordinate(uv11).build(),
                Vertex.builder().setPosition(p2).setNormal(down).setUvCoordinate(uv10).build(),
                Vertex.builder().setPosition(p3).setNormal(down).setUvCoordinate(uv00).build(),
                // Left
                Vertex.builder().setPosition(p7).setNormal(left).setUvCoordinate(uv01).build(),
                Vertex.builder().setPosition(p4).setNormal(left).setUvCoordinate(uv11).build(),
                Vertex.builder().setPosition(p0).setNormal(left).setUvCoordinate(uv10).build(),
                Vertex.builder().setPosition(p3).setNormal(left).setUvCoordinate(uv00).build(),
                // Front
                Vertex.builder().setPosition(p4).setNormal(front).setUvCoordinate(uv01).build(),
                Vertex.builder().setPosition(p5).setNormal(front).setUvCoordinate(uv11).build(),
                Vertex.builder().setPosition(p1).setNormal(front).setUvCoordinate(uv10).build(),
                Vertex.builder().setPosition(p0).setNormal(front).setUvCoordinate(uv00).build(),
                // Back
                Vertex.builder().setPosition(p6).setNormal(back).setUvCoordinate(uv01).build(),
                Vertex.builder().setPosition(p7).setNormal(back).setUvCoordinate(uv11).build(),
                Vertex.builder().setPosition(p3).setNormal(back).setUvCoordinate(uv10).build(),
                Vertex.builder().setPosition(p2).setNormal(back).setUvCoordinate(uv00).build(),
                // Right
                Vertex.builder().setPosition(p5).setNormal(right).setUvCoordinate(uv01).build(),
                Vertex.builder().setPosition(p6).setNormal(right).setUvCoordinate(uv11).build(),
                Vertex.builder().setPosition(p2).setNormal(right).setUvCoordinate(uv10).build(),
                Vertex.builder().setPosition(p1).setNormal(right).setUvCoordinate(uv00).build(),
                // Top
                Vertex.builder().setPosition(p7).setNormal(up).setUvCoordinate(uv01).build(),
                Vertex.builder().setPosition(p6).setNormal(up).setUvCoordinate(uv11).build(),
                Vertex.builder().setPosition(p5).setNormal(up).setUvCoordinate(uv10).build(),
                Vertex.builder().setPosition(p4).setNormal(up).setUvCoordinate(uv00).build()
            )
        )
        val numSides = 6
        val verticesPerSide = 4
        val triangleIndices = ArrayList<Int>()
        for (i in 0 until numSides) {
            // First triangle for this side.
            triangleIndices.add(3 + verticesPerSide * i)
            triangleIndices.add(1 + verticesPerSide * i)
            triangleIndices.add(0 + verticesPerSide * i)

            // Second triangle for this side.
            triangleIndices.add(3 + verticesPerSide * i)
            triangleIndices.add(2 + verticesPerSide * i)
            triangleIndices.add(1 + verticesPerSide * i)
        }
        val submesh =
            RenderableDefinition.Submesh.builder()
                .setTriangleIndices(triangleIndices)
                .setMaterial(material)
                .build()
        val renderableDefinition = RenderableDefinition.builder()
            .setVertices(vertices)
            .setSubmeshes(Arrays.asList(submesh))
            .build()
        val future = ModelRenderable.builder()
            .setSource(renderableDefinition)
            .build()
        return future.get()
    }

    /**
     * Creates a [ModelRenderable] in the shape of a cylinder with the give specifications.
     *
     * @param radius the radius of the constructed cylinder
     * @param height the height of the constructed cylinder
     * @param center the center of the constructed cylinder
     * @param material the material to use for rendering the cylinder
     * @return renderable representing a cylinder with the given parameters
     */
    fun makeCone(
        radius: Float, height: Float, center: Vector3?, material: Material?
    ): ModelRenderable? {
        val numberOfSides = 100
        val halfHeight = height / 2
        val thetaIncrement = (2 * Math.PI).toFloat() / numberOfSides
        var theta = 0f
        val uStep = 1.0.toFloat() / numberOfSides
        val vertices = ArrayList<Vertex>((numberOfSides + 1) * 4)
        val lowerCapVertices = ArrayList<Vertex>(numberOfSides + 1)
        val upperCapVertices = ArrayList<Vertex>(numberOfSides + 1)
        val upperEdgeVertices = ArrayList<Vertex>(numberOfSides + 1)

        // Generate vertices along the sides of the cylinder.
        for (side in 0..numberOfSides) {
            val cosTheta = Math.cos(theta.toDouble()).toFloat()
            val sinTheta = Math.sin(theta.toDouble()).toFloat()

            // Calculate edge vertices along bottom of cylinder
            var lowerPosition = Vector3(radius * cosTheta, -halfHeight, radius * sinTheta)
            var normal = Vector3(lowerPosition.x, 0f, lowerPosition.z).normalized()
            lowerPosition = Vector3.add(lowerPosition, center)
            var uvCoordinate = Vertex.UvCoordinate(uStep * side, 0f)
            var vertex = Vertex.builder()
                .setPosition(lowerPosition)
                .setNormal(normal)
                .setUvCoordinate(uvCoordinate)
                .build()
            vertices.add(vertex)

            // Create a copy of lower vertex with bottom-facing normals for cap.
            vertex = Vertex.builder()
                .setPosition(lowerPosition)
                .setNormal(Vector3.down())
                .setUvCoordinate(Vertex.UvCoordinate((cosTheta + 1f) / 2, (sinTheta + 1f) / 2))
                .build()
            lowerCapVertices.add(vertex)

            // Calculate edge vertices along top of cylinder
            var upperPosition = Vector3(0f, halfHeight, 0f)
            normal = Vector3(upperPosition.x, 0f, upperPosition.z).normalized()
            upperPosition = Vector3.add(upperPosition, center)
            uvCoordinate = Vertex.UvCoordinate(uStep * side, 1f)
            vertex =
                Vertex.builder()
                    .setPosition(Vector3.add(center, Vector3(0f, halfHeight, 0f)))
                    .setNormal(Vector3.up())
                    .setUvCoordinate(Vertex.UvCoordinate(.5f, .5f))
                    .build()
            upperEdgeVertices.add(vertex)

            // Create a copy of upper vertex with up-facing normals for cap.
            vertex =
                Vertex.builder()
                    .setPosition(Vector3.add(center, Vector3(0f, halfHeight, 0f)))
                    .setNormal(Vector3.up())
                    .setUvCoordinate(Vertex.UvCoordinate(.5f, .5f))
                    .build()
            upperCapVertices.add(vertex)
            theta += thetaIncrement
        }
        vertices.addAll(upperEdgeVertices)

        // Generate vertices for the centers of the caps of the cylinder.
        val lowerCenterIndex = vertices.size
        vertices.add(
            Vertex.builder()
                .setPosition(Vector3.add(center, Vector3(0f, -halfHeight, 0f)))
                .setNormal(Vector3.down())
                .setUvCoordinate(Vertex.UvCoordinate(.5f, .5f))
                .build()
        )
        vertices.addAll(lowerCapVertices)
        val upperCenterIndex = vertices.size
        vertices.add(
            Vertex.builder()
                .setPosition(Vector3.add(center, Vector3(0f, halfHeight, 0f)))
                .setNormal(Vector3.up())
                .setUvCoordinate(Vertex.UvCoordinate(.5f, .5f))
                .build()
        )
        vertices.addAll(upperCapVertices)
        val triangleIndices = ArrayList<Int>()

        // Create triangles for each side
        for (side in 0 until numberOfSides) {
            val bottomRight = side + 1
            val topLeft = side + numberOfSides + 1
            val topRight = side + numberOfSides + 2

            // First triangle of side.
            triangleIndices.add(side)
            triangleIndices.add(topRight)
            triangleIndices.add(bottomRight)

            // Second triangle of side.
            triangleIndices.add(side)
            triangleIndices.add(topLeft)
            triangleIndices.add(topRight)

            // Add bottom cap triangle.
            triangleIndices.add(lowerCenterIndex)
            triangleIndices.add(lowerCenterIndex + side + 1)
            triangleIndices.add(lowerCenterIndex + side + 2)

            // Add top cap triangle.
            triangleIndices.add(upperCenterIndex)
            triangleIndices.add(upperCenterIndex + side + 2)
            triangleIndices.add(upperCenterIndex + side + 1)
        }
        val submesh =
            RenderableDefinition.Submesh.builder().setTriangleIndices(triangleIndices).setMaterial(material).build()
        val renderableDefinition = RenderableDefinition.builder()
            .setVertices(vertices)
            .setSubmeshes(Arrays.asList(submesh))
            .build()
        val future = ModelRenderable.builder().setSource(renderableDefinition).build()
        val result: ModelRenderable = try {
            future.get()
        } catch (ex: ExecutionException) {
            throw AssertionError("Error creating renderable.", ex)
        } catch (ex: InterruptedException) {
            throw AssertionError("Error creating renderable.", ex)
        } ?: throw AssertionError("Error creating renderable.")
        return result
    }
}