/* Code for COMP102 Assignment 10
 * Name:
 * Usercode:
 * ID:
 */

import comp102.*;
import java.awt.Color;
import java.io.*;
import java.util.*;
import javax.swing.JColorChooser;
import javax.swing.JComponent;

/** The MiniDraw program allows the user to create, save, and reload files
specifying drawings consisting of a list of simple shapes.
The program allows the user to
- add a new shape to the drawing
- remove a shape from the drawing
- move a shape to a different position
- set the colour for the next shape
- save the current drawing to a file
- load a previous drawing from a file.
The shapes include lines, rectangles, ovals, and dots

Classes
The MiniDraw class handles all the user interaction:
buttons, mouse actions, file opening and closing.
It stores the current drawing in an array of Shape .

The Shape interface specifies the Shape type
The shape classes all implement Shape and represent different kinds of shapes.

Files:
A drawing is stored in a file containing one line for each shape,
Each line has the name of the type of shape,
followed by a specification of the shape,
including the position (x and y) and the
colour (three integers for red, blue, and green).
The other values will depend on the shape.

User Interface:
There are buttons for dealing with the whole drawing (New, Load, Save),
buttons for specifying the next shape to draw, and
buttons for moving and removing shapes, and setting the color.
 */

public class MiniDraw extends JComponent implements UIButtonListener, UIMouseListener{

    // Fields
    private ArrayList<Shape> shapes = new ArrayList<Shape>();    // the collection of shapes

    // suggested fields to store mouse positions, current action, current shape, current colour, etc

    private double pressedX;                 //where the mouse was pressed
    private double pressedY;  
    private String currentAction = "Line";   // current action ("Move", etc) or shape ("Line" etc)
    private Shape currentShape = null;      //  the current shape (or null if no shape)
    private Color currentColor = Color.red;
    private ArrayList<Dot> polygon = new ArrayList<Dot>();
    private boolean dragged = false;

    /** Constructor sets up the GUI:
     *        sets the mouse listener and adds all the buttons
     */
    public MiniDraw(){
        // YOUR CODE HERE
        // add the buttons
        UI.setMouseListener(this);
        UI.addButton("New", this);
        UI.addButton("Open", this);
        UI.addButton("Save", this);
        UI.addButton("Rectangle", this);
        UI.addButton("Oval", this);
        UI.addButton("Line", this);
        UI.addButton("Dot", this);
        UI.addButton("Polygon", this);
        UI.addButton("Tree", this);
        UI.addButton("Color", this);
        UI.addButton("Move", this);
        UI.addButton("Resize", this);
        UI.addButton("Delete", this);
    }

    /** Respond to button presses
     * For New, Open, Save, and Color, call the appropriate method (see below)
     *  to perform the action immediately.
     * For other buttons, store the button name in the currentAction field
     */
    public void buttonPerformed(String button){
        // YOUR CODE HERE
        // stores action into currentAction fields
        UI.clearGraphics();
        this.drawDrawing();
        if (button.equals("New")){ newDrawing();}
        if (button.equals("Open")){ currentAction = "Open"; openDrawing();}
        if (button.equals("Save")){ currentAction = "Save"; saveDrawing();}
        if (button.equals("Rectangle")){ currentAction = "Rectangle";}
        if (button.equals("Oval")){ currentAction = "Oval";}
        if (button.equals("Line")){ currentAction = "Line";}
        if (button.equals("Dot")){ currentAction = "Dot";}
        if (button.equals("Polygon")){ currentAction = "Polygon";}
        if (button.equals("Tree")){ currentAction = "Tree";}
        if (button.equals("Color")){ selectColor();}
        if (button.equals("Move")){ currentAction = "Move";}
        if (button.equals("Resize")){ currentAction = "Resize"; resizeShape();}
        if (button.equals("Delete")){ currentAction = "Delete";}
        // so it will stop counting the position of polygon and clear the arraylist
        if (!currentAction.equals("Polygon")) polygon = new ArrayList<Dot>();
    }

    // Respond to mouse events 

