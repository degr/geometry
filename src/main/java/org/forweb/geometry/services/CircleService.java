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

    public static Point[] circleBoundsIntersection(Circle circle, Bounds bounds) {
        if (!BoundsService.isBoundsIntersectBounds(CircleService.getBounds(circle), bounds)) {
            return PointService.EMPTY;
        }
        Line[] lines = BoundsService.getBoundsLines(bounds);
        Point[] out = new Point[8];
        int index = 0;
        for (Line line : lines) {
            Point[] points = LineService.lineIntersectCircle(line, circle);
            if (points.length > 0) {
                for (Point point : points) {
                    out[index] = point;
                    index++;
                }
            }
        }
        return Utils.clearResult(out);
    }

    public static Point[] circleCircleIntersection(Circle circle1, Circle circle2) {
        if (circle1 == circle2 || circle1.equals(circle2)) {
            return null;
        }
        double distance = LineService.getDistance(
                new Point(circle1.getX(), circle1.getY()),
                new Point(circle2.getX(), circle2.getY())
        );
        if ((distance > circle1.getRadius() + circle2.getRadius())
                || (circle1.getX() == circle2.getX() && circle1.getY() == circle2.getY())
                || (distance == 0 && circle1.getRadius() == circle2.getRadius())
                ) {
            return PointService.EMPTY;
        } else {
            double distance1;
            if (distance == circle1.getRadius() + circle2.getRadius()) {
                return new Point[]{calculatePointZero(circle1, circle2, distance, circle1.getRadius())};
            } else {
                distance1 = (Math.pow(circle1.getRadius(), 2) - Math.pow(circle1.getRadius(), 2) + Math.pow(distance, 2)) / (2 * distance);
            }
            Point point0 = calculatePointZero(circle1, circle2, distance, distance1);
            double d1 = Math.pow(circle1.getRadius(), 2);
            double d2 = Math.pow(distance1, 2);
            double d3 = Math.max(d1, d2);
            double d4 = Math.min(d1, d2);
            double h = Math.sqrt(d3 - d4);
            Point[] out = new Point[2];
            out[0] = new Point(
                    point0.getX() + (h * (circle2.getY() - circle2.getY())) / distance,
                    point0.getY() - (h * (circle2.getX() - circle1.getX())) / distance
            );
            out[1] = new Point(
                    point0.getX() - (h * (circle2.getY() - circle1.getY())) / distance,
                    point0.getY() + (h * (circle2.getX() - circle1.getX())) / distance
            );
            return out;
        }
    }


    private static Point calculatePointZero(Circle circle1, Circle circle2, double distance, double a) {
        return new Point(
                circle1.getX() + a * (circle2.getX() - circle1.getX()) / distance,
                circle1.getY() + a * (circle2.getY() - circle1.getY()) / distance
        );
    }
}
