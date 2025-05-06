package carloan;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pom.POMFile;

public class carLoan
{
	WebDriver driver=null;
	List<List<Object>> result = new ArrayList<>();
	POMFile pom;
	
	@BeforeTest
	@Parameters({"browser"})
	public void setDriver(String b) throws Exception
	{
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setPlatform(Platform.WIN11);
		
		switch (b.toLowerCase()) 
		{
	    	case "chrome":
	    		cap.setBrowserName("chrome");
	    		break;
	    	case "edge":
	    		cap.setBrowserName("MicrosoftEdge");
	    		break;
		}
 		
		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
        driver.get("https://emicalculator.net");
        driver.manage().window().maximize();
        
        pom = new POMFile(driver);
        
        List<Object> titles = new ArrayList<Object>();
        titles.add("Loan EMI");
        titles.add("Total Interest Payable");
        titles.add("Total Payment (Principal + Interest)");
        
        result.add(titles);
	}
	
    @Test(dataProvider="excelDataProvider",dataProviderClass=DataUtils.class)
    public void test(String carLoanAmt,String intRate,String tenure) throws Exception 
    {
    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
		pom.getCarLoan().click();
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
      
        js.executeScript("arguments[0].value='" + carLoanAmt + "';", pom.getCarLoanTxt());
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", pom.getCarLoanTxt());
        
        js.executeScript("arguments[0].value='" + intRate + "';", pom.getIntRateTxt());
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", pom.getIntRateTxt());
        
        js.executeScript("arguments[0].value='" + tenure + "';", pom.getTenureTxt());
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", pom.getTenureTxt());
        
        js.executeScript("arguments[0].scrollIntoView(true);", pom.getScroll1());
        
        String emiAmttxt = pom.getEmiAmt().getText();
        
        String totIntTxt = pom.getTotInt().getText();
        
        String totPayTxt = pom.getTotPay().getText();
        
        List<Object> values = new ArrayList<Object>();
        values.add(emiAmttxt);
        values.add(totIntTxt);
        values.add(totPayTxt);
        
        result.add(values);
        
        DataUtils.writeIntoExcel(result);
        
        WebElement scroll2 = driver.findElement(By.xpath("//*[@id=\"yearheader\"]"));
        js.executeScript("arguments[0].scrollIntoView(true);", scroll2);
        
        WebElement pdf = driver.findElement(By.linkText("Download PDF"));
		pdf.click();
		Thread.sleep(3000);
		File pdfFile = new File("C:\\Users\\2389002\\Downloads\\loan_amortization_schedule.pdf");
		if (pdfFile.exists()) 
		{
			System.out.println("PDF File downloaded successfully!");
		} 
		else 
		{
			System.out.println("PDF File not found.");
		}
		
		WebElement excel = driver.findElement(By.linkText("Download Excel"));
		excel.click();
		Thread.sleep(3000);
		File excelFile = new File("C:\\Users\\2389002\\Downloads\\loan_amortization_schedule.xlsx");
		if (excelFile.exists()) 
		{
			System.out.println("Excel File downloaded successfully!");
		} 
		else 
		{
			System.out.println("Excel File not found.");
		}
						
				
		
		WebElement share = driver.findElement(By.linkText("Share"));
		share.click();
		WebElement shareLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sharelink")));
		String link = shareLink.getAttribute("value");
		System.out.println("Share Link : " + link);
    }
    
    @AfterTest
    public void quitWindow()
    {
    	driver.quit();
    }
}
