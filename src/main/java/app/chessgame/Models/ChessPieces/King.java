package app.chessgame.Models.ChessPieces;

import app.chessgame.Models.MoveStrategy;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class King extends Piece {
    public King(MoveStrategy strategy, Color color) {
        super(strategy, color);
    }
    public King(MoveStrategy strategy) {
        super(strategy);
    }

    public King(MoveStrategy strategy, Color color, ImageView image) {
        super(strategy, color, image);
    }

    @Override
    public String toString() {
        if(this.getColor() == Color.WHITE)
            return "K";
        return "K";
    }
}
