package app.chessgame.Models.ChessPieces;

import app.chessgame.Models.MoveStrategy;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Queen extends Piece {
    public Queen(MoveStrategy strategy) {
        super(strategy);
    }

    public Queen(MoveStrategy strategy, Color color) {
        super(strategy, color);
    }
    public Queen(MoveStrategy strategy, Color color, ImageView image) {
        super(strategy, color, image);
    }
    @Override
    public String toString() {
        if(this.getColor() == Color.WHITE)
            return "Q";
        return "Q";
    }
}
