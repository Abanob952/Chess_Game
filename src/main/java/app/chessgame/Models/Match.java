package app.chessgame.Models;

import app.chessgame.Models.Events.*;
import javafx.scene.paint.Color;

public class Match implements BotEventListener {
    private Player player1;
    private Player player2;
    private boolean started;
    private Player turn;

    private CheckMateEvent checkMateEvent;
    private TurnChangedEvent turnChangedEvent;
    private MoveMadeEvent moveMadeEvent;
    private CheckEvent checkEvent;
    private MoveValidator validator;
    public Match(){
        this.started = false;
        this.validator = new MoveValidator(this);
        this.turnChangedEvent = new TurnChangedEvent();
        this.checkEvent = new CheckEvent();
        this.checkMateEvent = new CheckMateEvent();
        this.moveMadeEvent = new MoveMadeEvent();
        this.player1 = new BotPlayer(Color.BLACK, this);
        this.player2 = new Player("player2", Color.WHITE);
        this.turn = player2;
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
