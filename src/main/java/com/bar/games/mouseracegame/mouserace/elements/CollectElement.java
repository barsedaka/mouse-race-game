package com.bar.games.mouseracegame.mouserace.elements;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CollectElement extends Element {
    private static final int CHANGE_DIRECTION_INTERVAL = 2000;

    public CollectElement(double size, double speed, double gameWidth, double gameHeight) {
        super(new Rectangle(size, size, Color.GREEN), speed, gameWidth, gameHeight);
    }

    @Override
    public void move() {
        double newY = shape.getTranslateY() + speed * directionY;

        if (newY <= 0) {
            directionY = 1;
            newY = 0;
        } else if (newY >= gameHeight - shape.getBoundsInLocal().getHeight()) {
            directionY = -1;
            newY = gameHeight - shape.getBoundsInLocal().getHeight();
        }

        shape.setTranslateY(newY);

        if (System.currentTimeMillis() % CHANGE_DIRECTION_INTERVAL == 0) {
            directionY *= -1;
        }
    }

    @Override
    public void onClicked() {
        shape.setVisible(false);
    }
}
