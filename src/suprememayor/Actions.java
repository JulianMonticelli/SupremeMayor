/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package suprememayor;

/**
 * @author Julian
 */
public class Actions {
    public static final int NUM_ACTIONS = 100;
    public static final int CANCEL = -1;
    public static final int TEST_FUNCTION = 0;
    public static final int DEMOLISH = 1;
    public static final int CONSTRUCT_SMALL_BUSINESS = 2;
    public static final int CONSTRUCT_SMALL_HOUSE = 3;
    public static final int CONSTRUCT_GROCERY_STORE = 4;
    public static final int CONSTRUCT_ROAD = 5;
    
    private static int[][] tileActions;
    private static String[] tileStrings;
    
    private static void initActionStrings() {
        tileStrings[TEST_FUNCTION] = "TEST FUNCTION";
        tileStrings[DEMOLISH] = "Demolish";
        tileStrings[CONSTRUCT_SMALL_BUSINESS] = "Construct Small Business";
        tileStrings[CONSTRUCT_SMALL_HOUSE] = "Construct Small House";
        tileStrings[CONSTRUCT_GROCERY_STORE] = "Construct Grocery Store";
        tileStrings[CONSTRUCT_ROAD] = "Construct Road";
    }
    
    public static int[] getTileActions(int tileID) {
        return tileActions[tileID];
    }
    
    public static void initActions() {
        tileActions = new int[NUM_ACTIONS][];
        tileStrings = new String[NUM_ACTIONS];
        initActionStrings();
        int[] all_allowed = {CONSTRUCT_SMALL_BUSINESS, CONSTRUCT_SMALL_HOUSE, CONSTRUCT_GROCERY_STORE, CONSTRUCT_ROAD};
        int[] demolishable = {DEMOLISH};
        
        // Buildable tiles
        tileActions[TileSpriteLoader.TILE_GRASS_1] = all_allowed;
        tileActions[TileSpriteLoader.TILE_GRASS_2] = all_allowed;
        tileActions[TileSpriteLoader.TILE_GRASS_3] = all_allowed;
        tileActions[TileSpriteLoader.TILE_DIRT_1] = all_allowed;
        tileActions[TileSpriteLoader.TILE_DIRT_2] = all_allowed;
        
        // Building Tiles
        tileActions[TileSpriteLoader.TILE_SMALL_BUSINESS_1] = demolishable;
        tileActions[TileSpriteLoader.TILE_SMALL_BUSINESS_2] = demolishable;
        
        // Home Tiles
        tileActions[TileSpriteLoader.TILE_SMALL_HOME_1] = demolishable;
    }
    
    public static String[] getMenuTitles(int[] actions) {
        String[] returnS = new String[actions.length];
        for (int i = 0; i < actions.length; i++) {
            returnS[i] = tileStrings[actions[i]];
        }
        return returnS;
    }
    
    public static String getActionTitle(int action) {
        return tileStrings[action];
    }
    
}
