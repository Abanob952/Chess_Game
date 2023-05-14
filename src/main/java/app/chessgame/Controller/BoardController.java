package app.chessgame.Controller;

import app.chessgame.Models.Board;
import app.chessgame.Models.ChessPieces.Piece;
import app.chessgame.Models.Point;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class BoardController {
    @FXML
    private GridPane chessBoard;
    private static final int rows = 8;
    private static final int columns = 8;

    @FXML
    public void initialize() {
        for (int i = 0; i < rows; i++){
            chessBoard.getColumnConstraints().add(createColumnConstraint());
            chessBoard.getRowConstraints().add(createRowConstraint());
        }

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                Piece piece = Board.getInstance().getCell(i, j).getPiece();
                var button = createButton(piece);
                chessBoard.getChildren().add(button);
                GridPane.setConstraints(button, j, i, 1, 1);
            }
        }
    }

    public RowConstraints createRowConstraint(){
        var constraint =  new RowConstraints();
        constraint.setPercentHeight(12.5);
        return constraint;
    }

    public ColumnConstraints createColumnConstraint(){
        var constraint =  new ColumnConstraints(12.5);
        constraint.setPercentWidth(12.5);
        return constraint;
    }

    public Button createButton(Piece piece){
        Button button = new Button();
        button.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button.setUserData(piece);
        return button;
    }
}
