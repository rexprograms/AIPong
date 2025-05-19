package org.rex.junietest.entity

import java.awt.Color
import java.awt.Graphics2D
import org.rex.junietest.renderer.GamePanel

/**
 * Net entity that renders a vertical line in the center of the screen
 */
class NetEntity : Entity(
    x = (GamePanel.PANEL_WIDTH / 2 - 2.5f), // Center the net with 5px width
    y = 0f,
    width = 5,
    height = GamePanel.PANEL_HEIGHT
) {
    override fun render(g: Graphics2D) {
        g.color = Color.WHITE
        g.fillRect(x.toInt(), y.toInt(), width, height)
    }

    override fun update(deltaTime: Float) {
        // Net doesn't need to update
    }
} 