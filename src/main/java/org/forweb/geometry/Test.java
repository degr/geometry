package org.forweb.geometry;

import org.forweb.geometry.services.BoundsService;
import org.forweb.geometry.services.CircleService;
import org.forweb.geometry.services.LineService;
import org.forweb.geometry.services.PointService;
import org.forweb.geometry.shapes.*;

public class Test {
    public static void main(String ... args) {
        tesBounds();
        testLineLine();
        pointTest();
        testCircles();
        lineIntersectCircle();
    }

    private static void testCircles() {
        System.out.println("==== circles tests ====");
        Circle circle1 = new Circle(10, 10, 10);
        Circle circle2 = new Circle(15, 10, 10);
        System.out.println("circle intersect circle (2)");
        print(CircleService.circleCircleIntersection(circle1, circle2));

        Circle circle3 = new Circle(10, 10, 10);
        Circle circle4 = new Circle(40, 10, 20);
        System.out.println("circle intersect circle (1)");
        print(CircleService.circleCircleIntersection(circle3, circle4));

        Circle circle5 = new Circle(10, 10, 10);
        Circle circle6 = new Circle(40, 10, 10);
        System.out.println("circle intersect circle (0)");
        print(CircleService.circleCircleIntersection(circle5, circle6));


    }

    private static void pointTest() {
        System.out.println("==== point test bounds ====");
        System.out.println("point on circle (0) : " + PointService.pointBelongToCircle(new Point(0, 10), new Circle(10, 10, 10)));
        System.out.println("point in circle (0.5) : " + PointService.pointBelongToCircle(new Point(5, 5), new Circle(10, 10, 10)));
        System.out.println("point in circle (0.82) : " + PointService.pointBelongToCircle(new Point(7, 7), new Circle(10, 10, 10)));
        System.out.println("point in circle (1) : " + PointService.pointBelongToCircle(new Point(10, 10), new Circle(10, 10, 10)));
        System.out.println("point outside of circle (1) : " + PointService.pointBelongToCircle(new Point(0, 7), new Circle(10, 10, 10)));

        System.out.println("point on line (true) : " + PointService.pointBelongToLine(
                new Point(15, 15),
                new Line(10, 10, 20, 20))
        );
        System.out.println("point on line || oY (true) : " + PointService.pointBelongToLine(
                new Point(10, 15),
                new Line(10, 10, 10, 20))
        );
        System.out.println("point on line || oX (true) : " + PointService.pointBelongToLine(
                new Point(15, 10),
                new Line(10, 10, 20, 10))
        );

        System.out.println("point outside of line (false) : " + PointService.pointBelongToLine(
                new Point(15, 15),
                new Line(10, 10, 20, 10))
        );
        System.out.println("point on line start (true) : " + PointService.pointBelongToLine(
                new Point(15, 15),
                new Line(15, 15, 20, 10))
        );
        System.out.println("point on line end (true) : " + PointService.pointBelongToLine(
                new Point(20, 10),
                new Line(15, 15, 20, 10))
        );

    }


