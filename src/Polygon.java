/* Code for COMP 102 Assignment 10
 * Name:
 * Usercode:
 * ID:
 */

import java.util.*;
import comp102.*;
import java.awt.Color;
import java.io.*;

/** Polgon represents a polygon made of a sequence of straight lines.
Implements the Shape interface.
Has a field to record the colour of the line and two fields to store
lists of the x coordinates and the y coordinates of all the vertices
 */

public class Polygon implements Shape{
    // YOUR CODE HERE
    private ArrayList<Dot> polygon = new ArrayList<Dot>();
    private Color col;

    public Polygon(ArrayList<Dot> polygon, Color col){
        this.polygon = polygon;
        this.col = col;
    }

    public Polygon (Scanner data){
        int red = data.nextInt();
        int green = data.nextInt();
        int blue = data.nextInt();
        this.col = new Color(red, green, blue);
        while (data.hasNextDouble()){
            polygon.add(new Dot(data.nextDouble(), data.nextDouble(), col));
        }
    }

    public boolean on(double u, double v){
        double threshold = 3;
        for (int i = 0; i < polygon.size()-1; i++){
            // first check if it is past the ends of the line...
            if (!(u < Math.min(polygon.get(i).x(),polygon.get(i+1).x())-threshold ||     
                u > Math.max(polygon.get(i).x(),polygon.get(i+1).x())+threshold ||
                v < Math.min(polygon.get(i).y(),polygon.get(i+1).y())-threshold ||
                v > Math.max(polygon.get(i).y(),polygon.get(i+1).y())+threshold)) {
                // then check the distance from the point to the line
                double wd = polygon.get(i+1).x()-polygon.get(i).x();
                double ht = polygon.get(i+1).y()-polygon.get(i).y();
                boolean found = Math.abs(((v-polygon.get(i).y())*wd - (u-polygon.get(i).x())*ht)/Math.hypot(wd, ht)) <= threshold;
                if (found) return true;
                // distance of a point from a line, from linear algebra
            }
        }
        return false;
    }

    /** Changes the position of the shape by dx and dy.
    If it was positioned at (x, y), it will now be at (x+dx, y+dy)
     */
    public void moveBy(double dx, double dy){
        for (int i = 0; i < polygon.size(); i++){
            polygon.get(i).moveBy(dx, dy);
        }
    }

    /** Redraw the line which is stored in polygon arraylist
     */
    public void redraw(){
        if (polygon.size() > 0){
            for (int i = 0; i < polygon.size()-1; i++){
                UI.setColor(col);
                UI.drawLine(polygon.get(i).x(), polygon.get(i).y(), polygon.get(i+1).x(), polygon.get(i+1).y());
            }
            if (polygon.size() > 1) UI.drawLine(polygon.get(0).x(), polygon.get(0).y(), polygon.get(polygon.size()-1).x(), polygon.get(polygon.size()-1).y());
        }
    }

    /** Changes the width and height of the shape by the
    specified amounts.
    The amounts may be negative, which means that the shape
    should get smaller, at least in that direction.
    The shape should never become smaller than 1 pixel in width or height
    The center of the shape should remain the same.
     */
    public void resize(double x1, double y1, double x2, double y2){

    }

    /** Returns a string description of the Polygon in a form suitable for
    writing to a file in order to reconstruct the Polygon later
    The first word of the string must be Polygon */
    public String toString() {
        // YOUR CODE HERE
        String info = "Polygon "+col.getRed()+" "+col.getGreen()+" "+col.getBlue();
        for (int i = 0; i < polygon.size(); i++){
            info = info+" "+polygon.get(i).x()+" "+polygon.get(i).y();
        }
        return info;
    }
}
