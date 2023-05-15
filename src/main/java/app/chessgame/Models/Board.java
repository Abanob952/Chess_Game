package app.chessgame.Models;

import app.chessgame.Models.ChessPieces.PieceEnum;
import app.chessgame.Models.ChessPieces.PieceFactory;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Board {
    Cell [][] cells;
    int rows = 8; // nombre de lignes
    int cols = 8; // nombre de colonnes

    private static Board instance;

     private Board(){
         //Création du tableau
         this.cells = new Cell[rows][cols];

         //Initialisation
         for (int i = 0; i < rows; i++) {
             for (int j = 0; j < cols; j++) {
                 if ((i+j) % 2 == 0) {
                     cells[i][j] = new Cell(Color.BEIGE, new Point(i, j)); // case blanche
                 } else {
                     cells[i][j] = new Cell (Color.BROWN, new Point(i, j)); // case noire
                 }
             }
         }

     }

     public Board(PieceFactory factory) {
         this.cells = new Cell[rows][cols];

         for (int i = 0; i < rows; i++) {
             for (int j = 0; j < cols; j++) {
                 if ((i+j) % 2 == 0) {
                     cells[i][j] = new Cell( Color.BEIGE, new Point(i, j)); // case blanche
                 } else {
                     cells[i][j] = new Cell (Color.BROWN, new Point(i, j)); // case noire
                 }
             }
         }

         for (int i = 0; i < rows; i++) {
             for (int j = 0; j < cols; j++) {
                 if(i == 1 || i == 6){
                     Color color = i ==1?Color.BLACK:Color.WHITE;
                     cells[i][j].setPiece(factory.createPiece(PieceEnum.PAWN, color));
                 } else if ((i == 0 || i == 7) ) {
                     Color color = i ==0?Color.BLACK:Color.WHITE;
                     switch (j) {
                         case 0, 7 -> cells[i][j].setPiece(factory.createPiece(PieceEnum.ROOK, color));
                         case 1, 6 -> cells[i][j].setPiece(factory.createPiece(PieceEnum.KNIGHT, color));
                         case 2, 5 -> cells[i][j].setPiece(factory.createPiece(PieceEnum.BISHOP, color));
                         case 3 -> cells[i][j].setPiece(factory.createPiece(PieceEnum.QUEEN, color));
                         case 4 -> cells[i][j].setPiece(factory.createPiece(PieceEnum.KING, color));
                     }
                 }
             }
         }
     }


    /**
     * Get a singletone instance of the board class
     * @return a singletone instance of the board class
     */
     public static Board getInstance() {
         try{
             if (instance == null)
                 instance = new Board(new PieceFactory());
         }
         catch (Exception e){
             e.printStackTrace();
             instance = new Board();
         }


         return instance;
     }

    public void printBoard() {
        // Afficher la table d'échecs
        System.out.println("  a b c d e f g h");
        for (int i = 0; i < rows; i++) {
            System.out.print((8 - i) + " "); // afficher les numéros des rangées
            for (int j = 0; j < cols; j++) {
                System.out.print(cells[i][j].toString() + " "); // afficher la couleur de la cellule
            }
            System.out.print((8 - i) + " "); // afficher les numéros des rangées à nouveau
            System.out.println(); // sauter une ligne après chaque rangée
        }
        System.out.println("  a b c d e f g h"); // afficher les lettres des colonnes à nouveau
    }

    public Cell getCell(Point point) throws IndexOutOfBoundsException{
        return this.cells[point.getX()][point.getY()];
    }

    public Cell getCell(int x, int y) throws IndexOutOfBoundsException{
        return this.cells[x][y];
    }

    /**
     * Marks all the cells passed
     * @param cells to be highlited
     */
    public void highlightCells(List<Cell> cells){
        for (Cell cell: cells
             ) {
            cell.setHighlited(true);
        }
        printBoard();
    }

    public static void main(String[] args) {
        Board board = Board.getInstance(); // créer une instance de la classe Board
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Input row:");
            int row = scanner.nextInt();
            System.out.println("Input Col:");
            int col = scanner.nextInt();
            Cell cell = board.getCell(new Point(col, row));
            List<Cell> cells = cell.getPiece().possibleMoves(cell);
            board.highlightCells(cells);
        }
    }
}

