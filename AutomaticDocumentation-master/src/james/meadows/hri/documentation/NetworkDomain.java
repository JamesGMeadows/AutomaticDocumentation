package james.meadows.hri.documentation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import james.meadows.hri.util.ParseUtil;

public class NetworkDomain {

	
	private ArrayList<DomainData> addresses = new ArrayList<DomainData>();
	
	public ArrayList<DomainData> getAddress(){
		return addresses;
	}
	
	public NetworkDomain(String path) {
		File file = new File(path);
		try {
			List<String> lines = Files.readAllLines(file.toPath());
			System.out.println("loaded " + lines.size() + " addresses");
			parseAddresses(lines);
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private void parseAddresses(List<String> lines) {
		
		for (String line : lines) {
			
			DomainData data = new DomainData();
			
			line = ParseUtil.removeExcessSpaces(line, false);
			String[] split = line.split(",");
			if (!checkARecord(split))continue;
			data.name = split[0];
			if (data.name.isBlank())data.name = "Empty";
			data.ip = split[split.length-1];
			addresses.add(data);
		}
		
	}
	
	private boolean checkARecord(String[] split) {
		for (String line : split) {
			if (line.equals("A"))return true;
		}
		return false;
	}
	
	public static class DomainData {
		public String name;
		public String ip;
		public String type;
	}
}

