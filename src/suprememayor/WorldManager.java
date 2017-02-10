/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package suprememayor;

import java.awt.Graphics;

/**
 * @author Julian
 */
class WorldManager {
    private static int ticksSinceStartOfMonth;
    
    public static final int WORLD_WIDTH = 100;
    public static final int WORLD_HEIGHT = 100;
    public static final int ORIGIN_X = ( (WorldManager.WORLD_WIDTH * Tile.TILE_WIDTH) - 32 ) / 2 ;
    public static final int WORLD_PIXEL_WIDTH = (WorldManager.WORLD_WIDTH * Tile.TILE_WIDTH);
    public static final int WORLD_PIXEL_WIDTH_MIDDLE = (WorldManager.WORLD_WIDTH * Tile.TILE_WIDTH) / 2;
    public static final int WORLD_PIXEL_HEIGHT = (WorldManager.WORLD_HEIGHT * Tile.TILE_HEIGHT);
    public static final int WORLD_PIXEL_HEIGHT_MIDDLE = (WorldManager.WORLD_HEIGHT * Tile.TILE_HEIGHT) / 2;
    public static final int ORIGIN_Y = 0; // Origin Y should be 0 :)
    
    private Tile tiles[][];
    private static WorldManager SINGLETON;
    private static GamePanel gp;
    private int month, year;
    
    private String[] months =
    {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    
    /*******************************************************************
     * Returns singleton instance of the WorldManager
     * @return WorldManager singleton
     */
    public static WorldManager getInstance() {
        if(SINGLETON != null)
            return SINGLETON;
        else
            SINGLETON = new WorldManager();
        return SINGLETON;
    }
    
    public WorldManager() {
        gp = GamePanel.getInstance();
        
        month = 1;
        year = 1970;
        tiles = new Tile[WORLD_WIDTH][WORLD_HEIGHT];
        
        for(int col = 0; col < tiles.length; col++) {
            for(int row = 0; row < tiles[col].length; row++) {
                tiles[col][row] = new Tile(TileSpriteLoader.TILE_GRASS_2);
            }
        }
        
    }
    
    /*******************************************************************
     * Retuns the date (month, year)
     * @return String date
     */
    public String getDate() {
        String date = months[month-1] + ", " + year;
        return date;
    }
    
    /*******************************************************************
     * Increments the month to the next month, and updates the year if
     * necessary.
     */
    private void incrementMonth() {
        if(month == 12) {
            month = 1;
            year++;
        } else {
            month++;
        }
    }
    
    /*******************************************************************
     * Returns the amount of ticks since the start of the month. Max 899
     * @return int ticksSinceStartOfMonth
     */
    public static int getTicksSinceStartOfMonth() {
        return ticksSinceStartOfMonth;
    }
    
    /*******************************************************************
     * Queries the GamePanel to set all available tiles in a selection
     * to the given construction ID.
     * @param constructionID Index of constructable object
     */
    public void setSelectionConstructionTo(int constructionID) {
        gp.setSelectionConstructionTo(constructionID);
    }
    
    /*******************************************************************
     * Queries the GamePanel to set all available tiles in a selection
     * to the given construction ID, and to randomize the selection
     * assuming that there is more than one specific tile that the
     * construction can randomly build to.
     * @param constructionID Index of constructable object
     */
    public void setSelectionConstructionToRandom(int constructionID) {
        gp.setSelectionConstructionToRandom(constructionID);
    }
    
    /*******************************************************************
     * Queries the GamePanel to get the number of buildable tiles in a 
     * selection
     * @return number of buildable tiles in a selection
     */
    public int getNumBuildableTilesInSelection() {
        return gp.getNumBuildableTilesInSelection();
    }
    
    /*******************************************************************
     * Queries the number of tiles in total in a selection
     * @return total number of tiles in a selection
     */
    public int getNumTilesInSelection() {
        return gp.getNumTilesInSelection();
    }
    
    /*******************************************************************
     * Queries the GamePanel to check if whether there is an active
     * selection.
     * @return true if there is a selection, false if not
     */
    public boolean isSelected() {
        return gp.isSelected();
    }
    
    /*******************************************************************
     * Get a specific tile at a particular tile index
     * @param x x index of particular tile
     * @param y y index of particular tile
     * @return tile in question
     */
    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }
    
