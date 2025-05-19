package org.rex.junietest

import javax.swing.JFrame
import org.rex.junietest.renderer.GamePanel
import org.rex.junietest.entity.BallEntity
import org.rex.junietest.entity.Entity
import java.awt.Color
import java.lang.Thread.sleep

class Game : JFrame() {
    private val gamePanel: GamePanel
    private val ball: BallEntity

    // Game state
    private var running = false
    private var gameThread: Thread? = null

    // Game loop constants
    private val targetFPS = 60
    private val targetFrameTime = 1000 / targetFPS // in milliseconds

    init {
        title = "Ball Game"
        defaultCloseOperation = EXIT_ON_CLOSE

        // Create the game panel
        gamePanel = GamePanel()
        add(gamePanel)

        // Create the ball entity
        ball = BallEntity(0, 0, 50, Color.ORANGE)

        // Register the ball with the game panel
        gamePanel.registerEntity(ball)

        pack()
        setLocationRelativeTo(null) // Center on screen

        // Position the ball in the center of the panel
        ball.x = gamePanel.width / 2 - ball.radius
        ball.y = gamePanel.height / 2 - ball.radius

        isVisible = true

        // Start the game and rendering loops
        startGame()
    }

    /**
     * Starts the game loop
     */
    private fun startGame() {
        if (running) return

        running = true

        // Start the rendering loop in GamePanel
        gamePanel.startRendering()

        // Start the game loop in a separate thread
        gameThread = Thread {
            var lastUpdateTime = System.currentTimeMillis()

            while (running) {
                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime - lastUpdateTime

                if (elapsedTime >= targetFrameTime) {
                    update(elapsedTime.toFloat() / 1000f) // Convert to seconds
                    lastUpdateTime = currentTime

                    // Sleep to maintain the target frame rate
                    val sleepTime = targetFrameTime - (System.currentTimeMillis() - currentTime)
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
            name = "GameLoop"
            isDaemon = true
            start()
        }
    }

    /**
     * Stops the game loop
     */
    fun stopGame() {
        running = false
        gamePanel.stopRendering()
        gameThread?.join(1000) // Wait for the game thread to finish
        gameThread = null
    }

    /**
     * Updates the game state
     * @param deltaTime Time elapsed since the last update in seconds
     */
    private fun update(deltaTime: Float) {
        // Update game logic here
        // For now, we don't have any dynamic elements to update

        // Example: You could add movement to the ball here
        // ball.x += (100 * deltaTime).toInt()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Game()
        }
    }
}
