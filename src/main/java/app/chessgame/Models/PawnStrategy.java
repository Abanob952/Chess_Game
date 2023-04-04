package app.chessgame.Models;

import javafx.scene.paint.Color;

import java.util.*;

public class PawnStrategy implements MoveStrategy{
    private final Color color;

    public PawnStrategy(Color color){
        this.color = color;
    }

    /**
     * Calculates all the possible point for a pawn
     * @param currentCell
     * @return returns a list of cells that are valid moves for this pawn
     */
    @Override
    public List<Cell> getPossibleMoves(Cell currentCell) {
        List<Cell> possibleMoves = new ArrayList<>();
        Map<Color, List<Point>> moves = this.calculateMoves(currentCell.getPoint());
        for(Point potentialPoint: moves.get(this.color)){
            if (this.validPoint(potentialPoint)){
                possibleMoves.add(Board.getInstance().getCell(potentialPoint));
            }
        }

        return possibleMoves;
    }

    private boolean validPoint(Point point){
        return point.getX() < 8 && point.getX() >= 0 && point.getY() < 8 && point.getY() >= 0;
    }

    /**
     * Calculates all the possible moves for a pawn situated at this point for each color
     * @param point
     * @return
     */
    private Map<Color, List<Point>> calculateMoves(Point point){
        return new HashMap<>(){
            {
                put(Color.WHITE,
                        new ArrayList<>(Arrays.asList
                                (new Point(point.getX(), point.getY() + 1),
                                        new Point(point.getX() - 1, point.getY() + 1),
                                        new Point(point.getX() + 1, point.getY() + 1))));
                put(Color.BLACK,
                        new ArrayList<>(Arrays.asList
                                (new Point(point.getX(), point.getY() - 1),
                                        new Point(point.getX() - 1, point.getY()  - 1),
                                        new Point(point.getX() + 1, point.getY() - 1))));
            }
        };
    }
}
