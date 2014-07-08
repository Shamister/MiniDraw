/* Code for COMP102 Assignment 10
 * Name:
 * Usercode:
 * ID:
 */

import java.util.*;
import comp102.*;
import java.awt.Color;
import java.io.*;

/** Rectangle represents a solid rectangle shape
Implements the Shape interface.
Needs fields to record the position, size, and colour*/

public class Rectangle implements Shape {
    //fields
    // YOUR CODE HERE
    private double x;  // one end
    private double y;
    private double width; // width and length
    private double height;
    private Color col;  // the colour of the rect
    /** Constructor with explicit values
    Arguments are the x and y of the top left corner,
    the width and height, and the color. */
    public Rectangle(double x, double y, double wd, double ht, Color col) {
        // YOUR CODE HERE
        this.x = x;
        this.y = y;
        this.width = wd;
        this.height = ht;
        this.col = col;
    }

    /** [Completion] Constructor which reads values from a file (scanner)
    The argument is a Scanner that contains the specification of the 
    Rectangle. The next 7 integers specify the position of the top
    left corner, and the width and height, and three ints specifying the 
    color. */
    public Rectangle(Scanner data) {
        // YOUR CODE HERE
        int red = data.nextInt();
        int green = data.nextInt();
        int blue = data.nextInt();
        this.col = new Color(red, green, blue);
        this.x = data.nextDouble();
        this.y = data.nextDouble();
        this.width = data.nextDouble();
        this.height = data.nextDouble();
    }

    /** Returns true if the point (u, v) is on top of the shape */
    public boolean on(double u, double v) {
        // YOUR CODE HERE
        double threshold = 2;
        // if the position is on rectangle or still on the threshold, it will return true
        // otherwise it will return false
        if (((u > x+threshold) && (u < x+width-threshold) && (v > y+threshold) && (v < y+height-threshold)) ||
        (u > x) && (u < x+width) && (v > y+threshold) && (v < y+height-threshold) ||
        (u > x+threshold) && (u < x+width-threshold) && (v > y) && (v < y+height)) return true;
        return false;
    }

    /** Changes the position of the shape by dx and dy.
    If it was positioned at (x, y), it will now be at (x+dx, y+dy)*/
    public void moveBy(double dx, double dy) {
        // YOUR CODE HERE
        this.x += dx;
        this.y += dy;
    }

    /** Draws the rectangle on the graphics pane. It draws a black border and
    fills it with the color of the rectangle.
    It uses the drawing methods with the extra last argument of "false"
    so that the shape will not actually appear until the 
    graphics pane is redisplayed later. This gives much smoother redrawing.*/
    public void redraw() {
        // YOUR CODE HERE
        UI.setColor(col);
        UI.fillRect(x, y, width, height, false);
        UI.setColor(Color.black);
        UI.drawRect(x, y, width, height, false);
    }

    /** [Completion] Changes the width and height of the shape by the
    specified amounts.
    The amounts may be negative, which means that the shape
    should get smaller, at least in that direction.
    The shape should never become smaller than 1 pixel in width or height
    The center of the shape should remain the same.*/
    public void resize(double x1, double y1, double x2, double y2){
        // YOUR CODE HERE
        this.x = Math.min(x1, x2);
        this.y = Math.min(y1, y2);
        this.width = Math.abs(x1-x2);
        this.height = Math.abs(y1-y2);
    }

    /** Returns a string description of the rectangle in a form suitable for
    writing to a file in order to reconstruct the rectangle later
    The first word of the string must be Rectangle */
    public String toString() {
        // YOUR CODE HERE
        return ("Rectangle "+col.getRed()+" "+col.getGreen()+" "+col.getBlue()+" "+this.x+" "+this.y+" "+this.width+" "+this.height);
    }

}
