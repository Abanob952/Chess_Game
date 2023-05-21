package app.chessgame.Models.ChessPieces;

import app.chessgame.Models.*;
import javafx.scene.image.Image;
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
    public Piece createPiece(PieceEnum piece, Color color, Point point) {
        PieceDefinition definition = new PieceDefinition(piece, color);
        Image image = ImageViewFactoy.getImageForPiece(definition);
        switch (piece){
            case PAWN -> {
                return new Pawn(new PawnStrategy(color), color, image, point);
            }
            case KING -> {
                return new King(new KingStrategy(color), color, image, point);
            }
            case KNIGHT -> {
                return new Knight(new KnightStrategy(color), color, image, point);
            }
            case QUEEN -> {
                return new Queen(new QueenStrategy(color), color, image, point);
            }
            case ROOK -> {
                return new Rook(new RookStrategy(color), color, image, point);
            }
            case BISHOP -> {
                return new Bishop(new BishopStrategy(color), color, image, point);
            }
            default -> throw new RuntimeException("Piece " + piece + "does not exist");
        }
    }
}
