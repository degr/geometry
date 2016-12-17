package org.forweb.geometry.misc;

public class Angle extends Number{

    private static double D_PI = Math.PI * 2;

    private double value;
    private double sin;
    private double cos;

    public Angle(double value) {
        this.value = value;
        this.append(0);
    }

    public Angle sum(double angle) {
        return new Angle(this.value + angle);
    }
    public void append(Angle angle) {
        append(angle.doubleValue());
    }
    public void append(double angle) {
        value += angle;
        while(value > D_PI) {
            value -= D_PI;
        }
        while(value < 0) {
            value += D_PI;
        }
        this.sin = Math.sin(value);
        this.cos = Math.cos(value);
    }

    public double sin() {
        return sin;
    }

    public double cos() {
        return cos;
    }

    @Override
    public int intValue() {
        throw new RuntimeException("Angle can't be cast to int");
    }

    @Override
    public long longValue() {
        throw new RuntimeException("Angle can't be cast to long");
    }

    @Override
    public float floatValue() {
        throw new RuntimeException("Angle can't be cast to float");
    }

    @Override
    public double doubleValue() {
        return value;
    }
}
