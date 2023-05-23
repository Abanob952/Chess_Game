package app.chessgame.Controller;

import app.chessgame.Models.Cell;
import app.chessgame.Models.ChessPieces.Piece;
import app.chessgame.Models.Events.*;
import app.chessgame.HelloApplication;
import app.chessgame.Models.*;
import app.chessgame.Models.ChessPieces.*;
import app.chessgame.Models.Events.TurnChangeListener;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class BoardController implements MoveMadeEventListener, TurnChangeListener, CheckEventListener, CheckMateEventListener {
    private final Match match = new Match();
    @FXML
    public ComboBox<String> playerChoice;

    private final CellsObserver observer = new CellsObserver();
    @FXML
    public Button startMatch;
    private Button isSelected;
    private Button lastPlayed;
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

    private final BotEvent botEvent = new BotEvent();

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
            }
        }

        player2Label.getStyleClass().add("highlight");
        player1Label.getStyleClass().remove("highlight");
        player2Label.setText(this.match.getPlayer2().getName());
        this.player1CheckLabel.setVisible(false);
        this.player2CheckLabel.setVisible(false);

        this.match.subscribeToTurnChangedEvent(this);
        this.match.subscribeToCheckEvent(this);
        this.match.subscribeToCheckMateEvent(this);
        this.match.subscribeToMoveMadeEvent(this);
        this.botEvent.addEventListener(this.match);

        // Set the items to display in the ComboBox
        playerChoice.setItems(FXCollections.observableArrayList(
                "Bot",
                "Human player"));

        playerChoice.setValue("Bot");
        this.match.setPlayer1(new BotPlayer(Color.BLACK, this.match));
        playerChoice.setOnAction(event -> {
            String selectedOption = playerChoice.getValue();
            if(selectedOption.equals("Bot")){
                this.match.setPlayer1(new BotPlayer(Color.BLACK, this.match));
            }
            else{
                this.match.setPlayer1(new Player("Player1", Color.BLACK));
            }

            player1Label.setText(this.match.getPlayer1().getName());
        });

        this.startMatch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                match.startMatch();
            }
        });
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
        if(piece instanceof Pawn pawn && !this.match.getTurn().getName().equals("Bot")){
            if (pawn.getPoint().getX() == (pawn.getColor() == Color.WHITE ? 0 : 7)){
                var promoted = this.promptUserForPiece(piece);
                if(promoted != null){
                    sourceButton.setGraphic(null);
                    button.setGraphic(new ImageView(promoted.getImage()));
                    return;
                }
            }
        }

        sourceButton.setGraphic(null);
        button.setGraphic(new ImageView(piece.getImage()));
    }

    private void highlightLastPlayed(){
        if(this.lastPlayed!= null && this.lastPlayed.getUserData() instanceof Cell cell){
            cell.setColorObservable(Color.ORANGE);
        }
    }

    private void unhighlightLastPlayed(){
        if(this.lastPlayed!= null && this.lastPlayed.getUserData() instanceof Cell cell){
            cell.setColorObservable(cell.getColor());
        }
    }
    private void highlightInCheck(Color color){
        var cell = Board.getInstance().getCell(Board.getInstance().getKings().get(color).getPoint());
        if(cell != null)
            cell.setColorObservable(Color.RED);
    }

    private void unhighlightInCheck(Color color){
        var cell = Board.getInstance().getCell(Board.getInstance().getKings().get(color).getPoint());
        if(cell != null)
            cell.setColorObservable(cell.getColor());
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
        this.unhighlightInCheck(this.match.getTurn().getColor() == Color.WHITE? Color.BLACK: Color.WHITE);
    }


    @Override
    public void check(Color color) {
        this.showCheckLabel(color);
        this.highlightInCheck(color);
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
        this.unhighlightLastPlayed();
        this.lastPlayed = targetButton;
        this.highlightLastPlayed();
    }

    public Piece promptUserForPiece(Piece piece){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Pieces Dialog");
        alert.setHeaderText("Choose a Piece");
        alert.setContentText("Please select one of the following options:");

        ButtonType queenButton = new ButtonType(PieceEnum.QUEEN.toString());
        ButtonType bishopButton = new ButtonType(PieceEnum.BISHOP.toString());
        ButtonType rookButton = new ButtonType(PieceEnum.ROOK.toString());
        ButtonType knightButton = new ButtonType(PieceEnum.KNIGHT.toString());

        VBox queenImage = this.createPromotionImageView(PieceEnum.QUEEN);
        VBox bishopImage = this.createPromotionImageView(PieceEnum.BISHOP);
        VBox rookImage = this.createPromotionImageView(PieceEnum.ROOK);
        VBox knightImage = this.createPromotionImageView(PieceEnum.KNIGHT);

        alert.getButtonTypes().setAll(queenButton, bishopButton, rookButton, knightButton);
        alert.getDialogPane().setContent(new HBox(10, queenImage, bishopImage, rookImage, knightImage));

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        Piece promotion = null;
        if (result == queenButton) {
            promotion = PieceFactory.createPiece(PieceEnum.QUEEN, piece.getColor(), piece.getPoint());
            Board.getInstance().getQueens().get(piece.getColor()).add(promotion);
        } else if (result == bishopButton) {
            promotion = PieceFactory.createPiece(PieceEnum.BISHOP, piece.getColor(), piece.getPoint());
            Board.getInstance().getBishops().get(piece.getColor()).add(promotion);
        } else if (result == rookButton) {
            promotion = PieceFactory.createPiece(PieceEnum.ROOK, piece.getColor(), piece.getPoint());
            Board.getInstance().getRooks().get(piece.getColor()).add(promotion);
        } else if (result == knightButton) {
            promotion = PieceFactory.createPiece(PieceEnum.KNIGHT, piece.getColor(), piece.getPoint());
            Board.getInstance().getKnights().get(piece.getColor()).add(promotion);
        }

        if (promotion != null){
            Board.getInstance().getCell(piece.getPoint()).setPiece(promotion);
        }
        return promotion;
    }

    private VBox createPromotionImageView(PieceEnum piece) {
        Image image = ImageViewFactoy.getImageForPiece(new PieceDefinition(piece, this.match.getTurn().getColor()));
        ImageView imageView = new ImageView(image);
        Label label = new Label(piece.toString());
        return new VBox(10, imageView, label);
    }
}
