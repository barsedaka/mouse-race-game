package com.bar.games.mouseracegame.mouserace.elements;

import com.bar.games.mouseracegame.mouserace.GameEvent;
import com.bar.games.mouseracegame.mouserace.GameEventManager;
import javafx.animation.RotateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ChangeElement extends Element {
    private static final int CHANGE_COLOR_INTERVAL = 3000; // 3 seconds
    private Color currentColor;
    private boolean isCollect;

    public ChangeElement(double size, double speed, double gameWidth, double gameHeight) {
        super(new Rectangle(size, size), speed, gameWidth, gameHeight);
        currentColor = Color.GREEN;
        isCollect = true;
        startRotation();
        startColorChange();
    }

    private void startRotation() {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), shape);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.play();
    }

    private void startColorChange() {
        javafx.animation.Timeline timeline =
                new javafx.animation.Timeline(
                        new javafx.animation.KeyFrame(
                                Duration.millis(CHANGE_COLOR_INTERVAL), event -> {
                                    currentColor = (currentColor == Color.GREEN) ? Color.RED : Color.GREEN;
                                    isCollect = (currentColor == Color.GREEN);
                                    shape.setFill(currentColor);
                                }));
        timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
        timeline.play();
    }

    @Override
    public void move() {
        // No movement for ChangeElement
    }

    @Override
    public void onClicked() {
        if (isCollect) {
            shape.setVisible(false);
        } else {
            GameEventManager.getInstance().notifyGameEvent(GameEvent.GAME_OVER);
        }
    }
}