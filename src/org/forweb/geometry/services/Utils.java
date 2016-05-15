package org.forweb.geometry.services;

import org.forweb.geometry.shapes.Point;

public class Utils {

    public static final Point[] EMPTY = new Point[0];

    static Point[] clearResult(Point[] input) {
        int nullCount = 0;

        for(Point point : input) {
            if(point != null) {
                for (int i = 0; i < input.length; i++) {
                    Point point1 = input[i];
                    if (point1 != null && point != point1) {
                        if (point.equals(point1)) {
                            input[i] = null;
                        }
                    }
                }
            }
        }
        for(Point point : input) {
            if(point == null) {
                nullCount++;
            }
        }

        Point[] out = new Point[input.length - nullCount ];
        int index = 0;
        for(Point point : input) {
            if(point != null) {
                out[index] = point;
                index++;
            }
        }
        return out;
    }

}
