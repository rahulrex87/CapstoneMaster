package MobileAppAutomationTestSuite;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Utility.Items;
import Utility.Utility;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;


public class AppiumDriverBase {
	AppiumDriver<MobileElement> driver;
	
	
	@DataProvider(name = "ItemsData")
	public Object[][] LoadTestData() {  
		List<Items> items = Utility.readItemsFromCSV("C:\\Users\\rahul\\Desktop\\Capstone Project\\res\\items.csv");
		Object[][] objArr = new Object[items.size()][];
		for(int i=0;i< items.size();i++){
		      objArr[i] = new Object[1];
		      objArr[i][0] = items.get(i);
		   } 
		return objArr;
		}

	
	@BeforeTest
	public void SetupAppiumDriver() {
		//Set the Desired Capabilities
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("deviceName", "RN5");
		caps.setCapability("udid", "61db71eb"); //Give Device ID of your mobile phone
		caps.setCapability("platformName", "Android");
		//automationName=UiAutomator1
		caps.setCapability("automationName", "UiAutomator1");
		caps.setCapability("appPackage", "in.amazon.mShop.android.shopping");
		caps.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");
		caps.setCapability("noReset", "true");
		
		//Instantiate Appium Driver
		try {
				driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), caps);
				driver.rotate(ScreenOrientation.PORTRAIT);
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test(dataProvider = "ItemsData")
	public void SearchItemAndAddToCart(Items param) {
		NavigateToHomePage();
		//Search for Item and click where the price matches
		try{
			MobileElement SearchBox = driver.findElement(MobileBy.id("in.amazon.mShop.android.shopping:id/rs_search_src_text"));
			SearchBox.sendKeys(param.itemName + "\n");
			List<MobileElement> items = driver.findElements(MobileBy.className("android.widget.TextView"));
			boolean isItemfound = false;
			for(MobileElement item:items)
			{
				if(item.getAttribute("text").equalsIgnoreCase(param.price)) {
				item.click();
				isItemfound = true;
				break;
				}
			}
			if(isItemfound == false)
				org.testng.Assert.fail("Can not find " + param.itemName + " with Price " + param.price);
		}catch(Exception exp) {}
		
		  try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
		org.testng.Assert.fail("Time out , page could not be loaded .. Try increasing the sleep duration");
		}	
	    
		//Search for "Add To Cart" button and click it if found
		try {
			MobileElement el1 = driver.findElement(MobileBy.id("add-to-cart-button"));
			  this.Scroll();
			  el1.click();
			  el1.click();
			  el1.click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			org.testng.Assert.fail(e.getMessage());
		}      
	}
		
	@Test(dependsOnMethods = "SearchItemAndAddToCart")
	public void OpenCart() {
		NavigateToHomePage();
		try {
			MobileElement elem = driver.findElement(MobileBy.AccessibilityId("Cart"));
			elem.click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			org.testng.Assert.fail(e.getMessage());
		}
	}
	
	@Test(dependsOnMethods = "OpenCart")
	public void RemoveAnItemFromCart() {
		List<MobileElement> items = driver.findElementsByClassName("android.widget.Button");
		for(MobileElement elem:items) {
			if(elem.getAttribute("text").equalsIgnoreCase("Delete")) {
				elem.click();
				break;
			}
		}
	}

	//Code to execute post all the Tests have run
	@AfterTest
	public void TearDownAppiumDriver() {
		driver.quit();
	}
	
	private void NavigateToHomePage() {
		//Navigate back to the home page of the Amazon App
				try {
					MobileElement HomeButton = driver.findElement(MobileBy.id("in.amazon.mShop.android.shopping:id/action_bar_home_logo"));
					HomeButton.click();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					//e2.printStackTrace();
				}
				
				//Sleep to ensure page gets loaded
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
	}
	
	//Utility Method to Scroll the page
	public void Scroll() {
		TouchAction actions = new TouchAction(driver);
		actions.press(PointOption.point(10, driver.manage().window().getSize().height-1)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(10,1)).release().perform();
		actions.press(PointOption.point(10, driver.manage().window().getSize().height-1)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(10,driver.manage().window().getSize().height-50)).release().perform();
		actions.press(PointOption.point(10, driver.manage().window().getSize().height-1)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(10,driver.manage().window().getSize().height-50)).release().perform();
	}
	
}