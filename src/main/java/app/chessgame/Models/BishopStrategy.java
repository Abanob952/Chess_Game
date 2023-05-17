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
        for (Point potentialPoint : moves.get(this.color)) {
            if (Utility.validPoint(potentialPoint)) {
                Cell potentialCell = Board.getInstance().getCell(potentialPoint);
                if (potentialCell.isEmpty() || potentialCell.getPiece().getColor() != this.color) {
                    if (isPathClear(currentCell, potentialCell)) {
                        possibleMoves.add(potentialCell);
                    }
                }
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

    private boolean isPathClear(Cell currentCell, Cell potentialCell) {
        int startX = currentCell.getPoint().getX();
        int startY = currentCell.getPoint().getY();
        int endX = potentialCell.getPoint().getX();
        int endY = potentialCell.getPoint().getY();
        int xDirection = Integer.compare(endX, startX);
        int yDirection = Integer.compare(endY, startY);
        int x = startX + xDirection;
        int y = startY + yDirection;
        while (x != endX || y != endY) {
            Cell cell = Board.getInstance().getCell(x, y);
            if (!cell.isEmpty()) {
                return false;
            }
            x += xDirection;
            y += yDirection;
        }
        return true;
    }
}
