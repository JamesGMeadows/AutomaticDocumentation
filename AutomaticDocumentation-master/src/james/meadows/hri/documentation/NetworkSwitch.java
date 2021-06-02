package james.meadows.hri.documentation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import james.meadows.hri.util.ParseUtil;

public class NetworkSwitch {

	private String name;
	private boolean firewall;
	private ArrayList<SwitchData> list = new ArrayList<SwitchData>();
	
	
	public ArrayList<SwitchData> getList(){
		return list;
	}

	public String getName() {
		return name;
	}
	
	public boolean isFirewall() {
		return firewall;
	}

	
	public static NetworkSwitch parse(File file, boolean fw) {
		NetworkSwitch sw = new NetworkSwitch();
		sw.name = file.getName().replace(".txt", "");
		try {
			List<String> lines = Files.readAllLines(file.toPath());
			sw.firewall = fw;
			for (String line : lines) {
				line = line.replaceAll(",", "");
				if (line.contains("---") || line.toLowerCase().contains("mac address"))continue;
				if (!sw.firewall) {
					SwitchData data = new SwitchData();
					line = ParseUtil.removeExcessSpaces(line, false);
					String[] split = line.split(",");
					data.vlan = Integer.valueOf(split[1]);
					data.mac = split[2].replaceAll(":", "");
					data.port = Integer.valueOf(split[3].replaceAll("gi", ""));
					data.type = split[4];
					sw.list.add(data);
				}
				else {
					SwitchData data = new SwitchData();
					line = ParseUtil.removeExcessSpaces(line, true);
					String[] split = line.split(",");
					data.ip = split[0];
					data.type = split[1];
					data.mac = split[2].replaceAll(":", "");
					data.vendor = split[3];
					data.inter = split[4];
					sw.list.add(data);
				}
			}
		} catch (IOException e) {
			System.out.println("Could not parse file: " + file.getName());
			e.printStackTrace();
		}
		return sw;
	}



	public static class SwitchData {
		public int vlan;
		public String mac;
		public int port;
		public String type;
		public String ip;
		public String vendor;
		public String inter;
		public String name;
	}
}
