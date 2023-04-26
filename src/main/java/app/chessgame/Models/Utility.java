package app.chessgame.Models;

public class Utility {
    public static boolean validPoint(Point point){
        return point.getX() < 8 && point.getX() >= 0 && point.getY() < 8 && point.getY() >= 0;
    }
}
