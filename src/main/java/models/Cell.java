package models;
import javafx.scene.paint.Color;

public class Cell {
    Color color;
    Piece piece = null;

    //Constructeur
    public Cell(Color color){
        this.color = color;
    }

    //Setter et Getter
    public void setPiece(Piece piece){
        this.piece = piece;
    }
    public Piece getPiece(){
        return piece;
    }

    public boolean isEmpty(){
        return (this.piece == null);
    }

}
