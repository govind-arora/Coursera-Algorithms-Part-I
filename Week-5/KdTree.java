import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;

public class KdTree {

    private Node root;
    private int size;

    private static class Node {
        private final Point2D point;
        private final RectHV rect;
        private Node lb;
        private Node rb;

        public Node(Point2D point, RectHV rect) {
            this.point = point;

            RectHV r = rect;
            if (r == null)
                r = new RectHV(0, 0, 1, 1);
            this.rect = r;
        }
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // insert a horizontal node
    private Node insertH(Node node, Point2D point, RectHV rect) {
        if (node == null) {
            size++;
            return new Node(point, rect);
        }
        if (node.point.equals(point))
            return node;

        RectHV r;
        int cmp = Point2D.Y_ORDER.compare(node.point, point);
        if (cmp > 0) {
            if (node.lb == null)
                r = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
            else
                r = node.lb.rect;
            node.lb = insertV(node.lb, point, r);
        } else {
            if (node.rb == null)
                r = new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
            else
                r = node.rb.rect;
            node.rb = insertV(node.rb, point, r);
        }

        return node;
    }

    // insert a vertical node
    private Node insertV(Node node, Point2D point, RectHV rect) {
        if (node == null) {
            size++;
            return new Node(point, rect);
        }

        // if two points are same
        if (node.point.equals(point))
            return node;

        RectHV r;
        int cmp = Point2D.X_ORDER.compare(node.point, point);
        if (cmp > 0) {
            if (node.lb == null)
                r = new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax());
            else
                r = node.lb.rect;
            node.lb = insertH(node.lb, point, r);
        } else {
            if (node.rb == null)
                r = new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
            else
                r = node.rb.rect;
            node.rb = insertH(node.rb, point, r);
        }

        return node;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D point) {
        if (point == null)
            throw new IllegalArgumentException();

        if (isEmpty())
            root = insertV(root, point, null);
        else
            root = insertV(root, point, root.rect);
    }

    private boolean contains(Node node, Point2D point, boolean vert) {
        if (node == null)
            return false;
        if (node.point.equals(point))
            return true;
        int cmp;
        if (vert)
            cmp = Point2D.X_ORDER.compare(node.point, point);
        else
            cmp = Point2D.Y_ORDER.compare(node.point, point);
        if (cmp > 0)
            return contains(node.lb, point, !vert);
        else
            return contains(node.rb, point, !vert);
    }

    // does the set contains the point?
    public boolean contains(Point2D point) {
        if (point == null)
            throw new IllegalArgumentException();

        return contains(root, point, true);
    }

    private void draw(Node node, boolean vert) {
        if (node.lb != null)
            draw(node.lb, !vert);
        if (node.rb != null)
            draw(node.rb, !vert);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.point.x(), node.point.y());

        double xmin, ymin, xmax, ymax;
        if (vert) {
            StdDraw.setPenColor(StdDraw.RED);
            xmin = node.point.x();
            xmax = node.point.x();
            ymin = node.rect.ymin();
            ymax = node.rect.ymax();
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            ymin = node.point.y();
            ymax = node.point.y();
            xmin = node.rect.xmin();
            xmax = node.rect.xmax();
        }
        StdDraw.setPenRadius();
        StdDraw.line(xmin, ymin, xmax, ymax);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);
        if (isEmpty())
            return;
        draw(root, true);
    }

    private void range(Node node, RectHV rect, Queue<Point2D> queue) {
        if (node == null)
            return;
        if (rect.contains(node.point))
            queue.enqueue(node.point);
        if (node.lb != null && rect.intersects(node.lb.rect))
            range(node.lb, rect, queue);
        if (node.rb != null && rect.intersects(node.rb.rect))
            range(node.rb, rect, queue);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();

        Queue<Point2D> queue = new Queue<Point2D>();
        range(root, rect, queue);
        return queue;
    }

    private Point2D nearest(Node node, Point2D point, Point2D mp, boolean vert) {
        Point2D min = mp;

        if (node == null)
            return min;
        if (point.distanceSquaredTo(node.point) < point.distanceSquaredTo(min))
            min = node.point;

        if (vert) {
            if (node.point.x() < point.x()) {
                min = nearest(node.rb, point, min, !vert);
                if (node.lb != null && (min.distanceSquaredTo(point) > node.lb.rect.distanceSquaredTo(point)))
                    min = nearest(node.lb, point, min, !vert);
            } else {
                min = nearest(node.lb, point, min, !vert);
                if (node.rb != null && (min.distanceSquaredTo(point) > node.rb.rect.distanceSquaredTo(point)))
                    min = nearest(node.rb, point, min, !vert);
            }
        } else {
            if (node.point.y() < point.y()) {
                min = nearest(node.rb, point, min, !vert);
                if (node.lb != null && (min.distanceSquaredTo(point) > node.lb.rect.distanceSquaredTo(point)))
                    min = nearest(node.lb, point, min, !vert);
            } else {
                min = nearest(node.lb, point, min, !vert);
                if (node.rb != null && (min.distanceSquaredTo(point) > node.rb.rect.distanceSquaredTo(point)))
                    min = nearest(node.rb, point, min, !vert);
            }
        }
        return min;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D point) {
        if (point == null)
            throw new IllegalArgumentException();

        if (isEmpty())
            return null;
        return nearest(root, point, root.point, true);
    }
}