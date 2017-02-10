/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package suprememayor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * @author Julian
 */
public class WarningManager {
    
    private static WarningManager instance;
    
    private static int WARNING_X_POS = 200;
    private static int WARNING_Y_POS = 100;
    
    private static Font WARNING_FONT_DEFAULT = new Font("Lucida Console", Font.BOLD, 28);
    
    public static WarningManager getInstance() {
        if (instance == null)
            instance = new WarningManager();
        return instance;
    }
    
    private SimpleQueue<Warning> warningQueue;
    
    private WarningManager() {
        warningQueue = new SimpleQueue();
    }
    
    public void createWarning(String warning) {
        warningQueue.add(new Warning(warning, 60,  WARNING_FONT_DEFAULT, WARNING_X_POS, WARNING_Y_POS, Color.RED));
    }
    
    public void remove(Warning warning) {
        warningQueue.remove(warning);
    }
    
    public void update() {
        if(!warningQueue.isEmpty()) {
            Node curr = (Node)warningQueue.peek();
            Warning currW = (Warning)curr.obj;
            currW.update();
            // Queue should not be empty if we are in this method, so no null check
            while(curr.next != null) {
                curr = curr.next;
                currW = (Warning)curr.obj;
                currW.update();
            }
        }
    }
    
    public void draw(Graphics g, int xOffs, int yOffs) {
        if(!warningQueue.isEmpty()) {
            Node curr = (Node)warningQueue.peek();
            Warning currW = (Warning)curr.obj;
            currW.draw(g, xOffs, yOffs);
            // Queue should not be empty if we are in this method, so no null check
            while(curr.next != null) {
                curr = curr.next;
                currW = (Warning)curr.obj;
                currW.draw(g, xOffs, yOffs);
            }
        }
    }
}
