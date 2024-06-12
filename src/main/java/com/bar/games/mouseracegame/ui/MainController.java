package com.bar.games.mouseracegame.ui;

import com.bar.games.mouseracegame.mouserace.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class MainController {
    private Game game;

    @FXML
    private Pane gamePane;

    @FXML
    private Button startButton;

    public void setGame(Game game) {
        this.game = game;
    }

    @FXML
    private void startGame() {
        game.start();
        startButton.setDisable(true);
    }
}
