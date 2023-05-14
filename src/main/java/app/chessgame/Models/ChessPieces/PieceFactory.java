package app.chessgame.Models.ChessPieces;

import app.chessgame.Models.*;
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
        switch (piece){
            case PAWN -> {
                return new Pawn(new PawnStrategy(color), color);
            }
            case KING -> {
                return new King(new KingStrategy(color), color);
            }
            case KNIGHT -> {
                return new Knight(new KnightStrategy(color), color);
            }
            case QUEEN -> {
                return new Queen(new QueenStrategy(color), color);
            }
            case ROOK -> {
                return new Rook(new RookStrategy(color), color);
            }
            case BISHOP -> {
                return new Bishop(new BishopStrategy(color), color);
            }
            default -> throw new RuntimeException("Piece " + piece + "does not exist");
        }
    }
}
