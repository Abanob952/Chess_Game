package app.chessgame.Models;

import javafx.scene.paint.Color;
import jdk.jshell.spi.ExecutionControl;

import static app.chessgame.Models.PieceEnum.ROOK;

public class PieceFactory {

    /**
     * Factory method, creates a piece corresponding the enum and the color passed
     * @param piece enum corresponding to the piece
     * @param color color of the piece either black or white
     * @return a piece corresponding the enum and the color passed
     * @throws ExecutionControl.NotImplementedException if enum passed does not correspond to any piece
     */
    public Piece createPiece(PieceEnum piece, Color color) throws ExecutionControl.NotImplementedException {
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
            default -> throw new ExecutionControl.NotImplementedException("Piece " + piece + "does not exist");
        }
    }
}
