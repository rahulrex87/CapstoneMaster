package WebAPIAutomationTestSuite;

import org.testng.annotations.*;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APITestBase {
  @Test
  public void AmazonHomePageTest() {
	  RestAssured.baseURI = "https://www.amazon.com";
	  //Request Object  
	  RequestSpecification httpRequest = RestAssured.given();
	  
	  //Response Object
	   Response response = httpRequest.request(Method.GET,"");
	   int responseCode = response.statusCode();
	   System.out.println("Response Code :" + responseCode);
	   if(responseCode !=200) {org.testng.Assert.fail();}
  }
  
  @Test
  public void AmazonPrimeTest() {
	  RestAssured.baseURI = "https://www.amazon.in/Redmi-Pro-Black-64GB-Storage/dp/B07DJHXWZZ/ref=as_li_ss_tl?keywords=mi+phones&qid=1561218193&s=gateway&sr=8-1&linkCode=ll1&tag=teststoreid0d-21&linkId=2f8dd081f9ec84b135c4d0ed06e4723a&language=en_IN";
	  //Request Object  
	  RequestSpecification httpRequest = RestAssured.given();
	  
	  //Response Object
	   Response response = httpRequest.request(Method.GET,"type=load&isPrime=false&referrer=https%3A%2F%2Fwww.amazon.com%2F&height=455&width=1436&_=1553533054823");
	   
	  //Print response in console body
	  int responseCode = response.statusCode();
	  System.out.println("Response Code :" + responseCode);
	  if(responseCode !=200) {org.testng.Assert.fail();}
  }
  
  @Test
  public void AmazonOpenCartTest() {
	  RestAssured.baseURI = "https://www.amazon.in/gp/cart/view.html?ref_=nav_cart";
	  //Request Object  
	  RequestSpecification httpRequest = RestAssured.given();
	  
	  //Response Object
	   Response response = httpRequest.request(Method.GET,"");
	   
	  //Print response in console body
	  int responseCode = response.statusCode();
	  System.out.println("Response Code :" + responseCode);
	  if(responseCode !=200) {org.testng.Assert.fail();}
  }
  
  @Test
  public void OpenProductDescription()
  {
	  RestAssured.baseURI = "https://www.amazon.in/Redmi-Pro-Black-64GB-Storage/dp/B07DJHXWZZ/ref=sr_1_1?crid=1QQIHLC37E2ZZ&keywords=note+6+pro&qid=1561903128&s=gateway&sprefix=note%2Caps%2C328&sr=8-1";
	  //Request Object  
	  RequestSpecification httpRequest = RestAssured.given();
	  
	  //Response Object
	   Response response = httpRequest.request(Method.GET,"");
	   
	  //Print response in console body
	  int responseCode = response.statusCode();
	  System.out.println("Response Code :" + responseCode);
	  if(responseCode !=200) {org.testng.Assert.fail();}	  
  }
}
