package app.chessgame.Models;

import app.chessgame.Models.ChessPieces.Piece;
import app.chessgame.Models.ChessPieces.PieceEnum;
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
    private  static final int kingValue = 2000;
    private static final int[][] pawnSquareTable = {
            { 0,  0,  0,  0,  0,  0,  0,  0},
            {50, 50, 50, 50, 50, 50, 50, 50},
            {10, 10, 20, 30, 30, 20, 10, 10},
            { 5,  5, 10, 25, 25, 10,  5,  5},
            { 0,  0,  0, 20, 20,  0,  0,  0},
            { 5, -5,-10,  0,  0,-10, -5,  5},
            { 5, 10, 10,-20,-20, 10, 10,  5},
            { 0,  0,  0,  0,  0,  0,  0,  0}
    };

    private static final int[][] knightSquareTable = {
            {-50,-40,-30,-30,-30,-30,-40,-50},
            {-40,-20,  0,  0,  0,  0,-20,-40},
            {-30,  0, 10, 15, 15, 10,  0,-30},
            {-30,  5, 15, 20, 20, 15,  5,-30},
            {-30,  0, 15, 20, 20, 15,  0,-30},
            {-30,  5, 10, 15, 15, 10,  5,-30},
            {-40,-20,  0,  5,  5,  0,-20,-40},
            {-50,-40,-30,-30,-30,-30,-40,-50}
    };
    private static final int[][] bishopSquareTable = {
            {-20,-10,-10,-10,-10,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5, 10, 10,  5,  0,-10},
            {-10,  5,  5, 10, 10,  5,  5,-10},
            {-10,  0, 10, 10, 10, 10,  0,-10},
            {-10, 10, 10, 10, 10, 10, 10,-10},
            {-10,  5,  0,  0,  0,  0,  5,-10},
            {-20,-10,-10,-10,-10,-10,-10,-20}
    };

    private static final int[][] rookSquareTable = {
            {  0,  0,  0,  0,  0,  0,  0,  0},
            {  5, 10, 10, 10, 10, 10, 10,  5},
            { -5,  0,  0,  0,  0,  0,  0, -5},
            { -5,  0,  0,  0,  0,  0,  0, -5},
            { -5,  0,  0,  0,  0,  0,  0, -5},
            { -5,  0,  0,  0,  0,  0,  0, -5},
            { -5,  0,  0,  0,  0,  0,  0, -5},
            {  0,  0,  0,  5,  5,  0,  0,  0}
    };

    private static final int[][] queenSquareTable = {
            {-20,-10,-10, -5, -5,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5,  5,  5,  5,  0,-10},
            { -5,  0,  5,  5,  5,  5,  0, -5},
            {  0,  0,  5,  5,  5,  5,  0, -5},
            {-10,  5,  5,  5,  5,  5,  0,-10},
            {-10,  0,  5,  0,  0,  0,  0,-10},
            {-20,-10,-10, -5, -5,-10,-10,-20}
    };

    private static final int[][] kingSquareTable = {
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-20,-30,-30,-40,-40,-30,-30,-20},
            {-10,-20,-20,-20,-20,-20,-20,-10},
            { 20, 20,  0,  0,  0,  0, 20, 20},
            { 20, 30, 10,  0,  0, 10, 30, 20}
    };

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
        var king =Board.getInstance().getKings().get(color);
        sum += kingValue + (color == Color.WHITE ? kingSquareTable[king.getPoint().getX()][king.getPoint().getY()] :
                kingSquareTable[7 - king.getPoint().getX()][king.getPoint().getY()]);

        for (var table : Board.getInstance().getPieces()) {
            var pieces = table.get(color);
            for (Piece piece: pieces) {
                if (!piece.isInGame())
                    continue;
                switch (piece.getType()){
                    case QUEEN:
                        sum += queenValue + (color == Color.WHITE ? queenSquareTable[piece.getPoint().getX()][piece.getPoint().getY()] :
                                queenSquareTable[7 - piece.getPoint().getX()][piece.getPoint().getY()]);
                        break;
                    case KNIGHT:
                        sum += knightValue + (color == Color.WHITE ? knightSquareTable[piece.getPoint().getX()][piece.getPoint().getY()] :
                                knightSquareTable[7 - piece.getPoint().getX()][piece.getPoint().getY()]);
                        break;

                    case BISHOP:
                        sum += bishopValue + (color == Color.WHITE ? bishopSquareTable[piece.getPoint().getX()][piece.getPoint().getY()] :
                                bishopSquareTable[7 - piece.getPoint().getX()][piece.getPoint().getY()]);
                        break;

                    case PAWN:
                        sum += pawnValue + (color == Color.WHITE ? pawnSquareTable[piece.getPoint().getX()][piece.getPoint().getY()] :
                                pawnSquareTable[7 - piece.getPoint().getX()][piece.getPoint().getY()]);
                        break;
                    case ROOK:
                        sum += rookValue + (color == Color.WHITE ? rookSquareTable[piece.getPoint().getX()][piece.getPoint().getY()] :
                                rookSquareTable[7 - piece.getPoint().getX()][piece.getPoint().getY()]);
                        break;
                }
            }
        }

        return sum;
    }

    /**
     * Depth first minimax search used to find the best possible move for the bot
     * @param depth depth of the search, can be very slow for depths > 3
     * @param maximizingPlayer true for the maximizing player and false otherwise
     * @return Minimax result containing the best move possible
     */
    private MiniMaxResult miniMax(int depth, boolean maximizingPlayer, int alpha, int beta){
        if(depth == 0 || this.match.isCheckMate()){
            return new MiniMaxResult(null, evaluate());
        }

        var moves = this.getAllPossibleMoves(this.match.getTurn().getColor());

        if (maximizingPlayer){
            var bestEval = Integer.MIN_VALUE;
            MiniMaxResult bestMove = null;
            for (Move move: moves) {
                var piece = this.makeMove(move);
                var result = miniMax(depth - 1, false, alpha, beta);
                alpha = Math.max(alpha, result.getEvaluation());
                if(result.getEvaluation()  > bestEval){
                    bestEval = result.getEvaluation();
                    bestMove = new MiniMaxResult(move, bestEval);
                }
                this.undoMove(move, piece);
                if(beta <= alpha)
                    break;
            }

            return bestMove;
        }
        else{
            var bestEval = Integer.MAX_VALUE;
            MiniMaxResult bestMove = null;

            for (Move move: moves) {
                var piece = this.makeMove(move);
                var result = miniMax(depth - 1, true, alpha, beta);
                beta = Math.min(beta, result.getEvaluation());
                if(result.getEvaluation()  < bestEval){
                    bestEval = result.getEvaluation();
                    bestMove = new MiniMaxResult(move, bestEval);
                }
                this.undoMove(move, piece);
                if(beta<=alpha)
                    break;
            }

            return bestMove;
        }
    }

    public Move generateBestMove(int depth){
        var result = this.miniMax(depth, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
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
        var king = Board.getInstance().getKings().get(color);
        var kingCell = Board.getInstance().getCell(king.getPoint());
        var kingsMoves = king.possibleMoves(kingCell);
        for (var cell:kingsMoves) {
            var move = new Move(kingCell, cell);
            if(this.match.validateMove(move) == InvalidMoveReason.VALID){
                moves.add(move);
            }
        }
        for (var table : Board.getInstance().getPieces()) {
            var pieces = table.get(color);
            for (Piece piece: pieces) {
                var pieceCell = Board.getInstance().getCell(piece.getPoint());
                if(pieceCell.isEmpty() || !piece.isInGame())
                    continue;
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
