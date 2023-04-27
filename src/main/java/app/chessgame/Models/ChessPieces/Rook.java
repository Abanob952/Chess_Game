package app.chessgame.Models.ChessPieces;

import app.chessgame.Models.RookStrategy;
import javafx.scene.paint.Color;

public class Rook extends Piece {
    public Rook(RookStrategy rookStrategy, Color color) {
        super(rookStrategy,color);
    }

    @Override
    public String toString() {
        if(this.getColor() == Color.WHITE)
            return "R";
        return "R";
    }
}
