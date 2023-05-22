package app.chessgame.Models;

public class MiniMaxResult {
    Move move;
    int evaluation;

    public MiniMaxResult(Move move, int evaluation) {
        this.move = move;
        this.evaluation = evaluation;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }
}
