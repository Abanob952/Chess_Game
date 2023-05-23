package app.chessgame.Models;

import app.chessgame.Models.ChessPieces.King;
import app.chessgame.Models.ChessPieces.Piece;
import app.chessgame.Models.ChessPieces.Rook;
import app.chessgame.Models.Events.*;
import javafx.scene.paint.Color;

public class Match implements BotEventListener {
    private Player player1;
    private Player player2;
    private boolean started;
    private Player turn;

    private final CheckMateEvent checkMateEvent;
    private final TurnChangedEvent turnChangedEvent;
    private final MoveMadeEvent moveMadeEvent;
    private final CheckEvent checkEvent;
    private final MoveValidator validator;
    public Match(){
        this.started = false;
        this.validator = new MoveValidator(this);
        this.turnChangedEvent = new TurnChangedEvent();
        this.checkEvent = new CheckEvent();
        this.checkMateEvent = new CheckMateEvent();
        this.moveMadeEvent = new MoveMadeEvent();
        //this.player1 = new BotPlayer(Color.BLACK, this);
        this.player2 = new Player("player2", Color.WHITE);
        this.turn = player2;
    }

    public void setPlayer1(Player player){
        if(started)
            return;
        this.player1 = player;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getTurn() {
        return turn;
    }

    public boolean isStarted() {
        return started;
    }

    public boolean play(Cell source, Cell target){
        InvalidMoveReason result = this.validator.validateMove(source, target);
        if(result == InvalidMoveReason.VALID){
            if(!target.isEmpty() && target.getPiece().getColor() != source.getPiece().getColor()){
                this.getTurn().addLostPiece(target.getPiece());
            }

            if (this.isCastling(new Move(source, target))){
                if(!this.castling(source, target))
                    return false;
            }
            source.move(target);
            this.moveMadeEvent.raiseEvent(new Move(source, target));
            this.turn = this.turn == this.player1? this.player2: this.player1;
            this.turnChangedEvent.raiseEvent();
            if(this.validator.kingInCheck(this.turn.getColor())){
                if (this.validator.isCheckMate(this.turn.getColor())){
                    this.checkMateEvent.raiseEvent();
                    this.started = false;
                    return true;
                }
                this.checkEvent.raiseEvent(this.turn.getColor());
            }
        } else if (result == InvalidMoveReason.CHECK) {
            this.checkEvent.raiseEvent(this.turn.getColor());
        }

        return result == InvalidMoveReason.VALID;
    }

    public void startMatch(){
        this.started = true;
    }

    public void subscribeToTurnChangedEvent(TurnChangeListener listener){
        this.turnChangedEvent.addEventListener(listener);
    }

    public void subscribeToCheckEvent(CheckEventListener listener){
        this.checkEvent.addEventListener(listener);
    }

    public void subscribeToCheckMateEvent(CheckMateEventListener listener){
        this.checkMateEvent.addEventListener(listener);
    }

    public void subscribeToMoveMadeEvent(MoveMadeEventListener listener){
        this.moveMadeEvent.addEventListener(listener);
    }

    public InvalidMoveReason validateMove(Move move){
        return this.validator.validateMove(move.getSource(), move.getTarget());
    }

    /**
     * Checks if the move is for castling
     * @param move the move to be tested
     * @return true if it's a castling move false otherwise
     */
    private boolean isCastling(Move move){
        if(move.getSource().getPiece() instanceof King king && !king.hasMoved()){
            return Math.abs(move.getTarget().getPoint().getY() - king.getPoint().getY()) == 2;
        }

        return false;
    }

    /**
     * Attempts to castle the king in the source position
     * @param source where the king is located
     * @param target The king's final position after the castling
     * @return true if the castling was done correctly false otherwise
     */
    private boolean castling(Cell source, Cell target){
        if(source.getPiece() instanceof King king){
            var rooks = Board.getInstance().getRooks().get(king.getColor());
            Rook targetRook = null;
            for (var rook: rooks) {
                if(!((Rook)rook).hasMoved() && Math.abs(target.getPoint().getY() - rook.getPoint().getY()) <= 2){
                    targetRook = (Rook)rook;
                }
            }

            if(targetRook == null){
                return false;
            }
            var cells = targetRook.possibleMoves(Board.getInstance().getCell(targetRook.getPoint()));
            Cell min = null;
            for (Cell cell: cells) {
                if (cell.getPoint().getX() == king.getPoint().getX() &&
                        Math.abs(cell.getPoint().getY() - king.getPoint().getY()) == 1){
                    min = cell;
                    break;
                }
            }

            if(min == null || !min.isEmpty())
                return false;
            var targetRookCell = Board.getInstance().getCell(targetRook.getPoint());
            targetRookCell.move(min);
            this.moveMadeEvent.raiseEvent(new Move(targetRookCell, min));
            return true;
        }
        return false;
    }

    public boolean isCheckMate(){
        return this.validator.isCheckMate(this.turn.getColor());
    }

    @Override
    public void botPlay() {
        if(this.player1.getName().equals("Bot")){
            this.player1.getPlay();
        }
    }
}
