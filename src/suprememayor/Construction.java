/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package suprememayor;

/**
 * @author Julian
 */
public class Construction {
    
    public static final int SMALL_BUSINESS = TileSpriteLoader.TILE_SMALL_BUSINESS_1;
    public static final int SMALL_HOME = TileSpriteLoader.TILE_SMALL_HOME_1;
    public static final int ROAD_H = TileSpriteLoader.TILE_ROAD_HORIZONTAL;
    public static final int ROAD_V = TileSpriteLoader.TILE_ROAD_VERTICAL;
    
    
    private static int[] constructionTime;
    private static int[] constructionCost;
    private static int[] constructionVariants;
    
    
    public static void init() {
        constructionTime = new int[100];
        constructionCost = new int[100];
        constructionVariants = new int[100];
        
        // BUSINESSES
        
        // SMALL_BUSINESS
        constructionTime[SMALL_BUSINESS] = 20 * 30; // 20 seconds, or 600 ticks
        constructionCost[SMALL_BUSINESS] = 3000;
        constructionVariants[SMALL_BUSINESS] = 2;
        
        // 
        
        // HOMES
        
        // SMALL_HOME
        constructionTime[SMALL_HOME] = 15 * 30;
        constructionCost[SMALL_HOME] = 2500;
        constructionVariants[SMALL_HOME] = 1;
        
        // ROAD
        
        constructionTime[ROAD_H] = 8 * 30;
        constructionCost[ROAD_H] = 1000;
        constructionVariants[ROAD_H] = 1; // do we even need????
        
        constructionTime[ROAD_V] = 8 * 30;
        constructionCost[ROAD_V] = 1000;
        constructionVariants[ROAD_V] = 1; // do we even need????
        
        
        
    }
    
    public static int getConstructionTime(int tileID) {
        return constructionTime[tileID];
    }
    public static int getConstructionCost(int tileID) {
        return constructionCost[tileID];
    }
    public static int getConstructionVariants(int tileID) {
        return constructionVariants[tileID];
    }
}
