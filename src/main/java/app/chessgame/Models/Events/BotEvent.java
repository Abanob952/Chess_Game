package app.chessgame.Models.Events;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Event to be triggered when the human player has finished his turn
 */
public class BotEvent {
    private List<BotEventListener> listeners = new ArrayList<>();

    public void addEventListener(BotEventListener listener) {
        listeners.add(listener);
    }

    public void removeEventListener(BotEventListener listener) {
        listeners.remove(listener);
    }

    public void raiseEvent() {
        for (BotEventListener listener : listeners) {
            listener.botPlay();
        }
    }
}
