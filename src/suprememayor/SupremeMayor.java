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
    public static Random rand;
    public GamePanel gp;
    
    
    public SupremeMayor() {
        rand = new Random();
        try {
            TileSpriteLoader.initTiles(); // Load tiles into RAM
        } catch (IOException e) {
            e.printStackTrace();
        }
        Actions.initActions();
        Construction.init();
        TileIncome.initTileIncome();
        
        // GamePanel init
        gp = new GamePanel();
        
        // JFrame operations
        setTitle(GAME_TITLE);
        setResizable(true);
        setLayout(new BorderLayout());
        add(gp);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
    
    private void init() {
        gp.run();
    }
    
    public static void main(String[] args) {
        SupremeMayor game = new SupremeMayor();
        
        game.init();
    }
    
}
