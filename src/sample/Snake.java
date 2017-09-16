package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Komp on 2017-09-02.
 */
public class Snake {
    private Point head;
    private List<Point> body;

    public Snake(Point head, List<Point> body) {
        this.head = head;
        this.body = body;
    }
    public Snake(Point head) {
        this.head = head;
        this.body = new ArrayList<>();
    }

    public Point getHead() {
        return head;
    }

    public List<Point> getBody() {
        return body;
    }

    public int getIndex() {
        return body.size();
    }

    public void move(Direction direction, boolean appleEaten) {
        body.add(head);
        head = head.moveTo(direction);
        if (!appleEaten) {
            body.remove(0);
        }
    }

    public boolean contains (Point point) {
        return head.equals(point) || body.contains(point);
    }
}
