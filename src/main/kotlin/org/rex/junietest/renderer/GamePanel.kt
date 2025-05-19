package org.rex.junietest.renderer

import org.rex.junietest.entity.Entity
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.Toolkit
import javax.swing.JPanel
import java.lang.Thread.sleep

class GamePanel : JPanel() {
        // List to track all registered entities
        private val entities = mutableListOf<Entity>()

        // Rendering state
        private var rendering = false
        private var renderThread: Thread? = null
        private var currentFPS = 0 // Store the current FPS for rendering
        var displayBoundingBoxes = true // Flag to control bounding box display

        // Rendering loop constants
        private val maxFPS = 500
        private val minFrameTime = 1000 / maxFPS // in milliseconds

        init {
            preferredSize = Dimension(PANEL_WIDTH, PANEL_HEIGHT)
            background = Color.BLACK
            // Set double buffering for smoother rendering
            isDoubleBuffered = true
        }

        companion object {
            // Static width and height variables
            const val PANEL_WIDTH = 800
            const val PANEL_HEIGHT = 600
        }

        /**
         * Register an entity to be rendered by this panel
         */
        fun registerEntity(entity: Entity) {
            synchronized(entities) {
                entities.add(entity)
            }
        }

        /**
         * Unregister an entity from this panel
         */
        fun unregisterEntity(entity: Entity) {
            synchronized(entities) {
                entities.remove(entity)
            }
        }

        /**
         * Starts the independent rendering loop
         */
        fun startRendering() {
            if (rendering) return

            rendering = true

            renderThread = Thread {
                var lastRenderTime = System.currentTimeMillis()
                var frameCount = 0
                var lastFpsTime = lastRenderTime

                while (rendering) {
                    val currentTime = System.currentTimeMillis()
                    val elapsedTime = currentTime - lastRenderTime

                    if (elapsedTime >= minFrameTime) {
                        // Repaint the panel
                        repaint()

                        // Update FPS counter
                        frameCount++
                        if (currentTime - lastFpsTime >= 1000) {
                            // Store the current FPS for rendering
                            currentFPS = frameCount
                            frameCount = 0
                            lastFpsTime = currentTime
                        }

                        lastRenderTime = currentTime

                        // Sleep to maintain the max frame rate
                        val sleepTime = minFrameTime - (System.currentTimeMillis() - currentTime)
                        if (sleepTime > 0) {
                            try {
                                sleep(sleepTime)
                            } catch (e: InterruptedException) {
                                e.printStackTrace()
                            }
                        }
                    } else {
                        // Yield to other threads if we're ahead of schedule
                        Thread.yield()
                    }
                }
            }.apply {
                name = "RenderLoop"
                isDaemon = true
                start()
            }
        }

        /**
         * Stops the rendering loop
         */
        fun stopRendering() {
            rendering = false
            renderThread?.join(1000) // Wait for the render thread to finish
            renderThread = null
        }

        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)
            val g2d = g as Graphics2D

            // Enable anti-aliasing for smoother graphics
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

            // Render all registered entities
            synchronized(entities) {
                for (entity in entities) {
                    // Render the entity
                    entity.render(g2d)
                    
                    // Render bounding box if enabled
                    if (displayBoundingBoxes) {
                        g2d.color = Color(150, 150, 150, 128) // Medium gray with 50% opacity
                        entity.renderBoundingBox(g2d)
                    }
                }
            }

            // Render FPS in the top right corner
            g2d.font = Font("Arial", Font.PLAIN, 14)
            g2d.color = Color.WHITE
            val fpsText = "fps: $currentFPS"
            val metrics = g2d.fontMetrics
            val textWidth = metrics.stringWidth(fpsText)
            g2d.drawString(fpsText, width - textWidth - 10, 20)

            // Ensure rendering is completed
            Toolkit.getDefaultToolkit().sync()
        }
    }
