package app.chessgame.Controller;

import app.chessgame.Models.Board;
import app.chessgame.Models.CellsObserver;
import app.chessgame.Models.Cell;
import app.chessgame.Models.Events.TurnChangeListener;
import app.chessgame.Models.Match;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;

public class BoardController implements TurnChangeListener {
    private final Match match = new Match();
    private CellsObserver observer = new CellsObserver();
    private Cell isSelected;
    private Cell lastPlayed;
    @FXML
    private GridPane chessBoard;

    @FXML
    private Label player1Label;
    @FXML
    private Label player2Label;

    private static final int rows = 8;
    private static final int columns = 8;

    @FXML
    public void initialize() {
        for (int i = 0; i < rows; i++) {
            chessBoard.getColumnConstraints().add(this.createColumnConstraint());
            chessBoard.getRowConstraints().add(this.createRowConstraint());
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell cell = Board.getInstance().getCell(i, j);
                var button = this.createButton(cell);
                chessBoard.getChildren().add(button);
                GridPane.setConstraints(button, j, i, 1, 1);

                if (i == rows - 1 && j == 0) {
                    VBox labelContainer = new VBox();
                    labelContainer.setAlignment(Pos.CENTER_LEFT);
                    labelContainer.getStyleClass().add("coordinate-label");
                    Label rowLabel = new Label(Integer.toString(rows - i));
                    Label columnLabel = new Label("a");
                    labelContainer.getChildren().addAll(rowLabel, columnLabel);
                    GridPane.setConstraints(labelContainer, j, i);
                    chessBoard.getChildren().add(labelContainer);
                } else if (i == rows - 1 && j > 0) {
                    char columnChar = (char) ('b' + (j - 1));
                    Label columnLabel = new Label(Character.toString(columnChar));
                    GridPane.setConstraints(columnLabel, j, i);
                    chessBoard.getChildren().add(columnLabel);
                } else if (j == 0 && i < rows - 1) {
                    int rowNumber = rows - i;
                    Label rowLabel = new Label(Integer.toString(rowNumber));
                    rowLabel.getStyleClass().add("coordinate-label");
                    GridPane.setConstraints(rowLabel, j, i);
                    chessBoard.getChildren().add(rowLabel);
                }
            }
        }

        player2Label.getStyleClass().add("highlight");
        player1Label.getStyleClass().remove("highlight");
        player1Label.setText(this.match.getPlayer1().getName());
        player2Label.setText(this.match.getPlayer2().getName());

        this.match.subscribeToTurnChangedEvent(this);
        this.match.startMatch();
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
                if(!cell.isEmpty() && cell.getPiece().getColor() != this.isSelected.getPiece().getColor()){
                    this.match.getTurn().addLostPiece(cell.getPiece());
                }

                if(!this.match.play(this.isSelected, cell))
                    return;

                this.move(this.isSelected, cell, button);

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
        this.isSelected = null;
        this.observer.notifySubscribers();
        this.observer.clear();
    }

    private void checkCell(Button button){
        var cell = (Cell)button.getUserData();
        if(cell.isEmpty()){
            this.unhighlightCells();
            return;
        }

        this.isSelected = cell;
        this.unhighlightCells();
        this.observer.subscribe(cell);
        //this.observer.subscribe(Board.getInstance().getCell(cell.getPoint().getX(),cell.getPoint().getY()));
        var piece = cell.getPiece();
        var moves = piece.possibleMoves(cell);
        for (Cell move: moves) {
            this.observer.subscribe(move);
        }

        this.observer.notifySubscribers();
    }

    private void unhighlightCells(){
        this.observer.notifySubscribers();
        this.observer.clear();
    }

    private void move(Cell source, Cell target, Button button){
        var sourcePiece = source.getPiece();
        source.move(target);
        button.setGraphic(sourcePiece.getImage());
    }

    private void switchLabelsColor(){
        var playerNumber = this.match.getTurn().getColor() == Color.BLACK? 1: 2;
        if(playerNumber == 1 ){
            player1Label.getStyleClass().add("highlight");
            player2Label.getStyleClass().remove("highlight");
            return;
        }

        player2Label.getStyleClass().add("highlight");
        player1Label.getStyleClass().remove("highlight");
    }

    @Override
    public void turnChanged() {
        this.switchLabelsColor();
    }
}
