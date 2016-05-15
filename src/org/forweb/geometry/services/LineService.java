package org.forweb.geometry.services;

import org.forweb.geometry.shapes.Bounds;
import org.forweb.geometry.shapes.Circle;
import org.forweb.geometry.shapes.Line;
import org.forweb.geometry.shapes.Point;


public class LineService {

    public static Point[] lineIntersectCircle(Line line, Circle circle) {
        /*if(!BoundsService.isBoundInsideOfBound(getBounds(line), CircleService.getBounds(circle))) {
            return Utils.EMPTY;
        }*/
        Point pointB = line.getB();
        Point pointA = line.getA();
        if(pointA.equals(pointB)) {
            if (Math.pow(pointA.getX() - circle.getX(), 2) + Math.pow(pointA.getY() - circle.getY(), 2) == circle.getRadius() * circle.getRadius()) {
                return new Point[]{pointA};
            } else {
                return Utils.EMPTY;
            }
        }
        double baX = pointB.getX() - pointA.getX();
        double baY = pointB.getY() - pointA.getY();
        double caX = circle.getX() - pointA.getX();
        double caY = circle.getY() - pointA.getY();

        double a = baX * baX + baY * baY;
        double bBy2 = baX * caX + baY * caY;
        double c = caX * caX + caY * caY - circle.getRadius() * circle.getRadius();

        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return Utils.EMPTY;
        }
        double tmpSqrt = Math.sqrt(disc);
        double abScalingFactor1 = -pBy2 + tmpSqrt;
        double abScalingFactor2 = -pBy2 - tmpSqrt;

        Point p1 = new Point(
                pointA.getX() - baX * abScalingFactor1,
                pointA.getY() - baY * abScalingFactor1
        );
        Point out1 = getMiddle(pointA, pointB, p1);
        if (disc == 0) {
            if(out1 == p1) {
                return new Point[]{p1};
            } else {
                return Utils.EMPTY;
            }
        }
        Point p2 = new Point(
                pointA.getX() - baX * abScalingFactor2,
                pointA.getY() - baY * abScalingFactor2
        );
        Point out2 = getMiddle(pointA, pointB, p2);
        if(out1 == p1) {
            if(out2 == p2) {
                return new Point[]{p1, p2};
            } else {
                return new Point[]{p1};
            }
        } else {
            if(out2 == p2) {
                return new Point[]{p2};
            } else {
                return Utils.EMPTY;
            }
        }
    }



    public static Point[] lineBoundIntersections(Line line, Bounds rectangle) {
        if(!BoundsService.isBoundInsideOfBound(rectangle, getBounds(line))) {
            return Utils.EMPTY;
        }
        Line[] lines1 = BoundsService.getBoundLines(rectangle);
        Point[] out = new Point[4];
        int index = 0;
        for(Line line1 : lines1) {
            Point[] points = lineLineIntersections(line1, line);
            if(points != null) {
                for(Point point : points) {
                    out[index] = point;
                    index++;
                }
            }
        }
        return Utils.clearResult(out);
    }


    public static Point[] lineLineIntersections(Line lineA, Line lineB) {
        if(lineA.equals(lineB)) {
            return new Point[]{lineA.getA(), lineA.getB()};
        }
        double x1 = lineA.getA().getX();
        double x2 = lineA.getB().getX();
        double y1 = lineA.getA().getY();
        double y2 = lineA.getB().getY();
        double x3 = lineB.getA().getX();
        double x4 = lineB.getB().getX();
        double y3 = lineB.getA().getY();
        double y4 = lineB.getB().getY();

        //vectors
        double v1, v2, v3, v4;
        double xv12, xv13, xv14, xv31, xv32, xv34, yv12, yv13, yv14, yv31, yv32, yv34;

        //vector coordinates
        xv12 = x2 - x1;
        xv13 = x3 - x1;
        xv14 = x4 - x1;
        yv12 = y2 - y1;
        yv13 = y3 - y1;
        yv14 = y4 - y1;

        xv31 = x1 - x3;
        xv32 = x2 - x3;
        xv34 = x4 - x3;
        yv31 = y1 - y3;
        yv32 = y2 - y3;
        yv34 = y4 - y3;

        // vector multiplications
        v1 = xv34 * yv31 - yv34 * xv31;
        v2 = xv34 * yv32 - yv34 * xv32;
        v3 = xv12 * yv13 - yv12 * xv13;
        v4 = xv12 * yv14 - yv12 * xv14;

        if ((v1 * v2) <= 0 && (v3 * v4) <= 0) {
            double A1, B1, C1, A2, B2, C2;
            A1 = y2 - y1;
            A2 = y4 - y3;
            B1 = x1 - x2;
            B2 = x3 - x4;
            C1 = x1 * A1 + y1 * B1;
            C2 = x3 * A2 + y3 * B2;

            double D = (A1 * B2 - B1 * A2);

            if (D != 0) {
                double Dx = C1 * B2 - B1 * C2;
                double Dy = A1 * C2 - C1 * A2;
                return new Point[]{new Point(Dx / D, Dy / D)};
            } else {
                if ((B1 == B2 && B1 == 0) || (A1 / B1 == A2 / B2 && C1 / B1 == C2 / B2)) {
                    Point start = getMiddle(lineA.getA(), lineA.getB(), lineB.getA());
                    Point end;
                    if (start == lineA.getA()) {
                        end = getMiddle(lineA.getB(), lineB.getA(), lineB.getB());
                    } else if (start == lineA.getB()) {
                        end = getMiddle(lineA.getA(), lineB.getA(), lineB.getB());
                    } else {
                        end = getMiddle(lineA.getA(), lineA.getB(), lineB.getB());
                    }
                    return new Point[]{start, end};
                } else {
                    return Utils.EMPTY;
                }
            }
        } else {
            return Utils.EMPTY;
        }
    }


    /**
     * @param pointA start
     * @param poinB end
     * @return distance between two points
     */
    public static double getDistance(Point pointA, Point poinB) {
        return Math.sqrt(
                Math.pow(poinB.getX()-pointA.getX(), 2) + Math.pow(poinB.getY()-pointA.getY(), 2)
        );
    }

    /**
     * All points must be on one line. Return middle point.
     * @param pointA (candidate)
     * @param pointB (candidate)
     * @param pointC (candidate)
     * @return middle point
     */
    private static Point getMiddle(Point pointA, Point pointB, Point pointC){
        double ab = getDistance(pointA, pointB);
        double bc = getDistance(pointB, pointC);
        double ca = getDistance(pointC, pointA);
        if(ab >= bc && ab >= ca) {
            return pointC;
        } else if(ca >= ab && ca >= bc) {
            return pointB;
        } else {
            return pointA;
        }
    }


    public static Bounds getBounds(Line line) {
        Point p1 = line.getA();
        Point p2 = line.getB();

        double x;
        double width;
        double y;
        double height;

        if (p1.getX() < p2.getX()) {
            x = p1.getX();
            width = p2.getX() - p1.getX();
        } else {
            x = p2.getX();
            width = p1.getX() - p2.getX();
        }
        if (p1.getY() < p2.getY()) {
            y = p1.getY();
            height = p2.getY() - p1.getY();
        } else {
            y = p2.getY();
            height = p1.getY() - p2.getY();
        }
        return new Bounds(x, y, width, height);
    }
}
