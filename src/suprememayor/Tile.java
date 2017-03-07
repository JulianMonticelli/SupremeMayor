/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package suprememayor;

import java.awt.Graphics;

/**
 * @author Julian
 */
class Tile {
    
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 16;
    private static WorldManager worldManager = WorldManager.getInstance();
    
    // Instance variables
    private boolean hasAnimation;
    private Animation animation;
    private int animationTick;
    private int tileID;
    private boolean underConstruction;
    private int ticksUntilConstruction;
    private int tileIDToConstruct;
    private boolean highlightRed;
    private boolean highlightGreen;
    private boolean highlightWhite;
    private boolean highlightBlueWhite;
    private boolean generatingMoney;
    private int tileX; // Used for external reference 
    private int tileY; // Used for external reference
    
    public Tile() {
        tileID = TileLoader.Tile.GRASS_1.getValue();
        highlightRed = false;
        highlightGreen = false;
        highlightWhite = false;
        highlightBlueWhite = false;
        underConstruction = false;
        generatingMoney = false;
    }

    public int getTileID() {
        return this.tileID;
    }

    public void setTileID(int tileID) {
        this.tileID = tileID;
    }
    
    public Tile(int tileID, int x, int y) {
        this.tileX = x;
        this.tileY = y;
        this.tileID = tileID;
    }
    
    public void setHasAnimation(boolean hasAnimation) {
        this.hasAnimation = hasAnimation;
    }
    
    public boolean hasAnimation() {
        return this.hasAnimation;
    }
    
    public void setAnimation(Animation animation) {
        setHasAnimation(true);
        this.animation = animation;
    }
    
    public void setConstructionTo(int tileID) {
        setConstructionTo(tileID, tileID);
    }
    
    /********************
     * One class to control them all
     * @param constructionID
     * @param tileID 
     */
    public void setConstructionTo(int constructionID, int tileID) {
        // Deal with construction job
        underConstruction = true;
        ticksUntilConstruction = Construction.getConstructionTime(constructionID);
        tileIDToConstruct = tileID;
        
        // Set animation
        if (Construction.getConstructionTime(constructionID) > 0) {
            this.setAnimation(new Animation(6,ticksUntilConstruction,AnimationLoader.getAnimation(animationTick)));
        }
        
    }
    
    public void setConstructionToRandom(int constructionID) {
        int numVariants = Construction.getConstructionVariants(constructionID);
        int modifier = 0;
        if (numVariants > 0)
            modifier = Math.abs(SupremeMayor.rand.nextInt()) % numVariants;
        setConstructionTo(constructionID, constructionID + modifier);
    } 
    
    
    public void update() {
        if(underConstruction) {
            ticksUntilConstruction--;
            //if(this.ticksUntilConstruction % 10 == 0) System.out.println("Construction of " + this.tileID + " in " + this.ticksUntilConstruction);
            if(ticksUntilConstruction == 0) {
                finishConstruction();
            }
        }
        if(hasAnimation) {
            animationTick = (animationTick + 1) % animation.getMaxTicks();
        }
    }
    
    public void monthlyUpdate() {
        if(generatingMoney) {
            CashManager.getInstance().addCash(TileIncome.getTileIncome(tileID));
        }
        update();
    }
    
    public boolean isUnderConstruction() {
        return underConstruction;
    }
    
    private void finishConstruction() {
        // Deal with animation
        hasAnimation = false;
        animation = null;
        // Rest of the stuff
        underConstruction = false;
        tileID = tileIDToConstruct;
        tileIDToConstruct = 0;
        if(tileID == TileLoader.Tile.ROAD_HORIZONTAL.getValue() || tileID == TileLoader.Tile.ROAD_VERTICAL.getValue()) {
            worldManager.performRoadJunctionCheck(tileX, tileY, tileID);
        }
        if(TileIncome.generatesMoney(tileID))
            generatingMoney = true;
    }
    
    public boolean isRoadTile() {
        System.out.println("Checking if tile " + this.tileID + " is a road...");
        return (this.tileID >= TileLoader.Tile.ROAD_CROSS.getValue() && this.tileID <= TileLoader.Tile.ROAD_TR_BR.getValue());
    }
    
    public void highlightRed() {
        // May run into problems if we don't check for other highlightings... but for now...
        highlightRed = true;
    }
    
    public void highlightBlueWhite() {
        // May run into problems if we don't check for other highlightings... but for now...
        highlightBlueWhite = true;
    }
    
    public void highlightWhite() {
        // May run into problems if we don't check for other highlightings... but for now...
        highlightWhite = true;
    }
    
    public void highlightGreen() {
        // May run into problems if we don't check for other highlightings... but for now...
        highlightGreen = true;
    }
    
    public void unhighlightRed() {
        // May run into problems if we don't check for other highlightings... but for now...w
        highlightRed = false;
    }
    
    public void unhighlightBlueWhite() {
        // May run into problems if we don't check for other highlightings... but for now...
        highlightBlueWhite = false;
    }
    
    public void unhighlightWhite() {
        highlightWhite = false;
    }
    
    public void unhighlightGreen() {
        highlightGreen = false;
    }
    
    public void draw(Graphics g, int xPos, int yPos) {
                
        if(hasAnimation) {
            animation.draw(g, xPos, yPos, animationTick);
        } else {
            g.drawImage(TileLoader.getTileSprite(tileID),
                    xPos, yPos,
                    null);
        }
        
        // HIGHLIGHTING OPTIONS
        if (highlightWhite) {
            g.drawImage(TileLoader.getTileSprite(TileLoader.Tile.HIGHLIGHT_WHITE.getValue()), xPos, yPos, null);
        }
        if (highlightBlueWhite) {
            g.drawImage(TileLoader.getTileSprite(TileLoader.Tile.HIGHLIGHT_BLUEWHITE.getValue()), xPos, yPos, null);
        }
        if (highlightGreen) {
            g.drawImage(TileLoader.getTileSprite(TileLoader.Tile.HIGHLIGHT_GREEN.getValue()), xPos, yPos, null);
        }
        else if (highlightRed) {
            g.drawImage(TileLoader.getTileSprite(TileLoader.Tile.HIGHLIGHT_RED.getValue()), xPos, yPos, null);
        }
        //if (underConstruction) {
        //    g.drawImage(TileLoader.getTileSprite(TileLoader.Tile.FLAG_CONSTRUCTION.getValue()), xPos, yPos, null);
        //}
    }

    public boolean isBuildable() {
        if (tileID < 10 && !underConstruction)
            return true;
        return false;
    }
    
}
