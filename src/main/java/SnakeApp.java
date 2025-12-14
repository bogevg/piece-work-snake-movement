

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;


public class SnakeApp extends Application {

    private static final int CELL_SIZE = 20;
    private static final int FIELD_WIDTH = 20;
    private static final int FIELD_HEIGHT = 15;

    private Snake snake;
    private Pane root;
    private long frameDelayNanos = 200_000_000L;

    @Override
    public void start(Stage primaryStage) {
        snake = new Snake(FIELD_WIDTH, FIELD_HEIGHT);
        root = new Pane();
        root.setPrefSize(FIELD_WIDTH * CELL_SIZE, FIELD_HEIGHT * CELL_SIZE);

        drawBorders();

        Scene scene = new Scene(root, Color.BLACK);


        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            if (key == KeyCode.ADD || key == KeyCode.EQUALS) {
                increaseSpeed();
            } else if (key == KeyCode.SUBTRACT || key == KeyCode.MINUS) {
                decreaseSpeed();
            }
        });


        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN),
                () -> primaryStage.close()
        );

        primaryStage.setTitle("Змейка (Ctrl+D — выход, +/- — скорость)");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        root.requestFocus();

        new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdate > frameDelayNanos) {
                    update();
                    lastUpdate = now;
                }
            }
        }.start();
    }

    private void increaseSpeed() {
        if (frameDelayNanos > 50_000_000L) frameDelayNanos -= 25_000_000L;
    }

    private void decreaseSpeed() {
        if (frameDelayNanos < 500_000_000L) frameDelayNanos += 25_000_000L;
    }

    private void drawBorders() {
        for (int y = 0; y < FIELD_HEIGHT; y++) {
            drawCell(0, y, Color.GRAY);
            drawCell(FIELD_WIDTH - 1, y, Color.GRAY);
        }
        for (int x = 0; x < FIELD_WIDTH; x++) {
            drawCell(x, 0, Color.GRAY);
            drawCell(x, FIELD_HEIGHT - 1, Color.GRAY);
        }
    }

    private void update() {
        root.getChildren().clear();
        drawBorders();
        snake.move(FIELD_WIDTH, FIELD_HEIGHT);
        for (Cell cell : snake.getBody()) {
            drawCell(cell.getX(), cell.getY(), Color.LIMEGREEN);
        }
    }

    private void drawCell(int x, int y, Color color) {
        Rectangle rect = new Rectangle(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        rect.setFill(color);
        root.getChildren().add(rect);
    }

    public static void main(String[] args) {
        launch(args);
    }
}