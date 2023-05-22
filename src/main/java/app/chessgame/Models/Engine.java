package app.chessgame.Models;

import app.chessgame.Models.ChessPieces.Piece;
import app.chessgame.Models.Events.InvalidMoveReason;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Engine {
    private static final int pawnValue = 100;
    private static final int knightValue = 300;
    private static final int bishopValue = 300;
    private static final int rookValue = 500;
    private static final int queenValue = 900;
    private Match match;

    public Engine(Match match){
        this.match = match;
    }


    private int evaluate(){
        int whiteEvaluation = this.countBoard(Color.WHITE);
        int blackEvaluation = this.countBoard(Color.BLACK);

        int evaluation = whiteEvaluation - blackEvaluation;
        return this.match.getTurn().getColor() == Color.WHITE? evaluation: -1 * evaluation;

    }

    /**
     * Evaluates the value of the chess pieces of the player with the color passed in param
     * @param color the color to evaluate
     * @return the value of the chess pieces
     */
    private int countBoard(Color color){
        int sum = 0;
        sum += Board.getInstance().getPawns().get(color).stream().filter((Piece::isInGame)).toList().size() * pawnValue;
        sum += Board.getInstance().getKnights().get(color).stream().filter((Piece::isInGame)).toList().size() * knightValue;
        sum += Board.getInstance().getBishops().get(color).stream().filter((Piece::isInGame)).toList().size() * bishopValue;
        sum += Board.getInstance().getRooks().get(color).stream().filter((Piece::isInGame)).toList().size() * rookValue;
        sum += Board.getInstance().getQueens().get(color).stream().filter((Piece::isInGame)).toList().size() * queenValue;

        return sum;
    }

    /**
     * Depth first minimax search used to find the best possible move for the bot
     * @param depth depth of the search, can be very slow for depths > 3
     * @param maximizingPlayer true for the maximizing player and false otherwise
     * @return Minimax result containing the best move possible
     */
    private MiniMaxResult miniMax(int depth, boolean maximizingPlayer){
        if(depth == 0){
            return new MiniMaxResult(null, evaluate());
        }

        var moves = this.getAllPossibleMoves(this.match.getTurn().getColor());

        if (maximizingPlayer){
            var bestEval = Integer.MIN_VALUE;
            MiniMaxResult bestMove = null;
            for (Move move: moves) {
                var piece = this.makeMove(move);
                var result = miniMax(depth - 1, false);
                if(result.getEvaluation()  > bestEval){
                    bestEval = result.getEvaluation();
                    bestMove = new MiniMaxResult(move, bestEval);
                }
                this.undoMove(move, piece);
            }

            return bestMove;
        }
        else{
            var bestEval = Integer.MAX_VALUE;
            MiniMaxResult bestMove = null;

            for (Move move: moves) {
                var piece = this.makeMove(move);
                var result = miniMax(depth - 1, true);
                if(result.getEvaluation()  < bestEval){
                    bestEval = result.getEvaluation();
                    bestMove = new MiniMaxResult(move, bestEval);
                }
                this.undoMove(move, piece);
            }

            return bestMove;
        }
    }

    public Move generateBestMove(int depth){
        var result = this.miniMax(2, true);
        return result.getMove();
    }

    /**
     * Make a move on the chess board
     * @param move
     * @return
     */
    private Piece makeMove(Move move){
        var tmpTargetPiece = move.getTarget().getPiece();
        move.getSource().move(move.getTarget());
        return tmpTargetPiece;
    }

    private void undoMove(Move move, Piece tmpTargetPiece){
        move.getTarget().move(move.getSource());
        move.getTarget().setPiece(tmpTargetPiece);
    }

    /**
     * Generates all the possible legal moves for a certain player
     * @param color color of the player
     * @return
     */
    private List<Move> getAllPossibleMoves(Color color){
        List<Move> moves = new ArrayList<>();
        for (var table : Board.getInstance().getPieces()) {
            var pieces = table.get(color);
            for (Piece piece: pieces) {
                var pieceCell = Board.getInstance().getCell(piece.getPoint());
                var possibleMoves = piece.possibleMoves(pieceCell);
                for (var cell: possibleMoves) {
                    var move = new Move(pieceCell, cell);
                    if(this.match.validateMove(move) == InvalidMoveReason.VALID){
                        moves.add(move);
                    }
                }
            }
        }
        return moves;
    }
}
