package com.bar.games.mouseracegame.mouserace;

/**
 * Listener interface for game events.
 */
public interface GameEventListener {
    /**
     * Called when a game event occurs.
     *
     * @param event the game event
     */
    void onGameEvent(GameEvent event);
}
