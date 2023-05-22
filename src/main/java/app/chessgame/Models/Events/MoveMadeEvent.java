package app.chessgame.Models.Events;

import app.chessgame.Models.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Event to be triggered when a player has chosen to make a certain move
 */
public class MoveMadeEvent {
    private List<MoveMadeEventListener> listeners = new ArrayList<>();

    public void addEventListener(MoveMadeEventListener listener) {
        listeners.add(listener);
    }

    public void removeEventListener(MoveMadeEventListener listener) {
        listeners.remove(listener);
    }

    public void raiseEvent(Move move) {
        for (MoveMadeEventListener listener : listeners) {
            listener.moveMade(move);
        }
    }
}
