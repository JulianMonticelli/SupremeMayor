/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package suprememayor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;

/**
 * @author Julian
 */
public class RightClickMenu {
    private static final Color MENU_COLOR = Color.decode("#70B29C");
    private static final Color MENU_COLOR_BORDER = Color.decode("#38594E");
    private static final Font MENU_FONT = new Font("Lucida Console", Font.PLAIN, 12);
    
    private static RightClickMenu SINGLETON;
    private static int fieldHeight = GamePanel.getSizeY()/40;
    private static int fieldWidth = GamePanel.getSizeX()/8;
    
    
    private int numFields;
    private Field[] fields;
    private int width = 180;
    private int height = 300;
    
    private int tileAffectedX;
    private int tileAffectedY;
    
    private int drawX, drawY;
    private int lastSelected;
    
    public RightClickMenu(int drawX, int drawY, int tileAffectedX, int tileAffectedY) {
        lastSelected = -1;
        
        this.drawX = drawX;
        this.drawY = drawY;
        this.tileAffectedX = tileAffectedX;
        this.tileAffectedY = tileAffectedY;
        
        this.getFields();
        
        this.width = fieldWidth;
        this.height = fieldHeight * numFields + 20;
    }
    
    
    private void getFields() {
        // TODO: Fix this because it doesn't correct for selections
        
        int tileID = WorldManager.getInstance().getTile(tileAffectedX, tileAffectedY).tileID;
        
        // Stopped here
        
        int[] actions = Actions.getTileActions(tileID);
        String[] strings = Actions.getMenuTitles(actions);
        numFields = actions.length + 1;
        fields = new Field[numFields];
        
        for(int i = 0; i < actions.length; i++) {
            fields[i] = new Field(strings[i], i, actions[i]);
        }
        // all the inclusion stuff
        
        fields[numFields-1] = new Field("Cancel", numFields-1, Actions.CANCEL);
        
        
    }
    
    public void draw(Graphics g, int xOffs, int yOffs) {
        g.setColor(MENU_COLOR);
        g.fillRoundRect(drawX+xOffs, drawY+yOffs, width, height, 10, 10);
        g.setColor(MENU_COLOR_BORDER);
        g.drawRoundRect(drawX+xOffs, drawY+yOffs, width, height, 10, 10);
        g.setFont(MENU_FONT);
        for (int i = 0; i < fields.length; i++) {
            fields[i].draw(g, xOffs, yOffs);
        }
    }
    
    private void close() {
        SINGLETON = null;
    }
    
    public static boolean isMenuOpen() {
        return (SINGLETON != null);
    }
    
    public static boolean inRightClick(int absoluteX, int absoluteY) {
        if(SINGLETON == null)
            return false;
        return SINGLETON.isInBounds(absoluteX, absoluteY);
    }
    
    private void checkForHover(int absoluteY) {
        if(absoluteY >= drawY+10 && absoluteY <= drawY+(10+fieldWidth*numFields)) {
            if(lastSelected != -1) {
                fields[lastSelected].setSelected(false);
            }
            lastSelected = getSelectedField(absoluteY);
            if(lastSelected != -1) {
                fields[lastSelected].setSelected(true);
            }
        }
    }
    
    private int getSelectedField(int absoluteY) {
        int index = (absoluteY - drawY - 10) / fieldHeight;
        if (index >= fields.length || absoluteY <= (drawY+10)) 
            return -1;
        return index;
    }
    
    public static void hoverDetect(int absoluteY) {
        if(SINGLETON == null)
            return;
        SINGLETON.checkForHover(absoluteY);
    }
    
    
    private boolean isInBounds(int absoluteX, int absoluteY) {
        if(absoluteX >= drawX && absoluteX <= drawX+width && absoluteY >= drawY && absoluteY <= drawY+height) {
                return true;
            } else {
                return false;
            }
    }
    
    public void click(int absoluteX, int absoluteY) {
        if(absoluteY >= drawY+10 && absoluteY <= drawY+(10+fieldWidth*numFields)) {
            int selection = getSelectedField(absoluteY);
            if(selection != -1) {
                fields[selection].performAction();
            }
            close();
        }
    }
    
    public static void sendClick(int absoluteX, int absoluteY) {
        SINGLETON.click(absoluteX, absoluteY);
    }
    
    public static void clearRightClickMenu() {
        if(SINGLETON == null)
            return;
        SINGLETON.close();
    }
    
    public static void registerRightClick(int drawX, int drawY, int tileAffectedX, int tileAffectedY) {
        SINGLETON = new RightClickMenu(drawX, drawY, tileAffectedX, tileAffectedY);
    }
    
    public static RightClickMenu getInstance() {
        return SINGLETON;
    }

    private class Field {
        private String menuTitle;
        private int index;
        private boolean selected;
        private int actionIndex;
        public Field(String menuTitle, int index, int actionIndex) {
            this.menuTitle = menuTitle;
            this.index = index;
            this.actionIndex = actionIndex;
        }
        public void setSelected(boolean selected) {
            this.selected = selected;
        }
        
        public void performAction() {
            switch(this.actionIndex) {
                case Actions.CANCEL:
                    break;
                case Actions.TEST_FUNCTION:
                    System.out.println("Test function successful.");
                    break;
                case Actions.DEMOLISH:
                    System.err.println(Actions.getActionTitle(this.actionIndex) + " hasn't been implemented yet.");
                    break;
                case Actions.CONSTRUCT_SMALL_BUSINESS:
                    ActionHandler.getInstance().constructSmallBusiness(tileAffectedX, tileAffectedY);
                    break;
                case Actions.CONSTRUCT_SMALL_HOUSE:
                    ActionHandler.getInstance().constructSmallHome(tileAffectedX, tileAffectedY);
                    break;
                case Actions.CONSTRUCT_GROCERY_STORE:
                    System.err.println(Actions.getActionTitle(this.actionIndex) + " hasn't been implemented yet.");
                    break;
                case Actions.CONSTRUCT_ROAD:
                    ActionHandler.getInstance().constructRoad(tileAffectedX, tileAffectedY);
                    break;
                default:
                    break;
            }
        }
        
        public void draw(Graphics g, int xOffs, int yOffs) {
            if(!selected) {
                g.drawRect(drawX+xOffs, drawY+yOffs + (10 + (fieldHeight * index)), fieldWidth, fieldHeight);
                g.drawString(menuTitle, drawX+xOffs + 3, drawY+yOffs + (28 + (fieldHeight * index)));
            } else {
                g.fillRect(drawX+xOffs, drawY+yOffs + (10 + (fieldHeight * index)), fieldWidth, fieldHeight);
                g.setColor(MENU_COLOR);
                g.drawString(menuTitle, drawX+xOffs + 3, drawY+yOffs + (28 + (fieldHeight * index)));
                g.setColor(MENU_COLOR_BORDER);
            }
        }
    }
    
    
    
}
