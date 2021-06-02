package james.meadows.hri.export;

import james.meadows.hri.documentation.NetworkSwitch;
import james.meadows.hri.documentation.NetworkSwitch.SwitchData;
import james.meadows.hri.documentation.PropertyDocumentation;
import james.meadows.hri.export.PortMap.PortMapData;
import james.meadows.hri.util.ExcelFile;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExportExcel {

	ExcelFile excel;

	public ExportExcel() {
		excel = new ExcelFile();
	}
	
	public ExcelFile getExcel() {
		return excel;
	}
	
	public void createPortMapSheet(PortMap map) {
		Sheet sheet = excel.getWorkbook().createSheet("PortMap");
		String[] columns = PortMap.getPortMapColumns();
		this.createHeader(sheet, columns);
		
		ArrayList<PortMapData> ports = map.createPortMap();
		
		for (int i = 0; i < ports.size(); i++) {
			Row row = sheet.createRow(i + 1);
			PortMapData portData = ports.get(i);
			String[] data = portData.printData();
			if (data[0].equals("0")) {
				Cell cell = row.createCell(0);
				cell.setCellValue(data[2].split(":")[1]);
				CellStyle style = excel.getWorkbook().createCellStyle();
				Font headerFont = excel.getWorkbook().createFont();
				headerFont.setBold(true);
				headerFont.setColor((short) 0);
				style.setFont(headerFont);
				style.setFillForegroundColor((short)4);
				cell.setCellStyle(style);
				continue;
			}
			for (int j = 0; j < columns.length; j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue(data[j]);
			}
		}
	}
	
	private Row createHeader(Sheet sheet,String[] columns) {
		Row headerRow = sheet.createRow(0);
		CellStyle style = excel.getWorkbook().createCellStyle();
		Font headerFont = excel.getWorkbook().createFont();
		headerFont.setBold(true);
		headerFont.setColor((short) 0);
		style.setFont(headerFont);
		int counter = 0;
		for (String column : columns) {
			Cell cell = headerRow.createCell(counter);
			cell.setCellStyle(style);
			cell.setCellValue(column);
			counter++;
		}
		return headerRow;
	}

	public void createSwitchSheet(NetworkSwitch sw) {
		String[] columns = PropertyDocumentation.getSwitchColumns();
		Sheet sheet = excel.getWorkbook().createSheet(sw.getName());

		this.createHeader(sheet, columns);

		for (int i = 0; i < sw.getList().size(); i++) {
			Row row = sheet.createRow(i + 1);
			for (int j = 0; j < columns.length; j++) {
				Cell cell = row.createCell(j);
				SwitchData data = sw.getList().get(i);
				if (j == 0) {
					cell.setCellValue(data.vlan);
				}
				else if (j == 1) {
					cell.setCellValue(data.mac);
				}
				else if (j == 2) {
					cell.setCellValue(data.port);
				}
				else if (j == 3) {
					cell.setCellValue(data.type);
				}
				else if (j == 4) {
					if (data.ip == null) {
						cell.setCellValue("N/A");
					}
					else cell.setCellValue(data.ip);
				}
				else if (j == 5) {
					if (data.vendor == null) {
						cell.setCellValue("N/A");
					}
					else cell.setCellValue(data.vendor);
				}
				else if (j == 6) {
					if (data.name == null) {
						cell.setCellValue("N/A");
					}
					else cell.setCellValue(data.name);
				}
				else if (j == 7) {
					if (data.inter == null) {
						cell.setCellValue("N/A");
					}
					else cell.setCellValue(data.inter);
				}
			}
		}

		//sheet.autoSizeColumn(columns.length);
	}

}
