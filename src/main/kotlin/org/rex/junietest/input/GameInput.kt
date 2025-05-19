package org.rex.junietest.input

import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame

/**
 * Handles keyboard input for the game
 */
class GameInput(private val frame: JFrame) : KeyListener {
    // Map of key codes to lists of callbacks
    private val keyCallbacks = mutableMapOf<Int, MutableList<() -> Unit>>()
    
    // Set of currently pressed keys
    private val pressedKeys = mutableSetOf<Int>()

    init {
        frame.addKeyListener(this)
        frame.isFocusable = true
        frame.requestFocus()
    }

    /**
     * Register a callback for when a key is pressed
     * @param keyCode The key code to listen for (from KeyEvent)
     * @param callback The function to call when the key is pressed
     */
    fun registerKeyPress(keyCode: Int, callback: () -> Unit) {
        keyCallbacks.getOrPut(keyCode) { mutableListOf() }.add(callback)
    }

    /**
     * Unregister a callback for a key press
     * @param keyCode The key code to stop listening for
     * @param callback The callback to remove
     */
    fun unregisterKeyPress(keyCode: Int, callback: () -> Unit) {
        keyCallbacks[keyCode]?.remove(callback)
    }

    override fun keyPressed(e: KeyEvent) {
        if (!pressedKeys.contains(e.keyCode)) {
            pressedKeys.add(e.keyCode)
            keyCallbacks[e.keyCode]?.forEach { it() }
        }
    }

    override fun keyReleased(e: KeyEvent) {
        pressedKeys.remove(e.keyCode)
    }

    override fun keyTyped(e: KeyEvent) {
        // Not used
    }

    /**
     * Check if a key is currently pressed
     * @param keyCode The key code to check
     * @return true if the key is pressed, false otherwise
     */
    fun isKeyPressed(keyCode: Int): Boolean {
        return pressedKeys.contains(keyCode)
    }
} 