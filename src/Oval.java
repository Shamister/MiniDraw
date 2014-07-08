/* Code for COMP102 Assignment 10
 * Name:
 * Usercode:
 * ID:
 */

import java.util.*;
import comp102.*;
import java.awt.Color;
import java.io.*;

/** Oval represents an oval shape
Implements the Shape interface.
Needs fields to record the position, size, and colour
 */

public class Oval implements Shape{
    // YOUR CODE HERE
    private double x;  // one end
    private double y;
    private double width; // width and length
    private double height;
    private Color col;  // the colour of the oval

    /** Constructor with explicit values
    Arguments are the x and y of the top left corner,
    the width and height, and the color.  */
    public Oval (double x, double y, double wd, double ht, Color col){
        // YOUR CODE HERE
        // stores the position and color into fields
        this.x = x;
        this.y = y;
        this.width = wd;
        this.height = ht;
        this.col = col;
    }

    /** [Completion] Constructor which reads values from a file (scanner)
    The argument is a Scanner that contains the specification of the Oval.
    The next 7 integers specify the position, the size and three ints
    specifying the color.
     */
    public Oval (Scanner data){
        // YOUR CODE HERE
        // read the data of file by using scanner and store it into fields
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
    public boolean on(double u, double v){
        // an easy approximation is to pretend it is the enclosing rectangle.
        // It is nicer to do a little bit of geometry and get it right
        // YOUR CODE HERE
        double threshold = 2;
        // return false if it is outside rectangle of oval shape
        // set r min and r max of ellipse
        double r1 = width/2 + threshold;
        double r2 = height/2 + threshold;
        // get the distance between midpoint and clicked point
        double d1 = Math.abs((x+width/2) - u);
        double d2 = Math.abs((y+height/2) - v);
        // check the position whether it's inside the oval or not
        // using the area of ellipse equation
        // if the position is on the ellipse/oval/circle + threshold, it will return true, otherwise it will return false
        if ((u > (x-threshold)) && (u < (x+width+threshold))
        && (v > (y-threshold)) && (v < (y+height+threshold))){
            if ((((1-(d2*d2/(r2*r2)))*r1*r1) > d1*d1) && ((((1-(d1*d1/(r1*r1)))*r2*r2) > d2*d2))){ // ellipse equation : x2/a2 + y2/b2 = 1
                return true;
            }
        }
        return false;
    }

    /** Changes the position of the shape by dx and dy.
    If it was positioned at (x, y), it will now be at (x+dx, y+dy)
     */
    public void moveBy(double dx, double dy){
        // YOUR CODE HERE
        this.x += dx;
        this.y += dy;
    }

    /** Draws the oval on the graphics pane. It draws a black border and
    fills it with the color of the oval.
    It uses the drawing methods with the extra last argument of "false"
    so that the shape will not actually appear until the 
    graphics pane is redisplayed later. This gives much smoother redrawing.
     */
    public void redraw(){
        // YOUR CODE HERE
        // set x1 and y1 as the smallest values between x1 and x2 and y1 and y2
        // so it gives user able to draw from right to left
        UI.setColor(col);
        UI.fillOval(x, y, width, height, false);
        UI.setColor(Color.black);
        UI.drawOval(x, y, width, height, false);
    }

    /** [Completion] Changes the width and height of the shape by the
    specified amounts.
    The amounts may be negative, which means that the shape
    should get smaller, at least in that direction.
    The shape should never become smaller than 1 pixel in width or height
    The center of the shape should remain the same.
     */
    public void resize(double x1, double y1, double x2, double y2){
        // YOUR CODE HERE
        this.x = Math.min(x1, x2);
        this.y = Math.min(y1, y2);
        this.width = Math.abs(x1-x2);
        this.height = Math.abs(y1-y2);
    }

    /** Returns a string description of the oval in a form suitable for
    writing to a file in order to reconstruct the oval later
    The first word of the string must be Oval */
    public String toString(){
        // YOUR CODE HERE
        return ("Oval "+col.getRed()+" "+col.getGreen()+" "+col.getBlue()+" "+this.x+" "+this.y+" "+this.width+" "+this.height);
    }

}
