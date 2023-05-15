package app.chessgame.Models;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RookStrategy implements MoveStrategy {
    private final Color color;

    public RookStrategy(Color color){
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

    private Map<Color, List<Point>> calculateMoves(Point point) {
        Map<Color, List<Point>> moves = new HashMap<>();
        List<Point> possibleMoves = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            possibleMoves.add(new Point(point.getX(), point.getY() + i)); // Verticale Haute
            possibleMoves.add(new Point(point.getX(), point.getY() - i)); //verticale Basse
            possibleMoves.add(new Point(point.getX() + i, point.getY())); //Horizontale Gauche
            possibleMoves.add(new Point(point.getX() - i, point.getY())); //Horizontale Droite
        }
        moves.put(color, possibleMoves);
        return moves;
    }
}
