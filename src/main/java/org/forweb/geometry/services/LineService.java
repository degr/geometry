package org.forweb.geometry.services;

import org.forweb.geometry.shapes.Bounds;
import org.forweb.geometry.shapes.Circle;
import org.forweb.geometry.shapes.Line;
import org.forweb.geometry.shapes.Point;


public class LineService {

    public static Point[] lineIntersectCircle(Line line, Circle circle) {
        /*without this check it work a little bit faster.
        if(!BoundsService.isBoundsIntersectBounds(getBounds(line), CircleService.getBounds(circle))) {
            return Utils.EMPTY;
        }*/
        Point pointB = line.getB();
        Point pointA = line.getA();
        if(pointA.equals(pointB)) {
            if (Math.pow(pointA.getX() - circle.getX(), 2) + Math.pow(pointA.getY() - circle.getY(), 2) == circle.getRadius() * circle.getRadius()) {
                return new Point[]{pointA};
            } else {
                return PointService.EMPTY;
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
        if(disc <= 0.0001 && disc >= -0.0001) {
            disc = 0;
        }
        if (disc < 0) {
            return PointService.EMPTY;
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
                return PointService.EMPTY;
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
                return PointService.EMPTY;
            }
        }
    }

    private static double getDistance(Line line) {
        return getDistance(line.getA(), line.getB());
    }


    public static Point[] lineBoundsIntersections(Line line, Bounds rectangle) {
        if(!BoundsService.isBoundsIntersectBounds(rectangle, getBounds(line))) {
            return PointService.EMPTY;
        }
        Line[] lines1 = BoundsService.getBoundsLines(rectangle);
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
                    return PointService.EMPTY;
                }
            }
        } else {
            return PointService.EMPTY;
        }
    }


    /**
     * @param pointA start
     * @param poinB end
     * @return distance between two points
     */
    public static double getDistance(Point pointA, Point poinB) {
        return getDistance(pointA.getX(), pointA.getY(), poinB.getX(), poinB.getY());
    }

    /**
     * Distance between two points
     * @param x1 point1 x
     * @param y1 point1 y
     * @param x2 point2 x
     * @param y2 point2 y
     * @return distance between them
     */
    public static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
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

    public boolean lineHasIntersections(Line lineA, Line lineB) {
        return doIntersect(lineA.getA(), lineA.getB(), lineB.getA(), lineB.getB());
    }

    private boolean onSegment(Point p, Point q, Point r)
    {
        return (q.getX() <= Math.max(p.getX(), r.getX()) && q.getX() >= Math.min(p.getX(), r.getX()) &&
                q.getY() <= Math.max(p.getY(), r.getY()) && q.getY() >= Math.min(p.getY(), r.getY()));
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are colinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    //return int
    private int orientation(Point p, Point q, Point r)
    {
        // See http://www.geeksforgeeks.org/orientation-3-ordered-points/
        // for details of below formula.
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX()) -
                (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (val == 0) return 0;  // colinear

        return (val > 0)? 1: 2; // clock or counterclock wise
    }

    // The main function that returns true if line segment 'p1q1'
// and 'p2q2' intersect.
    private boolean doIntersect(Point p1, Point q1, Point p2, Point q2)
    {
        // Find the four orientations needed for general and
        // special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        // p1, q1 and p2 are colinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 and p2 are colinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 and p1 are colinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 and q1 are colinear and q1 lies on segment p2q2
        return o4 == 0 && onSegment(p2, q1, q2);
    }
}
