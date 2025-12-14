import java.util.LinkedList;


public class Snake {

    private LinkedList<Cell> snakeCoordinates;
    private Cell direction;

    public Snake(int fieldWidth, int fieldHeight) {
        snakeCoordinates = new LinkedList<>();
        direction = new Cell(1, 1);
        speed = 500;

        // Генерация начальной позиции головы
        int headX = (int) (Math.random() * (fieldWidth - 2)) + 1;
        int headY = (int) (Math.random() * (fieldHeight - 2)) + 1;

        snakeCoordinates.add(new Cell(headX, headY));
        for (int i = 1; i < 5; i++) {
            snakeCoordinates.addLast(new Cell(headX-i, headY));
        }
    }

    // Двигает змейку на один шаг с автоматическим отражением от стен.
    public void move(int fieldWidth, int fieldHeight) {
        Cell head = snakeCoordinates.getFirst();
        int newX = head.getX() + direction.getX();
        int newY = head.getY() + direction.getY();

        // Отражение от вертикальных стен
        if (newX <= 0 || newX >= fieldWidth - 1) {
            direction.setX(-direction.getX());
            newX = head.getX() + direction.getX();
        }

        // Отражение от горизонтальных стен
        if (newY <= 0 || newY >= fieldHeight - 1) {
            direction.setY(-direction.getY());
            newY = head.getY() + direction.getY();
        }

        // Добавляем новую голову, удаляем хвост
        snakeCoordinates.addFirst(new Cell(newX, newY));
        snakeCoordinates.removeLast();
    }

    public LinkedList<Cell> getBody() {
        return new LinkedList<>(snakeCoordinates);
    }

    public int getHeadX() {
        return snakeCoordinates.getFirst().getX();
    }

    public int getHeadY() {
        return snakeCoordinates.getFirst().getY();
    }

}