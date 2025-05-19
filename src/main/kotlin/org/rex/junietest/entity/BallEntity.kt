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
    var ballSpeed: Float = DEFAULT_VELOCITY,
    val scorePoint: ((Side) -> Unit)? = null
) : Entity(x, y, radius * 2, radius * 2) {
    // Normalized velocity vector (values between 0 and 1)
    var velocityX: Float = 0f
    var velocityY: Float = 0f

    init {
        // Set initial velocity
        setRandomVelocity()
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
        // Update position based on normalized velocity and ball speed
        x += velocityX * ballSpeed * deltaTime
        y += velocityY * ballSpeed * deltaTime

        var bounced = false

        // Check if ball hits left or right sides
        if (x < 0f) {
            // Call scorePoint lambda for right side scoring
            scorePoint?.invoke(Side.RIGHT)
            // Reset ball position to center
            centerInPanel()
            // Set a new random velocity
            setRandomVelocity()
        } else if (x > GamePanel.PANEL_WIDTH - width) {
            // Call scorePoint lambda for left side scoring
            scorePoint?.invoke(Side.LEFT)
            // Reset ball position to center
            centerInPanel()
            // Set a new random velocity
            setRandomVelocity()
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

        // Increase ball speed if the ball bounced
        if (bounced) {
            increaseVelocity()
        }
    }

    /**
     * Increases the ball's speed by VELOCITY_INCREASE amount
     * and ensures it doesn't exceed MAX_VELOCITY
     */
    private fun increaseVelocity() {
        // Calculate new ball speed
        val newBallSpeed = (ballSpeed + VELOCITY_INCREASE).coerceAtMost(MAX_VELOCITY)

        if (newBallSpeed != ballSpeed) {
            // Calculate current direction
            val currentDirection = Math.atan2(velocityY.toDouble(), velocityX.toDouble())

            // Update ball speed
            ballSpeed = newBallSpeed

            // Keep velocityX and velocityY as normalized direction vectors
            // We don't need to update them as they're already normalized
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
     * Sets a random velocity direction and ball speed
     * @param magnitude The desired ball speed
     */
    fun setRandomVelocity() {
        // Generate random direction
        val angle = Random.nextFloat() * 2 * Math.PI

        // Calculate normalized velocity components (values between 0 and 1)
        velocityX = Math.cos(angle).toFloat()
        velocityY = Math.sin(angle).toFloat()
    }
}
