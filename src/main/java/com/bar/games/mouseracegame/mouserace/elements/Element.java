package com.bar.games.mouseracegame.mouserace.elements;

import com.bar.games.mouseracegame.mouserace.interfaces.IClickable;
import com.bar.games.mouseracegame.mouserace.interfaces.IMovable;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

public abstract class Element implements IClickable, IMovable {
    protected Shape shape;
    protected double speed;
    protected double directionX;
    protected double directionY;
    protected double gameWidth;
    protected double gameHeight;

    /**
     * Constructs a new Element instance.
     *
     * @param shape      the shape representing the element
     * @param speed      the speed of the element
     * @param gameWidth  the width of the game area
     * @param gameHeight the height of the game area
     */
    public Element(Shape shape, double speed, double gameWidth, double gameHeight) {
        this.shape = shape;
        this.speed = speed;
        this.directionX = 1;
        this.directionY = 1;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
    }

    public Shape getShape() {
        return shape;
    }

    public void setOnClickListener(EventHandler<MouseEvent> handler) {
        shape.setOnMouseClicked(handler);
    }

    public abstract void move();

    public abstract void onClicked();
}
