import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> lineSegments;

    public BruteCollinearPoints(Point[] points) {

        checkNullPoints(points);
        checkDuplicatePoints(points);

        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);

        int pointsLength = pointsCopy.length;
        lineSegments = new ArrayList<LineSegment>();

        for (int p = 0; p < (pointsLength - 3); p++) {
            for (int q = (p + 1); q < (pointsLength - 2); q++) {
                for (int r = (q + 1); r < (pointsLength - 1); r++) {
                    if (pointsCopy[p].slopeTo(pointsCopy[q]) != pointsCopy[p].slopeTo(pointsCopy[r])) {
                        continue;
                    }

                    for (int s = (r + 1); s < pointsLength; s++) {
                        if (pointsCopy[p].slopeTo(pointsCopy[q]) == pointsCopy[p].slopeTo(pointsCopy[s])) {
                            lineSegments.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
                        }
                    }
                }
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

        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}