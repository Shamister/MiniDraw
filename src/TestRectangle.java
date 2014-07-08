/* Code for COMP 102 Assignment 10
 */

import java.util.*;
import comp102.*;
import java.awt.Color;
import java.io.*;

public class TestRectangle{

  // For testing the Rectangle class.  Run this method. It should display two rectangles
  // then test on(), move the blue rectangle, resize the red one,
  // and finally test the second constructor by displaying a yellow rectangle
  public static void main(String[] args) {
    Rectangle r1 = new Rectangle(100, 100, 200, 200, Color.blue);
    Rectangle r2 = new Rectangle(400, 200, 50, 50, Color.red);
    r1.redraw();
    r2.redraw();
    UI.repaintGraphics();                             // to redisplay them 

    UI.println("Testing on()");
    if (r1.on(10, 10)) {
      UI.println("Error: (10,10) should not be on the blue rectangle");
    }
    if (!r1.on(150, 190)) {
      UI.println("Error: (150,190) should be on the blue rectangle");
    }
    if (r2.on(451, 251)) {
      UI.println("Error: (451,251) should not be on the red rectangle");
    }
    if (!r2.on(401, 225)) {
      UI.println("Error: (401,225) should be just on the red rectangle");
    }
    UI.println("Blue should now move and red should be resized");
    UI.askBoolean("Ready?");
    r1.moveBy(20, 20);
    r1.redraw();
    r2.redraw();
    UI.repaintGraphics();                             // to redisplay them 

    UI.println("Make new yellow rectangle from Scanner");
    UI.askBoolean("Ready?");

    try {
      Rectangle r3 = new Rectangle(new Scanner("255 255 0 300 400 20 20"));
      //a yellow rectangle from a Scanner
      r3.redraw();
      UI.repaintGraphics();
    }
    catch (Exception e) {UI.println("Can't construct from a scanner yet");}
    UI.println("Done");
  }

}
