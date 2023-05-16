package app.chessgame.Models.Events;

import java.util.ArrayList;
import java.util.List;

public class TurnChangedEvent {
    private List<TurnChangeListener> listeners = new ArrayList<>();

    public void addEventListener(TurnChangeListener listener) {
        listeners.add(listener);
    }

    public void removeEventListener(TurnChangeListener listener) {
        listeners.remove(listener);
    }

    public void raiseEvent() {
        for (TurnChangeListener listener : listeners) {
            listener.turnChanged();
        }
    }
}
