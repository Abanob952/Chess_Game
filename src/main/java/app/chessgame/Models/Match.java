package app.chessgame.Models;

import app.chessgame.Models.Events.CheckEvent;
import app.chessgame.Models.Events.CheckListener;
import app.chessgame.Models.Events.TurnChangeListener;
import app.chessgame.Models.Events.TurnChangedEvent;
import javafx.scene.paint.Color;

public class Match {
    private Player player1;
    private Player player2;
    private boolean started;
    private Player turn;

    private TurnChangedEvent turnChangedEvent;
    private CheckEvent checkEvent;
    private MoveValidator validator;
    public Match(){
        this.player1 = new Player("player1", Color.WHITE);
        this.player2 = new Player("player2", Color.BLACK);
        this.started = false;
        this.turn = player1;
        this.validator = new MoveValidator(this);
        this.turnChangedEvent = new TurnChangedEvent();
        this.checkEvent = new CheckEvent();
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
        boolean result = this.validator.validateMove(source, target);
        if(result){
            this.turn = this.turn == this.player1? this.player2: this.player1;
            this.turnChangedEvent.raiseEvent();
        }

        return result;
    }

    public void startMatch(){
        this.started = true;
    }

    public void subscribeToTurnChangedEvent(TurnChangeListener listener){
        this.turnChangedEvent.addEventListener(listener);
    }

    public void subscribeToCheckEvent(CheckListener listener){
        this.checkEvent.addEventListener(listener);
    }
}
