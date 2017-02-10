/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package suprememayor;

/**
 * @author Julian
 */
public class ActionHandler {
    private CashManager cashManager;
    private WarningManager warningManager;
    private WorldManager worldManager;
    private static ActionHandler instance;
    
    /*******************************************************************
     * Returns singleton instance of the ActionHandler
     * @return ActionHandler singleton
     */
    public static ActionHandler getInstance() {
        if(instance == null)
            instance = new ActionHandler();
        return instance;
    }
    
    /*******************************************************************
     * Constructor to ActionHandler, only responsible for gathering
     * local references to CashManager and WorldManager
     */
    private ActionHandler() {
        cashManager = CashManager.getInstance();
        worldManager = WorldManager.getInstance();
        warningManager = WarningManager.getInstance();
    }
    
    /*******************************************************************
     * Alerts the user that there is not enough money.
     */
    private void alertNotEnoughMoney() {
        System.err.println("Not enough money!");
        warningManager.createWarning("Not enough money!");
    }
    
    /*******************************************************************
     * Alerts the user that nothing can be done because every possible
     * tile considered is under construction.
     */
    private void alertUnderConstruction() {
        System.err.println("Already under construction!");
        warningManager.createWarning("Already under construction!");
    }
    
    /*******************************************************************
     * Alerts the user that nothing can be done because every possible
     * tile cannot be built upon.
     */
    private void alertNoBuildableTiles() {
        System.err.println("No buildable tiles!");
        warningManager.createWarning("No buildable tiles!");
    }
    
    /*******************************************************************
     * Construct small business on a given tile or a selection, if there
     * is one available.
     * @param tileAffectedX Tile(X) RightClickMenu was spawned from
     * @param tileAffectedY Tile(Y) RightClickMenu was spawned from
     */
    public void constructSmallBusiness(int tileAffectedX, int tileAffectedY) {
        if(worldManager.isSelected()) {
            int numBuildable = worldManager.getNumBuildableTilesInSelection();
            if(cashManager.getCash() < Construction.getConstructionCost(Construction.SMALL_BUSINESS) * numBuildable) {
                alertNotEnoughMoney();
                return;
            }
            if(numBuildable == 0) {
                alertNoBuildableTiles();
                return;
            }
            worldManager.setSelectionConstructionToRandom(Construction.SMALL_BUSINESS);
            cashManager.removeCash(Construction.getConstructionCost(Construction.SMALL_BUSINESS) * numBuildable);
        } else {
            if(cashManager.getCash() < Construction.getConstructionCost(Construction.SMALL_BUSINESS)) {
                alertNotEnoughMoney();
                return;
            }
            if(worldManager.getTile(tileAffectedX, tileAffectedY).isUnderConstruction()) {
                alertUnderConstruction();
                return;
            }
            worldManager.getTile(tileAffectedX, tileAffectedY).setConstructionToRandom(Construction.SMALL_BUSINESS);
            cashManager.removeCash(Construction.getConstructionCost(Construction.SMALL_BUSINESS));
        }
    }
    
    /*******************************************************************
     * Construct small home on a given tile or a selection, if there
     * is one available.
     * @param tileAffectedX Tile(X) RightClickMenu was spawned from
     * @param tileAffectedY Tile(Y) RightClickMenu was spawned from
     */
    public void constructSmallHome(int tileAffectedX, int tileAffectedY) {
        if(worldManager.isSelected()) {
            int numBuildable = worldManager.getNumBuildableTilesInSelection();
            if(cashManager.getCash() < Construction.getConstructionCost(Construction.SMALL_HOME) * numBuildable) {
                alertNotEnoughMoney();
                return;
            }
            if(numBuildable == 0) {
                alertNoBuildableTiles();
                return;
            }
            worldManager.setSelectionConstructionToRandom(Construction.SMALL_HOME);
            cashManager.removeCash(Construction.getConstructionCost(Construction.SMALL_HOME) * numBuildable);
        } else {
            if(cashManager.getCash() < Construction.getConstructionCost(Construction.SMALL_HOME)) {
                alertNotEnoughMoney();
                return;
            }
            if(worldManager.getTile(tileAffectedX, tileAffectedY).isUnderConstruction()) {
                alertUnderConstruction();
                return;
            }
            worldManager.getTile(tileAffectedX, tileAffectedY).setConstructionToRandom(Construction.SMALL_HOME);
            cashManager.removeCash(Construction.getConstructionCost(Construction.SMALL_HOME));
        }
    }
    
    /*******************************************************************
     * Construct road on a given tile or a selection, if there is one
     * available.
     * @param tileAffectedX Tile(X) RightClickMenu was spawned from
     * @param tileAffectedY Tile(Y) RightClickMenu was spawned from
     */
    public void constructRoad(int tileAffectedX, int tileAffectedY) {
        if(worldManager.isSelected()) {
            int[] ss = worldManager.getSelectionSize();
            if ((ss[0] == 1 && ss[1] > 2) || (ss[0] > 2 && ss[1] == 1) ) {
                int numBuildable = worldManager.getNumBuildableTilesInSelection();
                int totalCost = numBuildable * Construction.getConstructionCost(Construction.ROAD_H);
                if (cashManager.getCash() < totalCost) {
                    alertNotEnoughMoney();
                    return;
                }
                if(numBuildable == 0) {
                    alertNoBuildableTiles();
                    return;
                }
                if (ss[0] == 1) {
                    worldManager.setSelectionConstructionTo(Construction.ROAD_V);
                } else {
                    worldManager.setSelectionConstructionTo(Construction.ROAD_H);
                }
                cashManager.removeCash(totalCost);
            } else {
                System.err.println("Invalid road length - use lines of length 2 or more.");
            }
        } else {
            System.err.println("I haven't implemented single-road tile construction yet.");
        }
    }
    
}
