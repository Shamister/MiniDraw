/* Code for COMP102 Assignment 10
 * Name:
 * Usercode:
 * ID:
 */

import comp102.*;
import java.util.*;
import java.awt.Color;
import java.io.*;

/** Dot represents a small circle shape of a fixed size (5 pixels)
Implements the Shape interface.
Needs fields to record the position, and colour
 */
public class Dot implements Shape{
    // YOUR CODE HERE
    private double x, y;
    private Color col;

    public Dot(double x, double y, Color col){
        this.x = x;
        this.y = y;
        this.col = col;
    }

    public Dot (Scanner data){
        // YOUR CODE HERE
        // read the data of file by using scanner and store it into fields
        int red = data.nextInt();
        int green = data.nextInt();
        int blue = data.nextInt();
        this.col = new Color(red, green, blue);
        this.x = data.nextDouble();
        this.y = data.nextDouble();
    }

    public boolean on(double u, double v){
        // an easy approximation is to pretend it is the enclosing rectangle.
        // It is nicer to do a little bit of geometry and get it right
        // YOUR CODE HERE
        double threshold = 2;
        // check the position whether it's inside the dot or not
        // if yes return true, otherwise return false
        if ((Math.abs(u-x) < 5-threshold) && (Math.abs(v-y) < 5-threshold)) {
            return true;
        }
        return false;
    }

    /** Returns the values of x and y
     */
    public double x(){
        return x;
    }

    public double y(){
        return y;
    }

    public void moveBy(double dx, double dy){
        // YOUR CODE HERE
        this.x += dx;
        this.y += dy;
    }

    public void redraw(){
        // YOUR CODE HERE
        UI.setColor(col);
        UI.fillOval(x-2.5, y-2.5, 5, 5, false);
    }

    public void resize(double x1, double y1, double x2, double y2){
        // YOUR CODE HERE
    }

    public String toString(){
        // YOUR CODE HERE
        return ("Dot "+col.getRed()+" "+col.getGreen()+" "+col.getBlue()+" "+this.x+" "+this.y);
    }
}
