package app.chessgame.Models;

import javafx.scene.paint.Color;

import java.util.*;


public class KnightStrategy implements MoveStrategy {
    private final Color color;

    public KnightStrategy(Color color) {
        this.color = color;
    }

    /**
     * Calculates all the possible moves for a knight from the current cell
     * @param currentCell
     * @return returns a list of cells that are valid moves for the knight
     */
    @Override
    public List<Cell> getPossibleMoves(Cell currentCell) {
        List<Cell> possibleMoves = new ArrayList<>();
        Map<Color, List<Point>> moves = this.calculateMoves(currentCell.getPoint());
        for (Point potentialPoint : moves.get(this.color)) {
            if (Utility.validPoint(potentialPoint)) {
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
     * Calculates all the possible moves for a knight from the given point
     * @param point
     * @return a map with a single entry for the knight's color, containing a list of possible moves
     */
    private Map<Color, List<Point>> calculateMoves(Point point) {
        return new HashMap<>() {
            {
                put(color,
                        new ArrayList<>(List.of(
                                new Point(point.getX() + 2, point.getY() - 1),
                                new Point(point.getX() + 2, point.getY() + 1),
                                new Point(point.getX() + 1, point.getY() - 2),
                                new Point(point.getX() + 1, point.getY() + 2),
                                new Point(point.getX() - 2, point.getY() - 1),
                                new Point(point.getX() - 2, point.getY() + 1),
                                new Point(point.getX() - 1, point.getY() - 2),
                                new Point(point.getX() - 1, point.getY() + 2)
                        )));
            }
        };
    }
}
