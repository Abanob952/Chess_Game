package app.chessgame.Models.ChessPieces;

import app.chessgame.Models.MoveStrategy;
import app.chessgame.Models.Point;
import app.chessgame.Models.RookStrategy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Rook extends Piece {
    public Rook(RookStrategy rookStrategy, Color color) {
        super(rookStrategy,color);
    }
    public Rook(MoveStrategy strategy, Color color, Image image) {
        super(strategy, color, image);
    }

    public Rook(MoveStrategy strategy, Color color, Image image, Point point) {
        super(strategy, color, image, point);
        this.hasMoved = false;
    }

    private boolean hasMoved;

    @Override
    public String toString() {
        if(this.getColor() == Color.WHITE)
            return "R";
        return "R";
    }

    @Override
    public PieceEnum getType() {
        return PieceEnum.ROOK;
    }

    public boolean hasMoved(){
        return this.hasMoved;
    }

    public void moved() {
        this.hasMoved = true;
    }
}
