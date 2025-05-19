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
    var height: Int
) {
    // Collision handler function, can be set by client code
    var onCollision: ((Entity) -> Unit)? = null

    // Get the collision bounding box
    val boundingBox: Rectangle
        get() = Rectangle(x.toInt(), y.toInt(), width, height)

    // Abstract render function to be implemented by subclasses
    abstract fun render(g: Graphics2D)

    // Abstract update function to be implemented by subclasses
    abstract fun update(deltaTime: Float)

    // Check if this entity collides with another entity
    fun collidesWith(other: Entity): Boolean {
        return boundingBox.intersects(other.boundingBox)
    }

    // Handle collision with another entity
    fun handleCollision(other: Entity) {
        onCollision?.invoke(other)
    }
}
