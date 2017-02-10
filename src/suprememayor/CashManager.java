/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package suprememayor;

/**
 * @author Julian
 */
public class CashManager {
    private static CashManager instance;
    
    private int currentCash;
    
    private CashManager() {
        currentCash = 500000; // 500K to start?
    }
    
    /*******************************************************************
     * Gets singleton instance of the CashManager
     * @return CashManager singleton
     */
    public static CashManager getInstance() {
        if(instance == null) 
            instance = new CashManager();
        return instance;
    }
    
    /*******************************************************************
     * Removes the given amount of cash from the user CashManager
     * @param cashAmount the amount of cash to be removed
     */
    public void removeCash(int cashAmount) {
        currentCash -= cashAmount;
    }
    
    /*******************************************************************
     * Adds the given amount of cash to the user CashManager
     * @param cashAmount the amount of cash to be added
     */
    public void addCash(int cashAmount) {
        currentCash += cashAmount;
    }

    /*******************************************************************
     * Returns the amount of cash that the user has.
     * @return current cash amount of the user
     */
    public int getCash() {
        return currentCash;
    }
    
}
