package org.rex.junietest.entity

import java.awt.Color
import java.awt.Graphics2D
import org.rex.junietest.renderer.GamePanel
import kotlin.random.Random

/**
 * Ball entity that extends the base Entity class.
 */
class BallEntity(x: Int, y: Int, val radius: Int, val color: Color) : Entity(x, y, radius * 2, radius * 2) {
    // Velocity vector (pixels per second)
    var velocityX: Float = 0f
    var velocityY: Float = 0f

    init {
        // Set a random initial velocity
        setRandomVelocity()
    }

    override fun render(g: Graphics2D) {
        g.color = color
        g.fillOval(x, y, width, height)
    }

    /**
     * Update the ball's position based on its velocity
     */
    override fun update(deltaTime: Float) {
        // Update position based on velocity and delta time
        x += (velocityX * deltaTime).toInt()
        y += (velocityY * deltaTime).toInt()

        // Simple boundary checking to keep the ball within the panel
        if (x < 0) {
            x = 0
            velocityX = -velocityX // Bounce off left wall
        } else if (x > GamePanel.PANEL_WIDTH - width) {
            x = GamePanel.PANEL_WIDTH - width
            velocityX = -velocityX // Bounce off right wall
        }

        if (y < 0) {
            y = 0
            velocityY = -velocityY // Bounce off top wall
        } else if (y > GamePanel.PANEL_HEIGHT - height) {
            y = GamePanel.PANEL_HEIGHT - height
            velocityY = -velocityY // Bounce off bottom wall
        }
    }

    /**
     * Centers the ball in the game panel
     */
    fun centerInPanel() {
        x = GamePanel.PANEL_WIDTH / 2 - radius
        y = GamePanel.PANEL_HEIGHT / 2 - radius
    }

    /**
     * Sets a random velocity for the ball
     * Not too fast, but enough to be visible
     */
    fun setRandomVelocity() {
        // Random velocity between -150 and 150 pixels per second
        velocityX = Random.nextFloat() * 300f - 150f
        velocityY = Random.nextFloat() * 300f - 150f
    }
}
