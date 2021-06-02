package james.meadows.hri.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import james.meadows.hri.documentation.NetworkSwitch;
import james.meadows.hri.documentation.NetworkSwitch.SwitchData;

public class PortMap {

	private static final int MAX_PORTS = 52;
	private ArrayList<NetworkSwitch> switches;

	public PortMap(ArrayList<NetworkSwitch> switches) {
		this.switches = switches;
	}

	public static String[] getPortMapColumns() {
		return new String[] { "SwitchPort", "Vlan", "Comment" };
	}

	public ArrayList<PortMapData> createPortMap() {
		ArrayList<PortMapData> output = new ArrayList<PortMapData>();
		for (NetworkSwitch sw : switches) {
			for (int i = 0; i <= MAX_PORTS; i++) {
				boolean uplink = false;
				ArrayList<SwitchData> list = new ArrayList<SwitchData>();
				for (SwitchData data : sw.getList()) {
					if (data.port != i)continue;
					
					if (data.name != null && data.vendor != null) {
						if (data.name.endsWith(".sw") || data.vendor.equalsIgnoreCase("CISCO_SYSTEMS")) {
							uplink = true;
							list.clear();
							list.add(data);
							continue;
						}
					}
					boolean contains = false;
					for (SwitchData check : list) {
						if (data.mac.equals(check.mac)) {
							contains = true;
						}
					}
					if (!contains)list.add(data);

				}
				
				if (list.size() == 0) {
					PortMapData portData = new PortMapData();
					portData.emptyPort = true;
					portData.port = i;
					portData.swName = sw.getName();
					output.add(portData);
				}
				if (uplink) {
					for (SwitchData data : list) {
						if (data.name == null || data.vendor == null)continue;
						if (data.name.endsWith(".sw") || data.vendor.equalsIgnoreCase("CISCO_SYSTEMS")) {
							PortMapData portData = new PortMapData();
							portData.uplink = true;
							portData.port = i;
							portData.data = data;
							portData.swName = sw.getName();
							output.add(portData);
						}
					}
				} 
				else {
					for (SwitchData data : list) {
						PortMapData portData = new PortMapData();
						portData.port = i;
						portData.data = data;
						portData.swName = sw.getName();
						output.add(portData);
					}
				}
			}
		}
		return output;
	}
	
	public static class PortMapData {
		SwitchData data;
		int port;
		String swName;
		boolean emptyPort = false;
		boolean uplink = false;
		
		public SwitchData getSWData() {
			return data;
		}
		
		public boolean isEmpty() {
			return emptyPort;
		}
		
		public boolean isUplink() {
			return uplink;
		}
		
		public String getSwitchName() {
			return swName;
		}
		
		public String[] printData() {
			String[] values = new String[getPortMapColumns().length];
			
			if (uplink) {
				values[0] = "" + data.port;
				values[1] = "" + data.vlan;
				values[2] = "UPLINK:" + data.name;
			}
			else if (emptyPort) {
				values[0] = "" + port;
				values[1] = "" + 0;
				values[2] = "EmptyPort";
			}
			else {
				String vendor = data.vendor == null ? "N/A" : data.vendor;
				String name = data.name == null ? "N/A" : data.name;
				values[0] = "" + data.port;
				values[1] = "" + data.vlan;
				values[2] = vendor + ":" + name;
			}
			return values;
		}
	}
}
