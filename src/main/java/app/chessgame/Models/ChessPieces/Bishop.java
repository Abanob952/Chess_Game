package app.chessgame.Models.ChessPieces;

import app.chessgame.Models.BishopStrategy;
import app.chessgame.Models.MoveStrategy;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bishop extends Piece {
    public Bishop(BishopStrategy bishopStrategy, Color color) {
        super(bishopStrategy, color);
    }
    public Bishop(MoveStrategy strategy, Color color, ImageView image) {
        super(strategy, color, image);
    }
    @Override
    public String toString() {
        if(this.getColor() == Color.WHITE)
            return "B";
        return "B";
    }
}
