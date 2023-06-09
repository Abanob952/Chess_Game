package app.chessgame.Models;

import app.chessgame.Models.ChessPieces.King;
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
        this.addCastlingMoves(currentCell, moves);
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

    public void addCastlingMoves(Cell currentCell,Map<Color, List<Point>> moves){
        if(!currentCell.isEmpty() && currentCell.getPiece() instanceof King king){
            if (!king.hasMoved()){
                var point = currentCell.getPoint();
                Point littleCastle = new Point(point.getX(), point.getY() + 1);
                Point littleCastle2 = new Point(point.getX(), point.getY() + 2);
                Point bigCastle = new Point(point.getX(), point.getY() - 1);
                Point bigCastle2 = new Point(point.getX(), point.getY() - 2);
                if(Board.getInstance().getCell(littleCastle).isEmpty() && Board.getInstance().getCell(littleCastle2).isEmpty()){
                    moves.get(this.color).add(new Point(point.getX(), point.getY() + 2));
                } else if (Board.getInstance().getCell(bigCastle).isEmpty() && Board.getInstance().getCell(bigCastle2).isEmpty()) {
                    moves.get(this.color).add(new Point(point.getX(), point.getY() - 2));
                }
            }
        }
    }
}
