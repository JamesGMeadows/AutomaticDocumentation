package james.meadows.hri.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class NetworkVisual {

	private BufferedImage image;
	private int x, y;
	private String text = "";

	public NetworkVisual(int x, int y, String path) {
		this.x = x;
		this.y = y;

		this.createImage(path);
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	

	private void createImage(String path) {
		try {
			image = ImageIO.read(new File("./images/" + path + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
