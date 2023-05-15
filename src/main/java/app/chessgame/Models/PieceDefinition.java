package app.chessgame.Models;

import app.chessgame.Models.ChessPieces.PieceEnum;
import javafx.scene.paint.Color;

public class PieceDefinition {
    PieceEnum piece;
    Color color;

    public PieceDefinition(PieceEnum piece, Color color) {
        this.piece = piece;
        this.color = color;
    }

    public PieceEnum getPiece() {
        return piece;
    }

    public Color getColor() {
        return color;
    }
}
