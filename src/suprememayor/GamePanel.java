/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package suprememayor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

import static suprememayor.WorldManager.ORIGIN_X;
import static suprememayor.WorldManager.ORIGIN_Y;
import static suprememayor.WorldManager.WORLD_PIXEL_WIDTH;
import static suprememayor.WorldManager.WORLD_PIXEL_WIDTH_MIDDLE;
import static suprememayor.WorldManager.WORLD_PIXEL_HEIGHT;
import static suprememayor.WorldManager.WORLD_PIXEL_HEIGHT_MIDDLE;

/**
 * @author Julian
 */
public class GamePanel extends JPanel {
    public static int WIDTH = 1440;
    public static int HEIGHT = 900;
    
    public static final Color sky = Color.decode("#BBFFFF");
    
    private static final int[] INVALID_TILE = {-1,-1};
    
    private static GamePanel SINGLETON;
    
    private int moveAmount = 8;
    
    
    // State vars
    private int lastTileHoveredX;
    private int lastTileHoveredY;
    private int[] firstTileSelectedOnDrag;
    private int[] lastTileSelectedOnDrag;
    private boolean[] mousePressedDown;
    
    WorldManager world;
    InfoBar infoBar;
    
    private boolean movingLeft, movingRight, movingUp, movingDown;
    
    int xOffs = 0;
    int yOffs = 0;
    
