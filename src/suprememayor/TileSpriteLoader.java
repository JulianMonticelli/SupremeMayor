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
public class TileSpriteLoader {
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
    
    // ENVIRONMENT TILES
    public static final int TILE_MASK = 0;
    public static final int TILE_GRASS_1 = 1;
    public static final int TILE_GRASS_2 = 2;
    public static final int TILE_GRASS_3 = 3;
    public static final int TILE_DIRT_1 = 4;
    public static final int TILE_DIRT_2 = 5;
    public static final int TILE_STONE_1 = 6;
    public static final int TILE_STONE_2 = 7;
    
    
    // BUSINESSES
    public static final int TILE_SMALL_BUSINESS_1 = 20;
    public static final int TILE_SMALL_BUSINESS_2 = TILE_SMALL_BUSINESS_1 + 1;
    
    
    // HOMES
    public static final int TILE_SMALL_HOME_1 = 40;
    
    // UTILITY TILES
    public static final int TILE_ROAD_CROSS = 70;
    public static final int TILE_ROAD_HORIZONTAL = TILE_ROAD_CROSS + 1;
    public static final int TILE_ROAD_VERTICAL = TILE_ROAD_CROSS + 2;
    public static final int TILE_ROAD_TL_TR = TILE_ROAD_CROSS + 3; // Top left, top right
    public static final int TILE_ROAD_BL_BR = TILE_ROAD_CROSS + 4; // Bottom left, bottom right
    public static final int TILE_ROAD_TL_BL = TILE_ROAD_CROSS + 5; // Top left, bottom left
    public static final int TILE_ROAD_TR_BR = TILE_ROAD_CROSS + 6; // Top right, bottom right
    
    
    // FLAG TILES
    public static final int TILE_FLAG_CONSTRUCTION = 90;
    
    // HIGHLIGHT
    // Last 4 (beginning NUM_TILE_SPRITES - 4)
    public static final int TILE_HIGHLIGHT_BLUEWHITE = NUM_TILE_SPRITES - 4;
    public static final int TILE_HIGHLIGHT_WHITE = NUM_TILE_SPRITES - 3;
    public static final int TILE_HIGHLIGHT_GREEN = NUM_TILE_SPRITES - 2;
    public static final int TILE_HIGHLIGHT_RED = NUM_TILE_SPRITES - 1;
    
    
    
    public static void initTiles() throws IOException {
        tileSprites = new BufferedImage[NUM_TILE_SPRITES];
        
        tileSprites[TILE_MASK] = ImageIO.read(getFile(PNG_TILE_MASK));
        
        // ENVIRONMENT TILES
        tileSprites[TILE_GRASS_1] = ImageIO.read(getFile(PNG_TILE_GRASS_1));
        tileSprites[TILE_GRASS_2] = ImageIO.read(getFile(PNG_TILE_GRASS_2));
        
        
        // BUSINESS TILES
        tileSprites[TILE_SMALL_BUSINESS_1] = ImageIO.read(getFile(PNG_TILE_SMALL_BUSINESS_1));
        tileSprites[TILE_SMALL_BUSINESS_2] = ImageIO.read(getFile(PNG_TILE_SMALL_BUSINESS_2));
        
        
        // HOME TILES
        tileSprites[TILE_SMALL_HOME_1] = ImageIO.read(getFile(PNG_TILE_SMALL_HOME_1));

        
        // UTILITY TILES
        tileSprites[TILE_ROAD_CROSS] = ImageIO.read(getFile(PNG_TILE_ROAD_CROSS));
        tileSprites[TILE_ROAD_HORIZONTAL] = ImageIO.read(getFile(PNG_TILE_ROAD_HORIZONTAL));
        tileSprites[TILE_ROAD_VERTICAL] = ImageIO.read(getFile(PNG_TILE_ROAD_VERTICAL));
        tileSprites[TILE_ROAD_TL_TR] = ImageIO.read(getFile(PNG_TILE_ROAD_TL_TR));
        tileSprites[TILE_ROAD_BL_BR] = ImageIO.read(getFile(PNG_TILE_ROAD_BL_BR));
        tileSprites[TILE_ROAD_TL_BL] = ImageIO.read(getFile(PNG_TILE_ROAD_TL_BL));
        tileSprites[TILE_ROAD_TR_BR] = ImageIO.read(getFile(PNG_TILE_ROAD_TR_BR));
        
        // FLAG TILES
        tileSprites[TILE_FLAG_CONSTRUCTION] = ImageIO.read(getFile(PNG_TILE_FLAG_CONSTRUCTION));
        
        // Highlight Tiles
        tileSprites[TILE_HIGHLIGHT_BLUEWHITE] = ImageIO.read(getFile(PNG_TILE_HIGHLIGHT_BLUEWHITE));
        tileSprites[TILE_HIGHLIGHT_WHITE] = ImageIO.read(getFile(PNG_TILE_HIGHLIGHT_WHITE));
        tileSprites[TILE_HIGHLIGHT_GREEN] = ImageIO.read(getFile(PNG_TILE_HIGHLIGHT_GREEN));
        tileSprites[TILE_HIGHLIGHT_RED] = ImageIO.read(getFile(PNG_TILE_HIGHLIGHT_RED));
        
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
    private static String resPath = "C:/Users/Julian/Pictures/IsometricTiles/";
    private static final String PNG_TILE_MASK = "iso_16_mask.png";
    private static final String PNG_TILE_GRASS_1 = "iso_16_grass_1.png";
    private static final String PNG_TILE_GRASS_2 = "iso_16_grass_1_outlined.png";
    
    // BUSINESSES
    private static final String PNG_TILE_SMALL_BUSINESS_1 = "iso_16_small_business_1.png";
    private static final String PNG_TILE_SMALL_BUSINESS_2 = "iso_16_small_business_2.png";
    
    // HOMES
    private static final String PNG_TILE_SMALL_HOME_1 = "iso_16_small_home_1.png";
    
    // UTILITY TILES
    private static final String PNG_TILE_ROAD_CROSS = "iso_16_road_cross.png";
    private static final String PNG_TILE_ROAD_HORIZONTAL = "iso_16_road_h.png";
    private static final String PNG_TILE_ROAD_VERTICAL = "iso_16_road_v.png";
    private static final String PNG_TILE_ROAD_TL_TR = "iso_16_road_tl_tr.png";
    private static final String PNG_TILE_ROAD_BL_BR = "iso_16_road_bl_br.png";
    private static final String PNG_TILE_ROAD_TL_BL = "iso_16_road_tl_bl.png";
    private static final String PNG_TILE_ROAD_TR_BR = "iso_16_road_tr_br.png";
    
    // FLAG TILES
    private static final String PNG_TILE_FLAG_CONSTRUCTION = "iso_16_construct_flag.png";
    
    // HIGHLIGHT
    private static final String PNG_TILE_HIGHLIGHT_BLUEWHITE = "iso_16_highlight_bluewhite.png";
    private static final String PNG_TILE_HIGHLIGHT_WHITE = "iso_16_highlight_white.png";
    private static final String PNG_TILE_HIGHLIGHT_GREEN = "iso_16_highlight_green.png";
    private static final String PNG_TILE_HIGHLIGHT_RED = "iso_16_highlight_red.png";
    
    private static File getFile(String fileName) {
        File file = new File(resPath + fileName);
        return file;
    }
    
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
        int c = tileSprites[TILE_MASK].getRGB(maskingImageX, maskingImageY);
        
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
