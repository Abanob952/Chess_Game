package app.chessgame.Models;

import javafx.scene.paint.Color;

public class King extends Piece {
    public King(MoveStrategy strategy, Color color) {
        super(strategy, color);
    }
    public King(MoveStrategy strategy) {
        super(strategy);
    }
}
