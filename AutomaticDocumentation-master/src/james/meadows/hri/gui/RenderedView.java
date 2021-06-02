package james.meadows.hri.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import james.meadows.hri.documentation.NetworkSwitch;
import james.meadows.hri.documentation.PropertyDocumentation;
import james.meadows.hri.export.PortMap;
import james.meadows.hri.export.PortMap.PortMapData;
import james.meadows.hri.util.NetworkUtil;

public class RenderedView extends JPanel {

	private DocumentationGUI gui;
	private JFrame main;
	
	private ArrayList<NetworkVisual> visuals = new ArrayList<NetworkVisual>();

	public RenderedView(DocumentationGUI gui) {
		this.gui = gui;
		this.main = gui.getFrame();
		this.createVisual();
		this.setupView();
	}
	
	public void createVisual() {
		this.createMatrix();
	}

	public void setupView() {

		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(4000, 4000));
		this.setBackground(Color.white);
		JScrollPane scroll = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JFrame frame = new JFrame("Rendered View");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(50, 0);
		frame.setSize(1280, 720);
		frame.add(scroll);
		frame.setResizable(true);
		frame.setVisible(true);

	}
	
	public void paint(Graphics g) {
		super.paint(g);
		drawNetworkDiagram(g);
	}

	private void drawNetworkDiagram(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (NetworkVisual visual : visuals) {
			BufferedImage image = visual.getImage();
			g2d.drawString(visual.getText(), visual.getX() + 175, visual.getY() - 5);
			g2d.drawImage(image, visual.getX(), visual.getY(), this);
		}
		/*
		 * g2d.drawLine(1200, 500, -3600, -500);
		 * 
		 * g2d.draw(new Line2D.Double(59.2d, 99.8d, 419.1d, 99.8d));
		 * 
		 * g2d.draw(new Line2D.Float(21.50f, 132.50f, 459.50f, 132.50f));
		 */

	}
	
	private void createMatrix(){
		PropertyDocumentation doc = gui.getDocumentation();
		this.visuals.add(new NetworkVisual(300,200,"switch"));
		
		PortMap portMap = new PortMap(doc.getSwitches());
		ArrayList<PortMapData> ports = portMap.createPortMap();
		NetworkSwitch core = NetworkUtil.findMasterSwitch(doc.getSwitches(), ports);
		
		NetworkVisual coreVis = new NetworkVisual(300, 400, "switch");
		coreVis.setText(core.getName());
		this.visuals.add(coreVis);
		
		
		ArrayList<NetworkSwitch> connected = NetworkUtil.findConnectedSwitches(core, doc.getSwitches(), ports);
		
		int counter = 0;
		for (NetworkSwitch sw : connected) {
			counter++;
			NetworkVisual visual = new NetworkVisual(-300 + (500 * counter), 600, "switch");
			visual.setText(sw.getName());
			this.visuals.add(visual);
		}
		
	}
}
