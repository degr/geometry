package org.forweb.geometry.shapes;

public class Point {

    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + "|" + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Point) {
            Point r = (Point) obj;
            return r.getX() == getX() && r.getY() == getY();
        } else {
            return false;
        }
    }
}