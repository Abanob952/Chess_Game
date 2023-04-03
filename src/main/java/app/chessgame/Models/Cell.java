package app.chessgame.Models;
import javafx.scene.paint.Color;

public class Cell {
    Color color;
    Piece piece = null;

    //Constructeur
    public Cell(Color color){
        this.color = color;
    }

    //Setter et Getter
    public Color getColor(){
        return this.color;
    }

    public void setPiece(Piece piece){
        if(this.piece != null){
            this.piece.setInGame(false);
        }

        this.piece = piece;
    }

    public Piece getPiece(){
        return piece;
    }

     public boolean isEmpty(){
        return (this.piece == null);
    }






}
