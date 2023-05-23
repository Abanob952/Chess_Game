package app.chessgame.Models;

public class Move {
    private Cell source;
    private Cell target;

    public Move(Cell source, Cell target) {
        this.source = source;
        this.target = target;
    }

    public Cell getSource() {
        return source;
    }

    public Cell getTarget() {
        return target;
    }
}
