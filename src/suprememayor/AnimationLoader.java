/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package suprememayor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Julian
 */
public class AnimationLoader {
    public enum Anims {
        CONSTRUCTION(0);
        
        int id;
        
        private Anims(int id) {
            this.id = id;
        }
        
        public int getValue() {
            return id;
        }
    }
    
    public static int NUM_ANIMATIONS = 10; // 10 for now
    private static BufferedImage[] ANIMATIONS;
    
    public static void initAnimations() {
        ANIMATIONS = new BufferedImage[NUM_ANIMATIONS];
        
        try {
            ANIMATIONS[Anims.CONSTRUCTION.getValue()] = ResourceManager.getImageFromFile("animation_construction.png");
            //??????
        } catch (IOException ex) {
            Logger.getLogger(AnimationLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static BufferedImage getAnimation(int animationID) {
        return ANIMATIONS[animationID];
    }
    
    public static BufferedImage getDefaultConstruction() {
        return getAnimation(AnimationLoader.Anims.CONSTRUCTION.getValue());
    }
    
    
}
