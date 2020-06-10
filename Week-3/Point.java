import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public double slopeTo(Point that) {
        if (this.compareTo(that) == 0)
            return Double.NEGATIVE_INFINITY;

        if (that.y == this.y)
            return 0.0;

        if (that.x == this.x)
            return Double.POSITIVE_INFINITY;

        double diffX = that.x - this.x;
        double diffY = that.y - this.y;

        return diffY / diffX;
    }

    public int compareTo(Point that) {
        if ((y < that.y) || (x < that.x))
            return -1;
        if ((y > that.y) || (x > that.x))
            return +1;
        return 0;
    }

    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point a, Point b) {
                double slopeDiff = slopeTo(a) - slopeTo(b);
                if (slopeDiff > 0)
                    return 1;
                else if (slopeDiff < 0)
                    return -1;
                else
                    return 0;
            }
        };
    }

    public static void main(String[] args) {
        /*
         * Point first = new Point(1, 2); Point second = new Point(2, 4);
         * 
         * System.out.println(first.slopeTo(second));
         */
    }
}