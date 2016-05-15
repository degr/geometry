package org.forweb.geometry.shapes;

public class Line {
    private Point a;
    private Point b;

    public Line(double aX, double aY, double bX, double bY) {
        this(new Point(aX, aY), new Point(bX, bY));
    }

    public Line(Point a, Point b){
        this.a = a;
        this.b = b;
    }

    public Point getB() {
        return b;
    }

    public void setB(Point b) {
        this.b = b;
    }

    public Point getA() {
        return a;
    }

    public void setA(Point a) {
        this.a = a;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Line) {
            Line r = (Line) obj;
            return r.getA().equals(getA()) && r.getB().equals(getB()) || r.getB().equals(getA()) && r.getA().equals(getB());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Line " + getA() + " " + getB();
    }
}
