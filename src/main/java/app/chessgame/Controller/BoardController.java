package app.chessgame.Controller;

import app.chessgame.Models.Board;
import app.chessgame.Models.CellsObserver;
import app.chessgame.Models.Cell;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class BoardController {
    private CellsObserver observer = new CellsObserver();
    @FXML
    private GridPane chessBoard;
    private static final int rows = 8;
    private static final int columns = 8;

    @FXML
    public void initialize() {
        for (int i = 0; i < rows; i++){
            chessBoard.getColumnConstraints().add(this.createColumnConstraint());
            chessBoard.getRowConstraints().add(this.createRowConstraint());
        }

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                Cell cell = Board.getInstance().getCell(i, j);
                var button = this.createButton(cell);
                chessBoard.getChildren().add(button);
                GridPane.setConstraints(button, j, i, 1, 1);
            }
        }
    }

    private RowConstraints createRowConstraint(){
        var constraint =  new RowConstraints();
        constraint.setPercentHeight(12.5);
        return constraint;
    }

    private ColumnConstraints createColumnConstraint(){
        var constraint =  new ColumnConstraints(12.5);
        constraint.setPercentWidth(12.5);
        return constraint;
    }

    public Button createButton(Cell cell){
        Button button = new Button();
        var piece = cell.getPiece();
        cell.getColorObservable().addListener((observable, oldValue, newValue) -> {
            button.setStyle("-fx-background-color:"+ newValue+";");
        });

        button.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button.setUserData(cell);
        button.setStyle("-fx-background-color:"+ cell.getColor().toString().replace("0x", "#") +";" +
                "-fx-background-radius: 0;" +
                "-fx-padding: 0;");
        if(piece!= null){
            button.setGraphic(piece.getImage());
        }

        button.setOnMouseClicked(event -> {
            if(cell.isHighlited()){
                uncheckCell(button);
            }
            else{
                checkCell(button);
            }
        });

        return button;
    }

    private void uncheckCell(Button button){
        //var cell = (Cell)button.getUserData();
//        button.setStyle("-fx-background-color:"+cell.getColor().toString().replace("0x", "#")+";");
        this.observer.notifySubscribers();
        this.observer.clear();
    }

    private void checkCell(Button button){
        var cell = (Cell)button.getUserData();
        if(cell.isEmpty()){
            this.observer.notifySubscribers();
            this.observer.clear();
            return;
        }

        this.observer.notifySubscribers();
        this.observer.clear();
        this.observer.subscribe(cell);
        //this.observer.subscribe(Board.getInstance().getCell(cell.getPoint().getX(),cell.getPoint().getY()));
        var piece = cell.getPiece();
        var moves = piece.possibleMoves(cell);
        for (Cell move: moves) {
            this.observer.subscribe(move);
        }

        this.observer.notifySubscribers();
    }
}
