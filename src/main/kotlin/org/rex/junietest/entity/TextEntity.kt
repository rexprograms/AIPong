package org.rex.junietest.entity

import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D

/**
 * Text entity that can display and update text
 */
class TextEntity(
    x: Float,
    y: Float,
    private val fontSize: Int = 24,
    private val color: Color = Color.WHITE
) : Entity(x, y, 0, 0) {
    private var text: String = ""

    override fun render(g: Graphics2D) {
        if (text.isEmpty()) return

        g.color = color
        g.font = Font("Arial", Font.BOLD, fontSize)
        
        // Get the text dimensions to center it
        val metrics = g.fontMetrics
        val textWidth = metrics.stringWidth(text)
        val textHeight = metrics.height
        
        // Update the entity's width and height based on text dimensions
        width = textWidth
        height = textHeight
        
        // Draw the text
        g.drawString(text, x.toInt(), y.toInt() + metrics.ascent)
    }

    override fun update(deltaTime: Float) {
        // Text update logic will be handled by the game
    }

    /**
     * Updates the text to be displayed
     */
    fun updateText(newText: String) {
        text = newText
    }
} 