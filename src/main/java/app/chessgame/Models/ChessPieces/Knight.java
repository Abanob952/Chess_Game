package app.chessgame.Models.ChessPieces;

import app.chessgame.Models.MoveStrategy;
import app.chessgame.Models.Point;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Knight extends Piece {
    public Knight(MoveStrategy strategy, Color color) {
        super(strategy, color);
    }
    public Knight(MoveStrategy strategy, Color color, ImageView image) {
        super(strategy, color, image);
    }
    public Knight(MoveStrategy strategy) {
        super(strategy);
    }

    public Knight(MoveStrategy strategy, Color color, ImageView image, Point point) {
        super(strategy, color, image, point);
    }

    @Override
    public String toString() {
        if(this.getColor() == Color.WHITE)
            return "N";
        return "N";
    }
}
