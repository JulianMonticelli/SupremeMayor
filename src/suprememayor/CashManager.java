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
        currentCash = 50000; // 50K to start?
    }
    
    public static CashManager getInstance() {
        if(instance == null) 
            instance = new CashManager();
        return instance;
    }
    
    public void removeCash(int cashAmount) {
        currentCash -= cashAmount;
    }
    
    public void addCash(int cashAmount) {
        currentCash += cashAmount;
    }

    public int getCash() {
        return currentCash;
    }
    
}
