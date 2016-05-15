package org.forweb.geometry;

import org.forweb.geometry.services.BoundsService;
import org.forweb.geometry.services.LineService;
import org.forweb.geometry.shapes.*;

import java.util.Random;

public class Lines {


    public static void main(String args[]){

        Random r = new Random();
        System.out.println("start");
        float start = System.nanoTime();
        Point[] p = null;
        int is  =0;
        double mult = 5d;
        double mult1 = 5d;
        for(int i = 0; i < 10000000; i++) {
            p = LineService.lineIntersectCircle(
                    new Line(r.nextDouble() * mult, r.nextDouble() * mult, r.nextDouble() * mult, r.nextDouble() * mult),
                    new Circle(r.nextDouble() * mult1 + 20, r.nextDouble() * mult1 + 20, r.nextDouble() * mult1)
            );
            if(p != null && p.length > 0) {
                is++;
            }
        }
        System.out.println((System.nanoTime() - start) +"  " + is);
    }



}