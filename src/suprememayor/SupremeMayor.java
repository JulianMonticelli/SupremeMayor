/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */
package suprememayor;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFrame;

/**
 *
 * @author Julian
 */
public class SupremeMayor extends JFrame {
    
    public static final String GAME_TITLE = "Supreme Mayor";
    public static final String VERSION = "0.03 Alpha";
    
    public static Random rand;
    
    public GamePanel gp;
    
    private void init() {
        // Load tiles into RAM
        try {
            TileLoader.initTiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Initializations of static classes
        Actions.initActions();
        AnimationLoader.initAnimations();
        Construction.init();
        TileIncome.initTileIncome();
        
        // Init RNG
        rand = new Random();
    }
    
    public SupremeMayor() {
        // Initialize everything that is not related to the JFrame
        init();
        
        // GamePanel init
        gp = new GamePanel();
        
        // JFrame operations
        setTitle(GAME_TITLE + " v" + VERSION);
        setUndecorated(true);
        setResizable(true);
        setLayout(new BorderLayout());
        add(gp);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
    
    private void start() {
        gp.run();
    }
    
    public static void main(String[] args) {
        SupremeMayor game = new SupremeMayor();
        
        game.start();
    }
    
}
