package app.chessgame.Controller;

import app.chessgame.Models.ChessPieces.Piece;
import app.chessgame.Models.Events.*;
import app.chessgame.HelloApplication;
import app.chessgame.Models.*;
import app.chessgame.Models.ChessPieces.*;
import app.chessgame.Models.Events.CheckListener;
import app.chessgame.Models.Events.TurnChangeListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class BoardController implements MoveMadeEventListener, TurnChangeListener, CheckEventListener, CheckMateEventListener {
    private final Match match = new Match();

    private CellsObserver observer = new CellsObserver();
    private Button isSelected;
    private Cell lastPlayed;
    @FXML
    private GridPane chessBoard;

    @FXML
    private Label player1Label;

    @FXML
    private Label player1CheckLabel;

    @FXML
    private Label player2Label;

    @FXML
    private Label player2CheckLabel;

    private BotEvent botEvent = new BotEvent();
    @FXML
    private HBox promote;

    private static final int rows = 8;
    private static final int columns = 8;
    private static Point promotePawn;
    private Color promotColor;

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
            }
        }

        player2Label.getStyleClass().add("highlight");
        player1Label.getStyleClass().remove("highlight");
        player1Label.setText(this.match.getPlayer1().getName());
        player2Label.setText(this.match.getPlayer2().getName());
        this.player1CheckLabel.setVisible(false);
        this.player2CheckLabel.setVisible(false);

        this.match.subscribeToTurnChangedEvent(this);
        this.match.subscribeToCheckEvent(this);
        this.match.subscribeToCheckMateEvent(this);
        this.match.subscribeToMoveMadeEvent(this);
        this.botEvent.addEventListener(this.match);
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
            button.setGraphic(new ImageView(piece.getImage()));
        }

        button.setOnMouseClicked(event -> {

            if(cell.isHighlited()){
                var selectedCell = (Cell)this.isSelected.getUserData();
                if(!this.match.play(selectedCell, cell))
                    return;
                this.botEvent.raiseEvent();
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
            this.isSelected = null;
            this.unhighlightCells();
            return;
        }

        this.isSelected = button;
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

    private void move(Button sourceButton, Piece piece, Button button){
        sourceButton.setGraphic(null);
        button.setGraphic(new ImageView(piece.getImage()));

        if(piece instanceof Pawn){
            Pawn pawn = (Pawn) piece;
            if (pawn.getPoint().getX() == (pawn.getColor() == Color.WHITE ? 0 : 7)){
                promotePawn = pawn.getPoint();
                promotColor = pawn.getColor();
                promotPrint();
            }
        }
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

    private void showCheckLabel(Color color){
        if(color == Color.BLACK){
            this.player1CheckLabel.setVisible(true);
        }
        else {
            this.player2CheckLabel.setVisible(true);
        }
    }

    private void hideCheckLabel(){
        this.player1CheckLabel.setVisible(false);
        this.player2CheckLabel.setVisible(false);
    }

    @Override
    public void turnChanged() {
        this.switchLabelsColor();
        this.hideCheckLabel();
    }

    @Override
    public void check(Color color) {
        this.showCheckLabel(color);
    }

    @Override
    public void CheckMate() {
        var color = this.match.getTurn().getColor();
        if (color == Color.BLACK){
            this.player1CheckLabel.setText("Check Mate");
            this.player1CheckLabel.setVisible(true);
        }
        else{
            this.player2CheckLabel.setText("Check Mate");
            this.player2CheckLabel.setVisible(true);
        }
    }

    private Button getButton(Point p){
        for (Node node : this.chessBoard.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer columnIndex = GridPane.getColumnIndex(node);

            if (rowIndex != null && columnIndex != null && rowIndex == p.getX() && columnIndex == p.getY()) {
                return (Button) node;
            }
        }

        return null;
    }

    @Override
    public void moveMade(Move move) {
        var selectedButton = this.isSelected != null && move.getSource().getPiece() == ((Cell)this.isSelected.getUserData()).getPiece()
        ? this.isSelected: this.getButton(move.getSource().getPoint());
        var targetButton = this.getButton(move.getTarget().getPoint());
        this.move(selectedButton, move.getTarget().getPiece(), targetButton);

           uncheckCell(targetButton);
    }

    @FXML
    public void promotPrint(){
        promote.setVisible(true);
        promote.setManaged(true);
    }

    @FXML
    public void onDameButtonClick() {
        String path = promotColor == Color.BLACK?"/Images/black-queen.png":"/Images/white-queen.png";
        Image queenImage = new Image(HelloApplication.class.getResourceAsStream(path));
        Board.getInstance().getCell(promotePawn).setPiece(null);
        Piece dame = new Queen(new QueenStrategy(promotColor), promotColor, queenImage, promotePawn);
        Board.getInstance().getCell(promotePawn).setPiece(dame);
        promote.setVisible(false);
        promote.setManaged(false);
    }

    @FXML
    public void onTourButtonClick() {
        String path = promotColor == Color.BLACK?"/Images/black-rook.png":"/Images/white-rook.png";
        Image rookImage = new Image(HelloApplication.class.getResourceAsStream(path));
        Board.getInstance().getCell(promotePawn).setPiece(null);
        Piece tour = new Rook(new RookStrategy(promotColor), promotColor, rookImage, promotePawn);
        Board.getInstance().getCell(promotePawn).setPiece(tour);
        promote.setVisible(false);
        promote.setManaged(false);
    }

    @FXML
    public void onOfficierButtonClick() {
        String path = promotColor == Color.BLACK?"/Images/black-bishop.png":"/Images/white-bishop.png";
        Image bishopImage = new Image(HelloApplication.class.getResourceAsStream(path));
        Board.getInstance().getCell(promotePawn).setPiece(null);
        Piece officier = new Bishop(new BishopStrategy(promotColor), promotColor, bishopImage, promotePawn);
        Board.getInstance().getCell(promotePawn).setPiece(officier);
        promote.setVisible(false);
        promote.setManaged(false);
    }

    @FXML
    public void onCavalierButtonClick() {
        String path = promotColor == Color.BLACK?"/Images/black-knight.png":"/Images/white-knight.png";
        Image knightImage = new Image(HelloApplication.class.getResourceAsStream(path));
        Board.getInstance().getCell(promotePawn).setPiece(null);
        Piece cavalier = new Knight(new KnightStrategy(promotColor), promotColor, knightImage, promotePawn);
        Board.getInstance().getCell(promotePawn).setPiece(cavalier);
        promote.setVisible(false);
        promote.setManaged(false);
    }
}
