package org.forweb.geometry.services;

import org.forweb.geometry.shapes.*;

public class BoundsService {
    public static Point[] circleIntersectRectangle(Circle circle, Bounds rectangle) {
        return null;
    }
    public static Point[] circleIntersectCircle(Circle circle1, Circle circle2) {
        return null;
    }


    public static Point[] boundBoundIntersections(Bounds rectangle1, Bounds rectangle2) {
        if(rectangle1.equals(rectangle2)) {
            return null;
        }
        if(!isBoundInsideOfBound(rectangle1, rectangle2)) {
            return Utils.EMPTY;
        }
        Line[] lines1 = getBoundLines(rectangle1);
        Line[] lines2 = getBoundLines(rectangle2);
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


    public static boolean isBoundInsideOfBound(Bounds boundsA, Bounds boundsB) {
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


    public static Line[] getBoundLines(Bounds bounds) {
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
