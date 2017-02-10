/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package suprememayor;

/**
 * @author Julian
 */
public class TileIncome {
    private static int[] tileIncome;
    
    public static void initTileIncome() {
        
        tileIncome = new int[100];
        
        tileIncome[TileSpriteLoader.TILE_SMALL_BUSINESS_1] = 90;
        tileIncome[TileSpriteLoader.TILE_SMALL_BUSINESS_2] = 100;
        
        tileIncome[TileSpriteLoader.TILE_SMALL_HOME_1] = 25;
        
    }
    
    /*******************************************************************
     * Gets the income from a particular tile
     * @param tileID ID of the Tile
     * @return income of the Tile
     */
    public static int getTileIncome(int tileID) {
        return tileIncome[tileID];
    }
    
    /*******************************************************************
     * Returns whether the tile generates money
     * @param tileID ID of the Tile
     * @return true if the tile generates money, false if not
     */
    public static boolean generatesMoney(int tileID) {
        return tileIncome[tileID] > 0;
    }
}
