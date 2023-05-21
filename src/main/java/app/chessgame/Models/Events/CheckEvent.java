package app.chessgame.Models.Events;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class CheckEvent {
    private List<CheckEventListener> listeners = new ArrayList<>();

    public void addEventListener(CheckEventListener listener) {
        listeners.add(listener);
    }

    public void removeEventListener(CheckEventListener listener) {
        listeners.remove(listener);
    }

    public void raiseEvent(Color color) {
        for (CheckEventListener listener : listeners) {
            listener.check(color);
        }
    }
}
