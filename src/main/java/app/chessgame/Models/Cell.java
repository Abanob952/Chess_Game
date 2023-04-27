package app.chessgame.Models;
import app.chessgame.Models.ChessPieces.Piece;
import javafx.scene.paint.Color;

public class Cell {
    private Point point;
    Color color;
    Piece piece = null;

    public boolean isHighlited() {
        return isHighlited;
    }

    public void setHighlited(boolean highlited) {
        isHighlited = highlited;
    }

    private boolean isHighlited;

    //Constructeur
    public Cell(Color color, Point point){
        this.color = color;
        this.point = point;
        this.isHighlited = false;
    }

    public Cell(Color color, Point point, Piece piece){
        this.color = color;
        this.point = point;
        this.piece = piece;
        this.isHighlited = false;
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


    public Point getPoint(){
        return this.point;
    }

    @Override
    public String toString() {
        if(!this.isEmpty())
            return getPiece().toString(); // print the symbol of the piece
        if(isHighlited)
            return ".";
        // renvoie la couleur de la cellule à la position donnée sous forme de chaîne de caractères "W" ou "B"
        return getColor() == Color.WHITE ? "W" : "B";
    }
}
