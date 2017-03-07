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
    
    /*******************************************************************
     * Initializes Action strings
     */
    private static void initActionStrings() {
        tileStrings[TEST_FUNCTION] = "TEST FUNCTION";
        tileStrings[DEMOLISH] = "Demolish";
        tileStrings[CONSTRUCT_SMALL_BUSINESS] = "Construct Small Business";
        tileStrings[CONSTRUCT_SMALL_HOUSE] = "Construct Small House";
        tileStrings[CONSTRUCT_GROCERY_STORE] = "Construct Grocery Store";
        tileStrings[CONSTRUCT_ROAD] = "Construct Road";
    }
    
    /*******************************************************************
     * Return what actions are available for a given tile.
     * @param tileID
     * @return 
     */
    public static int[] getTileActions(int tileID) {
        return tileActions[tileID];
    }
    
    /*******************************************************************
     * Initializes Actions and all Action-related variables.
     */
    public static void initActions() {
        tileActions = new int[NUM_ACTIONS][];
        tileStrings = new String[NUM_ACTIONS];
        initActionStrings();
        int[] all_allowed = {CONSTRUCT_SMALL_BUSINESS, CONSTRUCT_SMALL_HOUSE, CONSTRUCT_GROCERY_STORE, CONSTRUCT_ROAD};
        int[] demolishable = {DEMOLISH};
        
        // Buildable tiles
        tileActions[TileLoader.Tile.GRASS_1.getValue()] = all_allowed;
        tileActions[TileLoader.Tile.GRASS_2.getValue()] = all_allowed;
        tileActions[TileLoader.Tile.GRASS_3.getValue()] = all_allowed;
        tileActions[TileLoader.Tile.DIRT_1.getValue()] = all_allowed;
        tileActions[TileLoader.Tile.DIRT_2.getValue()] = all_allowed;
        
        // Building Tiles
        tileActions[TileLoader.Tile.SMALL_BUSINESS_1.getValue()] = demolishable;
        tileActions[TileLoader.Tile.SMALL_BUSINESS_2.getValue()] = demolishable;
        
        // Home Tiles
        tileActions[TileLoader.Tile.SMALL_HOME_1.getValue()] = demolishable;
        
        // Road Tiles
        tileActions[TileLoader.Tile.ROAD_CROSS.getValue()] = demolishable;
        tileActions[TileLoader.Tile.ROAD_HORIZONTAL.getValue()] = demolishable;
        tileActions[TileLoader.Tile.ROAD_VERTICAL.getValue()] = demolishable;
        tileActions[TileLoader.Tile.ROAD_TL_TR.getValue()] = demolishable;
        tileActions[TileLoader.Tile.ROAD_BL_BR.getValue()] = demolishable;
        tileActions[TileLoader.Tile.ROAD_TL_BL.getValue()] = demolishable;
        tileActions[TileLoader.Tile.ROAD_TR_BR.getValue()] = demolishable;
    }
    
    /*******************************************************************
     * Returns String[] of Action titles from a given int[] of action
     * indices
     * @param actions Array of action indexes
     * @return String[] of Action titles
     */
    public static String[] getMenuTitles(int[] actions) {
        String[] returnS = new String[actions.length];
        for (int i = 0; i < actions.length; i++) {
            returnS[i] = tileStrings[actions[i]];
        }
        return returnS;
    }
    
    /*******************************************************************
     * Gets the title of a specific action via an action index
     * @param action integer index of a specific action
     * @return action title String
     */
    public static String getActionTitle(int action) {
        return tileStrings[action];
    }
    
}
