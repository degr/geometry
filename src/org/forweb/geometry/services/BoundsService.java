package org.forweb.geometry.services;

import org.forweb.geometry.shapes.*;

public class BoundsService {
    /**
     * Search for bounds intersection points.
     * @param bounds1 Bounds
     * @param bounds2 Bounds
     * @return null in case when bounds are equal, Point[] otherwise
     */
    public static Point[] boundsBoundsIntersections(Bounds bounds1, Bounds bounds2) {
        if(bounds1.equals(bounds2)) {
            return null;
        }
        if(!isBoundsIntersectBounds(bounds1, bounds2)) {
            return PointService.EMPTY;
        }
        Line[] lines1 = getBoundsLines(bounds1);
        Line[] lines2 = getBoundsLines(bounds2);
        Point[] out = new Point[6];
        int index = 0;
        for(Line line1 : lines1) {
            for(Line line2 : lines2) {
                Point[] points = LineService.lineLineIntersections(line1, line2);
                if(points != null) {
                    for(Point point : points) {
                        out[index] = point;
                        index++;
                    }
                }
            }
        }
        return Utils.clearResult(out);
    }


    public static boolean isBoundsIntersectBounds(Bounds boundsA, Bounds boundsB) {
        if(boundsA.getY() >= boundsB.getY()) {
            if(boundsA.getY() > boundsB.getY() + boundsB.getHeight()) {
                return false;
            }
        } else {
            if(boundsB.getY() > boundsA.getY() + boundsA.getHeight()) {
                return false;
            }
        }
        if(boundsA.getX() >= boundsB.getX()) {
            if(boundsA.getX() > boundsB.getX() + boundsB.getWidth()) {
                return false;
            }
        } else {
            if(boundsB.getX() > boundsA.getX() + boundsA.getWidth()) {
                return false;
            }
        }
        return true;
    }


    public static Line[] getBoundsLines(Bounds bounds) {
        Line[] lines = new Line[4];
        double x12 = bounds.getX() + bounds.getWidth();
        double y12 = bounds.getY() + bounds.getHeight();
        Point p11 = new Point(bounds.getX(), bounds.getY());
        Point p12 = new Point(x12, bounds.getY());
        Point p13 = new Point(x12, y12);
        Point p14 = new Point(bounds.getX(), y12);
        lines[0] = new Line(p11, p12);
        lines[1] = new Line(p12, p13);
        lines[2] = new Line(p13, p14);
        lines[3] = new Line(p14, p11);
        return lines;
    }
}
