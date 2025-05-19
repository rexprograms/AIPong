package org.rex.junietest.entity

import java.awt.Color
import java.awt.Graphics2D

/**
 * Ball entity that extends the base Entity class.
 */
class BallEntity(x: Int, y: Int, val radius: Int, val color: Color) : Entity(x, y, radius * 2, radius * 2) {
    override fun render(g: Graphics2D) {
        g.color = color
        g.fillOval(x, y, width, height)
    }

    // Additional ball-specific methods can be added here
}