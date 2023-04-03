package app.chessgame.Models;

import javafx.scene.control.Cell;
import javafx.scene.paint.Color;

import java.util.List;

public abstract class Piece {
    private Color color;

    public boolean isInGame(){
        return false;
    }

    // ToDO
    // public abstract List<Cell> possibleMoves(Cell currentCell);

    public void move(Cell cell){

    }

    public Color getColor(){
        return this.color;
    }

    private boolean isCheck(){
        return false;
    }
}
