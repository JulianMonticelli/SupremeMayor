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
    private static WorldManager world;
    private static GamePanel gp;
    private int month, year;
    
    private String[] months =
    {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    
    
    public static WorldManager getInstance() {
        if(world != null)
            return world;
        else
            world = new WorldManager();
        return world;
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
    
    public String getDate() {
        String date = months[month-1] + ", " + year;
        return date;
    }
    
    private void incrementMonth() {
        if(month == 12) {
            month = 1;
            year++;
        } else {
            month++;
        }
    }
    
    public static int getTicksSinceStartOfMonth() {
        return ticksSinceStartOfMonth;
    }
    
    public void setSelectionConstructionTo(int constructionID) {
        gp.setSelectionConstructionTo(constructionID);
    }
    
    public void setSelectionConstructionToRandom(int constructionID) {
        gp.setSelectionConstructionToRandom(constructionID);
    }
    
    public int getNumBuildableTilesInSelection() {
        return gp.getNumBuildableTilesInSelection();
    }
    
    public int getNumTilesInSelection() {
        return gp.getNumTilesInSelection();
    }
    
    public boolean isSelected() {
        return gp.isSelected();
    }
    
    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }
    
    public int[] getSelectionSize() {
        return gp.getSelectionSize();
    }
    
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
    
    public void draw(Graphics g, int xOffs, int yOffs) {
        
        for(int col = 0; col < tiles.length; col++) {
            for(int row = 0; row < tiles[col].length; row++) {
                int xPos = ORIGIN_X - (Tile.TILE_WIDTH/2 * row) + (Tile.TILE_WIDTH/2 * col) + xOffs;
                int yPos = ORIGIN_Y + (Tile.TILE_HEIGHT/2 * col) + (Tile.TILE_HEIGHT/2 * row) + yOffs;
                
                // Bounds checking for drawing - still could be optimized
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
    
    public void unhighlightRed(int x, int y) {
        tiles[x][y].unhighlightRed();
    }
    
    public void unhighlightGreen(int x, int y) {
        tiles[x][y].unhighlightGreen();
    }
    
    public void unhighlightWhite(int x, int y) {
        tiles[x][y].unhighlightWhite();
    }
    
    public void unhighlightBlueWhite(int x, int y) {
        tiles[x][y].unhighlightBlueWhite();
    }
    
    public void highlightRed(int x, int y) {
        tiles[x][y].highlightRed();
    }
    
    public void highlightGreen(int x, int y) {
        tiles[x][y].highlightGreen();
    }
    
    public void highlightWhite(int x, int y) {
        tiles[x][y].highlightWhite();
    }
    
    public void highlightBlueWhite(int x, int y) {
        tiles[x][y].highlightBlueWhite();
    }

}