    /** When mouse is pressed, remember the position in fields
    and also find the shape it is on (if any), and store
    the shape in a field (use the findShape(..) method)
     *  When the Mouse is released, depending on the currentAction,
     *  - perform the action (move, delete, or resize).
     *    move and resize are done on the shape  where the mouse was pressed,
     *    delete is done on the shape where the mouse was released 
     *  - construct the shape and add to the shapes array.
     *    (though the polygon is more complicated)
     *  It is easiest to call other methods (see below) to actually do the work,
     *  otherwise this method gets too big!
     */
    public void mousePerformed(String mouseAction, double x, double y) {
        if (mouseAction.equals("pressed")){
            // YOUR CODE HERE
            // stores the position into fields
            pressedX = x;
            pressedY = y;
            if (currentAction.equals("Polygon")){
                addPolygon(pressedX, pressedY);
            }
            else if (currentAction.equals("Dot")){
                addShape(pressedX, pressedY, pressedX, pressedY);
                currentShape = shapes.get(shapes.size()-1);
            }
            else if (currentAction.equals("Delete")){
                deleteShape(x, y);   
            }
            else {
                currentShape = findShape(x, y);
            }

        }

        if (mouseAction.equals("released")){
            // YOUR CODE HERE
            // check whether currentAction is one of them below, if yes and it satisfies the size (should be more than 3 px) it will create the shape
            if (currentAction.equals("Line") || currentAction.equals("Oval") ||currentAction.equals("Rectangle") || currentAction.equals("Tree")){
                if ((Math.abs(pressedX-x) > 2) && (Math.abs(pressedY-y) > 2)){
                    addShape(pressedX, pressedY, x, y);
                    currentShape = shapes.get(shapes.size()-1);
                }
            }
            else if (currentAction.equals("Move") && currentShape != null){
                moveShape(x-pressedX, y-pressedY);
            }
            else if (currentAction.equals("Resize")){
                if (currentShape == null) currentShape = shapes.get(shapes.size()-1);
                Info data = shapeInfo(currentShape);
                if (data != null){
                    double x1 = data.x1;
                    double y1 = data.y1;
                    double x2 = data.x2;
                    double y2 = data.y2;
                    boolean found = false;
                    if (data.shapeType.equals("Line")){
                        if (Math.abs(pressedX-data.x1) < 3 && Math.abs(pressedY-data.y1) < 3){ x1 = x; y1 = y; found = true;}
                        else if (Math.abs(pressedX-data.x2) < 3 && Math.abs(pressedY-data.y2) < 3){ x2 = x; y2 = y; found = true;}
                    }

                    else if ((data.shapeType.equals("Oval")) || (data.shapeType.equals("Rectangle")) || ((data.shapeType.equals("Tree")))){
                        if (Math.abs(pressedX-data.x1) < 3 && Math.abs(pressedY-data.y1) < 3){ x1 = x; y1 = y; found = true;}
                        else if (Math.abs(pressedX-data.x2) < 3 && Math.abs(pressedY-data.y1) < 3){ y1 = y; x2 = x; found = true;}
                        else if (Math.abs(pressedX-data.x1) < 3 && Math.abs(pressedY-data.y2) < 3){ x1 = x; y2 = y; found = true;}
                        else if (Math.abs(pressedX-data.x2) < 3 && Math.abs(pressedY-data.y2) < 3){ x2 = x; y2 = y; found = true;}
                    }

                    if (found){
                        resizeShape(x1, y1, x2, y2, data.index);
                    }

                }
            }
            this.drawDrawing();
            if (currentAction.equals("Resize")) resizeShape();
        }
    }

    // -----------------------------------------------------
    // Methods that actually do the work  
    // -----------------------------------------------------

    /** Draws all the shapes in the list on the graphics pane
    First clears the graphics pane, then draws each shape,
    Finally repaints the graphics pane
     */
    public void drawDrawing(){
        UI.clearGraphics(false);
        // YOUR CODE HERE
        // draw all shapes on the arraylist
        // use for loop
        for (int i = 0; i < shapes.size(); i++){
            shapes.get(i).redraw();
        }
        UI.repaintGraphics();
    }   

    /** Checks each shape in the list to see if the point (x,y) is on the shape.
    It returns the topmost shape for which this is true.
    Returns null if there is no such shape.
     */
    public Shape findShape(double x, double y){
        // YOUR CODE HERE
        // find the position whether it is on the shape or not
        // using for loop and count from the end to the beginning
        // so when we want to move the shape, the shape will get moving is the shape on the top
        for (int i = shapes.size()-1; i > -1; i--){
            if (shapes.get(i).on(x,y)){
                return shapes.get(i);
            }
        }
        return null;  
    }

    /** Sets the current color.
     * Asks user for a new color using a JColorChooser (see MiniPaint, Assig 6)
     * As long as the color is not null, it remembers the color */
    private void selectColor(){
        // YOUR CODE HERE
        currentColor = JColorChooser.showDialog(this, "Choose Color", currentColor);
    }

