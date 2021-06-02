package james.meadows.hri.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import james.meadows.hri.documentation.NetworkSwitch;
import james.meadows.hri.documentation.PropertyDocumentation;

public class DocumentationGUI {
	
	private JFrame frame;
	private JPanel swPanel;
	private PropertyDocumentation documentation;

	public DocumentationGUI() {
		this.setupFrame();
		new Toolbar(this);
		frame.add(swPanel);
		setupNetworkPanel("Default", PropertyDocumentation.default_switch_path, PropertyDocumentation.default_firewall_path, PropertyDocumentation.default_domain_path);
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public PropertyDocumentation getDocumentation() {
		return documentation;
	}
	
	private void reloadFrame() {
		frame.setVisible(true);
	}
	
	private void setupFrame() {
		frame = new JFrame("Automatic Documentation");
		swPanel = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout(FlowLayout.LEFT));
		frame.setLocation(300, 150);
		frame.setSize(720, 480);
		frame.setResizable(false);
		this.reloadFrame();
	}
	
	@SuppressWarnings("serial")
	public void setupNetworkPanel(String property,String sw_path, String fw_path, String domain_path) {
		documentation = new PropertyDocumentation();
		frame.setTitle(property + " Project"); 
		documentation.loadSwitches(sw_path);
		documentation.loadFirewall(fw_path);
		documentation.loadDomain(domain_path);
		documentation.matchSwitchToFirewall();
		documentation.matchDomain();
		
		swPanel.removeAll();
		swPanel.setLayout(new GridLayout(documentation.getSwitches().size(),3,50,15));
		
		

		for (NetworkSwitch sw : documentation.getSwitches()) {
			
			JTextField propertyField = new JTextField(" " + sw.getName(),10);
			propertyField.setBackground(Color.white);
			propertyField.setEditable(false);
			propertyField.setPreferredSize(new Dimension(35,35));
			
		
			swPanel.add(propertyField);
			
			final JButton button = new JButton("Devices");
			button.setName(sw.getName());
			button.addActionListener(new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String name = button.getName();
					new DeviceView(DocumentationGUI.this,documentation.getSwitch(name));
					}
				});
			swPanel.add(button);
			
		}
		
		this.reloadFrame();
	}
}
