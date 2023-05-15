package app.chessgame.Models.ChessPieces;

import app.chessgame.Models.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class PieceFactory {

    /**
     * Factory method, creates a piece corresponding the enum and the color passed
     * @param piece enum corresponding to the piece
     * @param color color of the piece either black or white
     * @return a piece corresponding the enum and the color passed
     * @throws if enum passed does not correspond to any piece
     */
    public Piece createPiece(PieceEnum piece, Color color) {
        PieceDefinition definition = new PieceDefinition(piece, color);
        ImageView image = ImageViewFactoy.getImageForPiece(definition);
        image.setPreserveRatio(false);
        switch (piece){
            case PAWN -> {
                return new Pawn(new PawnStrategy(color), color, image);
            }
            case KING -> {
                return new King(new KingStrategy(color), color, image);
            }
            case KNIGHT -> {
                return new Knight(new KnightStrategy(color), color, image);
            }
            case QUEEN -> {
                return new Queen(new QueenStrategy(color), color, image);
            }
            case ROOK -> {
                return new Rook(new RookStrategy(color), color, image);
            }
            case BISHOP -> {
                return new Bishop(new BishopStrategy(color), color, image);
            }
            default -> throw new RuntimeException("Piece " + piece + "does not exist");
        }
    }
}
