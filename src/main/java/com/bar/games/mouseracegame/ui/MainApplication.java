package com.bar.games.mouseracegame.ui;

import com.bar.games.mouseracegame.mouserace.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApplication extends Application {
    private Game game;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bar/games/mouseracegame/ui/leaderboard.fxml"));
        Parent root = loader.load();
        MainController controller = loader.getController();

        Pane gamePane = (Pane) root.lookup("#gamePane");
        game = new Game(gamePane);
        controller.setGame(game);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Mouse Race Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
