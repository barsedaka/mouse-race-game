package com.bar.games.mouseracegame.mouserace.elements;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ElementFactory {
    private static final Random random = new Random();

    /**
     * Creates a list of game elements.
     *
     * @param numCollectElements the number of collect elements to create
     * @param numAvoidElements   the number of avoid elements to create
     * @param numChangeElements  the number of change elements to create
     * @param gameWidth          the width of the game area
     * @param gameHeight         the height of the game area
     * @return a list of game elements
     */
    public static List<Element> createElements(int numCollectElements, int numAvoidElements, int numChangeElements, double gameWidth, double gameHeight) {
        return Stream.of(
                Stream.generate(() -> createCollectElement(gameWidth, gameHeight)).limit(numCollectElements),
                Stream.generate(() -> createAvoidElement(gameWidth, gameHeight)).limit(numAvoidElements),
                Stream.generate(() -> createChangeElement(gameWidth, gameHeight)).limit(numChangeElements)
        ).flatMap(s -> s).collect(Collectors.toList());
    }

    private static CollectElement createCollectElement(double gameWidth, double gameHeight) {
        double size = random.nextDouble() * 50 + 10;
        double speed = random.nextDouble() * 2 + 1;
        CollectElement element = new CollectElement(size, speed, gameWidth, gameHeight);
        setRandomPosition(element.getShape(), gameWidth, gameHeight);
        return element;
    }

    private static AvoidElement createAvoidElement(double gameWidth, double gameHeight) {
        double size = random.nextDouble() * 30 + 10;
        double speed = random.nextDouble() * 3 + 1;
        AvoidElement element = new AvoidElement(size, speed, gameWidth, gameHeight);
        setRandomPosition(element.getShape(), gameWidth, gameHeight);
        return element;
    }

    private static ChangeElement createChangeElement(double gameWidth, double gameHeight) {
        double size = random.nextDouble() * 40 + 20;
        double speed = random.nextDouble() * 4 + 2;
        ChangeElement element = new ChangeElement(size, speed, gameWidth, gameHeight);
        setRandomPosition(element.getShape(), gameWidth, gameHeight);
        return element;
    }

    private static void setRandomPosition(javafx.scene.shape.Shape shape, double gameWidth, double gameHeight) {
        shape.setTranslateX(random.nextDouble() * (gameWidth - shape.getBoundsInLocal().getWidth()));
        shape.setTranslateY(random.nextDouble() * (gameHeight - shape.getBoundsInLocal().getHeight()));
    }
}
