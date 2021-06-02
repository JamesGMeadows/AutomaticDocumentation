package james.meadows.hri.export;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ExportPNG {

	private void export(JPanel panel) {
		BufferedImage bi = new BufferedImage(panel.getSize().width, panel.getSize().height, BufferedImage.TYPE_INT_ARGB); 
		Graphics g = bi.createGraphics();
		panel.paint(g);
		g.dispose();
		try{
			ImageIO.write(bi,"png",new File("test.png"));
		}catch (Exception e) {}
	}
}
