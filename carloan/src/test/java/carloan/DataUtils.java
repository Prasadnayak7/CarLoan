package carloan;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.DataProvider;

public class DataUtils {
    @DataProvider(name = "excelDataProvider")
    public Object[][] provideExcelData() throws IOException 
    {
        ExcelUtils excel = new ExcelUtils();
        Object[][] data = excel.readExcelData("src/test/resources/CarLoan.xlsx", "Sheet1");
        
        for(Object[] row : data) 
        {
        	System.out.println(Arrays.toString(row));
        }
        
        return data;
    }
    
    public static void writeIntoExcel(List<List<Object>> results) throws Exception 
    {
    	ExcelUtils excelUtils = new ExcelUtils();
		excelUtils.writeExcelData("src/test/resources/CarLoan.xlsx", "Car Loan Output", results);
    }
}
 
