package org.forweb.geometry.misc;

public class Vector {

    public double x;
    public double y;

    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }


    public Vector plus(Vector vector){
        return new Vector(this.x + vector.x, this.y + vector.y);
    }

    public void append(Vector vector) {
        this.x += vector.x;
        this.y += vector.y;
    }
}
