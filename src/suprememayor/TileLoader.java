/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package suprememayor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author Julian
 */
public class TileLoader {
    /*
    
    Be wary editing any of the TileIDs, certain parts of the code may break.
    This is very true in regards to checking whether a tile can be built upon,
    the amount of variants of a certain tile, etc.
    
    Just be warned - if you change a value you probably will mess something up.
    
    The system is going to be changed A LOT as I slowly implement this game.
    
    It's in alpha. Things will be added. Things will be removed. Things will be changed.
    
    */ 
    
    
    private static final int NUM_TILE_SPRITES = 100; // Setting to 10 for now
    
    private static BufferedImage[] tileSprites;
    
    // Enum
    public enum Tile {
        MASK(0),
        GRASS_1(1),
        GRASS_2(2),
        GRASS_3(3),
        DIRT_1(10),
        DIRT_2(11),
        STONE_1(20),
        STONE_2(21),
        SMALL_BUSINESS_1(30),
        SMALL_BUSINESS_2(31),
        // HOMES
        SMALL_HOME_1(40),
        // UTILITY TILES
        ROAD_CROSS(70),
        ROAD_HORIZONTAL(71),
        ROAD_VERTICAL(72),
        ROAD_TL_TR(73), // Top left, top right
        ROAD_BL_BR(74), // Bottom left, bottom right
        ROAD_TL_BL(75), // Top left, bottom left
        ROAD_TR_BR(76), // Top right, bottom right
        // FLAG TILES
        FLAG_CONSTRUCTION(90),
        // HIGHLIGHT
        HIGHLIGHT_BLUEWHITE(95),
        HIGHLIGHT_WHITE(96),
        HIGHLIGHT_GREEN(97),
        HIGHLIGHT_RED(98);
        
        private int id = 99;
        
        private Tile(int id) {
            this.id = id;
        }
        
        public int getValue() {
            return this.id;
        }
    }
    
    public static void initTiles() throws IOException {
        BufferedImage spriteSheet = ResourceManager.getImageFromFile("tilesheet_small.png");
        tileSprites = new BufferedImage[NUM_TILE_SPRITES];
        System.out.println(spriteSheet.toString());
        int height = 16;
        int width = 32;
        
        int sheetSpritesWidth = 10;
        
        for(Tile tile : Tile.values()) {
            int sheetXOffset = (tile.getValue() % sheetSpritesWidth) * width;
            int sheetYOffset = (tile.getValue() / sheetSpritesWidth) * height;
            tileSprites[tile.getValue()] = spriteSheet.getSubimage(sheetXOffset, sheetYOffset, width, height);
        }
        
        
    }
    /*******************************************************************
     * Returns BufferedImage (sprite) of given Tile via tileIndex
     * @param tileIndex index of a given Tile
     * @return BufferedImage sprite 
     */
    public static BufferedImage getTileSprite(int tileIndex) {
        return tileSprites[tileIndex];
    }
    
    
    // Change this when we fix/change the res path
    
    
    /*******************************************************************
     * Get the selected tile adjustment based on the tile mask image.
     * @param maskingImageX X coordinate distance from top-left pixel of
     * masking image.
     * @param maskingImageY Y coordinate distance from top-left pixel of
     * masking image.
     * @return Adjustment of selected tile
     */
    static int[] getMaskAdjustment(int maskingImageX, int maskingImageY) {
        int[] maskAdj = {0, 0};
        // Get pixel at maskingImage offset
        System.out.println("maskingImageX = " + maskingImageX + " | maskingImageY = " + maskingImageY);
        int c = tileSprites[Tile.MASK.getValue()].getRGB(maskingImageX, maskingImageY);
        
        if (c == 0x000000) { // Black - no adjustment
            return maskAdj; // Return no adjustment
        } else if ((c & 0xFFFFFF) > 0xFFFFFA) { // White
            maskAdj[0] = 1; // X = +1
        } else if ((c & 0xFF0000) > 0) { // Red
            maskAdj[0] = -1; // X = -1
        } else if ((c & 0x00FF00) > 0) { // Green
            maskAdj[1] = -1; // Y = -1
        } else if ((c & 0x0000FF) > 0) { // Blue
            maskAdj[1] = 1; // Y = +1
        }
        return maskAdj;
    }
}
