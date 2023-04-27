package app.chessgame.Models;

import javafx.scene.paint.Color;

public class Knight extends Piece {
    public Knight(MoveStrategy strategy, Color color) {
        super(strategy, color);
    }

    public Knight(MoveStrategy strategy) {
        super(strategy);
    }
}
