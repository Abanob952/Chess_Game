module app.chessgame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens app.chessgame  to javafx.fxml;
    opens app.chessgame.Controller to javafx.fxml;
    exports app.chessgame;
    exports app.chessgame.Controller to javafx.fxml;
}
