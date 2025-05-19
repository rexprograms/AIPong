package org.rex.junietest.entity

import java.awt.Graphics2D
import java.awt.Rectangle

/**
 * Base class for all game entities.
 * Provides position, rendering, and collision detection capabilities.
 */
abstract class Entity(
    var x: Float,
    var y: Float,
    var width: Int,
    var height: Int,
    var boundingBox: Rectangle = Rectangle(0, 0, width, height)
) {
    // Collision handler function, can be set by client code
    var onCollision: ((Entity) -> Unit)? = null

    // Abstract render function to be implemented by subclasses
    abstract fun render(g: Graphics2D)

    // Abstract update function to be implemented by subclasses
    abstract fun update(deltaTime: Float)

    // Render the bounding box if enabled
    fun renderBoundingBox(g: Graphics2D) {
        g.draw(Rectangle(
            x.toInt() + boundingBox.x,
            y.toInt() + boundingBox.y,
            boundingBox.width,
            boundingBox.height
        ))
    }

    // Check if this entity collides with another entity
    fun collidesWith(other: Entity): Boolean {
        // Create temporary rectangles with absolute positions
        val thisBox = Rectangle(
            x.toInt() + boundingBox.x,
            y.toInt() + boundingBox.y,
            boundingBox.width,
            boundingBox.height
        )
        val otherBox = Rectangle(
            other.x.toInt() + other.boundingBox.x,
            other.y.toInt() + other.boundingBox.y,
            other.boundingBox.width,
            other.boundingBox.height
        )
        return thisBox.intersects(otherBox)
    }

    // Handle collision with another entity
    fun handleCollision(other: Entity) {
        onCollision?.invoke(other)
    }
}
