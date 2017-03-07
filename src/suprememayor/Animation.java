/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package suprememayor;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * @author Julian
 */
public class Animation {
    
    private BufferedImage[] frames;
    
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 16;
    private static final int FRAMES_CLIP_WIDTH = 5;
    
    private int frameCount;
    private int frameInterval; // 
    private int maxTicks;
    
    
    public Animation(int frameCount, int maxTicks, BufferedImage animationClip) {
        this.frameCount = frameCount;
        this.maxTicks = maxTicks;
        frameInterval = maxTicks / frameCount;
        this.frames = framesFromClip(animationClip);
    }
    
    private BufferedImage getCurrentFrame(int currentAnimationTick) {
        return frames[currentAnimationTick / frameInterval];
    }
    
    public void draw(Graphics g, int xPos, int yPos, int currentAnimationTick) {
        g.drawImage(getCurrentFrame(currentAnimationTick), xPos, yPos, null);
    }
    
    public int getMaxTicks() {
        return maxTicks;
    }
    
    private BufferedImage[] framesFromClip(BufferedImage animationClip){
        BufferedImage[] frameArray = new BufferedImage[frameCount];
        for(int i = 0; i < frameCount; i++) {
            int sheetXOffset = (i % FRAMES_CLIP_WIDTH) * TILE_WIDTH;
            int sheetYOffset = (i / FRAMES_CLIP_WIDTH) * TILE_HEIGHT;
            frameArray[i] = animationClip.getSubimage(sheetXOffset, sheetYOffset, TILE_WIDTH, TILE_HEIGHT);
        }
        return frameArray;
    }
    
    
}
