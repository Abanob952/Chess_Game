package app.chessgame.Models;

public class MoveValidator {

    private Match match;

    public MoveValidator(Match match){
        this.match = match;
    }

    public boolean validateMove(Cell source, Cell target){
        if(!this.match.isStarted())
            return false;

        if(source.getPiece().getColor() != this.match.getTurn().getColor())
            return false;

        if(target.getPiece() != null && source.getPiece().getColor() == target.getPiece().getColor())
            return false;
        
        return true;
    }
}
