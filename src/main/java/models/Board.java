package models;

import javafx.scene.paint.Color;

public class Board {
    Cell [][] cells;
    int rows = 8; // nombre de lignes
    int cols = 8; // nombre de colonnes
     public Board(){

         //Création du tableau
         this.cells = new Cell[rows][cols];

         //Initialisation
         for (int i = 0; i < rows; i++) {
             for (int j = 0; j < cols; j++) {
                 if ((i+j) % 2 == 0) {
                     cells[i][j] = new Cell(Color.WHITE); // case blanche
                 } else {
                     cells[i][j] = new Cell (Color.BLACK); // case noire
                 }
             }
         }

     }


    public void printBoard() {
        // Afficher la table d'échecs
        System.out.println("  a b c d e f g h");
        for (int i = 0; i < rows; i++) {
            System.out.print((8 - i) + " "); // afficher les numéros des rangées
            for (int j = 0; j < cols; j++) {
                System.out.print(getCellColor(i, j) + " "); // afficher la couleur de la cellule
            }
            System.out.print((8 - i) + " "); // afficher les numéros des rangées à nouveau
            System.out.println(); // sauter une ligne après chaque rangée
        }
        System.out.println("  a b c d e f g h"); // afficher les lettres des colonnes à nouveau
    }
    public String getCellColor(int row, int col) {
        // renvoie la couleur de la cellule à la position donnée sous forme de chaîne de caractères "W" ou "B"
        return cells[row][col].getColor() == Color.WHITE ? "W" : "B";
    }

    public static void main(String[] args) {
        Board board = new Board(); // créer une instance de la classe Board
        board.printBoard(); // afficher la table d'échecs
    }
}