    public GamePanel() {
        SINGLETON = this;
        
        mousePressedDown = new boolean[4];
        
        lastTileHoveredX = lastTileHoveredY = -1;
        
        firstTileSelectedOnDrag = new int[]{-1, -1};
        lastTileSelectedOnDrag = new int[]{-1, -1};
        
        world = WorldManager.getInstance();
        infoBar = InfoBar.getInstance();
        
        // Assign camera to middle of screen
        xOffs = -WORLD_PIXEL_WIDTH_MIDDLE + (WIDTH/2);
        yOffs = -WORLD_PIXEL_HEIGHT_MIDDLE + (HEIGHT/2);
        
        
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        this.setVisible(true);
        
        // Mouse presses, clicks, and releases
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int absoluteX = e.getX() - xOffs;
                int absoluteY = e.getY() - yOffs;
                mousePressedDown[e.getButton()] = true;
                if (e.getButton() == 1 && !RightClickMenu.isMenuOpen()) {
                    clearSelection();
                }
                if (RightClickMenu.inRightClick(absoluteX, absoluteY)) {
                    if (e.getButton() == 1) {
                        RightClickMenu.sendClick(absoluteX, absoluteY);
                    }
                }
                else {
                    int tileX = 0; // Init tileX
                    int tileY = 0; // Init tileY


                    if(absoluteX < 0 || absoluteY < 0 || absoluteX > WORLD_PIXEL_WIDTH || absoluteY > WORLD_PIXEL_HEIGHT) {
                        tileX = tileY = -1; // Invalid tiles
                    } else {
                        int[] tileClicked = determineTileAt(absoluteX, absoluteY);
                        tileX = tileClicked[0];
                        tileY = tileClicked[1];
                    }

                    System.out.println("Absolute x: " + absoluteX + ", y: " + absoluteY + " | Event: " + e.getButton() + " | " + "Tile X: " + tileX + ", Y: " + tileY);

                    // Send action to worldManager?
                    if(tileX != -1) { // If our X tile is EVER -1, it means it's a completely invalid tile
                        // Handle right-click
                        if(e.getButton() == MouseEvent.BUTTON3) {
                            RightClickMenu.registerRightClick(absoluteX, absoluteY, tileX, tileY);
                        }
                    }
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                mousePressedDown[e.getButton()] = false;
            }
        });
        
        // Mouse drag operations
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int absoluteX = e.getX() - xOffs;
                int absoluteY = e.getY() - yOffs;
                unhighlightSelection();
                if(firstTileSelectedOnDrag[0] == -1) {
                    firstTileSelectedOnDrag = determineTileAt(absoluteX, absoluteY);
                    lastTileSelectedOnDrag = firstTileSelectedOnDrag.clone();
                } else {
                    lastTileSelectedOnDrag = determineTileAt(absoluteX, absoluteY);
                }
                highlightSelection();
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                int absoluteX = e.getX() - xOffs;
                int absoluteY = e.getY() - yOffs;
                
                if (RightClickMenu.inRightClick(absoluteX, absoluteY)) {
                    RightClickMenu.hoverDetect(absoluteY);
                } else {
                    int tileX = 0; // Init tileX
                    int tileY = 0; // Init tileY

                    if(absoluteX < 0 || absoluteY < 0 || absoluteX > WORLD_PIXEL_WIDTH || absoluteY > WORLD_PIXEL_HEIGHT) {
                        tileX = tileY = -1; // Invalid tiles
                    } else {
                        int[] tileClicked = determineTileAt(absoluteX, absoluteY);
                        tileX = tileClicked[0];
                        tileY = tileClicked[1];
                    }

                    System.out.println("Highlight x: " + absoluteX + ", y: " + absoluteY + " | Event: " + e.getButton() + " | " + "Tile X: " + tileX + ", Y: " + tileY);

                    // Handle highlight
                    if(tileX != -1) { // If our X tile is EVER -1, it means it's a completely invalid tile
                        if (lastTileHoveredX != -1) {
                            world.unhighlightWhite(lastTileHoveredX, lastTileHoveredY);
                        }
                        world.highlightWhite(tileX, tileY);
                        lastTileHoveredX = tileX;
                        lastTileHoveredY = tileY;
                    } else {
                        if (lastTileHoveredX != -1) {
                            world.unhighlightWhite(lastTileHoveredX, lastTileHoveredY);
                        }
                    }
                }
            }
        });
        
        // KeyListener operations
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        movingUp = true;
                        break;
                    case KeyEvent.VK_S:
                        movingDown = true;
                        break;
                    case KeyEvent.VK_A:
                        movingLeft = true;
                        break;
                    case KeyEvent.VK_D:
                        movingRight = true;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        if (RightClickMenu.isMenuOpen())
                            RightClickMenu.clearRightClickMenu();
                        else if (isSelected())
                            clearSelection();
                        else
                            System.err.println("Menu not yet implemented");
                        break;
                    default:
                        break;
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        movingUp = false;
                        break;
                    case KeyEvent.VK_S:
                        movingDown = false;
                        break;
                    case KeyEvent.VK_A:
                        movingLeft = false;
                        break;
                    case KeyEvent.VK_D:
                        movingRight = false;
                        break;
                    default:
                        break;
                }
            }
        });
        
    }
    
    public boolean isSelected() {
        boolean isSelected = firstTileSelectedOnDrag[0] > -1;
        System.out.println("isSelected() : " + isSelected);
        return isSelected;
    }
    
    private void highlightSelection() {
        int tlx;
        int tly;
        int brx;
        int bry;
        
        if(firstTileSelectedOnDrag[0] == -1 || lastTileSelectedOnDrag[0] == -1) {
            return; // There is an error if we reached this, don't highlight anything.
        }
        
        if(firstTileSelectedOnDrag[0] > lastTileSelectedOnDrag[0]) {
            tlx = lastTileSelectedOnDrag[0];
            brx = firstTileSelectedOnDrag[0];
        } else {
            tlx = firstTileSelectedOnDrag[0];
            brx = lastTileSelectedOnDrag[0];
        }
        
        if(firstTileSelectedOnDrag[1] > lastTileSelectedOnDrag[1]) {
            tly = lastTileSelectedOnDrag[1];
            bry = firstTileSelectedOnDrag[1];
        } else {
            tly = firstTileSelectedOnDrag[1];
            bry = lastTileSelectedOnDrag[1];
        }
        
        for (int i = tlx; i <= brx; i++) {
            for (int j = tly; j <= bry; j++) {
                world.highlightBlueWhite(i, j);
            }
        }
    }
    
    public void setSelectionConstructionTo(int constructionID) {
        int tlx;
        int tly;
        int brx;
        int bry;
        
        if(firstTileSelectedOnDrag[0] == -1 || lastTileSelectedOnDrag[0] == -1) {
            return; // There is an error if we reached this, don't highlight anything.
        }
        
        if(firstTileSelectedOnDrag[0] > lastTileSelectedOnDrag[0]) {
            tlx = lastTileSelectedOnDrag[0];
            brx = firstTileSelectedOnDrag[0];
        } else {
            tlx = firstTileSelectedOnDrag[0];
            brx = lastTileSelectedOnDrag[0];
        }
        
        if(firstTileSelectedOnDrag[1] > lastTileSelectedOnDrag[1]) {
            tly = lastTileSelectedOnDrag[1];
            bry = firstTileSelectedOnDrag[1];
        } else {
            tly = firstTileSelectedOnDrag[1];
            bry = lastTileSelectedOnDrag[1];
        }
        
        for (int i = tlx; i <= brx; i++) {
            for (int j = tly; j <= bry; j++) {
                if(world.getTile(i, j).isBuildable())
                    world.getTile(i, j).setConstructionTo(constructionID);
            }
        }
    }
    
    public int[] getSelectionSize() {
        int[] ret = new int[2];
        ret[0] = Math.abs(firstTileSelectedOnDrag[0]-lastTileSelectedOnDrag[0]) + 1;
        ret[1] = Math.abs(firstTileSelectedOnDrag[1]-lastTileSelectedOnDrag[1]) + 1;
        return ret;
    }
    
    public int getNumTilesInSelection() {
        int tlx;
        int tly;
        int brx;
        int bry;
        
        if(firstTileSelectedOnDrag[0] == -1 || lastTileSelectedOnDrag[0] == -1) {
            return 0; // There is an error if we reached this, don't highlight anything.
        }
        
        if(firstTileSelectedOnDrag[0] > lastTileSelectedOnDrag[0]) {
            tlx = lastTileSelectedOnDrag[0];
            brx = firstTileSelectedOnDrag[0];
        } else {
            tlx = firstTileSelectedOnDrag[0];
            brx = lastTileSelectedOnDrag[0];
        }
        
        if(firstTileSelectedOnDrag[1] > lastTileSelectedOnDrag[1]) {
            tly = lastTileSelectedOnDrag[1];
            bry = firstTileSelectedOnDrag[1];
        } else {
            tly = firstTileSelectedOnDrag[1];
            bry = lastTileSelectedOnDrag[1];
        }
        int count = 0;
        for (int i = tlx; i <= brx; i++) {
            for (int j = tly; j <= bry; j++) {
                count++;
            }
        }
        return count;
    }
    
    public int getNumBuildableTilesInSelection() {
        int tlx;
        int tly;
        int brx;
        int bry;
        
        if(firstTileSelectedOnDrag[0] == -1 || lastTileSelectedOnDrag[0] == -1) {
            return 0; // There is an error if we reached this, don't highlight anything.
        }
        
        if(firstTileSelectedOnDrag[0] > lastTileSelectedOnDrag[0]) {
            tlx = lastTileSelectedOnDrag[0];
            brx = firstTileSelectedOnDrag[0];
        } else {
            tlx = firstTileSelectedOnDrag[0];
            brx = lastTileSelectedOnDrag[0];
        }
        
        if(firstTileSelectedOnDrag[1] > lastTileSelectedOnDrag[1]) {
            tly = lastTileSelectedOnDrag[1];
            bry = firstTileSelectedOnDrag[1];
        } else {
            tly = firstTileSelectedOnDrag[1];
            bry = lastTileSelectedOnDrag[1];
        }
        int count = 0;
        for (int i = tlx; i <= brx; i++) {
            for (int j = tly; j <= bry; j++) {
                if(world.getTile(i, j).isBuildable())
                    count++;
            }
        }
        return count;
    }
    
    public void setSelectionConstructionToRandom(int constructionID) {
        int tlx;
        int tly;
        int brx;
        int bry;
        
        if(firstTileSelectedOnDrag[0] == -1 || lastTileSelectedOnDrag[0] == -1) {
            return; // There is an error if we reached this, don't highlight anything.
        }
        
        if(firstTileSelectedOnDrag[0] > lastTileSelectedOnDrag[0]) {
            tlx = lastTileSelectedOnDrag[0];
            brx = firstTileSelectedOnDrag[0];
        } else {
            tlx = firstTileSelectedOnDrag[0];
            brx = lastTileSelectedOnDrag[0];
        }
        
        if(firstTileSelectedOnDrag[1] > lastTileSelectedOnDrag[1]) {
            tly = lastTileSelectedOnDrag[1];
            bry = firstTileSelectedOnDrag[1];
        } else {
            tly = firstTileSelectedOnDrag[1];
            bry = lastTileSelectedOnDrag[1];
        }
        
        for (int i = tlx; i <= brx; i++) {
            for (int j = tly; j <= bry; j++) {
                if(world.getTile(i, j).isBuildable())
                    world.getTile(i, j).setConstructionToRandom(constructionID);
            }
        }
    }
    
    private void clearSelection() {
        unhighlightSelection();
        firstTileSelectedOnDrag = new int[]{-1, -1};
        lastTileSelectedOnDrag = new int[]{-1, -1};
    }
    
    private void unhighlightSelection() {
        int tlx;
        int tly;
        int brx;
        int bry;
        
        if(firstTileSelectedOnDrag[0] == -1 || lastTileSelectedOnDrag[0] == -1) {
            return; // There is an error if we reached this, don't highlight anything.
        }
        
        if(firstTileSelectedOnDrag[0] > lastTileSelectedOnDrag[0]) {
            tlx = lastTileSelectedOnDrag[0];
            brx = firstTileSelectedOnDrag[0];
        } else {
            tlx = firstTileSelectedOnDrag[0];
            brx = lastTileSelectedOnDrag[0];
        }
        
        if(firstTileSelectedOnDrag[1] > lastTileSelectedOnDrag[1]) {
            tly = lastTileSelectedOnDrag[1];
            bry = firstTileSelectedOnDrag[1];
        } else {
            tly = firstTileSelectedOnDrag[1];
            bry = lastTileSelectedOnDrag[1];
        }
        
        for (int i = tlx; i <= brx; i++) {
            for (int j = tly; j <= bry; j++) {
                world.unhighlightBlueWhite(i, j);
            }
        }
        
    }
    private int[] determineTileAt(int absoluteX, int absoluteY) {
        int[] tileAt = new int[2];
        
        // If our absoluteX or absoluteY is more than they can possibly be, automatically assume it's an invalid tile
        if(absoluteX > (WorldManager.WORLD_WIDTH * Tile.TILE_WIDTH - 1) || absoluteY > (WorldManager.WORLD_HEIGHT * Tile.TILE_HEIGHT - 1)) {
            return INVALID_TILE;
        }
        
        // Get uncorrected tile position
        tileAt[0] = ((absoluteX-WORLD_PIXEL_WIDTH_MIDDLE) / (Tile.TILE_WIDTH/2) + absoluteY / (Tile.TILE_HEIGHT/2)) / 2;
        tileAt[1] = (absoluteY / (Tile.TILE_HEIGHT/2) - (absoluteX-WORLD_PIXEL_WIDTH_MIDDLE) / (Tile.TILE_WIDTH/2)) / 2;
        
        
        // Calculate top-left position of sprite for tile
        int xPos = ORIGIN_X - (Tile.TILE_WIDTH/2 * tileAt[1]) + (Tile.TILE_WIDTH/2 * tileAt[0]);
        int yPos = ORIGIN_Y + (Tile.TILE_HEIGHT/2 * tileAt[0]) + (Tile.TILE_HEIGHT/2 * tileAt[1]);
        
        
        // Gather maskingImage offsets - correct for div/0
        int maskingImageX = absoluteX; // Assume absoluteX
        if(xPos != 0) // Avoid div/0
            maskingImageX %= xPos; // Possible div/0
        int maskingImageY = absoluteY; // Assume absoluteY
        if(yPos != 0) // Avoid div/0
            maskingImageY %= yPos; // Possible div0
        
        // Assure our maskingImageX and maskingImageY variables are 100% in the okay range
        if (maskingImageX > 31 || maskingImageX < 0 || maskingImageY > 31 || maskingImageY < 0) {
            return INVALID_TILE;
        }
        
        // Actually call for mask adjustment
        int[] maskCorrection = TileSpriteLoader.getMaskAdjustment(maskingImageX, maskingImageY);
        // Apply mask adjustment        
        tileAt[0] += maskCorrection[0];
        tileAt[1] += maskCorrection[1];
        
        // Assure our tile is VALID at all
        if (tileAt[0] > WorldManager.WORLD_WIDTH-1 || tileAt[0] < 0 || tileAt[1] > WorldManager.WORLD_HEIGHT-1 || tileAt[1] < 0) {
            return INVALID_TILE;
        }
                
        return tileAt;
    }
    
    
    public void run() { 
        int ticksPerSecond = 30; // 30FPS
        while(true) { // while true lolz
            long beginTime = System.currentTimeMillis();
            tick();
            repaint(); // Calls paintComponent()
            long endTime = System.currentTimeMillis();
            if((endTime - beginTime) < (1000/ticksPerSecond)) {
                try {
                    Thread.sleep(1000/ticksPerSecond - (endTime-beginTime)); // Correct game loop
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    public void tick() {
        world.update();
        infoBar.update();
        if (movingRight && !movingLeft) {
            xOffs -= moveAmount; // Moving CAMERA right is setting offset to LEFT
        } else if (!movingRight && movingLeft) {
            xOffs += moveAmount;// Moving CAMERA left is setting offset to RIGHT
        }
        if (movingUp && !movingDown) {
            yOffs += moveAmount;
        } else if (!movingUp && movingDown) {
            yOffs -= moveAmount;
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(sky);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        world.draw(g, xOffs, yOffs);
        
        infoBar.draw(g);
        
        if(RightClickMenu.getInstance() != null) {
            RightClickMenu.getInstance().draw(g, xOffs, yOffs);
        }
        
    }
    
    public static int getSizeX() {
        return SINGLETON.getWidth();
    }
    public static int getSizeY() {
        return SINGLETON.getHeight();
    }
    public static GamePanel getInstance() {
        return SINGLETON;
    }

}
