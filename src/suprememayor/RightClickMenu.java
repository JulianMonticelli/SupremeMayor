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
public class RightClickMenu {
    
    // Right click menu colors
    private static final Color MENU_COLOR = Color.decode("#70B29C");
    private static final Color MENU_COLOR_BORDER = Color.decode("#38594E");
    private static final Font MENU_FONT = new Font("Lucida Console", Font.PLAIN, 12);
    
    // Singleton instance
    private static RightClickMenu SINGLETON;
    
    // Size variables
    private static int vertical_pad = 7; // Padding at top and bottom of RCM
    private static int total_vertical_padding = 2 * vertical_pad; // Total vertical_padding
    private static int rounding_radius = 10; // Rounding radius of corners on RCM
    private static int string_draw_offs_x = 5; // X Offset of String in RCM
    private static int string_draw_offs_y = 23; // Y Offset of String in RCM Field
    
    private int fieldHeight;
    private int fieldWidth;
    
    // State variables
    private int numFields;
    private Field[] fields;
    private int width;
    private int height;
    
    private int tileAffectedX;
    private int tileAffectedY;
    
    private int drawX, drawY;
    private int lastSelected;
    
    public RightClickMenu(int drawX, int drawY, int tileAffectedX, int tileAffectedY) {
        lastSelected = -1;
        
        // Set draw variables to variables passed in (from where user right-clicked)
        this.drawX = drawX;
        this.drawY = drawY;
        
        // Affected tile, being the tile that the user right-clicked on
        this.tileAffectedX = tileAffectedX;
        this.tileAffectedY = tileAffectedY;
        
        // Establish
        this.getFields();
        
        
        this.width = fieldWidth = GamePanel.getSizeX()/8; // Width double variable may be unneccessary but we're going to clean this up later - ran into some issues changing this
        fieldHeight = GamePanel.getSizeY()/40;
        this.height = fieldHeight * numFields + total_vertical_padding;
    }
    
