package app.chessgame.Models.Events;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class CheckEvent {
    private List<CheckListener> listeners = new ArrayList<>();

    public void addEventListener(CheckListener listener) {
        listeners.add(listener);
    }

    public void removeEventListener(CheckListener listener) {
        listeners.remove(listener);
    }

    public void raiseEvent(Color color) {
        for (CheckListener listener : listeners) {
            listener.check(color);
        }
    }
}
