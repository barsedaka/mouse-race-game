module com.bar.games.mouseracegame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires jedis;
    requires com.fasterxml.jackson.databind;

    opens com.bar.games.mouseracegame.ui to javafx.fxml;
    opens com.bar.games.mouseracegame.mouserace to com.fasterxml.jackson.databind;
    exports com.bar.games.mouseracegame.ui;
    opens com.bar.games.mouseracegame.mouserace.elements to com.fasterxml.jackson.databind;
}