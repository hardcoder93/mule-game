import java.awt.Dimension;
import java.awt.Image;


import javax.swing.ImageIcon;


public class mapImages{
	
	private final static String IMAGE_FILE_MOUNTAIN1 = "IMAGES/mountain_1.png";
	private final static String IMAGE_FILE_MOUNTAIN2 = "IMAGES/mountain_2.png";
	private final static String IMAGE_FILE_MOUNTAIN3 = "IMAGES/mountain_3.png";
	private final static String IMAGE_FILE_PLAINS = "IMAGES/Grass.png";
	private final static String IMAGE_FILE_RIVER = "IMAGES/River.png";
	private final static String IMAGE_FILE_TOWN = "IMAGES/techTower.png";
	
	public final static Image MOUNTAIN1 = new ImageIcon(IMAGE_FILE_MOUNTAIN1).getImage();
	public final static Image MOUNTAIN2 = new ImageIcon(IMAGE_FILE_MOUNTAIN2).getImage();
	public final static Image MOUNTAIN3 = new ImageIcon(IMAGE_FILE_MOUNTAIN3).getImage();
	public final static Image PLAINS = new ImageIcon(IMAGE_FILE_PLAINS).getImage();
	public final static Image RIVER = new ImageIcon(IMAGE_FILE_RIVER).getImage();
	public final static Image TOWN = new ImageIcon(IMAGE_FILE_TOWN).getImage();
	public final static Dimension TILE_SIZE = new Dimension(MOUNTAIN1.getWidth(null), MOUNTAIN1.getHeight(null));
	
}