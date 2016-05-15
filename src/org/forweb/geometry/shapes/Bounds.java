package org.forweb.geometry.shapes;

public class Bounds {

    public Bounds(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public Bounds(Point a, Point b) {
        if(a.getX() < b.getX()) {
            this.x = a.getX();
            this.width = b.getX() - a.getX();
        } else {
            this.x = b.getX();
            this.width = a.getX() - b.getX();
        }
        if(a.getY() < b.getY()) {
            this.y = a.getY();
            this.height = b.getY() - a.getY();
        } else {
            this.y = b.getY();
            this.height = a.getY() - b.getY();
        }
    }

    double x;
    double y;
    double width;

    double height;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Bounds) {
            Bounds r = (Bounds) obj;
            return r.getX() == getX() && r.getY() == getY() && r.getWidth() == getWidth() && r.getHeight() == getHeight();
        } else {
            return false;
        }
    }
}
