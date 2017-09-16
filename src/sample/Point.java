package sample;

import java.util.Random;

/**
 * Created by Komp on 2017-09-02.
 */
public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point moveTo(Direction direction) {
        switch (direction) {
            case DOWN:
                return new Point(x, y + 1);
            case UP:
                return new Point(x, y - 1);
            case RIGHT:
                return new Point(x + 1, y);
            case LEFT:
                return new Point(x - 1, y);
            case START_POSITION:
                return new Point(0,0);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals (Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Point) {
            Point other = (Point) obj;
            return getX() == other.getX() && getY() == other.getY();
        } else return false;
    }
}
