package app.chessgame.Models.Events;

import java.util.ArrayList;
import java.util.List;

public class CheckMateEvent {
    private List<CheckMateEventListener> listeners = new ArrayList<>();

    public void addEventListener(CheckMateEventListener listener) {
        listeners.add(listener);
    }

    public void removeEventListener(CheckMateEventListener listener) {
        listeners.remove(listener);
    }

    public void raiseEvent() {
        for (CheckMateEventListener listener : listeners) {
            listener.CheckMate();
        }
    }
}
