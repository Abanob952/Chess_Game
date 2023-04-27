package app.chessgame.Models.ChessPieces;

import app.chessgame.Models.BishopStrategy;
import javafx.scene.paint.Color;

public class Bishop extends Piece {
    public Bishop(BishopStrategy bishopStrategy, Color color) {
        super(bishopStrategy, color);
    }

    @Override
    public String toString() {
        if(this.getColor() == Color.WHITE)
            return "B";
        return "B";
    }
}
