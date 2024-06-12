package com.bar.games.mouseracegame.mouserace;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager class for handling game events and notifying listeners.
 */
public class GameEventManager {
    private static GameEventManager instance;
    private List<GameEventListener> listeners;

    private GameEventManager() {
        listeners = new ArrayList<>();
    }

    /**
     * Gets the singleton instance of the GameEventManager.
     *
     * @return the GameEventManager instance
     */
    public static synchronized GameEventManager getInstance() {
        if (instance == null) {
            instance = new GameEventManager();
        }
        return instance;
    }

    public void addListener(GameEventListener listener) {
        listeners.add(listener);
    }

    public void removeListener(GameEventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies all listeners of a game event.
     *
     * @param event the game event to notify
     */
    public void notifyGameEvent(GameEvent event) {
        for (GameEventListener listener : listeners) {
            listener.onGameEvent(event);
        }
    }
}
