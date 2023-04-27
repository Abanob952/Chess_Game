package app.chessgame.Models;

import javafx.scene.paint.Color;
import jdk.jshell.spi.ExecutionControl;

public class PieceFactory {
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
            default -> throw new ExecutionControl.NotImplementedException("Piece " + piece + "does not exist");
        }
    }
}
