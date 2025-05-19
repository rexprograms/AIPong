package org.rex.junietest.entity

import java.awt.Color
import java.awt.Graphics2D
import org.rex.junietest.renderer.GamePanel
import kotlin.random.Random
import kotlin.math.sqrt

/**
 * Ball entity that extends the base Entity class.
 */
class BallEntity(
    x: Float, 
    y: Float, 
    val radius: Int, 
    val color: Color,
    initialVelocity: Float = DEFAULT_VELOCITY
) : Entity(x, y, radius * 2, radius * 2) {
    // Velocity vector (pixels per second)
    var velocityX: Float = 0f
    var velocityY: Float = 0f

    // Current velocity magnitude
    var velocity: Float = initialVelocity
        private set

    init {
        // Set initial velocity
        setRandomVelocity(initialVelocity)
    }

    companion object {
        const val MAX_VELOCITY = 100f
        const val DEFAULT_VELOCITY = 400f
        const val VELOCITY_INCREASE = 5f
    }

    override fun render(g: Graphics2D) {
        g.color = color
        g.fillOval(x.toInt(), y.toInt(), width, height)
    }

    /**
     * Update the ball's position based on its velocity
     */
    override fun update(deltaTime: Float) {
        // Update position based on velocity and delta time
        x += velocityX * deltaTime
        y += velocityY * deltaTime

        var bounced = false

        // Simple boundary checking to keep the ball within the panel
        if (x < 0f) {
            x = 0f
            velocityX = -velocityX // Bounce off left wall
            bounced = true
        } else if (x > GamePanel.PANEL_WIDTH - width) {
            x = (GamePanel.PANEL_WIDTH - width).toFloat()
            velocityX = -velocityX // Bounce off right wall
            bounced = true
        }

        if (y < 0f) {
            y = 0f
            velocityY = -velocityY // Bounce off top wall
            bounced = true
        } else if (y > GamePanel.PANEL_HEIGHT - height) {
            y = (GamePanel.PANEL_HEIGHT - height).toFloat()
            velocityY = -velocityY // Bounce off bottom wall
            bounced = true
        }

        // Increase velocity if the ball bounced
        if (bounced) {
            increaseVelocity()
        }
    }

    /**
     * Increases the ball's velocity by VELOCITY_INCREASE amount
     * and ensures it doesn't exceed MAX_VELOCITY
     */
    private fun increaseVelocity() {
        // Calculate new velocity
        val newVelocity = (velocity + VELOCITY_INCREASE).coerceAtMost(MAX_VELOCITY)

        if (newVelocity != velocity) {
            // Calculate current direction
            val currentDirection = Math.atan2(velocityY.toDouble(), velocityX.toDouble())

            // Update velocity
            velocity = newVelocity

            // Update velocity components while maintaining direction
            velocityX = (velocity * Math.cos(currentDirection)).toFloat()
            velocityY = (velocity * Math.sin(currentDirection)).toFloat()
        }
    }

    /**
     * Centers the ball in the game panel
     */
    fun centerInPanel() {
        x = (GamePanel.PANEL_WIDTH / 2 - radius).toFloat()
        y = (GamePanel.PANEL_HEIGHT / 2 - radius).toFloat()
    }

    /**
     * Sets a random velocity for the ball with the specified magnitude
     * @param magnitude The desired velocity magnitude
     */
    fun setRandomVelocity(magnitude: Float = DEFAULT_VELOCITY) {
        // Set velocity to the specified magnitude
        velocity = magnitude.coerceAtMost(MAX_VELOCITY)

        // Generate random direction
        val angle = Random.nextFloat() * 2 * Math.PI

        // Calculate velocity components
        velocityX = (velocity * Math.cos(angle)).toFloat()
        velocityY = (velocity * Math.sin(angle)).toFloat()
    }
}
