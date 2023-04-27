package app.chessgame.Models;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BishopStrategy implements MoveStrategy {
    private final Color color;

    public BishopStrategy(Color color){
        this.color=color;
    }

    /**
     * Calculates all the possible point for a rook
     * @param currentCell
     * @return returns a list of cells that are valid moves for this rook
     */
    @Override
    public List<Cell> getPossibleMoves(Cell currentCell) {
        List<Cell> possibleMoves = new ArrayList<>();
        Map<Color, List<Point>> moves = this.calculateMoves(currentCell.getPoint());
        for(Point potentialPoint: moves.get(this.color)){
            if (Utility.validPoint(potentialPoint)){
                possibleMoves.add(Board.getInstance().getCell(potentialPoint));
            }
        }

        return possibleMoves;
    }

    private Map<Color, List<Point>> calculateMoves(Point point) {
        Map<Color, List<Point>> moves = new HashMap<>();
        List<Point> possibleMoves = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            possibleMoves.add(new Point(point.getX()+i, point.getY() + i)); // Diagonale droite haute
            possibleMoves.add(new Point(point.getX()+i, point.getY() - i)); // Diagonale droite basse

            possibleMoves.add(new Point(point.getX()-i, point.getY() + i)); //Diagonale gauche haute
            possibleMoves.add(new Point(point.getX() - i, point.getY()- i)); //Diagonale haute basse

        }
        moves.put(color, possibleMoves);
        return moves;
    }
}
