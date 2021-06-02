package james.meadows.hri.util;

import java.util.ArrayList;

import james.meadows.hri.documentation.NetworkSwitch;
import james.meadows.hri.export.PortMap.PortMapData;

public class NetworkUtil {

	public static NetworkSwitch findMasterSwitch(ArrayList<NetworkSwitch> switches, ArrayList<PortMapData> portMap) {
		NetworkSwitch highest = null;
		int highest_count = 0;
		for (NetworkSwitch sw : switches) {
			int count = 0;
			for (PortMapData port : portMap) {
				if (port.isUplink() && port.getSwitchName().equals(sw.getName())) {
					count++;
				}
			}
			if (count > highest_count) {
				highest = sw;
				highest_count = count;
			}
		}
		return highest;
	}
	
	public static ArrayList<NetworkSwitch> findConnectedSwitches(NetworkSwitch master,ArrayList<NetworkSwitch> all, ArrayList<PortMapData> portMap){
		ArrayList<NetworkSwitch> connected = new ArrayList<NetworkSwitch>();
		
		for (PortMapData port : portMap) {
			if (port.isUplink() && port.getSwitchName().equals(master.getName())){
				NetworkSwitch sw = getSwitchFromName(port.getSWData().name,all);
				if (sw == null)throw new IllegalStateException("Switch Not Found: " + port.getSWData().name);
				if (sw.equals(master))continue;
				connected.add(sw);
			}
		}
		
		return connected;
	}
	
	public static NetworkSwitch getSwitchFromName(String name, ArrayList<NetworkSwitch> switches) {
		if (name.endsWith(".sw"))name = name.replace(".sw", "");
		for (NetworkSwitch sw : switches) {
			if (sw.getName().equals(name))return sw;
		}
		return null;
	}
}
