package app.chessgame.Models;

import javafx.scene.paint.Color;

public class Queen extends Piece {
    public Queen(MoveStrategy strategy) {
        super(strategy);
    }

    public Queen(MoveStrategy strategy, Color color) {
        super(strategy, color);
    }
}