    /** Start a new drawing -
    initialise the shapes array and clear the graphics pane. */
    public void newDrawing(){
        // YOUR CODE HERE
        shapes = new ArrayList<Shape>();
        currentShape = null;
        currentAction = "Line";
        polygon = new ArrayList<Dot>();
        UI.clearGraphics();
    }

    /** Construct a new Shape object of the appropriate kind
    (depending on currentAction) using the appropriate constructor
    of the Line, Rectangle, Oval, or Dot classes.
    Adds the shape to the end of the collection of shapes in the drawing, and
    Re-draws the drawing */
    public void addShape(double x1, double y1, double x2, double y2){
        Trace.printf("Drawing shape %s, at (%.2f, %.2f)-(%.2f, %.2f)\n",
            this.currentAction, x1, y1, x2, y2);  //for debugging
        // YOUR CODE HERE
        double X1 = Math.min(x1, x2);
        double Y1 = Math.min(y1, y2);
        double width = Math.abs(x1-x2);
        double height = Math.abs(y1-y2);
        if (currentAction.equals("Line")){
            shapes.add(new Line(x1, y1, x2, y2, currentColor));
        }
        else if (currentAction.equals("Rectangle")){
            shapes.add(new Rectangle(X1, Y1, width, height, currentColor));
        }

        else if (currentAction.equals("Oval")){
            shapes.add(new Oval(X1, Y1, width, height, currentColor));
        }
        else if (currentAction.equals("Dot")){
            shapes.add(new Dot(X1, Y1, currentColor));
        }
        else if (currentAction.equals("Tree")){
            shapes.add(new Tree(X1, Y1, width, height, currentColor));
        }

        // stores to currentShape
        currentShape = shapes.get(shapes.size()-1);

        // redraw the shapes
        for (int i = 0; i < shapes.size(); i++){
            shapes.get(i).redraw();
        }
    }

    /** Moves the current shape (if there is one)
    to where the mouse was released.
    Ie, change its position by (toX-fromX) and (toY-fromY)
     */
    public void moveShape(double changeX, double changeY){
        Trace.printf("Moving shape by (%.2f, %.2f)\n", changeX, changeY);  //for debugging
        // YOUR CODE HERE
        // find which one satisfies through the array, if found, it will move the shape
        for (int i = 0; i < shapes.size(); i++){
            if(currentShape == shapes.get(i)){
                shapes.get(i).moveBy(changeX, changeY);
                currentShape = shapes.get(i);
            }
        }
    }

    /** Finds the shape that was under the mouseReleased position (x, y)
    and then removes it from the array of shapes, moving later shapes down. 
     */
    public void deleteShape(double x, double y){
        Trace.printf("Deleting shape under (%.2f, %.2f)\n", x, y);  //for debugging
        //Find the index of the shape that (pressedX, pressedY) is on.
        //and remove the shape from the drawing, then draw the drawing again
        //If not pressed on any shape, then do nothing.
        // YOUR CODE HERE
        // find through the array, if it's found, remove it from arraylist
        for (int i = shapes.size()-1; i > -1 ; i--){
            if(shapes.get(i).on(x,y)){
                shapes.remove(i);
            }
        }
    }

    // METHODS FOR THE COMPLETION PART
    /** Resizes the current shape. A simple way of doing it is to
    resize the shape by the amount that the mouse was moved
    (ie from (fromX, fromY) to (toX, toY)). 
    If the mouse is moved to the right, the shape should
    be made that much wider on each side; if the mouse is moved to
    the left, the shape should be made that much narrower on each side
    If the mouse is moved up, the shape should be made
    that much higher top and bottom; if the mouse is moved down, the shape 
    should be made that much shorter top and bottom.
    The effect is that if the user drags from the top right corner of
    the shape, the shape should be resized to whereever the dragged to.
     */

    /** Returns array which contains information of Shapes
     */
    public Info shapeInfo(Shape shape){
        // check the information of shape from toString() method (I think it's better than editing the interface class :) )
        Info data = new Info();
        for (int i = shapes.size()-1; i > -1; i--){
            if (shape == shapes.get(i)){
                data.index = i;
                Scanner scan = new Scanner(shapes.get(i).toString());
                data.shapeType = scan.next(); //form of shape (line, rect, etc)
                int red = scan.nextInt();
                int green = scan.nextInt();
                int blue = scan.nextInt();
                data.color = new Color(red, green, blue);
                if (data.shapeType.equals("Line")){
                    data.x1 = scan.nextDouble(); // x1
                    data.y1 = scan.nextDouble(); // y1
                    data.x2 = scan.nextDouble(); // x2
                    data.y2 = scan.nextDouble(); // y2
                }
                else if (data.shapeType.equals("Rectangle") || data.shapeType.equals("Oval") || data.shapeType.equals("Tree")){
                    data.x1 = scan.nextDouble(); // x1
                    data.y1 = scan.nextDouble(); // y1
                    data.x2 = data.x1+scan.nextDouble(); // x2
                    data.y2 = data.y1+scan.nextDouble(); // y2
                }
                break;
            }
        }
        return data;
    }

