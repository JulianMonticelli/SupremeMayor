/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package suprememayor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * @author Julian
 */
public class Warning {
    int ticksTillKilled;
    int ticksSinceDispatch;
    String message;
    Font font;
    Color color;
    int absX;
    int absY;
    
    public Warning(String message, int limit, Font font, int absX, int absY, Color color) {
        this.ticksSinceDispatch = 0;
        this.ticksTillKilled = limit;
        this.message = message;
        this.absX = absX;
        this.absY = absY;
        this.font = font;
        this.color = color;
    }
    
    public void update() {
        ticksSinceDispatch++;
        if (ticksSinceDispatch == ticksTillKilled) {
            WarningManager.getInstance().remove(this);
        }
    }
    
    public void draw(Graphics g, int xOffs, int yOffs) {
        // draw code here
        //
        g.setFont(font);
        g.setColor(color);
        g.drawString(message, absX, absY);
    }
}
