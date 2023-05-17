package app.chessgame.Models;

import javafx.scene.paint.Color;

import java.util.*;

public class KingStrategy implements MoveStrategy {
    private final Color color;

    public KingStrategy(Color color) {
        this.color = color;
    }

    /**
     * Calculates all the possible moves for a king from the current cell
     * @param currentCell
     * @return returns a list of cells that are valid moves for the king
     */
    @Override
    public List<Cell> getPossibleMoves(Cell currentCell) {
        List<Cell> possibleMoves = new ArrayList<>();
        Map<Color, List<Point>> moves = this.calculateMoves(currentCell.getPoint());
        for (Point potentialPoint : moves.get(this.color)) {
            if (Utility.validPoint(potentialPoint)) {
                Cell potentialCell = Board.getInstance().getCell(potentialPoint);
                if (potentialCell.isEmpty() || potentialCell.getPiece().getColor() != this.color) {
                    possibleMoves.add(potentialCell);
                }
            }
        }
        return possibleMoves;
    }


    /**
     * Calculates all the possible moves for a king from the given point
     * @param point
     * @return a map with a single entry for the king's color, containing a list of possible moves
     */
    private Map<Color, List<Point>> calculateMoves(Point point) {
        return new HashMap<>() {
            {
                put(color,
                        new ArrayList<>(List.of(
                                new Point(point.getX() - 1, point.getY() - 1),
                                new Point(point.getX() - 1, point.getY()),
                                new Point(point.getX() - 1, point.getY() + 1),
                                new Point(point.getX(), point.getY() - 1),
                                new Point(point.getX(), point.getY() + 1),
                                new Point(point.getX() + 1, point.getY() - 1),
                                new Point(point.getX() + 1, point.getY()),
                                new Point(point.getX() + 1, point.getY() + 1)
                        )));
            }
        };
    }
}
