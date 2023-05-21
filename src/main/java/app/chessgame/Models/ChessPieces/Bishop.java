package app.chessgame.Models.ChessPieces;

import app.chessgame.Models.BishopStrategy;
import app.chessgame.Models.MoveStrategy;
import app.chessgame.Models.Point;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bishop extends Piece {
    public Bishop(BishopStrategy bishopStrategy, Color color) {
        super(bishopStrategy, color);
    }
    public Bishop(MoveStrategy strategy, Color color, Image image) {
        super(strategy, color, image);
    }

    public Bishop(MoveStrategy strategy, Color color, Image image, Point point) {
        super(strategy, color, image, point);
    }

    @Override
    public String toString() {
        if(this.getColor() == Color.WHITE)
            return "B";
        return "B";
    }
}
