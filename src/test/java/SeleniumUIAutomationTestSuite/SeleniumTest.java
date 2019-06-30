package SeleniumUIAutomationTestSuite;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Utility.Items;
import Utility.Utility;

public class SeleniumTest {
	WebDriver driver;
	String email = "email";
	String pwd = "pwd";
	
	    @BeforeTest
	    public void SetUpSeleniumWebDriver() {
	    	//if you didn't update the Path system variable to add the full directory path to the executable as above mentioned then doing this directly through code
	    	System.setProperty("webdriver.chrome.driver", "C:\\Users\\rahul\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");
	    	driver = new ChromeDriver();
	    	
	    	// TODO Auto-generated method stub
	        System.out.println("Driver is running");
	        System.out.println(driver.toString());
	        System.out.println("Driver is running");
	        driver.manage().window().maximize();
	    }
	    
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

	    
	    
	    @Test
	    public void LoadAmazonWebsiteTestCase() {
	    	driver.get("https://www.amazon.in");
	    	try {
	    	driver.findElement(By.id("twotabsearchtextbox"));
	    	}catch(Exception exp) {
	    		org.testng.Assert.fail("The Amazon Page Could not be loaded");
	    	}
	    }
	    
	    //@Test(dependsOnMethods = "LoadAmazonWebsiteTestCase")
	    public void LoginToAmazonWebsiteTestCase() {
	    	try {
				Thread.sleep(3000);				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	driver.findElement(By.id("nav-link-accountList")).click();
	    	driver.findElement(By.name("email")).sendKeys(email);
	    	driver.findElement(By.id("continue")).click();
	    	driver.findElement(By.name("password")).sendKeys(pwd);
	    	driver.findElement(By.id("signInSubmit")).click();
	    	try {
	    		driver.findElement(By.xpath("//*[@id=\"nav-link-accountList\"]/span[1]"));
	    	}catch(Exception exp) {
	    		org.testng.Assert.fail("Login Failed");
	    	}
	    }
	    
	    //@Test(dependsOnMethods = "LoginToAmazonWebsiteTestCase")
	    public void LogOut()
	    {
	    	try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	driver.findElement(By.id("nav-item-signout")).click();
	    	try {
	    		
	    	}catch(Exception exp) {
	    		org.testng.Assert.fail("Logout Failed");
	    	}
	    }
	    
	    @Test(dataProvider = "ItemsData")// ,dependsOnMethods = "LoginToAmazonWebsiteTestCase")
	    public void SearchItemAndAddToCart(Items param)
	    {
	    	NavigateToHomePage();
	    	try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	try {
	    	driver.findElement(By.id("twotabsearchtextbox")).sendKeys(param.itemName);
	    	driver.findElement(By.xpath("//*[@id=\"nav-search\"]/form/div[2]/div/input")).click();
	    	}catch(Exception exp) {
	    		org.testng.Assert.fail(exp.getMessage());
	    	}
	    	try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	//Open description page of the item where price matches
	    	try {
	    	List<WebElement> list = driver.findElements(By.className("a-price-whole"));	
	    	for(WebElement elem : list) {
	    		if(elem.getAttribute("innerText").equalsIgnoreCase(param.price)) {
	    			elem.click();
	    			break;
	    		}
	    	}
	    	}catch(Exception exp) {
	    		org.testng.Assert.fail(exp.getMessage());
	    	}
	    	try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	//Add items to cart
	    	try {
	    		WebDriverWait wait = new WebDriverWait(driver, 10); 
	    		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.className("a-button-input")));
	    		element.click();
	    	}catch(Exception exp) {
	    		org.testng.Assert.fail(exp.getMessage());
	    	}
	    }
	    
	    @Test(dependsOnMethods = "SearchItemAndAddToCart")
	    public void OpenCart() {
	    	NavigateToHomePage();
	    	try {
	    		Thread.sleep(5000);
	    		//driver.findElement(By.className("nav-a nav-a-2")).click();
	    		driver.navigate().to("https://www.amazon.in/gp/cart/view.html?ref_=nav_cart");
	    	}catch(Exception exp) {
	    	org.testng.Assert.fail(exp.getMessage());
	    }
	  }	
	    
	  @Test(dependsOnMethods = "OpenCart")
	  public void RemoveAnItemFromCart() {
		  try {
			  Thread.sleep(5000);
			  driver.findElement(By.xpath("*[@id='sc-item-Ce52ccab4-c569-40df-bc82-c0822c8c776c']/div[4]/div/div[1]/div/div/div[2]/div/span[1]/span/input")).click();
	  }catch(Exception exp) {
		  org.testng.Assert.fail(exp.getMessage());
	  }
	  }
	  
	    
	    private void NavigateToHomePage() {
	    	try {
	    	driver.navigate().to("https://www.amazon.in/ref=nav_logo");
		}catch(Exception exp) {
			
		}
	   }

		@AfterTest
	    public void TearApartSeleniumWebDriver() {
			driver.close();
	    }
}


