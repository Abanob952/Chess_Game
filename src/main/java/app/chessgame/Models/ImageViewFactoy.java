package app.chessgame.Models;

import app.chessgame.HelloApplication;
import app.chessgame.Models.ChessPieces.PieceEnum;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Flyweight implementation to store, use and reuse Image views for all chess pieces.
 */
public class ImageViewFactoy {
    private static Map<PieceDefinition, ImageView> images = new HashMap<>();


    public static ImageView getImageForPiece(PieceDefinition piece){
        if(images.containsKey(piece))
            return images.get(piece);

        switch (piece.getPiece()){
            case KING -> {
                String path = piece.getColor() == Color.BLACK?"/Images/black-king.png":"/Images/white-king.png";
                Image kingImage = new Image(HelloApplication.class.getResourceAsStream(path));
                ImageView kingImageView = new ImageView(kingImage);
                images.put(piece, kingImageView);
                return kingImageView;
            }

            case QUEEN -> {
                String path = piece.getColor() == Color.BLACK?"/Images/black-queen.png":"/Images/white-queen.png";
                Image queenImage = new Image(HelloApplication.class.getResourceAsStream(path));
                ImageView queenImageView = new ImageView(queenImage);
                images.put(piece, queenImageView);
                return queenImageView;
            }
            case BISHOP -> {
                String path = piece.getColor() == Color.BLACK?"/Images/black-bishop.png":"/Images/white-bishop.png";
                Image bishopImage = new Image(HelloApplication.class.getResourceAsStream(path));
                ImageView bishopImageView = new ImageView(bishopImage);
                images.put(piece, bishopImageView);
                return bishopImageView;
            }
            case KNIGHT -> {
                String path = piece.getColor() == Color.BLACK?"/Images/black-knight.png":"/Images/white-knight.png";
                Image knightImage = new Image(HelloApplication.class.getResourceAsStream(path));
                ImageView knightImageView = new ImageView(knightImage);
                images.put(piece, knightImageView);
                return knightImageView;
            }
            case ROOK -> {
                String path = piece.getColor() == Color.BLACK?"/Images/black-rook.png":"/Images/white-rook.png";
                Image rookImage = new Image(HelloApplication.class.getResourceAsStream(path));
                ImageView rookImageView = new ImageView(rookImage);
                images.put(piece, rookImageView);
                return rookImageView;
            }
            case PAWN -> {
                String path = piece.getColor() == Color.BLACK?"/Images/black-pawn.png":"/Images/white-pawn.png";
                Image pawnImage = new Image(HelloApplication.class.getResourceAsStream(path));
                ImageView pawnImageView = new ImageView(pawnImage);
                images.put(piece, pawnImageView);
                return pawnImageView;
            }
            default -> throw new RuntimeException(piece.getPiece() + " Not implemented");
        }
    }
}
