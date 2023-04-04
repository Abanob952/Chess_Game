package app.chessgame.Models;

import java.util.List;

public interface MoveStrategy {
    /**
     * Checks for all the possible moves this piece can do
     * @param currentCell
     * @return a list of the possible cells to which this current piece can move to
     */
    List<Cell> getPossibleMoves(Cell currentCell);
}
