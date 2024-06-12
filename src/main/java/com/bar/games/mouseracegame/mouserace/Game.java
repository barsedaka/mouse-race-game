package com.bar.games.mouseracegame.mouserace;

import com.bar.games.mouseracegame.mouserace.elements.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Game implements GameEventListener {
    private Pane gamePane;
    private List<Element> elements;
    private AnimationTimer gameLoop;
    private long startTime;
    private Leaderboard leaderboard;
    private GameState gameState;

    public Game(Pane gamePane) {
        this.gamePane = gamePane;
        this.gameState = GameState.READY;
        this.leaderboard = new Leaderboard();
        this.elements = new ArrayList<>();
        GameEventManager.getInstance().addListener(this);
    }

    public void start() {
        reset();
        gameState = GameState.RUNNING;
        startTime = System.currentTimeMillis();
        gameLoop.start();
    }

    private void reset() {
        gamePane.getChildren().clear();
        initializeElements();
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
    }

    private void initializeElements() {
        elements.clear();
        elements = ElementFactory.createElements(
                GameConfig.NUM_COLLECT_ELEMENTS,
                GameConfig.NUM_AVOID_ELEMENTS,
                GameConfig.NUM_CHANGE_ELEMENTS,
                gamePane.getWidth(),
                gamePane.getHeight()
        );
        elements.forEach(element -> {
            gamePane.getChildren().add(element.getShape());
            element.setOnClickListener(event -> handleElementClick(element));
        });
    }

    private void handleElementClick(Element element) {
        if (gameState == GameState.RUNNING) {
            element.onClicked();
            if (element instanceof AvoidElement) {
                endGame(GameState.GAME_OVER_LOST);
            } else if (isGameOver()) {
                endGame(GameState.GAME_OVER_WIN);
            }
        }
    }

    @Override
    public void onGameEvent(GameEvent event) {
        if (event == GameEvent.GAME_OVER) {
            endGame(GameState.GAME_OVER_LOST);
        }
    }

    private void update() {
        if (gameState == GameState.RUNNING) {
            elements.forEach(Element::move);
            if (isGameOver()) {
                endGame(GameState.GAME_OVER_WIN);
            }
        }
    }

    private boolean isGameOver() {
        return elements.stream()
                .filter(element -> element instanceof CollectElement || element instanceof ChangeElement)
                .allMatch(element -> !element.getShape().isVisible());
    }

    private void endGame(GameState gameOverState) {
        gameState = gameOverState;
        gameLoop.stop();
        long endTime = System.currentTimeMillis();
        long elapsedTime = (endTime - startTime) / 1000; // Convert to seconds
        showGameOverDialog(elapsedTime);
    }

    private void showGameOverDialog(long elapsedTime) {
        // Create a custom dialog for entering the player's name
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Game Over");
        nameDialog.setHeaderText(gameState == GameState.GAME_OVER_WIN ? "Congratulations!" : "Game Over");
        nameDialog.setContentText("Enter your name:");

        Optional<String> nameResult = nameDialog.showAndWait();
        if (nameResult.isPresent()) {
            String name = nameResult.get().trim();
            if (!name.isEmpty()) {
                leaderboard.addScore(name, elapsedTime);
            }

            // Create a custom dialog for displaying the leaderboard and options
            Dialog<ButtonType> leaderboardDialog = new Dialog<>();
            leaderboardDialog.setTitle("Leaderboard");
            leaderboardDialog.setHeaderText(null);

            // Create a VBox to hold the leaderboard and buttons
            VBox vbox = new VBox();
            vbox.setSpacing(10);
            vbox.setAlignment(Pos.CENTER);

            // Create a label to display the leaderboard
            Label leaderboardLabel = new Label(leaderboard.getTopScoresString());
            vbox.getChildren().add(leaderboardLabel);

            // Create the "Exit" and "Restart" buttons
            ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType restartButton = new ButtonType("Restart", ButtonBar.ButtonData.OK_DONE);
            leaderboardDialog.getDialogPane().getButtonTypes().addAll(exitButton, restartButton);

            // Set the content of the dialog to the VBox
            leaderboardDialog.getDialogPane().setContent(vbox);

            leaderboardDialog.setResultConverter(buttonType -> {
                if (buttonType == restartButton) {
                    return restartButton;
                }
                return exitButton;
            });

            Optional<ButtonType> result = leaderboardDialog.showAndWait();
            if (result.isPresent()) {
                if (result.get() == restartButton) {
                    restart();
                } else {
                    Platform.exit();
                }
            }
        }
    }

    private void restart() {
        reset();
        start();
    }
}
