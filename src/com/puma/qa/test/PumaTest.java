package com.puma.qa.test;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PumaTest {
	WebDriver driver;
	
	@BeforeMethod
	public void setUp()
	{
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver.exe");
		//E:\BULUT DRIVE\New Software\Naveen_Mat
		//System.getProperty("user.dir")+"/drivers/chromedrivers.exe"
		driver=new ChromeDriver();
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		
		driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.get("https://in.puma.com/");

}
	@Test(priority=1)
	public void validatePageTitle()
	{
		String title=driver.getTitle();
		Assert.assertEquals(title, "PUMA.COM | Forever Faster");
	}
	
	@Test(priority=2)
	public void moveOnMen()
	{
		Actions act = new Actions(driver);
		WebElement ele=driver.findElement(By.xpath("//a[@class='digimeg-nav-item' and @data-subnav='men-subnav' ]"));
		act.moveToElement(ele);
		act.build().perform();
		
		driver.findElement(By.xpath("//a[@href='https://in.puma.com/men/shoes.html?dir=asc&order=position&in-stock=1']")).click();
		//driver.switchTo().frame("iframe");
		//a[@href='https://in.puma.com/men/shoes.html?dir=asc&order=position&in-stock=1']
		//a[contains(@href,'https://in.puma.com/men/shoes.html?dir=asc&order=position&in-stock=1')]
		
		driver.findElement(By.xpath("//a[@href='https://in.puma.com/men/shoes.html?cat=62&dir=asc&in-stock=1&order=position']")).click();
		driver.findElement(By.xpath("//img[@id='product-collection-image-90846']")).click();
		
		Set<String>  allWindows= driver.getWindowHandles();
		Iterator<String> itr=allWindows.iterator();
		String mainWindow=itr.next();
		String popUpWindow=itr.next();
		
		driver.switchTo().window(popUpWindow);

		driver.findElement(By.xpath("//a[@class='wishlistcustom']")).click();
		try {
		    WebDriverWait wait = new WebDriverWait(driver, 2);
		    wait.until(ExpectedConditions.alertIsPresent());
		    Alert alert = driver.switchTo().alert();
		    System.out.println(alert.getText());
		    alert.accept();
		    Assert.assertTrue(alert.getText().contains("Yes!"));
		} catch (Exception e) {
		    //exception handling
		}
		
		//driver.findElement(By.xpath("//a[@id='close-btn']")).click();
		
		driver.findElement(By.xpath("//input[@id='magestore-sociallogin-popup-email']")).sendKeys("test.automanual@gmail.com");
		driver.findElement(By.xpath("//input[@id='magestore-sociallogin-popup-pass']")).sendKeys("Test@1234");
		driver.findElement(By.xpath("//button[@id='magestore-button-sociallogin']")).click();
		
		String productTitle=driver.getTitle();
		Assert.assertEquals(productTitle, "Hybrid Runner FUSEFIT Men's Running Shoes1");

		//Hybrid Runner FUSEFIT Men's Running Shoes

	}
	
/*	@Test
	public void clickOnShoe()
	{
		driver.findElement(By.xpath("//a[@xpath='1']"));
	}*/
	
	@AfterMethod
	public void tearDown()
	{
		//driver.quit();
	}
}
