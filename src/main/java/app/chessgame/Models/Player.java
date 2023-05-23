package app.chessgame.Models;

import app.chessgame.Models.ChessPieces.Piece;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Piece> lostPieces;
    private String name;
    private Color color;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.lostPieces = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public void addLostPiece(Piece piece){
        this.lostPieces.add(piece);
    }

    public void getPlay(){

    }
}