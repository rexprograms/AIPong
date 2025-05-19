package org.rex.junietest.entity

import java.awt.Color
import java.awt.Graphics2D

/**
 * Paddle entity that renders a vertical bar with rounded corners
 */
class PaddleEntity(
    x: Float,
    y: Float,
    private val color: Color
) : Entity(x, y, 10, 80) {
    override fun render(g: Graphics2D) {
        g.color = color
        // Draw a rounded rectangle with arc width and height of 10 for rounded corners
        g.fillRoundRect(x.toInt(), y.toInt(), width, height, 10, 10)
    }

    override fun update(deltaTime: Float) {
        // Paddle movement will be handled by the game
    }
} 