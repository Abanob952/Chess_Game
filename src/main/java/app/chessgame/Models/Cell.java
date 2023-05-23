package app.chessgame.Models;
import app.chessgame.Models.ChessPieces.King;
import app.chessgame.Models.ChessPieces.Pawn;
import app.chessgame.Models.ChessPieces.Piece;
import app.chessgame.Models.ChessPieces.Rook;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Cell {
    private Point point;
    Color color;
    Piece piece = null;

    private SimpleStringProperty colorObservable;
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
        this.colorObservable = new SimpleStringProperty(color.toString().replace("0x", "#"));
    }

    public Cell(Color color, Point point, Piece piece){
        this.color = color;
        this.point = point;
        this.piece = piece;
        this.isHighlited = false;
        this.colorObservable = new SimpleStringProperty(color.toString().replace("0x", "#"));
    }

    //Setter et Getter
    public Color getColor()
    {
        if (isHighlited)
            return Color.YELLOW;
        return this.color;
    }

    public void setPiece(Piece piece){
        if(this.piece != null){
            this.piece.setInGame(false);
        }

        if(piece != null)
            piece.setInGame(true);
        this.piece = piece;
    }

    /**
     * moves the piece in this cell to the Cell passed in param
     * @param cell
     */

    public void move(Cell cell){
        cell.setPiece(this.piece);
        this.piece.setPoint(cell.getPoint());
        this.piece = null;
    }

    public SimpleStringProperty getColorObservable() {
        return colorObservable;
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
        return getColor() == Color.BEIGE ? "W" : "B";
    }

    public void updateColor (){
        if (this.isHighlited){
            this.isHighlited = false;
            this.getColorObservable().setValue(this.getColor().toString().replace("0x", "#"));
            return;
        }

        this.isHighlited = true;
        this.getColorObservable().setValue(this.getColor().toString().replace("0x", "#"));
    }
}
