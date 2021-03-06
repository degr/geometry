package org.forweb.geometry.services;

import org.forweb.geometry.misc.Angle;
import org.forweb.geometry.shapes.Circle;
import org.forweb.geometry.shapes.Line;
import org.forweb.geometry.shapes.Point;

public class PointService {

    public static final Point[] EMPTY = new Point[0];

    public static Point translate(Point rotationCenter, Point point, Angle angle) {
        if (angle.doubleValue() == 0) {
            return point;
        }
        double x = (angle.cos() * (point.getX() - rotationCenter.getX())) -
                (angle.sin() * (point.getY() - rotationCenter.getY())) +
                rotationCenter.getX();
        double y = (angle.sin() * (point.getX() - rotationCenter.getX())) +
                (angle.cos() * (point.getY() - rotationCenter.getY())) +
                rotationCenter.getY();
        return new Point(x, y);
    }

    /**
     * Find point position realtive to circle
     * @param point Point
     * @param circle Circle
     * @return 0 if point ON circle, 1 if point INSIDE of circle, -1 if point OUTSIDE of circle
     */
    public static double pointBelongToCircle(Point point, Circle circle) {
        double r = Math.pow(circle.getRadius(), 2),
                a = Math.pow(circle.getX() - point.getX(), 2),
                b = Math.pow(circle.getY() - point.getY(), 2);
        double check = a + b;
        if (check == r) {
            return 0;
        } else if (check < r) {
            double back = r - check;
            return back / r;
        } else {
            return -1;
        }
    }

    /**
     * Check is point belong to circle, or is it outside of circle
     * @param point Point
     * @param circle Circle
     * @return boolean
     */
    public static boolean isPointOnCircle(Point point, Circle circle) {
        return pointBelongToCircle(point, circle) >= 0;
    }

    public static boolean pointBelongToLine(Point point, Line line) {
        if(point.equals(line.getA()) || point.equals(line.getB())) {
            return true;
        } else {
            double dxc = point.getX() - line.getA().getX();
            double dyc = point.getY() - line.getA().getY();

            double dxl = line.getB().getX() - line.getA().getX();
            double dyl = line.getB().getY() - line.getA().getY();

            double cross = dxc * dyl - dyc * dxl;
            if (cross != 0) {
                return false;
            } else {
                if (Math.abs(dxl) >= Math.abs(dyl)) {
                    return dxl > 0 ?
                            line.getA().getX() <= point.getX() && point.getX() <= line.getB().getX() :
                            line.getB().getX() <= point.getX() && point.getX() <= line.getA().getX();
                } else {
                    return dyl > 0 ?
                            line.getA().getY() <= point.getY() && point.getY() <= line.getB().getY() :
                            line.getB().getY() <= point.getY() && point.getY() <= line.getA().getY();
                }
            }
        }
    }
}
