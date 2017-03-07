/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package suprememayor;

import java.util.Arrays;

/**
 * @author Julian
 */
public class Construction {
    
    public static final int SMALL_BUSINESS = TileLoader.Tile.SMALL_BUSINESS_1.getValue();
    public static final int SMALL_HOME = TileLoader.Tile.SMALL_HOME_1.getValue();
    public static final int ROAD_H = TileLoader.Tile.ROAD_HORIZONTAL.getValue();
    public static final int ROAD_V = TileLoader.Tile.ROAD_VERTICAL.getValue();
    
    
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
    
    
    /*******************************************************************
     * Get the amount of time it takes to build a constructable
     * @param constructionID index of constructable 
     * @return time it takes to construct given constructable
     */
    public static int getConstructionTime(int constructionID) {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
        return constructionTime[constructionID];
    }
    
    /*******************************************************************
     * Get the amount of cash it takes to build a constructable
     * @param constructionID index of constructable 
     * @return cash it takes to construct given constructable
     */
    public static int getConstructionCost(int constructionID) {
        return constructionCost[constructionID];
    }
    
    /*******************************************************************
     * Get the amount of variables constructable has
     * @param constructionID index of constructable 
     * @return variables constructable has
     */
    public static int getConstructionVariants(int constructionID) {
        return constructionVariants[constructionID];
    }
}
