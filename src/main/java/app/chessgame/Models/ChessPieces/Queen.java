package app.chessgame.Models.ChessPieces;

import app.chessgame.Models.MoveStrategy;
import app.chessgame.Models.Point;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Queen extends Piece {
    public Queen(MoveStrategy strategy) {
        super(strategy);
    }

    public Queen(MoveStrategy strategy, Color color) {
        super(strategy, color);
    }
    public Queen(MoveStrategy strategy, Color color, Image image) {
        super(strategy, color, image);
    }

    public Queen(MoveStrategy strategy, Color color, Image image, Point point) {
        super(strategy, color, image, point);
    }

    @Override
    public String toString() {
        if(this.getColor() == Color.WHITE)
            return "Q";
        return "Q";
    }
}
