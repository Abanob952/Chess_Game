package app.chessgame.Models;

import javafx.scene.paint.Color;

import java.util.List;

public abstract class Piece {
    private Color color;
    private boolean inGame;

    public Piece(){
        this.inGame = false;
    }

    /**
     * Used to check if this piece was eaten or not
     * @return true if this piece wasn't eaten yet and false otherwise
     */
    public boolean isInGame(){
        return this.inGame;
    }

    public void setInGame(boolean value){
        this.inGame = value;
    }


    /**
     * Checks for all the possible moves this piece can do
     * @param currentCell
     * @return a list of the possible cells to which this current piece can move to
     */
    public abstract List<Cell> possibleMoves(Cell currentCell);

    /**
     * moves this piece to the Cell passed in param
     * @param cell
     */

    public void move(Cell cell){
        cell.setPiece(this);
    }

    public Color getColor(){
        return this.color;
    }

    private boolean isCheck(){
        return false;
    }
}
