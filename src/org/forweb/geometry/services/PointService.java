package org.forweb.geometry.services;

import org.forweb.geometry.shapes.Circle;
import org.forweb.geometry.shapes.Point;

public class PointService {
    public static int pointBelongToCircle(Point point, Circle circle) {
        double r = Math.pow(circle.getRadius(), 2),
                a = Math.pow(circle.getX() - point.getX(), 2),
                b = Math.pow(circle.getY() - point.getY(), 2);
        double check = a + b;
        if (check == r) {
            return 0;
        } else if (check < r) {
            return 1;
        } else {
            return -1;
        }
    }

    public static boolean isPointOnCircle(Point point, Circle circle) {
        return pointBelongToCircle(point, circle) >= 0;
    }


}
