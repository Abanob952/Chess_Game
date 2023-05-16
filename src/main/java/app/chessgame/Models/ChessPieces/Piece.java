package app.chessgame.Models.ChessPieces;

import app.chessgame.Models.Cell;
import app.chessgame.Models.MoveStrategy;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.List;

public class Piece {
    private ImageView image;
    private Color color;
    private boolean inGame;
    private final MoveStrategy moveStrategy;

    public ImageView getImage() {
        return image;
    }

    public Piece(MoveStrategy strategy){
        this.inGame = false;
        this.moveStrategy = strategy;
    }

    public Piece(MoveStrategy strategy, Color color){
        this.inGame = false;
        this.moveStrategy = strategy;
        this.color = color;
    }


    public Piece(MoveStrategy strategy, Color color, ImageView image){
        this.inGame = false;
        this.moveStrategy = strategy;
        this.color = color;
        this.image = image;
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
    public List<Cell> possibleMoves(Cell currentCell){
        return this.moveStrategy.getPossibleMoves(currentCell);
    }


    public Color getColor(){
        return this.color;
    }

    private boolean isCheck(){
        return false;
    }
}
