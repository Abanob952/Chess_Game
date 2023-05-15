package app.chessgame.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer class, used to keep track of highlighted cells, and to toggle and untoggle them
 */
public class CellsObserver {
    private final List<Cell> subsribers = new ArrayList<>();

    public void subscribe(Cell cell){
        this.subsribers.add(cell);
    }

    public void clear(){
     this.subsribers.clear();
    }

    public void notifySubscribers(){
        for (Cell subsriber: this.subsribers) {
            subsriber.updateColor();
        }
    }
}
