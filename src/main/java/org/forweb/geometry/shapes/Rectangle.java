package org.forweb.geometry.shapes;

public class Rectangle {
    private Point a;
    private Point b;
    private Point c;
    private Point d;

    public Rectangle(Point a, Point b) {
        this.a = a;
        this.b = new Point(a.getY(), b.getX());
        this.c = b;
        this.d = new Point(a.getX(), b.getY());
    }

    public Rectangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = new Point(c.getX() - (b.getX() - a.getX()), c.getY() - (b.getY() - a.getY()));
    }

    @Override
    public String toString() {
        return a + " " + b + " " + c + " " + d;
    }
}
