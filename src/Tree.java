/* Code for COMP 102 Assignment 10
 * Name:
 * Usercode:
 * ID:
 */

import java.util.*;
import comp102.*;
import java.awt.Color;
import java.io.*;

/** Tree represents a nice-looking tree with branches.
The base of its trunk is at (x, y), and its size field
governs the size of the tree.
Since each tree is different, in a random way, it has
has a seed, which starts the random number generator in the same
place every time it is drawn.
Implements the Shape interface.
Needs fields, constructors, and all the methods specified in the interface.
 */

// YOUR CODE HERE
public class Tree implements Shape{
    private double x, y, width, height;
    private Color col;
    private int mainBranches, spreadBranches;
    private ArrayList<Integer> branches = new ArrayList<Integer>();

    public Tree(double x, double y, double width, double height, Color col){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.col = col;
        this.mainBranches = (int)(3+Math.random()*height/(height/4));
        branches.add(1);
        for (int i = 2; i < mainBranches+1; i++){
            branches.add((int)(i+Math.random()*1.1));
        }
    }

    /** Constructor which reads values from a file (scanner)
    The argument is a Scanner that contains the specification of the tree.
    The next 7 integers should be the x and y of the first end
    the width and height, and three integers that specify the color.
     */
    public Tree (Scanner data){
        int red = data.nextInt();
        int green = data.nextInt();
        int blue = data.nextInt();
        this.col = new Color(red, green, blue);
        this.x = data.nextDouble();
        this.y = data.nextDouble();
        this.width = data.nextDouble();
        this.height = data.nextDouble();
        totalBranches(data.nextDouble());
    }

    public void totalBranches(double totBranches){
        if(totBranches >= 1){
            String totBranchesString = Integer.toString((int)totBranches);
            branches.add(Integer.parseInt(totBranchesString.substring(0,1)));
            if(totBranchesString.length() > 1){
                totalBranches(Integer.parseInt(totBranchesString.substring(1, totBranchesString.length())));
            }
            else {
                mainBranches = branches.size();
            }
        }
    }

    public boolean on(double x, double y){
        double threshold = 2;
        // first, check whether it is inside the border of tree of not (inside the rectangle of tree)
        // if yes, it will check where the position is
        // if it's on the stem of tree, it returns true
        // or it's on leaves, it will return true
        // otherwise, it returns false
        if ((x > this.x-threshold) && (x < this.x+width+threshold) && (y > this.y-threshold) && (y < this.y+height+threshold)){
            if ((x > this.x + width/2 - threshold) && (x < this.x + width/2 + threshold) && (y > this.y - threshold + (mainBranches-1)*height/mainBranches) && (y < this.y + height + threshold)) return true;
            else if ((y > this.y-threshold) && (y < this.y+(mainBranches-1)*height/mainBranches+threshold) && ( x > this.x + (width/2)*(y-this.y)/((mainBranches-1)*height/mainBranches) - threshold && (x < this.x + width - (width/2)*(y-this.y)/((mainBranches-1)*height/mainBranches) + threshold))) return true;
        }
        return false;
    }

    public void moveBy(double dx, double dy){
        this.x += dx;
        this.y += dy;
    }

    public void redraw(){
        drawTree(this.x+width/2, this.y+height, 9*this.width/10, 0);
        UI.repaintGraphics();
    }

    public void drawTree(double x1, double y1, double width1, int index){
        // first, place the position of the main branch on the middle of area
        // then for next branches, it will be drawn started from the end of last branches
        // and it will keep going until the end of arraylist (using recursion)
        for (int i = 1; i < branches.get(index)+1; i++){
            double x2 = x1- width1/2 + i * width1/(branches.get(index)+1);
            double y2;
            if (i > 1){
                y2 = y1 - height/mainBranches;
            }
            else {
                y2 = y1 - height/mainBranches;
            }
            UI.setColor(col);
            UI.drawLine(x1, y1, x2, y2, false);
            double width2 = 3*width1/4;
            if (index < mainBranches-1) drawTree(x2, y2, width2, index+1);
        }
    }

    public void resize(double x1, double y1, double x2, double y2){
        this.x = Math.min(x1, x2);
        this.y = Math.min(y1, y2);
        this.width = Math.abs(x1-x2);
        this.height = Math.abs(y1-y2);
    }

    public String toString(){
        String info = "Tree "+col.getRed()+" "+col.getGreen()+" "+col.getBlue()+" "+x+" "+y+" "+width+" "+height+" ";
        for (int i: branches){
            info = info + i;
        }
        return(info);
    }
}