    public void resizeShape(){
        // Challenge (good GUI of resizing);
        // check whether the shape is able to be resized or not
        // if yes, it will draw small rectangle on the edges of the shape
        if (currentAction.equals("Resize")){
            if (currentShape != null){
                Info data = shapeInfo(currentShape);
                if (data.shapeType.equals("Line")){
                    UI.setColor(Color.black);
                    UI.drawRect(data.x1-3, data.y1-3, 6, 6);
                    UI.drawRect(data.x2-3, data.y2-3, 6, 6);
                }
                else if (data.shapeType.equals("Oval") || data.shapeType.equals("Rectangle") || data.shapeType.equals("Tree")){
                    UI.setColor(Color.black);
                    UI.drawRect(data.x1-3, data.y1-3, 6, 6);
                    UI.drawRect(data.x1-3, data.y2-3, 6, 6);
                    UI.drawRect(data.x2-3, data.y1-3, 6, 6);
                    UI.drawRect(data.x2-3, data.y2-3, 6, 6);
                }
            }
        }
    }

    public void resizeShape(double x1, double y1, double x2, double y2, int index){
        // YOUR CODE HERE
        // replace the resized shape in its class
        shapes.get(index).resize(x1, y1, x2, y2);
        currentShape = shapes.get(index);
    }

    /** Adds a polygon.
    If the currentPolygon is null, then create a new polygon with
    just the point x,y. Store it in currentPolygon, and add it to shapes.
    If the currentPolygon is not null, then add a new point to it.
    (Don't add it to shapes, since it is already there).
    Note, you need to reset currentPolygon to null every time a button is pressed
     */
    public void addPolygon(double x, double y){
        // YOUR CODE HERE
        // if it's new polygon, it will add it into shape and polygon arraylist
        // if it's not, it put the position into polygon arraylist, and replace the information in shape arraylist
        if (polygon.size() == 0){
            polygon.add(new Dot(x, y, currentColor));
            shapes.add(new Polygon(polygon, currentColor));
        }
        else {
            for (int i = shapes.size()-1; i > -1; i--){
                if (currentShape == shapes.get(i)){
                    polygon.add(new Dot(x, y, currentColor));
                    shapes.set(i, new Polygon(polygon, currentColor));
                    break;
                }
            }
        }
        currentShape = shapes.get(shapes.size()-1);
    }

    /** Ask the user to select a file and save the current drawing to the file. */
    public void saveDrawing(){
        // save the drawing into "save.txt" file, then tell the user that the data has been saved
        try {
            PrintStream out = new PrintStream("save.txt");
            for (int i = 0; i < shapes.size(); i++){
                out.println(shapes.get(i).toString());
            }
            UI.println("Data has been saved in \"save.txt\"");
        }
        catch (IOException e) {UI.println("Error when reading the file");}
    }

    /** Ask the user for a file to open,
    then read all the shape descriptions into the current drawing. */
    public void openDrawing(){
        // YOUR CODE HERE
        // open the file and then read through the data and put them into arraylist
        String filename = UIFileChooser.open("Choose the file");
        try {
            Scanner scan = new Scanner(new File(filename));
            while (scan.hasNext()){
                String shape = scan.next();
                Scanner scan2 = new Scanner(scan.nextLine());
                if (shape.equals("Line")){ shapes.add(new Line(scan2));}
                else if (shape.equals("Oval")){ shapes.add(new Oval(scan2));}
                else if (shape.equals("Rectangle")){ shapes.add(new Rectangle(scan2));}
                else if (shape.equals("Polygon")){ shapes.add(new Polygon(scan2));}
                else if (shape.equals("Tree")){ shapes.add(new Tree(scan2));}
                else if (shape.equals("Dot")){ shapes.add(new Dot(scan2));}
            }
            this.drawDrawing();
        }
        catch (IOException e){ UI.println("Error while loading the file");}
    }

    public static void main(String args[]){
        new MiniDraw();
    }

}

class Info {
    public int index;
    public String shapeType;
    public Color color;
    public double x1;
    public double x2;
    public double y1;
    public double y2;
}

