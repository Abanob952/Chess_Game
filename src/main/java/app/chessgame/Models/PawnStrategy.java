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
            if (Utility.validPoint(potentialPoint)){
                Cell cell = Board.getInstance().getCell(potentialPoint);
                if (cell.isEmpty() || cell.getPiece().getColor() != this.color) {
                    possibleMoves.add(cell);
                }
                if (!cell.isEmpty()) {
                    break;
                }
            }
        }

        return possibleMoves;
    }

    /**
     * Calculates all the possible moves for a pawn situated at this point for each color
     * @param point
     * @return
     */
    private Map<Color, List<Point>> calculateMoves(Point point){
        return new HashMap<>(){
            {
                put(Color.BLACK,
                        new ArrayList<>(Arrays.asList
                                (new Point(point.getX() +1, point.getY()),
                                        new Point(point.getX() + 2, point.getY()))));
                put(Color.WHITE,
                        new ArrayList<>(Arrays.asList
                                (new Point(point.getX()-1, point.getY()),
                                        new Point(point.getX() - 2, point.getY()))));
            }
        };
    }
}
