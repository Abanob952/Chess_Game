package app.chessgame.Models.ChessPieces;

import app.chessgame.Models.MoveStrategy;
import javafx.scene.paint.Color;

public class Knight extends Piece {
    public Knight(MoveStrategy strategy, Color color) {
        super(strategy, color);
    }

    public Knight(MoveStrategy strategy) {
        super(strategy);
    }
    @Override
    public String toString() {
        if(this.getColor() == Color.WHITE)
            return "N";
        return "N";
    }
}
