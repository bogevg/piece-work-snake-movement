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

    private static final int SEGMENT_DIMENSION = 20;
    private static final int GRID_COLUMNS = 20;
    private static final int GRID_ROWS = 15;

    private Snake gameSnake;
    private Pane gameCanvas;
    private long intervalBetweenFrames = 200_000_000L;

    @Override
    public void start(Stage mainWindow) {
        gameSnake = new Snake(GRID_COLUMNS, GRID_ROWS);
        gameCanvas = new Pane();
        gameCanvas.setPrefSize(GRID_COLUMNS * SEGMENT_DIMENSION, GRID_ROWS * SEGMENT_DIMENSION);

        drawBorderWalls();

        Scene mainScene = new Scene(gameCanvas, Color.BLACK);

        mainScene.setOnKeyPressed(keyEvent -> {
            KeyCode pressedKey = keyEvent.getCode();
            if (pressedKey == KeyCode.ADD || pressedKey == KeyCode.EQUALS) {
                makeFaster();
            } else if (pressedKey == KeyCode.SUBTRACT || pressedKey == KeyCode.MINUS) {
                makeSlower();
            }
        });

        mainScene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN),
                () -> mainWindow.close()
        );

        mainWindow.setTitle("Змейка (Ctrl+D — выход, +/- — скорость)");
        mainWindow.setScene(mainScene);
        mainWindow.setResizable(false);
        mainWindow.show();

        gameCanvas.requestFocus();

        new AnimationTimer() {
            private long previousFrameTime = 0;
            @Override
            public void handle(long currentTime) {
                if (currentTime - previousFrameTime > intervalBetweenFrames) {
                    refreshDisplay();
                    previousFrameTime = currentTime;
                }
            }
        }.start();
    }

    private void makeFaster() {
        if (intervalBetweenFrames > 50_000_000L) intervalBetweenFrames -= 25_000_000L;
    }

    private void makeSlower() {
        if (intervalBetweenFrames < 500_000_000L) intervalBetweenFrames += 25_000_000L;
    }

    private void drawBorderWalls() {
        for (int row = 0; row < GRID_ROWS; row++) {
            drawGridSquare(0, row, Color.GRAY);
            drawGridSquare(GRID_COLUMNS - 1, row, Color.GRAY);
        }
        for (int col = 0; col < GRID_COLUMNS; col++) {
            drawGridSquare(col, 0, Color.GRAY);
            drawGridSquare(col, GRID_ROWS - 1, Color.GRAY);
        }
    }

    private void refreshDisplay() {
        gameCanvas.getChildren().clear();
        drawBorderWalls();
        gameSnake.move(GRID_COLUMNS, GRID_ROWS);
        for (Cell segment : gameSnake.getBody()) {
            drawGridSquare(segment.getX(), segment.getY(), Color.LIMEGREEN);
        }
    }

    private void drawGridSquare(int column, int row, Color fillColor) {
        Rectangle square = new Rectangle(column * SEGMENT_DIMENSION, row * SEGMENT_DIMENSION,
                SEGMENT_DIMENSION, SEGMENT_DIMENSION);
        square.setFill(fillColor);
        gameCanvas.getChildren().add(square);
    }

    public static void main(String[] args) {
        launch(args);
    }
}