    /*******************************************************************
     * Get the appropriate fields for the right-click menu
     */
    private void getFields() {
        //~~!> TODO: Fix this because it doesn't correct for selections
        
        int tileID = WorldManager.getInstance().getTile(tileAffectedX, tileAffectedY).getTileID();
        
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
    
    
    /*******************************************************************
     * Draw the RightClickMenu
     * @param g Graphics variable we are using
     * @param xOffs Screen horizontal offset
     * @param yOffs Screen vertical offset
     */
    public void draw(Graphics g, int xOffs, int yOffs) {
        g.setColor(MENU_COLOR);
        g.fillRoundRect(drawX+xOffs, drawY+yOffs, width, height, rounding_radius, rounding_radius);
        g.setColor(MENU_COLOR_BORDER);
        g.drawRoundRect(drawX+xOffs, drawY+yOffs, width, height, rounding_radius, rounding_radius);
        g.setFont(MENU_FONT);
        for (int i = 0; i < fields.length; i++) {
            fields[i].draw(g, xOffs, yOffs);
        }
    }
    
    /*******************************************************************
     * Close the RightClickMenu
     */
    private void close() {
        SINGLETON = null;
    }
    
    /*******************************************************************
     * Determine if the RightClickMenu is open.
     * @return true if the menu is open, false if the menu is closed
     */
    public static boolean isMenuOpen() {
        return (SINGLETON != null);
    }
    
    /*******************************************************************
     * Determines if the mouse at a specific absolute position is within
     * the bounds of the right click menu.
     * @param absoluteX Absolute X pos of the mouse (xPos - xOffs)
     * @param absoluteY Absolute Y pos of the mouse (yPos - yOffs)
     * @return true if the mouse pointer is in the RightClickMenu,
     * false if the mouse is not in the RightClickMenu
     */
    public static boolean inRightClick(int absoluteX, int absoluteY) {
        if(SINGLETON == null)
            return false;
        return SINGLETON.isInBounds(absoluteX, absoluteY);
    }
    
    /*******************************************************************
     * Checks to see if the mouse is hovering over a given field, and if 
     * so, selects the field for highlighting.
     * @param absoluteY 
     */
    private void checkForHover(int absoluteY) {
        if(absoluteY >= drawY+vertical_pad && absoluteY <= drawY+(vertical_pad+fieldWidth*numFields)) {
            if(lastSelected != -1) {
                fields[lastSelected].setSelected(false);
            }
            lastSelected = getSelectedField(absoluteY);
            if(lastSelected != -1) {
                fields[lastSelected].setSelected(true);
            }
        }
    }
    
    /*******************************************************************
     * Returns the specific field that is being selected (post-click).
     * @param absoluteY Absolute Y pos of the mouse (yPos - yOffs)
     * @return index of the field that is being selected, otherwise -1
     * if not selecting a field
     */
    private int getSelectedField(int absoluteY) {
        int index = (absoluteY - drawY - vertical_pad) / fieldHeight;
        if (index >= fields.length || absoluteY <= (drawY+vertical_pad)) 
            return -1;
        return index;
    }
    
    /*******************************************************************
     * If the RightClickMenu is active, calls checkForHover
     * @param absoluteY Absolute Y pos of the mouse (yPos - yOffs)
     */
    public static void hoverDetect(int absoluteY) {
        if(SINGLETON == null)
            return;
        SINGLETON.checkForHover(absoluteY);
    }
    
    /*******************************************************************
     * Returns whether the mouse is in bounds of the RightClickMenu or
     * not.
     * @param absoluteX Absolute X pos of the mouse (xPos - xOffs)
     * @param absoluteY Absolute Y pos of the mouse (yPos - yOffs)
     * @return true if in RightClickMenu, false if not
     */
    private boolean isInBounds(int absoluteX, int absoluteY) {
        if(absoluteX >= drawX && absoluteX <= drawX+width && absoluteY >= drawY && absoluteY <= drawY+height) {
                return true;
            } else {
                return false;
            }
    }
    /*******************************************************************
    * Turns a click into an action.
    * @param absoluteX Absolute X pos of the mouse (xPos - xOffs)
    * @param absoluteY Absolute Y pos of the mouse (yPos - yOffs)
    */
    private void click(int absoluteX, int absoluteY) {
        if(absoluteY >= drawY+vertical_pad && absoluteY <= drawY+(vertical_pad+fieldWidth*numFields)) {
            int selection = getSelectedField(absoluteY);
            if(selection != -1) {
                fields[selection].performAction();
            }
            close();
        }
    }
    /*******************************************************************
     * Sends a click to the SINGLETON object.
     * @param absoluteX Absolute X pos of the mouse (xPos - xOffs)
     * @param absoluteY Absolute Y pos of the mouse (yPos - yOffs)
     */
    public static void sendClick(int absoluteX, int absoluteY) {
        SINGLETON.click(absoluteX, absoluteY);
    }
    
    /*******************************************************************
     * Clears the RightClickMenu via closing it
     */
    public static void clearRightClickMenu() {
        if(SINGLETON == null)
            return;
        SINGLETON.close();
    }
    
    /*******************************************************************
     * 
     * @param drawX X of RightClickMenu 
     * @param drawY Y of RightClickMenu
     * @param tileAffectedX X index of tile affected
     * @param tileAffectedY Y index of tile affected
     */
    public static void registerRightClick(int drawX, int drawY, int tileAffectedX, int tileAffectedY) {
        SINGLETON = new RightClickMenu(drawX, drawY, tileAffectedX, tileAffectedY);
    }
    
    /*******************************************************************
     * Returns the singleton of the RightClickMenu.
     * @return RightClickMenu singleton instance
     */
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
                g.drawRect(drawX+xOffs, drawY+yOffs + (vertical_pad + (fieldHeight * index)), fieldWidth, fieldHeight);
                g.drawString(menuTitle, drawX+xOffs + string_draw_offs_x, drawY+yOffs + (string_draw_offs_y + (fieldHeight * index)));
            } else {
                g.fillRect(drawX+xOffs, drawY+yOffs + (vertical_pad + (fieldHeight * index)), fieldWidth, fieldHeight);
                g.setColor(MENU_COLOR);
                g.drawString(menuTitle, drawX+xOffs + string_draw_offs_x, drawY+yOffs + (string_draw_offs_y + (fieldHeight * index)));
                g.setColor(MENU_COLOR_BORDER);
            }
        }
    }
    
    
    
}
