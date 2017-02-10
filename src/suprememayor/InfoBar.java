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
public class InfoBar {
    private static final Color INFOBAR_COLOR_OUTLINE = new Color(164, 66, 244, 0xFF);
    private static final Color INFOBAR_COLOR = new Color(164, 66, 244, 0x80);
    private static final Color INFOBAR_TEXT_COLOR = Color.decode("#54D854");
    
    private static final Font INFOBAR_FONT_DEFAULT = new Font("Lucida Console", Font.BOLD, 14);
    
    private static final int INFOBAR_WIDTH = 120;
    private static final int INFOBAR_HEIGHT = 50;
    private static final int INFOBAR_X = 10;
    private static final int INFOBAR_Y = 10;
    private static final int INFOBAR_TEXT_WIDTH_OFFSET = 20;
    private static final int INFOBAR_TEXT_HEIGHT_OFFSET = 30;
    private static final int INFOBAR_TEXT_HEIGHT = 20;
    
    private static InfoBar instance;
    
    int cash;
    
    private WorldManager worldManager;
    
    public static InfoBar getInstance() {
        if(instance == null) 
            instance = new InfoBar();
        return instance;
    }
    
    private InfoBar() {
       cash = 0; 
       worldManager = WorldManager.getInstance();
    }
    
    
    public void update() {
        cash = CashManager.getInstance().getCash();
    }
    
    public void draw(Graphics g) {
        g.setColor(INFOBAR_COLOR);
        g.fillRoundRect(INFOBAR_X, INFOBAR_Y, INFOBAR_WIDTH, INFOBAR_HEIGHT, 10, 10);
        g.setColor(INFOBAR_COLOR_OUTLINE);
        g.drawRoundRect(INFOBAR_X, INFOBAR_Y, INFOBAR_WIDTH, INFOBAR_HEIGHT, 10, 10);
        g.setFont(INFOBAR_FONT_DEFAULT);
        g.setColor(INFOBAR_TEXT_COLOR);
        g.drawString(("Cash: " + cash), INFOBAR_TEXT_WIDTH_OFFSET, INFOBAR_TEXT_HEIGHT_OFFSET);
        g.drawString(worldManager.getDate(), INFOBAR_TEXT_WIDTH_OFFSET, INFOBAR_TEXT_HEIGHT_OFFSET + INFOBAR_TEXT_HEIGHT);
    }
}
