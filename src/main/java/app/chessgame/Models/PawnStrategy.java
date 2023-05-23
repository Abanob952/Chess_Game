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
        for (Point potentialPoint : moves.get(this.color)) {
            if (Utility.validPoint(potentialPoint)) {
                Cell potentialCell = Board.getInstance().getCell(potentialPoint);
                if (potentialCell.isEmpty()) {
                    possibleMoves.add(potentialCell);
                } else if (isCapturePossible(currentCell, potentialCell)) {
                    possibleMoves.add(potentialCell);
                }
            }
        }
        return possibleMoves;
    }

    private boolean isCapturePossible(Cell currentCell, Cell potentialCell) {
        int startX = currentCell.getPoint().getX();
        int startY = currentCell.getPoint().getY();
        int endX = potentialCell.getPoint().getX();
        int endY = potentialCell.getPoint().getY();

        int xDirection = Integer.compare(endX, startX);
        int yDirection = Integer.compare(endY, startY);

        int x = startX + xDirection;
        int y = startY + yDirection;

        if (Math.abs(endX - startX) == 1 && Math.abs(endY - startY) == 1) {
            Cell adjacentCell = Board.getInstance().getCell(x, y);
            if (!adjacentCell.isEmpty() && adjacentCell.getPiece().getColor() != this.color) {
                return true;
            }
        }

        return false;
    }

    private Map<Color, List<Point>> calculateMoves(Point point) {
        Map<Color, List<Point>> moves = new HashMap<>();
        List<Point> possibleMoves = new ArrayList<>();

        int initPosition = color == Color.WHITE ? -1 : 1;
        Point forwardOne = new Point(point.getX() + initPosition, point.getY());
        Point forwardTwo = new Point(point.getX() + (2 * initPosition), point.getY());
        Point captureRight = new Point(point.getX() + initPosition, point.getY() + initPosition);
        Point captureLeft = new Point(point.getX() + initPosition, point.getY() - initPosition);

        if (Utility.validPoint(forwardOne) && Board.getInstance().getCell(forwardOne).isEmpty()) {
            possibleMoves.add(forwardOne);
        }
        if (point.getX() == (color == Color.WHITE ? 6 : 1) && Utility.validPoint(forwardTwo) && Board.getInstance().getCell(forwardTwo).isEmpty()) {
            possibleMoves.add(forwardTwo);
        }

        if (Utility.validPoint(captureRight)) {
            Cell rightCell = Board.getInstance().getCell(captureRight);
            if (!rightCell.isEmpty() && rightCell.getPiece().getColor() != this.color) {
                possibleMoves.add(captureRight);
            }
        }

        if (Utility.validPoint(captureLeft)) {
            Cell leftCell = Board.getInstance().getCell(captureLeft);
            if (!leftCell.isEmpty() && leftCell.getPiece().getColor() != this.color) {
                possibleMoves.add(captureLeft);
            }
        }

        moves.put(color, possibleMoves);
        return moves;
    }
}
