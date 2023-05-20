package app.chessgame.Models;

import app.chessgame.Models.ChessPieces.Piece;
import app.chessgame.Models.Events.TurnChangeListener;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;

public class MoveValidator implements TurnChangeListener {

    private Match match;

    public MoveValidator(Match match){
        this.match = match;
    }

    public boolean validateMove(Cell source, Cell target){
        if(!this.match.isStarted())
            return false;

        // different player turn
        if(source.getPiece().getColor() != this.match.getTurn().getColor())
            return false;

        // trying to eat same friendly piece
        if(target.getPiece() != null && source.getPiece().getColor() == target.getPiece().getColor())
            return false;

        if(!this.resolvesCheckIfExists(source, target))
            return false;

        if(this.willCauseCheck(source, target))
            return false;

        return true;
    }

    /**
     * Checks if this move will cause the current's player king to be in check
     * @param source
     * @param target
     * @return true if it will cause check, false otherwise
     */
    private boolean willCauseCheck(Cell source, Cell target){
        var tmpSourcePiece = source.getPiece() == null? null: source.getPiece();
        var tmpTargetPiece = target.getPiece() == null? null: target.getPiece();

        source.move(target);
        var check= this.kingInCheck(tmpSourcePiece.getColor());
        this.undoMove(source, target,tmpTargetPiece);
        return check;
    }

    private void undoMove(Cell source, Cell target, Piece tmpTargetPiece){
        target.move(source);
        target.setPiece(tmpTargetPiece);
    }

    /**
     * Checks if a check exists and if the move is going to solve it
     * @param source
     * @param target
     * @return true if the move will resolve the check if exists or if there is no check, false otherwise
     */
    private boolean resolvesCheckIfExists(Cell source, Cell target){
        var tmpSourcePiece = source.getPiece() == null? null: source.getPiece();
        var tmpTargetPiece = target.getPiece() == null? null: target.getPiece();

        if(this.kingInCheck(source.getPiece().getColor())){
            source.move(target);
            if(!this.kingInCheck(tmpSourcePiece.getColor())){
                this.undoMove(source, target, tmpTargetPiece);
                return true;
            }

           this.undoMove(source, target, tmpTargetPiece);
            return true;
        }

        return true;
    }

    /**
     * Checks if the king with the color passed in param is in check
     * @param color
     * @return true if the king is in check, false otherwise
     */
    private boolean kingInCheck(Color color){
        var king = Board.getInstance().getKings().get(color);
        Color enemyColor = this.alternateColor(color);
        for (HashMap<Color, List<Piece>> table: Board.getInstance().getPieces()) {
            var pieces = table.get(enemyColor);
            for (Piece piece: pieces) {
                if(piece.getPoint()==null)
                    continue;
                var moves = piece.possibleMoves(Board.getInstance().getCell(piece.getPoint()));
                for (Cell cell:moves) {
                    if(cell.getPiece() != null && cell.getPiece().equals(king)){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private Color alternateColor(Color color){
        return color == Color.WHITE? Color.BLACK: Color.WHITE;
    }

    @Override
    public void turnChanged() {
//        if(!this.kingInCheck()){
//
//        }
    }
}
