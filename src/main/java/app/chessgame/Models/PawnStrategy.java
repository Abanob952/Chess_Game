package app.chessgame.Models;

import javafx.scene.paint.Color;

import java.util.*;

public class PawnStrategy implements MoveStrategy{
    private Color color;

    public PawnStrategy(Color color){
        this.color = color;
    }

    @Override
    public List<Cell> getPossibleMoves(Cell currentCell) {
        Map moves = this.calculateMoves(currentCell.getPoint());
        return new ArrayList<>();
    }

    private boolean validPoint(Point point){
        return point.getX() < 8 && point.getX() >= 0 && point.getY() < 8 && point.getY() >= 0;
    }

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
