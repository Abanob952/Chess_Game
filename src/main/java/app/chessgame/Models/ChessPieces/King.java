package app.chessgame.Models.ChessPieces;

import app.chessgame.Models.MoveStrategy;
import app.chessgame.Models.Point;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class King extends Piece {
    public King(MoveStrategy strategy, Color color) {
        super(strategy, color);
    }
    public King(MoveStrategy strategy) {
        super(strategy);
    }

    private boolean hasMoved;
    public King(MoveStrategy strategy, Color color, Image image) {
        super(strategy, color, image);
    }

    public King(MoveStrategy strategy, Color color, Image image, Point point) {
        super(strategy, color, image, point);
        this.hasMoved = false;
    }

    @Override
    public String toString() {
        if(this.getColor() == Color.WHITE)
            return "K";
        return "K";
    }

    @Override
    public PieceEnum getType() {
        return PieceEnum.KING;
    }

    public boolean hasMoved(){
        return this.hasMoved;
    }

    public void moved() {
        this.hasMoved = true;
    }
}
