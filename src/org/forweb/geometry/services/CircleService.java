package org.forweb.geometry.services;

import org.forweb.geometry.shapes.Bounds;
import org.forweb.geometry.shapes.Circle;
import org.forweb.geometry.shapes.Line;
import org.forweb.geometry.shapes.Point;

public class CircleService {
    public static Bounds getBounds(Circle circle) {
        double d = circle.getRadius() * 2;
        return new Bounds(circle.getX() - circle.getRadius(), circle.getY() - circle.getRadius(), d, d);
    }

    public static Point[] circleBoundIntersection(Circle circle, Bounds bounds) {
        if (!BoundsService.isBoundInsideOfBound(CircleService.getBounds(circle), bounds)) {
            return Utils.EMPTY;
        }
        Line[] lines = BoundsService.getBoundLines(bounds);
        Point[] out = new Point[8];
        int index = 0;
        for (Line line : lines) {
            Point[] points = LineService.lineIntersectCircle(line, circle);
            if (points.length > 0) {
                for(Point point : points) {
                    out[index] = point;
                    index++;
                }
            }
        }
        return Utils.clearResult(out);
    }

    public static boolean isCircleIntersectBound(Circle circle, Bounds bounds) {
        return false;
    }
}
