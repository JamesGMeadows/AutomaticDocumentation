package james.meadows.hri.gui;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import james.meadows.hri.documentation.NetworkSwitch;
import james.meadows.hri.documentation.NetworkSwitch.SwitchData;

public class DeviceView {

	DocumentationGUI gui;
	NetworkSwitch sw;
	public DeviceView(DocumentationGUI gui, NetworkSwitch sw) {
		this.gui = gui;
		this.sw = sw;
		this.createMenu();
	}
	
	private void createMenu() {
		JFrame menu = new JFrame(sw.getName() + " Table");
		menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		menu.setLocation(50, 0);
		menu.setSize(1280, 720);
		menu.setResizable(false);
		menu.setVisible(true);
		
		this.createTable(menu);
	}
	
	private void createTable(JFrame menu) {
		String[][] data = this.parseData();
		//String[] columns = new String[] {"Vlan"};
		String[] columns = new String[] {"Vlan","MacAddress","Port","Type","IPAddress","Vendor","Name","Interface"};
		JTable table = new JTable(data,columns);
		table.setFont(new Font("Calibri", Font.PLAIN, 15));
		table.setRowHeight(20); 
		JScrollPane scroll = new JScrollPane(table); 
        menu.add(scroll); 
	}
	
	private String[][] parseData(){
		ArrayList<SwitchData> list = sw.getList();
		String[][] data = new String[list.size() + 10][8];
		
		int counter = -1;
		for (SwitchData device : list) {
			counter++;
			String vendor = device.vendor == null ? "N/A" : device.vendor;
			String name = device.name == null ? "N/A" : device.name;
			data[counter] = new String[] {"" + device.vlan,device.mac,"" + device.port,device.type,device.ip,vendor,name,device.inter};
		}
		for (int i = 0; i < 10; i++) {
			//data[list.size()+i] = 
		}
		return data;
	}
	
}
