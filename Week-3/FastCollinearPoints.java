import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> lineSegments = new ArrayList<>();

    // Finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        checkNullPoints(points);
        checkDuplicatePoints(points);

        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);

        for (int i = 0; i < pointsCopy.length - 3; i++) {
            Arrays.sort(pointsCopy);
            Arrays.sort(pointsCopy, pointsCopy[i].slopeOrder());

            for (int p = 0, first = 1, last = 2; last < pointsCopy.length; last++) {
                while (last < pointsCopy.length && Double.compare(pointsCopy[p].slopeTo(pointsCopy[first]),
                        pointsCopy[p].slopeTo(pointsCopy[last])) == 0) {
                    last++;
                }

                if (last - first >= 3 && pointsCopy[p].compareTo(pointsCopy[first]) < 0) {
                    lineSegments.add(new LineSegment(pointsCopy[p], pointsCopy[last - 1]));
                }

                first = last;
            }
        }
    }

    // The number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // The line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    // Check if there no null values
    private void checkNullPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    // Check if there no duplicated points
    private void checkDuplicatePoints(Point[] points) {
        for (int p = 0; p < points.length - 1; p++) {
            for (int q = p + 1; q < points.length; q++) {
                if (points[p].compareTo(points[q]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    // Test client
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}