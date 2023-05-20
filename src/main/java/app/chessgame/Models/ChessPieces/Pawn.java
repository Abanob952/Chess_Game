package app.chessgame.Models.ChessPieces;

import app.chessgame.Models.Cell;
import app.chessgame.Models.MoveStrategy;
import app.chessgame.Models.Point;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Pawn extends Piece{
    private boolean moved;
    public Pawn(MoveStrategy strategy) {
        super(strategy);
        this.moved = false;
    }

    public Pawn(MoveStrategy strategy, Color color) {
        super(strategy, color);
        this.moved = false;
    }

    public Pawn(MoveStrategy strategy, Color color, ImageView image) {
        super(strategy, color, image);
        this.moved = false;
    }

    public Pawn(MoveStrategy strategy, Color color, ImageView image, Point point) {
        super(strategy, color, image, point);
        this.moved = false;
    }

    public void moved() {
        this.moved = true;
    }

    /**
     * Checks if thus pawn was moved or not
     * @return true if the pawn was moved, false otherwise
     */
    public boolean isMoved(){
        return this.moved;
    }
    @Override
    public String toString() {
        if(this.getColor() == Color.WHITE)
            return "P";
        return "P";
    }
}
