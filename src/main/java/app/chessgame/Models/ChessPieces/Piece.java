package app.chessgame.Models.ChessPieces;

import app.chessgame.Models.Cell;
import app.chessgame.Models.MoveStrategy;
import app.chessgame.Models.Point;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.List;

public class Piece {
    private Image image;
    private Color color;
    private boolean inGame;
    private final MoveStrategy moveStrategy;

    private Point point;

    public Image getImage() {
        return image;
    }

    public Piece(MoveStrategy strategy){
        this.inGame = true;
        this.moveStrategy = strategy;
    }

    public Piece(MoveStrategy strategy, Color color){
        this(strategy);
        this.color = color;
    }


    public Piece(MoveStrategy strategy, Color color, Image image){
        this(strategy, color);
        this.image = image;
    }

    public Piece(MoveStrategy strategy, Color color, Image image, Point point){
        this(strategy, color, image);
        this.point = point;
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

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Color getColor(){
        return this.color;
    }

    private boolean isCheck(){
        return false;
    }
}
