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
        tileID = TileSpriteLoader.TILE_GRASS_1;
        highlightRed = false;
        highlightGreen = false;
        highlightWhite = false;
        highlightBlueWhite = false;
        underConstruction = false;
        generatingMoney = false;
    }

    public int getTileID() {
        return tileID;
    }

    public void setTileID(int tileID) {
        this.tileID = tileID;
    }
    
    public Tile(int tileID, int x, int y) {
        this.tileX = x;
        this.tileY = y;
        this.tileID = tileID;
    }
    
    public void setConstructionTo(int tileID) {
        underConstruction = true;
        ticksUntilConstruction = Construction.getConstructionTime(tileID);
        tileIDToConstruct = tileID;
    }
    
    public void setConstructionTo(int constructionID, int tileID) {
        underConstruction = true;
        ticksUntilConstruction = Construction.getConstructionTime(constructionID);
        tileIDToConstruct = tileID;
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
            if(ticksUntilConstruction == 0) {
                finishConstruction();
            }
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
        underConstruction = false;
        tileID = tileIDToConstruct;
        if(tileIDToConstruct == Construction.ROAD_H || tileIDToConstruct == Construction.ROAD_V) {
            worldManager.performRoadJunctionCheck(tileX, tileY, tileID);
        }
        tileIDToConstruct = 0;
        if(TileIncome.generatesMoney(tileID))
            generatingMoney = true;
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
                
        // DRAWING THE BARE TILE
        g.drawImage(TileSpriteLoader.getTileSprite(tileID),
                xPos, yPos,
                null);
        
        
        // HIGHLIGHTING OPTIONS
        if (highlightWhite) {
            g.drawImage(TileSpriteLoader.getTileSprite(TileSpriteLoader.TILE_HIGHLIGHT_WHITE), xPos, yPos, null);
        }
        if (highlightBlueWhite) {
            g.drawImage(TileSpriteLoader.getTileSprite(TileSpriteLoader.TILE_HIGHLIGHT_BLUEWHITE), xPos, yPos, null);
        }
        if (highlightGreen) {
            g.drawImage(TileSpriteLoader.getTileSprite(TileSpriteLoader.TILE_HIGHLIGHT_GREEN), xPos, yPos, null);
        }
        else if (highlightRed) {
            g.drawImage(TileSpriteLoader.getTileSprite(TileSpriteLoader.TILE_HIGHLIGHT_RED), xPos, yPos, null);
        }
        if (underConstruction) {
            g.drawImage(TileSpriteLoader.getTileSprite(TileSpriteLoader.TILE_FLAG_CONSTRUCTION), xPos, yPos, null);
        }
    }

    public boolean isBuildable() {
        if (tileID < 10 && !underConstruction)
            return true;
        return false;
    }
}
