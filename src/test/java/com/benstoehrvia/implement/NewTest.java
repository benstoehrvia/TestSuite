package com.benstoehrvia.implement;

import org.testng.annotations.Test;

//import com.google.gson.JsonArray;
//import com.google.gson.JsonParser;
//import com.google.gson.stream.JsonReader;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;


import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class NewTest {
	
	private static AppiumDriver driver;
	private static IOSDriver idriver;
	private static AndroidDriver andriver;
	
	private String platform;
	
	  @BeforeSuite
	  public void beforeSuite() {
		  
		  DesiredCapabilities capabilities = new DesiredCapabilities();
	
		  try {
			 System.out.println("AndroidDriver setup: Go");
			 try{
				 andriver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub/"), capabilities);
			 }catch(Exception androidDriver){
				 throw new Exception("AndroidDriverError");
			 }
			 
			 System.out.println("AndroidDriver setup: Set Implicit Wait");
			 andriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

			 System.out.println("\nAttempting to 'getCapability(app).getBrowserName'\n");
			 platform = driver.getCapabilities().getBrowserName().toString();
			 System.out.println("getBrowserName() =="+platform+"\n");
			 
			 System.out.println("\nAttempting to 'getCapability(app)'\n");
			 String app = driver.getCapabilities().getCapability("app").toString();
			 System.out.println("getCapability(app):"+app+"\n");

			 if(platform.equalsIgnoreCase("iOS")){
				 System.out.println("Throwing browserName exception");
				 andriver.quit();
				 Thread.sleep(2000);
				 throw new Exception("browserName");
			 }else if(app.contains(".ipa")){
				 System.out.println("Throwing .ipa exception");
				 andriver.quit();
				 Thread.sleep(2000);
				 throw new Exception("ipa");
			 }
			//Set platform to correct one for the rest of the test
			 platform = "Android";	  
			  
		  } catch (Exception a) {
			  	if(a.getMessage().equals("browserName")){
			  		System.out.println("\n\nAndroid couldn't connect to an Android Appium Server: BrowserNameException\n\n");
			  	}else if(a.getMessage().equals("ipa")){
			  		System.out.println("\n\nAndroid couldn't connect to an Android Appium Server: '.ipa' Exception\n\n");
			  	}else if(a.getMessage().equals("AndroidDriverError")){
			  		System.out.println("\n\nAndroid couldn't connect to an Android Appium Server: AndroidDriverError Exception\n\n");
			  	}else{
			  		System.out.println("\n\nUnknown error occured\n\n");
			  	}
			  	
				try {
					System.out.println("IOSDriver setup: Go");
					idriver = new IOSDriver<IOSElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
					System.out.println("IOSDriver setup: Setting Implicit Wait (20 Seconds)");
					idriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				} catch (Exception i) {
					System.out.println("\n\niOS couldn't connect to an iOS Appium Server\n\n");
					System.out.println("\n\nFailed to connect\n\n");
				}
				//Set platform to correct one for the rest of the test
				platform = "iOS";
		}	
		  
		  if(platform.equalsIgnoreCase("Android")){
			  System.out.println("Successfully set up AndroidDriver");
			  driver = andriver;
		  }else{
			  System.out.println("Successfully set up IOSDriver");
			  driver = idriver;
		  }
	  }

	  @AfterSuite
	  public void afterSuite() {
	  }

	  @BeforeTest
	  public void beforeTest() {
	  }

	  @AfterTest
	  public void afterTest() {
	  }
	  
	  @BeforeMethod
	  public void beforeMethod() {
		  driver.resetApp();
	  }
	
	  @AfterMethod
	  public void afterMethod() {
	  }

	  @Test
	  public void clickSignUpButton(){
		  
		  MobileElement signUpButton;
		  
		  if(platform.equals("iOS")){
			  ((new WebDriverWait(driver,60)).until(ExpectedConditions.elementToBeClickable(By.id("SignUpButton")))).click();
		  }else{
			  ((new WebDriverWait(driver,60)).until(ExpectedConditions.elementToBeClickable(By.id("via.rider:id/btnLogin")))).click();
		  }
		  
	  }

}

//		AndroidDriver<AndroidElement> android;
//		try {
//			  driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723"), androidCapabilities);
//			  System.out.println(driver.getCapabilities().getPlatform());
//		} catch (Exception e) {
//		
//			System.out.println("Android couldn't connect to an Android Appium Server");
//		}
//		
//		DesiredCapabilities iOSCapabilities = new DesiredCapabilities();
//			//iOSCapabilities.setCapability("platformName", "iOS");
//			//iOSCapabilities.setCapability("platformVersion", "9.3");
//			//iOSCapabilities.setCapability("deviceName", "iPhone 6s");
//			//iOSCapabilities.setCapability("app", "/Users/Jammin/Desktop/ViaRider_Dev_vX.Y.Z_bX 2016-07-14 13-43-34/ViaRider_Dev_vX.Y.Z_bX.ipa");
//		
//		IOSDriver<IOSElement> iOS;
//		try {
//			  driver = new IOSDriver<IOSElement>(new URL("http://127.0.0.1:4723/wd/hub"), iOSCapabilities);
//			  System.out.println(driver.getCapabilities().getPlatform());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("iOS couldn't connect to an iOS Appium Server");
//		}