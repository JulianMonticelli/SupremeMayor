/*
 * This program, if distributed by its author to the public as source code,
 * can be used if credit is given to its author and any project or program
 * released with the source code is released under the same stipulations.
 */

package suprememayor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author Julian
 */
public class ResourceManager {
    private static String resPath = "C:/Users/Julian/Pictures/IsometricTiles/";
    
    public static File getFile(String fileName) {
        File file = new File(resPath + fileName);
        return file;
    }
    
    public static BufferedImage getImageFromFile(String fileName) throws IOException {
        return ImageIO.read(getFile(fileName));
    }
}
