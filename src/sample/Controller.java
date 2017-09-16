package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.Random;

public class Controller {
    @FXML
    private Button upButton;
    @FXML
    private Button downButton;
    @FXML
    private Button leftButton;
    @FXML
    private Button rightButton;
    @FXML
    private Canvas canvas;
    @FXML
    private Label label;
    @FXML
    private Button newGame;

    private GraphicsContext graphicsContext;
    private static final int SIZE = 10;
    private Point apple;
    private boolean snakeOn;
    private Snake snake;
    private Direction direction;

    public void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();

        canvas.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    direction = Direction.UP;
                    break;
                case DOWN:
                    direction = Direction.DOWN;
                    break;
                case RIGHT:
                    direction = Direction.RIGHT;
                    break;
                case LEFT:
                    direction = Direction.LEFT;
                    break;
            }
        });

        upButton.setOnAction(event -> direction = Direction.UP);
        downButton.setOnAction(event -> direction = Direction.DOWN);
        leftButton.setOnAction(event -> direction = Direction.LEFT);
        rightButton.setOnAction(event -> direction = Direction.RIGHT);

        startSnake();
    }

    private void startSnake() {
        direction = Direction.START_POSITION;
        snakeOn = true;
        snake = new Snake(new Point(0, 0));
        randomApple();

        Runnable snakeRunnable = () -> {while (snakeOn) {
            try {
                Thread.sleep(speed());
            } catch (InterruptedException e) {
                e.printStackTrace();
                snakeOn = false;
                label.setOpacity(1);
                newGame.setOpacity(1);
                newGame.setDisable(false);
            }
            moveSnake();
            if (checkIfSnakeCrashed()) {
                snakeOn = false;
                label.setOpacity(1);
                newGame.setOpacity(1);
                newGame.setDisable(false);
            }
            draw();
        }};

        Thread t = new Thread(snakeRunnable);
        t.setDaemon(true);
        t.start();

        newGame.setOnAction(event -> {
            clearCanvas();
            direction = Direction.START_POSITION;
            snakeOn = true;
            snake = new Snake(new Point(0, 0));
            randomApple();
            label.setOpacity(0);
            newGame.setOpacity(0);
            newGame.setDisable(true);
            Thread t2 = new Thread(snakeRunnable);
            t2.setDaemon(true);
            t2.start();
        });
    }

    private int speed() {
        int speed = 180;
        if(snake.getIndex() > 0) {
            speed = 180 - (snake.getIndex()*2);
        }
        return speed;
    }

    private boolean checkIfSnakeCrashed() {
        if (snake.getBody().contains(snake.getHead())) {
            return true;
        } else {
            int xUpperBound = (int) canvas.getWidth() / SIZE;
            int yUpperBound = (int) canvas.getHeight() / SIZE;

            int headX = snake.getHead().getX();
            int headY = snake.getHead().getY();
            return headX < 0 || headY < 0 || headX >= xUpperBound || headY >= yUpperBound;
        }
    }

    private void moveSnake() {
        if (snake.getHead().moveTo(direction).equals(apple)) {
            snake.move(direction, true);
            randomApple();
        } else {
            snake.move(direction, false);
        }
    }

    private void draw() {
        clearCanvas();
        drawSnake();
        drawApple();
    }

    private void drawApple() {
        graphicsContext.setFill(new Color(0.7647, 1, 0.0784, 1));
        drawPoint(apple);
    }

    private void drawSnake() {
        graphicsContext.setFill(new Color(0.8, 0, 0.4431, 1));
        drawPoint(snake.getHead());
        graphicsContext.setFill(new Color(0.8, 0.4392, 0.6275, 1));
        snake.getBody().forEach(point -> drawPoint(point));

    }

    private void clearCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void drawPoint(Point point) {
        graphicsContext.fillRect(point.getX() * SIZE, point.getY() * SIZE, SIZE, SIZE);
    }

    private void randomApple() {
        Random r = new Random();
        int xUpperBound = (int) canvas.getWidth() / SIZE;
        int yUpperBound = (int) canvas.getHeight() / SIZE;

        do {
            apple = new Point(r.nextInt(xUpperBound), r.nextInt(yUpperBound));
        } while (snake.contains(apple));
    }
}
