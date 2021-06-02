package james.meadows.hri.util;


import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFile {

	Workbook workbook = new XSSFWorkbook();
	CreationHelper createHelper = workbook.getCreationHelper();


	public Workbook getWorkbook() {
		return workbook;
	}
	
	public CreationHelper getHelper() {
		return createHelper;
	}

	public void export(String path) {
		try {
			FileOutputStream fileOut = new FileOutputStream("./" + path + ".xlsx");
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}