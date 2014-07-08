/* Code for COMP 102 Assignment 10
 */

import java.util.*;
import comp102.*;
import java.awt.Color;
import java.io.*;

public class TestTree{

    // For testing the Tree class.  Run this method. It should display four trees.
    // Click on and off them to see if your on is working correctly
    public static void test(){
        final Tree t1 = new Tree( 100, 100, 100, 50, Color.blue);
        final Tree t2 = new Tree( 200, 200, 160, 30, Color.green);
        final Tree t3 = new Tree( 300, 300, 300, 50,  Color.red);
        final Tree t4 = new Tree(new Scanner("0 0 0 400 400 100 100 12345"));
        t1.redraw();
        t2.redraw();
        t3.redraw();
        t4.redraw();

        // Oh Dear!! This is an anonymous inner class!!!!
        // It is the right way, but it is tricky!!
        UI.setMouseListener(new UIMouseListener(){ 
                public void mousePerformed(String action, double x, double y){ 
                    if (action.equals("released")){
                        if (t1.on(x, y)){
                            UI.println("On blue tree");
                            UI.println(t1.toString());
                        }
                        else if (t2.on(x, y)){
                            UI.println("On green tree");
                            UI.println(t2.toString());
                        }
                        else if (t3.on(x, y)){
                            UI.println("On red tree");
                            UI.println(t2.toString());
                        }
                        else if (t4!=null && t4.on(x, y)){
                            UI.println("On black tree");
                            UI.println(t2.toString());
                        }
                        else UI.println("Not on shape");
                    }}});

        UI.repaintGraphics();
    }

}
