package com.bar.games.mouseracegame.mouserace.elements;

import com.bar.games.mouseracegame.mouserace.GameEvent;
import com.bar.games.mouseracegame.mouserace.GameEventManager;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class AvoidElement extends Element {
    private static final int CHANGE_DIRECTION_INTERVAL = 3000; // 3 seconds

    public AvoidElement(double size, double speed, double gameWidth, double gameHeight) {
        super(new Circle(size, Color.RED), speed, gameWidth, gameHeight);
    }

    @Override
    public void move() {
        double newX = shape.getTranslateX() + speed * directionX;

        if (newX <= 0) {
            directionX = 1;
            newX = 0;
        } else if (newX >= gameWidth - shape.getBoundsInLocal().getWidth()) {
            directionX = -1;
            newX = gameWidth - shape.getBoundsInLocal().getWidth();
        }

        shape.setTranslateX(newX);

        if (System.currentTimeMillis() % CHANGE_DIRECTION_INTERVAL == 0) {
            directionX *= -1;
        }
    }

    @Override
    public void onClicked() {
        GameEventManager.getInstance().notifyGameEvent(GameEvent.GAME_OVER);
    }
}
