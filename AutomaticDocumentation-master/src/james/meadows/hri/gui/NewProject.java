package james.meadows.hri.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class NewProject {

	private DocumentationGUI gui;
	private JFrame frame;
	private JTextField propertyField;
	private String fw_path;
	private String domain_path;
	private String switch_path;
	
	public NewProject(DocumentationGUI gui) {
		this.gui = gui;
		this.frame = gui.getFrame();
		this.createMenu();
	}
	
	private void createMenu() {
		JFrame menu = new JFrame("New Project");
		menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		menu.setLocation(412, 250);
		menu.setSize(480, 220);
		menu.setResizable(false);
		menu.setVisible(true);
		
		this.createFields(menu);
        this.createOptions(menu);
   
		
	}
	
	private void createFields(JFrame menu) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel name_label = new JLabel("Property Name: ");
		propertyField = new JTextField(27);
		name_label.setLabelFor(propertyField);
		panel.add(name_label);
		panel.add(propertyField);
		
		
		final JLabel sw_label = new JLabel("Switch Folder:     "); 
		panel.add(sw_label); 
		
		JTextField sw_loc = new JTextField("Default Path",27);
		sw_loc.setEditable(false);
		panel.add(sw_loc);
		 
		JButton sw = new JButton("Files");
		sw.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				  JFileChooser chooser = new JFileChooser();
				  chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
                  if (chooser.showOpenDialog(menu) == JFileChooser.APPROVE_OPTION) {
                      String path = chooser.getSelectedFile().getAbsolutePath();
                      sw_loc.setText(path);
                      NewProject.this.switch_path = path;
                  }
             
			}			
		});
		panel.add(sw);
		
		final JLabel fw_label = new JLabel("Firewall File:        "); 
		panel.add(fw_label); 
		
		JTextField fw_loc = new JTextField("Default Path",27);
		fw_loc.setEditable(false);
		panel.add(fw_loc);
		
		JButton fw = new JButton("Files");
		fw.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				  JFileChooser chooser = new JFileChooser();
				  chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); 
                  if (chooser.showOpenDialog(menu) == JFileChooser.APPROVE_OPTION) {
                      String path = chooser.getSelectedFile().getAbsolutePath();
                      fw_loc.setText(path);
                      NewProject.this.fw_path = path;
                  }
             
			}			
		});
		panel.add(fw);
		
		
		final JLabel domain_label = new JLabel("Domain File:        "); 
		panel.add(domain_label); 
		
		JTextField domain_loc = new JTextField("Default Path",27);
		domain_loc.setEditable(false);
		panel.add(domain_loc);
		
		JButton domain = new JButton("Files");
		domain.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				  JFileChooser chooser = new JFileChooser();
				  chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); 
                  if (chooser.showOpenDialog(menu) == JFileChooser.APPROVE_OPTION) {
                      String path = chooser.getSelectedFile().getAbsolutePath();
                      domain_loc.setText(path);
                      NewProject.this.domain_path = path;
                  }
             
			}			
		});
		panel.add(domain);
		
		
		
		
        menu.getContentPane().add(panel,BorderLayout.CENTER);
	}
	
	@SuppressWarnings("serial")
	private void createOptions(JFrame menu) {
		 JPanel end_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	        JButton enter = new JButton("Enter");
	        
	        enter.addActionListener(new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					 gui.setupNetworkPanel(propertyField.getText(), switch_path, fw_path, domain_path);
					
					 menu.setVisible(false);
					 menu.dispose();
				}			
			});
	        
	        end_panel.add(enter);
	        
	        JButton cancel = new JButton("Cancel");
	        cancel.addActionListener(new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent e) {
					 menu.setVisible(false);
					 menu.dispose();
				}			
			});
	        end_panel.add(cancel);
	        
	        menu.getContentPane().add(end_panel,BorderLayout.PAGE_END);
	}
}
