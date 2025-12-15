import java.util.LinkedList;


public class Snake {

    private LinkedList<Cell> snakeCoordins;
    private Cell direction;

    public Snake(int fieldWidth, int fieldHeight) {
        snakeCoordins = new LinkedList<>();
        direction = new Cell(
                Math.random() < 0.5 ? -1 : 1,
                Math.random() < 0.5 ? -1 : 1
        );

        int headX = fieldWidth / 2;
        int headY = fieldHeight /2;

        snakeCoordins.add(new Cell(headX, headY));
        for (int i = 1; i < 5; i++) {
            snakeCoordins.addLast(new Cell(headX-i, headY));
        }
    }

    public void move(int fieldWidth, int fieldHeight) {
        Cell head = snakeCoordins.getFirst();
        int newX = head.getX() + direction.getX();
        int newY = head.getY() + direction.getY();

        //момент отражения
        if (newX <= 0 || newX >= fieldWidth - 1) {
            direction.setX(-direction.getX());
            newX = head.getX() + direction.getX();
        }

        if (newY <= 0 || newY >= fieldHeight - 1) {
            direction.setY(-direction.getY());
            newY = head.getY() + direction.getY();
        }

        snakeCoordins.addFirst(new Cell(newX, newY));
        snakeCoordins.removeLast();
    }

    public LinkedList<Cell> getBody() {
        return new LinkedList<>(snakeCoordins);
    }

}