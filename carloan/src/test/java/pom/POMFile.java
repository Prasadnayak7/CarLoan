package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class POMFile 
{
	
	public POMFile(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(linkText="Car Loan")
	WebElement carLoan;
	
	@FindBy(css="input#loanamount")
	WebElement carLoanTxt;
	
	@FindBy(id="loaninterest")
	WebElement intRateTxt;
	
	@FindBy(id="loanterm")
	WebElement tenureTxt;
	
	@FindBy(xpath="//*[@id=\"emiamount\"]")
	WebElement scroll1;
	
	@FindBy(xpath="//*[@id=\"emiamount\"]/p/span")
	WebElement emiAmt;
	
	@FindBy(xpath="//*[@id=\"emitotalinterest\"]/p/span")
	WebElement totInt;
	
	@FindBy(xpath="//*[@id=\"emitotalamount\"]/p/span")
	WebElement totPay;

	public WebElement getCarLoan() {
		return carLoan;
	}

	public WebElement getCarLoanTxt() {
		return carLoanTxt;
	}

	public WebElement getIntRateTxt() {
		return intRateTxt;
	}

	public WebElement getTenureTxt() {
		return tenureTxt;
	}

	public WebElement getScroll1() {
		return scroll1;
	}

	public WebElement getEmiAmt() {
		return emiAmt;
	}

	public WebElement getTotInt() {
		return totInt;
	}

	public WebElement getTotPay() {
		return totPay;
	}
	
}