    /*******************************************************************
     * Queries the GamePanel to return the size of a selection
     * @return selection size in number of tiles 
     */
    public int[] getSelectionSize() {
        return gp.getSelectionSize();
    }
    
    /*******************************************************************
     * Update method for the WorldManager - will call tick updates as
     * necessary for each individual tile.
     */
    public void update() {
        ticksSinceStartOfMonth = (ticksSinceStartOfMonth == 900) ? 0 : ++ticksSinceStartOfMonth; // Update ticksSinceStartOfMonth
        if(ticksSinceStartOfMonth == 0) {
            incrementMonth();
            for(int col = 0; col < tiles.length; col++) {
                for(int row = 0; row < tiles[col].length; row++) {
                    tiles[col][row].monthlyUpdate();
                }
            }
        } else {
            for(int col = 0; col < tiles.length; col++) {
                for(int row = 0; row < tiles[col].length; row++) {
                    tiles[col][row].update();
                }
            }
        }
    }
    
    /*******************************************************************
     * Draws each relevant tile to screen and utilizes offsets to decide
     * whether or not to draw a particular tile to screen or not. Such
     * decisions keep the game running smooth and efficient.
     * 
     * This method utilizes drawing only on-screen tiles and guarantees
     * low-CPU utilization while during rendering.
     * 
     * @param g Graphics context
     * @param xOffs xOffset
     * @param yOffs yOffset
     */
    public void draw(Graphics g, int xOffs, int yOffs) {
        
        for(int col = 0; col < tiles.length; col++) {
            for(int row = 0; row < tiles[col].length; row++) {
                // VALUABLE maths :3
                int xPos = ORIGIN_X - (Tile.TILE_WIDTH/2 * row) + (Tile.TILE_WIDTH/2 * col) + xOffs;
                int yPos = ORIGIN_Y + (Tile.TILE_HEIGHT/2 * col) + (Tile.TILE_HEIGHT/2 * row) + yOffs;
                
                // Bounds checking for drawing
                // Still could be further optimized
                if(xOffs < -31) {
                    if((xPos + 32) <= 0)
                        continue;
                }
                if(yOffs < -15) {
                    if((yPos + 16) <= 0)
                        continue;
                }
                if(xPos > GamePanel.getSizeX())
                    continue;
                if(yPos > GamePanel.getSizeY())
                    continue;
                // End bounds checking
                
                tiles[col][row].draw(g, xPos, yPos);
            }
        }
    }
    
    /*******************************************************************
     * Unightlights a tile for the given method-specific color.
     * @param x Tile X index
     * @param y Tile Y index
     */
    public void unhighlightRed(int x, int y) {
        tiles[x][y].unhighlightRed();
    }
    
    /*******************************************************************
     * Unightlights a tile for the given method-specific color.
     * @param x Tile X index
     * @param y Tile Y index
     */
    public void unhighlightGreen(int x, int y) {
        tiles[x][y].unhighlightGreen();
    }
    
    /*******************************************************************
     * Unightlights a tile for the given method-specific color.
     * @param x Tile X index
     * @param y Tile Y index
     */
    public void unhighlightWhite(int x, int y) {
        tiles[x][y].unhighlightWhite();
    }
    
    /*******************************************************************
     * Unightlights a tile for the given method-specific color.
     * @param x Tile X index
     * @param y Tile Y index
     */
    public void unhighlightBlueWhite(int x, int y) {
        tiles[x][y].unhighlightBlueWhite();
    }
    
    /*******************************************************************
     * Hightlights a tile with the given method-specific color.
     * @param x Tile X index
     * @param y Tile Y index
     */
    public void highlightRed(int x, int y) {
        tiles[x][y].highlightRed();
    }
    
    /*******************************************************************
     * Hightlights a tile with the given method-specific color.
     * @param x Tile X index
     * @param y Tile Y index
     */
    public void highlightGreen(int x, int y) {
        tiles[x][y].highlightGreen();
    }
    
    /*******************************************************************
     * Hightlights a tile with the given method-specific color.
     * @param x Tile X index
     * @param y Tile Y index
     */
    public void highlightWhite(int x, int y) {
        tiles[x][y].highlightWhite();
    }
    
    /*******************************************************************
     * Hightlights a tile with the given method-specific color.
     * @param x Tile X index
     * @param y Tile Y index
     */
    public void highlightBlueWhite(int x, int y) {
        tiles[x][y].highlightBlueWhite();
    }

}