    public static void tesBounds() {
        System.out.println("==== test bounds ====");
        
        Bounds bound1 = new Bounds(10, 10, 20, 20);
        Bounds bound2 = new Bounds(20, 20, 20, 20);

        System.out.println("bound inside bound: "
                + BoundsService.isBoundsIntersectBounds(bound1, bound2));
        System.out.println("bound inside bound (revert): "
                + BoundsService.isBoundsIntersectBounds(bound2, bound1));

        Bounds bound3 = new Bounds(10, 10, 20, 20);
        Bounds bound4 = new Bounds(30, 10, 20, 20);

        System.out.println("bound on border x: "
                + BoundsService.isBoundsIntersectBounds(bound3, bound4));
        System.out.println("bound on border x(revert): "
                + BoundsService.isBoundsIntersectBounds(bound4, bound3));


        Bounds bound5 = new Bounds(10, 10, 20, 20);
        Bounds bound6 = new Bounds(10, 30, 20, 20);

        System.out.println("bound on border y: "
                + BoundsService.isBoundsIntersectBounds(bound5, bound6));
        System.out.println("bound on border y(revert): "
                + BoundsService.isBoundsIntersectBounds(bound6, bound5));

        Bounds bound7 = new Bounds(10, 10, 20, 20);
        Bounds bound8 = new Bounds(30, 30, 20, 20);

        System.out.println("bound on point: "
                + BoundsService.isBoundsIntersectBounds(bound7, bound8));
        System.out.println("bound on point(revert): "
                + BoundsService.isBoundsIntersectBounds(bound8, bound7));

        Bounds bound9 = new Bounds(10, 10, 20, 20);
        Bounds bound10 = new Bounds(40, 40, 20, 20);

        System.out.println("bound outside bound: "
                + !BoundsService.isBoundsIntersectBounds(bound9, bound10));
        System.out.println("bound outside bound(revert): "
                + !BoundsService.isBoundsIntersectBounds(bound10, bound9));


        Bounds bound11 = new Bounds(10, 10, 0, 0);
        Bounds bound12 = new Bounds(10, 10, 0, 0);

        System.out.println("zero-size bound in zero-size bound: "
                + BoundsService.isBoundsIntersectBounds(bound11, bound12));
        System.out.println("zero-size bound in zero-size bound(revert): "
                + BoundsService.isBoundsIntersectBounds(bound12, bound11));


        Bounds bound13 = new Bounds(10, 10, 0, 0);
        Bounds bound14 = new Bounds(15, 15, 0, 0);

        System.out.println("zero-size bound outside zero-size bound: "
                + !BoundsService.isBoundsIntersectBounds(bound13, bound14));
        System.out.println("zero-size bound outside zero-size bound(revert): "
                + !BoundsService.isBoundsIntersectBounds(bound14, bound13));

        Bounds bound15 = new Bounds(10, 10, 0, 0);
        Bounds bound16 = new Bounds(9, 9, 20, 20);

        System.out.println("zero-size bound inside bound: "
                + BoundsService.isBoundsIntersectBounds(bound15, bound16));
        System.out.println("zero-size bound inside bound(revert): "
                + BoundsService.isBoundsIntersectBounds(bound16, bound15));


        Bounds bound17 = new Bounds(50, 50, 0, 0);
        Bounds bound18 = new Bounds(10, 10, 20, 20);

        System.out.println("zero-size bound outside bound: "
                + !BoundsService.isBoundsIntersectBounds(bound17, bound18));
        System.out.println("zero-size bound outside bound(revert): "
                + !BoundsService.isBoundsIntersectBounds(bound18, bound17));

    }

    public static void testLineLine() {
        System.out.println("==== Test lines ====");
        Line line1 = new Line(12, 11, 20, 20);
        Line line2 = new Line(20, 14, 16, 25);
        System.out.print("line x line intersection (2): ");
        print( LineService.lineLineIntersections(line1, line2));


        Line line3 = new Line(10, 10, 20, 20);
        Line line4 = new Line(20, 20, 50, 50);
        System.out.print("line v line intersection(1): ");
        print( LineService.lineLineIntersections(line3, line4));

        Line line5 = new Line(10, 10, 20, 20);
        Line line6 = new Line(15, 10, 25, 20);
        System.out.print("line || line(0): ");
        print( LineService.lineLineIntersections(line5, line6));

        Line line7 = new Line(10, 10, 20, 20);
        Line line8 = new Line(15, 15, 50, 50);
        System.out.print("line partial compatible line(2): ");
        print( LineService.lineLineIntersections(line7, line8));


        Line line9 = new Line(10, 10, 20, 20);
        Line line10 = new Line(20, 20, 10, 10);
        System.out.print("equal lines(2): ");
        print( LineService.lineLineIntersections(line9, line10));
    }

    public static void lineIntersectCircle(){
        System.out.println("==== Test Circles ====");
        Line line1 = new Line(10, 10, 50, 50);
        Circle circle1 = new Circle(22, 22, 10);
        System.out.print("line intersect circle (|), (2): ");
        print( LineService.lineIntersectCircle(line1, circle1));

        Line line2 = new Line(10, 10, 20, 20);
        Circle circle2 = new Circle(30, 30, 15);
        System.out.print("line intersect circle (x), (1): ");
        print( LineService.lineIntersectCircle(line2, circle2));

        Line line4 = new Line(10, 10, 20, 20);
        Circle circle4 = new Circle(15, 15, 40);
        System.out.print("line inside of circle, (0): ");
        print( LineService.lineIntersectCircle(line4, circle4));

        Line line5 = new Line(10, 10, 10, 10);
        Circle circle5 = new Circle(10, 20, 10);
        System.out.print("zero-size line on circle, (1): ");
        print( LineService.lineIntersectCircle(line5, circle5));


        Line line6 = new Line(225, 123, 342, 123);
        Circle circle6 = new Circle(307, 143, 20);
        System.out.print("line on circle, (|), (1): ");
        print( LineService.lineIntersectCircle(line6, circle6));
    }

    private static void print(Point[] points) {
        for (Point poin: points) {
            System.out.print(poin + " ");
        }
        System.out.println();
    }
}
