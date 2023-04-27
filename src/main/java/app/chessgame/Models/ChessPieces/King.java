package app.chessgame.Models.ChessPieces;

import app.chessgame.Models.MoveStrategy;
import javafx.scene.paint.Color;

public class King extends Piece {
    public King(MoveStrategy strategy, Color color) {
        super(strategy, color);
    }
    public King(MoveStrategy strategy) {
        super(strategy);
    }

    @Override
    public String toString() {
        if(this.getColor() == Color.WHITE)
            return "K";
        return "K";
    }
}
