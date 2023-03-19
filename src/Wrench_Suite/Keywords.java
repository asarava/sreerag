/*
 * Created By : Krishnadas Narayanapillai
 * Created On : 05/07/2014
 * Version    : 1.0
 * Tools Used : Selenium WebDriver, TestNG, JDK 6.0, Eclipse
 */
package Wrench_Suite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
//import java.util.Set;
import java.util.StringTokenizer;
//import org.sikuli.natives.Vision;
//import org.sikuli.script.App;
//import org.sikuli.script.Screen;
import org.apache.commons.io.FileUtils;
//import org.apache.commons.logging.Log;
//import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.sikuli.script.FindFailed;
//import org.sikuli.script.Pattern;
//import org.openqa.selenium.chrome.ChromeDriver;





//import org.sikuli.script.FindFailed;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Keywords {
	
	private WebDriver driver = null;
	private WebElement strelement = null;
	private ArrayList<WebElement> strelementList = null;
	private String strtext,strelmntxpath;
	private String[] strarr1;
	private int strwaitsec;
	public Date D1,D2;
	int e;
	//Logger Log = Logger.getLogger(Log.class.getName());
	
	//open_browser() - Keyword method used to open a browser session with the url provided by the user.
	public int open_browser(String url,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx,String strbrowser) throws IOException {
		if(strbrowser.equalsIgnoreCase("FireFox")){ // Creating the FireFox Object			
			
			driver = new FirefoxDriver();
			//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		}
		
		else if(strbrowser.equalsIgnoreCase("Safari")){ // Creating the FireFox Object			
			
			driver = new SafariDriver();
			//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	else if(strbrowser.equalsIgnoreCase("Chrome")) {// Creating the Chrome Object		
			System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Driver\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		else if(strbrowser.equalsIgnoreCase("IE")) {// Creating the IE Object	
			System.setProperty("webdriver.ie.driver", "C:\\Selenium\\Driver\\IEDriverServer.exe");
			 driver = new InternetExplorerDriver();
			 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		//driver.manage().window().maximize(); //For maximizing browser window
		driver.get(url);// Opening the page		
		sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Session opened successfully");
		fos = new FileOutputStream(path);
		workbook.write(fos);		
		fos.close();
		return 1;
	}
	
	//close_browser() - Keyword method used to close the browser session opened for current execution.
	public int close_browser(int counter,String path,FileOutputStream fos,XSSFWorkbook flow_workbook,XSSFSheet flow_sheet,int extncmtsindx_flow,int extnstatsindx) throws IOException {		
		// Closing the session created for execution
		if(driver == null) {
			flow_sheet.getRow(counter).getCell(extncmtsindx_flow).setCellValue("No opened sessions found");
			fos = new FileOutputStream(path);
			flow_workbook.write(fos);		
			fos.close();
			return 2;
		}
		else {
			driver.quit();
			flow_sheet.getRow(counter).getCell(extncmtsindx_flow).setCellValue("Session closed successfully");
			flow_sheet.getRow(counter).getCell(extnstatsindx).setCellValue("Pass");
			fos = new FileOutputStream(path);
			flow_workbook.write(fos);		
			fos.close();
			return 1;
		}
	}	
	
	//find_element() is a framework method. Please don't modify the same.
	public WebElement find_element(String strelementidvalue) {//return the WebElement object which identifies the element in page
		strarr1 = strelementidvalue.split("=");
		if(strarr1.length == 2) {
			strtext = strarr1[0];
			strelmntxpath = strarr1[1];
			if(strtext.equals("xpath")) {
				try {
					strelement= new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath(strelmntxpath)));
					
				}
				catch (TimeoutException toe) {
					strelement = null;
				}
			}
			
			else if(strtext.equals("id")) {
				try {
					strelement= new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.id(strelmntxpath)));
				}
				catch (TimeoutException toe) {
					strelement = null;
				}
			}
			else if(strtext.equals("name")) {
				try {
					strelement= new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.name(strelmntxpath)));
				}
				catch (TimeoutException toe) {
					strelement = null;
				}
			}
			else if(strtext.equals("class")) {
				try {
					strelement= new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.className(strelmntxpath)));
				}
				catch (TimeoutException toe) {
					strelement = null;
				}
			}
			else if(strtext.equals("linktext")) {
				try {
					strelement= new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.linkText(strelmntxpath)));
				}
				catch (TimeoutException toe) {
					strelement = null;
				}
			}
		}
		else
			strelement = null;
		return strelement;
	}
	
	//find_multipleElement() is a framework method for identifying multiple Web Elements. Please don't modify same.
	public ArrayList<WebElement> find_multipleElement(String strelementidvalue) throws IOException, InterruptedException{
		strelementList = new ArrayList<WebElement>();
		strarr1 = strelementidvalue.split("=");
  		if(strarr1.length == 2) {
  			strtext = strarr1[0];
  			strelmntxpath = strarr1[1];
  			if(strtext.equals("xpath")) {
  				try{ 
  					StringTokenizer st = new StringTokenizer(strelmntxpath,",");
  					while(st.hasMoreTokens()){
  						//strelement1 = st.nextToken();
  						strelementList.add(driver.findElement(By.xpath(st.nextToken())));
  					}
  				} catch(NoSuchElementException e) {
  					strelementList = null;
  				}
			}
  			else
  				strelementList = null;
			}
  		
  		return strelementList;
	}
	

	//wait_function() - Keyword function. Please don't modify the same.
	public int wait_function(int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx,String strwaittime) throws IOException, InterruptedException {
		if(strwaittime.isEmpty())
		{
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Wait time is missing");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
			return 2;
		}
		else{
		strwaitsec = Integer.parseInt(strwaittime);
		strwaitsec *= 1000;
		Thread.sleep(strwaitsec);
		strwaitsec /= 1000;
		sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Script successfully wait for "+strwaittime+" second");
		fos = new FileOutputStream(path);
		workbook.write(fos);		
		fos.close();
		return 1;
		}
	}
	
	
	//enter_text() - Keyword function used to enter text into textbox provided by the user.
		public int enter_text(String strelementid,String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			strelement = find_element(strelementid);
			if(strelement!= null){
			strelement.sendKeys(strdatavalue);
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Entered the text "+strdatavalue+" successfully");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
			return 1;
		   }
		    else{
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element not visible");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				// Now you can do whatever you need to do with it, for example copy somewhere
				FileUtils.copyFile(scrFile, new File("C:\\Selenium\\Screenshots\\Errors\\Error"+e+".jpg"));
				e++;
				return 2;
		    }
		}
		public int autoit_upload(String strelementid,String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			strelement = find_element(strelementid);
			if(strelement!= null){
			strelement.click();
			Runtime.getRuntime().exec(strdatavalue);
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Entered the text "+strdatavalue+" successfully");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
			return 1;
		   }
		    else{
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element not visible");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				// Now you can do whatever you need to do with it, for example copy somewhere
				FileUtils.copyFile(scrFile, new File("C:\\Selenium\\Screenshots\\Errors\\Error"+e+".jpg"));
				e++;
				return 2;
		    }
		}
		public int enter_login(String strelementid,String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			strelement = find_element(strelementid);
			if(strelement!= null){
			strelement.clear();
		   }
			else{
			
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element not visible");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 2;	
		    }
			try {
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		   }
			if(strelement!= null){
			strelement.sendKeys(strdatavalue);
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Entered the text "+strdatavalue+" successfully");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
			return 1;
		   }
			else{
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element not visible");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 2;
		    }
		}
		public int click_button(String strelementid,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			strelement = find_element(strelementid);
			if(strelement!= null){
				//String strbutton=strelement.getText(); //why this statement added here ?
				WebDriverWait wait = new WebDriverWait(driver,30);
				wait.until(ExpectedConditions.visibilityOf(strelement));
				wait.until(ExpectedConditions.elementToBeClickable(strelement));
				
				try {
					strelement.click();
				} catch (Exception e) {
					strelement.click();
					System.out.println("Error on click is "+e.getMessage());
				}
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Clicked successfully");
				//sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Clicked on "+strbutton+" successfully");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 1;
		    }
			else{
					sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element is not visible");
					fos = new FileOutputStream(path);
					workbook.write(fos);		
					fos.close();
					File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					// Now you can do whatever you need to do with it, for example copy somewhere
					FileUtils.copyFile(scrFile, new File("C:\\Selenium\\Screenshots\\Errors\\Error"+e+".jpg"));
					e++;
					return 2 ;
				}
			
		}

	/*	public int click_image(String strdatavalue, int counter,String path, FileOutputStream fos,XSSFWorkbook workbook, XSSFSheet sheet,int extncmtsindx){
			
			//String FilePath="C:\\Selenium\\Sikuli Images\\checkout.png";
			String FilePath = strdatavalue;
			
			try{
				//Starting Screen Instance of Sikuli
				Screen s = new Screen();
				s.find(FilePath).click(); 
				
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Clicked successfully.");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 1;
			}
			catch (Exception e){
				System.out.println(e);
				return 2;
			}
		}

		
		public int click_png(String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx,int extnstatsindx) throws IOException, FindFailed {
			
            if(strdatavalue!=null)  { 
            	Screen s = new Screen();
            	Pattern pattern = new Pattern(strdatavalue);
				s.click(pattern);
                	sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Clicked the item successfully");
        			fos = new FileOutputStream(path);
        			workbook.write(fos);		
        			fos.close();
        			return 1;
        	}
                else{
                sheet.getRow(counter).getCell(extncmtsindx).setCellValue("The text "+strdatavalue+"  could not be found");
    			fos = new FileOutputStream(path);
    			workbook.write(fos);		
    			fos.close();
    			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				// Now you can do whatever you need to do with it, for example copy somewhere
				FileUtils.copyFile(scrFile, new File("C:\\Selenium\\Screenshots\\Errors\\Error"+e+".jpg"));
				e++;
                return 2;
                }
            }
            */
		public int verify_value(String strelementid,String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx,int extnstatsindx) throws IOException {
			strelement = find_element(strelementid);
            if(strelement!=null)  { 
            	//String s=strelement.getAttribute("Value");
			if(strdatavalue.equals(strelement.getText())){
                	sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Verified the text "+strdatavalue+" successfully");
        			fos = new FileOutputStream(path);
        			workbook.write(fos);		
        			fos.close();
        			return 1;
        	}
                else{
                sheet.getRow(counter).getCell(extncmtsindx).setCellValue("The text "+strdatavalue+"  could not be found");
    			fos = new FileOutputStream(path);
    			workbook.write(fos);		
    			fos.close();
    			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				// Now you can do whatever you need to do with it, for example copy somewhere
				FileUtils.copyFile(scrFile, new File("C:\\Selenium\\Screenshots\\Errors\\Error"+e+".jpg"));
				e++;
                return 2;
                }
            }
            else{
                sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element is not visible");
                sheet.getRow(counter).getCell(extnstatsindx).setCellValue("Fail");
    			fos = new FileOutputStream(path);
    			workbook.write(fos);		
    			fos.close();
    			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				// Now you can do whatever you need to do with it, for example copy somewhere
				FileUtils.copyFile(scrFile, new File("C:\\Selenium\\Screenshots\\Errors\\Error"+e+".jpg"));
				e++;
                return 2;
            }
            	
		}
		public int verify_hiddenValue(String strelementid,String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx,int extnstatsindx) throws IOException {
			strelement = find_element(strelementid);
            if(strelement!=null)  { 
            //	String s=strelement.getAttribute("title");
			if(strdatavalue.equals(strelement.getAttribute("title"))){
                	sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Verified the text "+strdatavalue+" successfully");
        			fos = new FileOutputStream(path);
        			workbook.write(fos);		
        			fos.close();
        			return 1;
        	}
                else{
                sheet.getRow(counter).getCell(extncmtsindx).setCellValue("The text "+strdatavalue+"  could not be found");
    			fos = new FileOutputStream(path);
    			workbook.write(fos);		
    			fos.close();
                return 2;
                }
            }
            else{
                sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element is not visible");
                sheet.getRow(counter).getCell(extnstatsindx).setCellValue("Fail");
    			fos = new FileOutputStream(path);
    			workbook.write(fos);		
    			fos.close();
                return 2;
            }
            	
		}
		
		public int verify_attribute(String strelementid,String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx,int extnstatsindx) throws IOException {
			strelement = find_element(strelementid);
            if(strelement!=null)  { 
            	String s=strelement.getAttribute("Value");
			if(strdatavalue.equals(s)){
                	sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Verified the text "+strdatavalue+" successfully");
        			fos = new FileOutputStream(path);
        			workbook.write(fos);		
        			fos.close();
        			return 1;
        	}
                else{
                sheet.getRow(counter).getCell(extncmtsindx).setCellValue("The text "+strdatavalue+"  could not be found");
    			fos = new FileOutputStream(path);
    			workbook.write(fos);		
    			fos.close();
    			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				// Now you can do whatever you need to do with it, for example copy somewhere
				FileUtils.copyFile(scrFile, new File("C:\\Selenium\\Screenshots\\Errors\\Error"+e+".jpg"));
				e++;
                return 2;
                }
            }
            else{
                sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element is not visible");
                sheet.getRow(counter).getCell(extnstatsindx).setCellValue("Fail");
    			fos = new FileOutputStream(path);
    			workbook.write(fos);		
    			fos.close();
    			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				// Now you can do whatever you need to do with it, for example copy somewhere
				FileUtils.copyFile(scrFile, new File("C:\\Selenium\\Screenshots\\Errors\\Error"+e+".jpg"));
				e++;
                return 2;
            }
            	
		}
		
		public int fetch_value(String strelementid,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx,int extnstatsindx) throws IOException {
			strelement = find_element(strelementid);
            if(strelement!=null)  {  
            	String strdatavalue=strelement.getAttribute("class");
                	sheet.getRow(counter).getCell(extncmtsindx).setCellValue(strdatavalue);
        			fos = new FileOutputStream(path);
        			workbook.write(fos);		
        			fos.close();
        			return 1;
            }
            else{
                sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element is not visible");
                sheet.getRow(counter).getCell(extnstatsindx).setCellValue("Fail");
    			fos = new FileOutputStream(path);
    			workbook.write(fos);		
    			fos.close();
                return 2;
            }
            	
		}
		
		
 
		public int verify_absence(String strelementid,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx,int extnstatsindx) throws IOException {
			strelement = find_element(strelementid);
			String s= strelement.getAttribute("title");
            if(s==null){  
            	
			 sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Specified location is blank");
        	fos = new FileOutputStream(path);
        	workbook.write(fos);		
        	fos.close();
        	return 1;
        	}
             else{
                sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element is not blank");
                sheet.getRow(counter).getCell(extnstatsindx).setCellValue("Fail");
    			fos = new FileOutputStream(path);
    			workbook.write(fos);		
    			fos.close();
                return 2;
             }
            }
		
		public int verify_presence(String strelementid,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx,int extnstatsindx) throws IOException {
			strelement = find_element(strelementid);
           // if(strelement!=null){
            String s= strelement.getAttribute("text");
            if(s!=" "){
            sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Specified element "+s+" present");
			 sheet.getRow(counter).getCell(extnstatsindx).setCellValue("Pass");
        	fos = new FileOutputStream(path);
        	workbook.write(fos);		
        	fos.close();
        	return 1;
        	
        	}
         
        	//return 2;
        	//}
            else{
                sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element is not present");
                sheet.getRow(counter).getCell(extnstatsindx).setCellValue("Fail");
    			fos = new FileOutputStream(path);
    			workbook.write(fos);		
    			fos.close();
                return 2;
             }
            }
		            	
		public int tabkeypress(String strelementid,String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			strelement = find_element(strelementid);
			if(strelement!=null){
			strelement.sendKeys(Keys.TAB);                   
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Pressed TAB key successfully");
        			fos = new FileOutputStream(path);
        			workbook.write(fos);		
        			fos.close();
        			return 1;
			}
			else{
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element not present");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 2;	
			}
			
		}
		
		public int enter_key(int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			if(strelement!=null){
			strelement.sendKeys(Keys.RETURN);                   
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Pressed TAB key successfully");
        	fos = new FileOutputStream(path);
        	workbook.write(fos);		
        	fos.close();
        	return 1;   
			}
			 else{
	                sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element is not visible");
	                fos = new FileOutputStream(path);
	    			workbook.write(fos);		
	    			fos.close();
	    			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					// Now you can do whatever you need to do with it, for example copy somewhere
					FileUtils.copyFile(scrFile, new File("C:\\Selenium\\Screenshots\\Errors\\Error"+e+".jpg"));
					e++;
	                return 2;
	            }
			
		}
		
		
		public int down_key(String strelementid,String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException, InterruptedException {
			strelement.sendKeys(Keys.DOWN); 
			Thread.sleep(2000);
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Pressed down key successfully");
        	fos = new FileOutputStream(path);
        	workbook.write(fos);		
        	fos.close();
        	return 1;            
			
		}
		public int alert_accept(int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Accepted the alert pop up");
        	fos = new FileOutputStream(path);
        	workbook.write(fos);		
        	fos.close();
        	return 1;            
			
		}
		public int alert_verifytext(String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
	      try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			Alert alert = driver.switchTo().alert();
			String alerttext=alert.getText();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				if(strdatavalue.equals(alerttext)){
                	sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Verified the alert successfully");
        			fos = new FileOutputStream(path);
        			workbook.write(fos);		
        			fos.close();
        			return 1;
        	}
				else{
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("The text could not be found");
    			fos = new FileOutputStream(path);
    			workbook.write(fos);		
    			fos.close();
                return 2;
                }
			}
		public int alert_reject(int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			Alert alert = driver.switchTo().alert();
				alert.dismiss();
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Rejected the alert successfully");
	        			fos = new FileOutputStream(path);
	        			workbook.write(fos);		
	        			fos.close();
	        			return 1;            
				
			}
		
		public int backkeypress(String strelementid,String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			strelement = find_element(strelementid);
			if(strelement!=null){
			strelement.sendKeys(Keys.BACK_SPACE);                        
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Pressed Backspace key successfully");
        			fos = new FileOutputStream(path);
        			workbook.write(fos);		
        			fos.close();
        			return 1; 
			}
			else{
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element is not visible");
    			fos = new FileOutputStream(path);
    			workbook.write(fos);		
    			fos.close();
    			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				// Now you can do whatever you need to do with it, for example copy somewhere
				FileUtils.copyFile(scrFile, new File("C:\\Selenium\\Screenshots\\Errors\\Error"+e+".jpg"));
				e++;
    			return 2; 
			}
			
		}
		public int forwardkeypress(String strelementid,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			strelement = find_element(strelementid);
			String selectAll = Keys.chord(Keys.ALT, Keys.ENTER);
			if(strelement!=null){
			strelement.sendKeys(selectAll);
			     sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Navigated to forward successfully");
        			fos = new FileOutputStream(path);
        			workbook.write(fos);		
        			fos.close();
        			return 1;}
			else{
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element is not visible");
    			fos = new FileOutputStream(path);
    			workbook.write(fos);		
    			fos.close();
    			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				// Now you can do whatever you need to do with it, for example copy somewhere
				FileUtils.copyFile(scrFile, new File("C:\\Selenium\\Screenshots\\Errors\\Error"+e+".jpg"));
				e++;
    			return 2;
			}
			
		}
		public int clear_text(String strelementid,String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			strelement = find_element(strelementid);
			if(strelement!=null){
			strelement.clear();
			     sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Cleared the text content from the textbox");
        			fos = new FileOutputStream(path);
        			workbook.write(fos);		
        			fos.close();
        			return 1;
			}
			else{
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element is not visible");
    			fos = new FileOutputStream(path);
    			workbook.write(fos);		
    			fos.close();
    			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				// Now you can do whatever you need to do with it, for example copy somewhere
				FileUtils.copyFile(scrFile, new File("C:\\Selenium\\Screenshots\\Errors\\Error"+e+".jpg"));
				e++;
    			return 2;
			}
			
		}
		
		public int new_windowswch(int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			String parentHandle;
			parentHandle = driver.getWindowHandle(); // get the current window handle
						for (String winHandle : driver.getWindowHandles()){
				if(!winHandle.equals(parentHandle)){
			    driver.switchTo().window(winHandle);
			    // switch focus of WebDriver to the next found window handle (that's your newly opened window)
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				}
			}					
		    sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Navigated to forward successfully");
    		fos = new FileOutputStream(path);
    		workbook.write(fos);		
    		fos.close();
    		return 1;            
		
	}
		public int switch_newWindow(int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException{
			String parentHandle = driver.getWindowHandle(); //to get handle of current window
			for (String winHandle : driver.getWindowHandles()){
				if(!winHandle.equals(parentHandle)){
					driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
				}
			}
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Driver control changed to new window successfully");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
			return 1;
		}
		
		/*public int switch_newWindow(int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException{
			String parentHandle = driver.getWindowHandle(); //to get handle of current window
			for (String winHandle : driver.getWindowHandles()){
				if(!winHandle.equals(parentHandle)){
					driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
				}
			}
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Driver control changed to new window successfully");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
			return 1;
		}*/
		

	
		public int switch_preWindow(int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			String currentHandle = driver.getWindowHandle(); //to get handle of current window
			for (String winHandle : driver.getWindowHandles()){
				if(!winHandle.equals(currentHandle)){
					driver.close();
					driver.switchTo().window(winHandle);
					break;	
				}
			}
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Driver controls switched back to previous window successfully");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
			return 1;
		}
		
		public int mouse_hover(String strelementid, int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			strelement = find_element(strelementid);
			if(strelement!=null){
			Actions actions = new Actions(driver);
			WebElement menuHoverLink = strelement;
			actions.moveToElement(menuHoverLink);
			//actions.click();
			actions.perform();
		    sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Mouser hovered over the menu");
    		fos = new FileOutputStream(path);
    		workbook.write(fos);		
    		fos.close();
    		return 1;
    		}
			else{
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element is not visible");
	    		fos = new FileOutputStream(path);
	    		workbook.write(fos);		
	    		fos.close();
	    		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				// Now you can do whatever you need to do with it, for example copy somewhere
				FileUtils.copyFile(scrFile, new File("C:\\Selenium\\Screenshots\\Errors\\Error"+e+".jpg"));
				e++;
	    		return 2;	
			}
		
	}
				
		public int select_dropdown(String strelementid, String strdatavalue, int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			strelement = find_element(strelementid);
			if(strelement!=null){
			Select dropdown = new Select(strelement);
			dropdown.selectByVisibleText(strdatavalue);
		    sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Selected the value "+strdatavalue+" from the dropdown");
    		fos = new FileOutputStream(path);
    		workbook.write(fos);		
    		fos.close();
    		return 1;}
			else{
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element is not visible");
	    		fos = new FileOutputStream(path);
	    		workbook.write(fos);		
	    		fos.close();
	    		return 2;	
			}
		
	}
		
		public int rowselection(int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException{
			Actions builder = new Actions(driver);
			builder.keyDown(Keys.CONTROL)
			 .click(driver.findElement(By.xpath("/html/body/div[1]/div[5]/div[1]/form/div/div[3]/div/div[2]/div/div[3]/div[2]/div/div[14]/div[1]/div")))
			 .keyUp(Keys.CONTROL);
			Action selectMultiple = builder.build();
			selectMultiple.perform();
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Selected the rows successfully");
    		fos = new FileOutputStream(path);
    		workbook.write(fos);		
    		fos.close();
			return 1;	
			
			}	
		
		 /*  public int scroll_up(String strelementid, String strdatavalue, int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException, InterruptedException {
			   strelement = find_element(strelementid);
			   strarr1 = strdatavalue.split(",");
		  		strfirstdata= strarr1[0];
		  		strrange = strarr1[1];
		  		//System.out.println("First data value is" +strfirstdata);
		  		//System.out.println("First data value is" +strrange);
		  			
		  	 Actions dragger = new Actions(driver);
		  		WebElement draggablePartOfScrollbar = find_element(strelementid);
		  		if(draggablePartOfScrollbar!=null){
		  		int PixelsToDrag = Integer.parseInt(strfirstdata);
		  		int range = Integer.parseInt(strrange);
		  		for (int i=PixelsToDrag;i<range;i=i+ PixelsToDrag){
		  		// this causes a gradual drag of the scroll bar, 10 units at a time
		  		dragger.moveToElement(draggablePartOfScrollbar).clickAndHold().moveByOffset(0, PixelsToDrag).release().perform();
		  		Thread.sleep(1000L);
		  		}
		  		}
		  		else{
		  			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element is not visible");
			  		fos = new FileOutputStream(path);
			  		workbook.write(fos);		
			  		fos.close();
			  		return 2;
		  		}
		  		sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Navigated to forward successfully");
		  		fos = new FileOutputStream(path);
		  		workbook.write(fos);		
		  		fos.close();
		  		return 1;
		  				
		  	 	}*/
		   
		 /*  public int scroll_down(String strelementid, String strdatavalue, int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException, InterruptedException {
			      //System.out.println("data value is" +strelement);
		  		  strfirstdata= strdatavalue;
		  			Actions dragger = new Actions(driver);
		  			WebElement scrollbar = find_element(strelementid);
		  					// drag downwards
		  					int PixelsToDrag = Integer.parseInt(strfirstdata);
		  					for (int i=PixelsToDrag;i<200;i=i+ PixelsToDrag){
		  						// this causes a gradual drag of the scroll bar, Pixels to Drag units at a time
		  						dragger.moveToElement(scrollbar).clickAndHold().moveByOffset(0,PixelsToDrag).release().perform();
		  						Thread.sleep(1000L);
		  					}

		  					sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Navigated to forward successfully");
		  		    		fos = new FileOutputStream(path);
		  		    		workbook.write(fos);		
		  		    		fos.close();
		  		    	    return 1;	
		  			}
		   */
		   /*public int scroll_horizontal(String strelementid, String strdatavalue, int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException, InterruptedException {
			    //System.out.println("data value is" +strelement);
	  		    strfirstdata= strdatavalue;
	  			System.out.println("First data value is" +strfirstdata);
	  			System.out.println("second data value is" +strrange);
	  			Actions dragger = new Actions(driver);
	  			WebElement scrollbar = find_element(strelementid);
	  					// drag downwards
	  					int PixelsToDrag = Integer.parseInt(strfirstdata);
	  					for (int i=PixelsToDrag;i<200;i=i+ PixelsToDrag){
	  						// this causes a gradual drag of the scroll bar, Pixels to Drag units at a time
	  						dragger.moveToElement(scrollbar).clickAndHold().moveByOffset(PixelsToDrag,0).release().perform();
	  						Thread.sleep(1000L);
	  					}

	  					sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Navigated to forward successfully");
	  		    		fos = new FileOutputStream(path);
	  		    		workbook.write(fos);		
	  		    		fos.close();
	  		    	    return 1;	
	  			}*/
		   
		   public int click_scroll(String strelementid, String strdatavalue, int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException, InterruptedException {
			                strelement = find_element(strelementid);
			   			    int times = Integer.parseInt(strdatavalue);
		  					for (int i=0;i<times;i=i+ 1){
		  					if(strelement!=null){
		  					strelement.click();
		  					}
		  					else{
		  						sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element is not visible");
			  		    		fos = new FileOutputStream(path);
			  		    		workbook.write(fos);		
			  		    		fos.close();
			  		    	    return 2;
		  					}
		  					}
		  					sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Navigated to forward successfully");
		  		    		fos = new FileOutputStream(path);
		  		    		workbook.write(fos);		
		  		    		fos.close();
		  		    	    return 1;
		  					}
		   public int scroll_right(String strelementid, String strdatavalue, int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException, InterruptedException {
			   strelement = find_element(strelementid);
			   			    Actions dragger = new Actions(driver);
		  					WebElement draggablePartOfScrollbar = strelement;
		  					// drag downwards
		  					int PixelsToDrag = 1;
		  					int r1;
		  					int range = Integer.parseInt(strdatavalue);
		  				r1=range*PixelsToDrag;
		  					for (int i=0;i<r1;i=i+ PixelsToDrag){
		  						// this causes a gradual drag of the scroll bar, 10 units at a time
		  						dragger.moveToElement(draggablePartOfScrollbar).clickAndHold().moveByOffset(PixelsToDrag, 0).release().perform();
		  						//dragger.moveToElement(draggablePartOfScrollbar).clickAndHold().moveByOffset(0, PixelsToDrag).release().perform();
		  						Thread.sleep(1000L);
		  					}

		  					sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Navigated to forward successfully");
		  		    		fos = new FileOutputStream(path);
		  		    		workbook.write(fos);		
		  		    		fos.close();
		  		    			
		  				
		  		return 1;	
		  			}
		      
		   
		   
		   public int wait_implicit(String strelementid, int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException, InterruptedException {
			 try{
				   WebDriverWait wait = new WebDriverWait(driver, 10);
				   wait.until(ExpectedConditions.visibilityOf(find_element(strelementid)));
				 //  WebElement myDynamicElement = (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(find_element(strelementid)));
			   }  catch(NoSuchElementException e){}
			   
			   sheet.getRow(counter).getCell(extncmtsindx).setCellValue("waited implicitly");
		  		    fos = new FileOutputStream(path);
		  		    workbook.write(fos);		
		  		    fos.close();
		  		  return 1;	
		  			} 
		   
		   public int hidden_upload(String strelementid, String strdatavalue, int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException, InterruptedException {
			   strelement = find_element(strelementid);
			   if(strelement!=null){
			   JavascriptExecutor executor= (JavascriptExecutor)driver; // Creating instance of Java script executor
			 executor.executeScript("document.getElementById('file').style.display='block'; document.getElementById('file').style.width='auto';document.getElementById('file').style.height='25px';document.getElementById('file').style.opacity='1';"); //Java script executing for showing hidden element
			  Thread.sleep(5000);
			  //strelement.sendKeys("D:\\All_files\\PLC FAT PROCEDURE REV-1.pdf");
			  strelement = find_element(strelementid);
			  strelement.sendKeys(strdatavalue);
			  Thread.sleep(1000);
			  executor.executeScript("document.getElementById('file').style.display='none';");
			  // JavascriptExecutor jse = (JavascriptExecutor)driver;
			    sheet.getRow(counter).getCell(extncmtsindx).setCellValue("clicked hidden element");
		  		fos = new FileOutputStream(path);
		  		    workbook.write(fos);		
		  		    fos.close();
		  		  return 1;	
			 }
			 else{
					sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element is not visible");
		    		fos = new FileOutputStream(path);
		    		workbook.write(fos);		
		    		fos.close();
		    		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					// Now you can do whatever you need to do with it, for example copy somewhere
					FileUtils.copyFile(scrFile, new File("C:\\Selenium\\Screenshots\\Errors\\Error"+e+".jpg"));
					e++;
		    		return 2;	
				}
			}
				
		   public int hidden_link(String strelementid, String strdatavalue, int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException, InterruptedException {
				 JavascriptExecutor executor= (JavascriptExecutor)driver; // Creating instance of Java script executor
				 executor.executeScript("document.getElementById('Leaf_menu').style.display='block';"); //Java script executing for showing hidden element
				  Thread.sleep(5000);
				  //strelement.sendKeys("D:\\All_files\\PLC FAT PROCEDURE REV-1.pdf");
				  strelement = find_element(strelementid);
				  strelement.click();
				 Thread.sleep(3000);
				  executor.executeScript("document.getElementByclass('Leaf_menu').style.display='none';");
				  // JavascriptExecutor jse = (JavascriptExecutor)driver;
				    sheet.getRow(counter).getCell(extncmtsindx).setCellValue("clicked hidden element");
			  		fos = new FileOutputStream(path);
			  		    workbook.write(fos);		
			  		    fos.close();
			  		  return 1;	
			}
		   
		   public int downarrow_scroll(String strelementid,String strdatavalue, int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException{
			   strelement = find_element(strelementid);
			   int range = Integer.parseInt(strdatavalue); 
			   for(int i=0;i<range;i++) {
			   strelement.sendKeys(Keys.ARROW_DOWN);
			   }
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Selected the rows successfully");
	    		fos = new FileOutputStream(path);
	    		workbook.write(fos);		
	    		fos.close();
				return 1;	
				}	
		   
		   public int multiple_selection(String strelementid,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException, InterruptedException {
				strelementList = find_multipleElement(strelementid);
				Iterator<WebElement> itr = strelementList.iterator();	
				Actions builder = new Actions(driver);	
				while(itr.hasNext()){
					strelement = (WebElement) itr.next();	
					builder.keyDown(Keys.CONTROL).click(strelement).keyUp(Keys.CONTROL);
				}
				builder.build().perform();
				//Action selectMultiple = builder.build();
				//selectMultiple.perform();	
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Multiple elements have been selected successfully");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 1;
			}
			
		
		 
		public int date_picker(String strelementid,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException{
		 	WebElement dateWidget = find_element(strelementid);
		// List<WebElement> rows=dateWidget.findElements(By.tagName("tr"));
		  List<WebElement> columns=dateWidget.findElements(By.tagName("td"));
		  for (WebElement cell: columns){
		    //Select 13th Date
		  if (cell.getText().equals("13")){
		   cell.findElement(By.linkText("13")).click();
		    break;
		    }
		  sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Selected the rows successfully");
  		fos = new FileOutputStream(path);
  		workbook.write(fos);		
  		fos.close();
			
		    }
		   return 1;
		   
		    }
		public int capture_screenshot(String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException{   
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy somewhere
		FileUtils.copyFile(scrFile, new File(strdatavalue));
		sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Captured and saved the screenshot to the provided path");
  		fos = new FileOutputStream(path);
  		workbook.write(fos);		
  		fos.close();
		return 1;
		}
		public int get_errormessage(String strelementid,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException{   
		strelement=find_element(strelementid);
		if(strelement!=null){
		String strmessage=strelement.getText();
	    sheet.getRow(counter).getCell(extncmtsindx).setCellValue("The message obtained is:" +strmessage);
		fos = new FileOutputStream(path);
		workbook.write(fos);		
		fos.close();
	    return 2;
		}
	    else{
	    	sheet.getRow(counter).getCell(extncmtsindx).setCellValue("There is no comments after operation");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
		    return 1;	
	    	
	    }
	    
	}
		public int navigate_link(String strelementid,String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException{   
			strelement=find_element(strelementid);
			if(strelement!=null){
		    driver.navigate().to(strdatavalue);
		    sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Navigating to link"+strdatavalue);
		    fos = new FileOutputStream(path);
		    workbook.write(fos);		
		    fos.close();
	        return 1;
		}
	    else{
	    	driver.navigate().back();
	    	sheet.getRow(counter).getCell(extncmtsindx).setCellValue("The step is bypassed since there is no failure");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
		    return 1;
	    	
	    }
	    
	}
		public int date_entry(String strelementid,String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException{
			strelement=find_element(strelementid);
			strarr1 = strdatavalue.split(",");
			if(strarr1.length == 3) {
				//String strday = strarr1[0];
				//String strmonth = strarr1[1];
				//String stryear = strarr1[2];
				//strelement.sendKeys(strday);
		sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Selected the rows successfully");
  		fos = new FileOutputStream(path);
  		workbook.write(fos);		
  		fos.close();
			 }	
			return 1;
		}
		public int selDropDown(String strelementid,String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException,InterruptedException  {
			strelement = find_element(strelementid);
			Select dropdown = new Select(strelement);
			List<WebElement> oSize = dropdown.getOptions();
			int iListSize = oSize.size();
			for(int k=0;k<iListSize;k++)
			{
				String sValue =dropdown.getOptions().get(k).getText();
				if(sValue.equals(strdatavalue))
				
				{				
					if(strelement.getText()==null){
						dropdown.selectByVisibleText(strdatavalue);
					    sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Value Selected successfully");
						fos = new FileOutputStream(path);
						workbook.write(fos);		
						fos.close();
						return 1;						
					}
					else if(strdatavalue.equals(strelement.getText())){						
						sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Value already Selected in the dropdown");
						fos = new FileOutputStream(path);
						workbook.write(fos);		
						fos.close();
						return 1;
					}
					
				}
			}
			
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Value not in the list");
			return 2;
			
		}
		public int file_upload(int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Runtime.getRuntime().exec("D:\\Workspace_new\\Wrench_Framework\\file_upload.exe");
			try {
				Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("File uploaded successfully");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 1;
		}
		public int tick_checkbox(String strelementid,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException,InterruptedException  {
			strelement = find_element(strelementid);
			if(!strelement.isSelected()){
			//System.out.println("Result is"+strelement.isSelected());
				strelement.click();
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Value Selected successfully");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 1;
			}
			else if(strelement==null){
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element not visible");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				
				return 2;
			}
					
			else{
				    //System.out.println("Result from else"+strelement.isSelected());
				    sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Value already selected");
					fos = new FileOutputStream(path);
					workbook.write(fos);		
					fos.close();
					return 1;
			}
		}
		   
	  public int verify_group(String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException,InterruptedException  {
			ArrayList<String> Buttons = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(strdatavalue,",");
	  	while(st.hasMoreTokens()){
	  		Buttons.add(st.nextToken());
	  	   }
	  		ArrayList<String> List1 = new ArrayList<String>();
	  		ArrayList<String> List2 = new ArrayList<String>();
	  		ArrayList<String> List3 = new ArrayList<String>();
	  		StringBuilder sb = new StringBuilder();
	   for(int i=0; i<Buttons.size();i++){
	  		StringTokenizer st1 = new StringTokenizer(Buttons.get(i),"-");
	  		List1.add(st1.nextToken());
	  		List2.add(st1.nextToken());
	  		strelement=find_element(List1.get(i));
	  		if(strelement!=null){
	  		if(List2.get(i).equals(strelement.getText())){
	  		sb.append(List2.get(i)+"-"+"Pass"+"; ");
	  		List3.add("Pass");
	  	   }
	  		else{
	  		sb.append(List2.get(i)+"-"+"Fail"+"; ");
	  		List3.add("Fail");
	  	   }
	   }
	  	  else{
	  			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element is not visible");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 2;
	  			
	  		}
	  		}
	     sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Result obtained are :"+sb);
		 fos = new FileOutputStream(path);
		 workbook.write(fos);		
		 fos.close();
			if(List3.contains("Fail")){
	  			return 2;
	  	  		}
			else if(List3.contains("Fail")){
	  		      		}
			return 1;
	  		}
		public int objectSelect(String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException,InterruptedException  {
			String xpathfull="/html/body/div[1]/div[7]/div[1]/form/div/div[3]/div/div[2]/div/div[3]/div[2]/div/div[1]/div[3]/div";
			String part1=xpathfull.substring(0, 84); // First part of xpath
			String part3=xpathfull.substring(87,98);
			for(int i=1;i<10;i++){
			String part2="["+i+"]";	
			String strelementid="xpath="+part1+part2+part3;
			strelement = find_element(strelementid);
			if(strelement!=null){
			if(strdatavalue.equals(strelement.getText())){
				strelement.click();
			}
			}
			else{
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element not visible");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 2;
			}
			}
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Document successfully clicked");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
			return 1;		
			}
		public int objectClick(String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException,InterruptedException  {
			String xpathfull="/html/body/div[1]/div[7]/div[1]/form/div/div[3]/div/div[2]/div/div[3]/div[2]/div/div[1]/div[3]/div/a";
			String part1=xpathfull.substring(0, 84); // First part of xpath
			String part3=xpathfull.substring(87,100);
		   for(int i=1;i<10;i++){
				String part2="["+i+"]";	
				String strelementid="xpath="+part1+part2+part3;
				strelement = find_element(strelementid);
			 if(strelement!=null){
				if(strdatavalue.equals(strelement.getText())){
						strelementid="xpath="+part1+part2+part3;
						strelement.click();
						sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Object successfully clicked");
						fos = new FileOutputStream(path);
						workbook.write(fos);		
						fos.close();
						return 1;							
			    }
		    }
				else{
					sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Object not visible");
					fos = new FileOutputStream(path);
					workbook.write(fos);		
					fos.close();
					return 2;
					
				}
		    }
		        sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Object with given name not available");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 2;	
			}
		
		public int grid_select(String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException,InterruptedException  {
			String xpathfull="/html/body/div[1]/div[7]/div[1]/form/div/div[3]/div/div[2]/div/div[3]/div[2]/div/div[1]/div[1]/div";
			String part1=xpathfull.substring(0, 84); // First part of xpath
			String part3=xpathfull.substring(87,91);
			String part5=xpathfull.substring(94,98);
			for(int i=1;i<19;i++){
				for(int j=1;j<6;j++){
					String part2="["+i+"]";	
					String part4="["+j+"]";
					String newxpath="xpath="+part1+part2+part3+part4+part5;
					strelement = find_element(newxpath);
			 if(strelement!=null){
				if(strdatavalue.equals(strelement.getText())){
					  strelement.click();
					}
			    }
			 else{
				 sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element not visible");
					fos = new FileOutputStream(path);
					workbook.write(fos);	
					return 1;
				 
			      }
			    }
			 }
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Row successfully clicked");
			fos = new FileOutputStream(path);
			workbook.write(fos);	
			return 1;
			}
		
		public int folderSelect(String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException,InterruptedException  {
			String xpathfull="/html/body/div[1]/div[7]/div[1]/div/div/div/div/div[1]/ul/li[1]/div";
			String part1=xpathfull.substring(0, 60); // First part of xpath
			String part3=xpathfull.substring(63,67);
			for(int i=1;i<16;i++){
			String part2="["+i+"]";	
			String strelementid="xpath="+part1+part2+part3;
			strelement = find_element(strelementid);
			if(strelement!=null){
			if(strdatavalue.equals(strelement.getText())){
				strelement.click();
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Folder successfully clicked");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 1;
			}
		  }
			else{
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Folder is not visible");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 2;
			}
			}
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Folder with given name not available");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
			return 2;
			
			}
		public int genealogySelect(String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException,InterruptedException  {
			String xpathfull="/html/body/div/div[5]/div/div[2]/div/div/div[1]/ul/li[1]/div";
			String part1=xpathfull.substring(0, 53); // First part of xpath
			String part3=xpathfull.substring(56,60);
		for(int i=1;i<10;i++){
			String part2="["+i+"]";	
		  if(strelement!=null){
			String strelementid="xpath="+part1+part2+part3;
			strelement = find_element(strelementid);
			if(strdatavalue.equals(strelement.getText())){
				strelement.click();
		   }
		 }
			else{
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Genealogy is not visible");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
		    }
	    }
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Selected the document genealogy successfully");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
			return 1;
	   }
		
		public int multiple_docselect(String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException,InterruptedException  {
			String xpathfull="/html/body/div[1]/div[7]/div[1]/form/div/div[3]/div/div[2]/div/div[3]/div[2]/div/div[1]/div[3]/div";
			String part1=xpathfull.substring(0, 84); // First part of xpath
			String part3=xpathfull.substring(87,98);
			ArrayList<String> List3 = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(strdatavalue,",");
	  		while(st.hasMoreTokens()){
	  		List3.add(st.nextToken());
	  		}
			for(int j=0; j<List3.size();j++ )
	  		for(int i=1;i<19;i++){
			String part2="["+i+"]";	
			String newxpath="xpath="+part1+part2+part3;
			strelement = find_element(newxpath);
			Actions builder = new Actions(driver);
			if(strelement!=null){
			if(List3.get(j).equals(strelement.getText())){
			builder.keyDown(Keys.CONTROL).click(strelement).keyUp(Keys.CONTROL);	
			}
			}
			else{
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element not visible");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
			}
			builder.build().perform();
	  		}
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Selected the documents successfully");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
	  		return 1;
		}
		
		public int user_icon(String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException,InterruptedException  {
			String xpathfull="/html/body/header/div[2]/div[5]/div/ul/li[1]/a/div/span";
			//String xpathfull="/html/body/header/div[2]/div[5]/div/ul/li[1]/a/div";
			String part1=xpathfull.substring(0, 41); // First part of xpath
			String part3=xpathfull.substring(44,55);
			WebElement Usermenu=find_element("id=btnUserAccount");
			Usermenu.click();
			Thread.sleep(1000);
			for(int i=1;i<10;i++){
				String part2="["+i+"]";	
				String strelementid="xpath="+part1+part2+part3;
				strelement = find_element(strelementid);
				if(strelement!=null){
				if(strdatavalue.equals(strelement.getText())){
					strelement.click();
					break;
				}
				}
				else{
					sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element not visible");
					fos = new FileOutputStream(path);
					workbook.write(fos);		
					fos.close();
					return 2;	
				}
		   }
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Clicked on "+strdatavalue+" successfully");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
				return 1;
				}
		
		
		
		public int Pin_operations(String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException,InterruptedException  {
			String xpathfull="/html/body/div[1]/div[5]/div/div/div[1]/div[3]/table/tbody/tr/td[1]/div/div[2]/div/div[3]/div[2]/div/div[1]/div[3]/div/div[1]/div";
			String part1=xpathfull.substring(0, 104); // First part of xpath
			String part3=xpathfull.substring(107,129);
			int max= Integer.parseInt(strdatavalue);
			max=max+1;
			for(int i=1;i<max;i++){
			String part2="["+i+"]";	
			String newxpath="xpath="+part1+part2+part3;
			strelement = find_element(newxpath);
			if(strelement!=null){
			strelement.click();
			Thread.sleep(1000);
		   }
			else{
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element not visible");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 2;
			}
		   }
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Pinned"+strdatavalue+" operations successfully");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
			return 1;
		}
	  public int Sel_operations(String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException,InterruptedException  {
				String xpathfull="/html/body/div[1]/div[5]/div/div/div[1]/div[3]/table/tbody/tr/td[1]/div/div[2]/div/div[3]/div[2]/div/div[1]/div[1]/div/div[1]/div";
				String part1=xpathfull.substring(0, 104); // First part of xpath
				String part3=xpathfull.substring(107,129);
				int max= Integer.parseInt(strdatavalue);
				max=max+1;
		for(int i=1;i<max;i++){
				String part2="["+i+"]";	
				String newxpath="xpath="+part1+part2+part3;
				strelement = find_element(newxpath);
			if(strelement!=null){
				strelement.click();
				Thread.sleep(1000);
			}
	    }
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Selected "+strdatavalue+" operations successfully");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
		  		return 1;
	  		
	}
	   public int add_file(String strelementid, String strdatavalue, int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException, InterruptedException {
			  strelement = find_element(strelementid);
			  strelement.sendKeys(strdatavalue);
			  Thread.sleep(1000);
			  sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Button Clicked");
		  		fos = new FileOutputStream(path);
		  		    workbook.write(fos);		
		  		    fos.close();
		  		  return 1;	
		  			}
	   
		/*public int enter_dynamic(String strelementid,String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			Calendar cal = Calendar.getInstance();
	    	cal.getTime();
	    	SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
	    	//System.out.println( sdf.format(cal.getTime()));
	    	strelement = find_element(strelementid);
	    	dynamictext=strdatavalue+"_"+sdf.format(cal.getTime());
	    	if(strelement!=null){
	    	strelement.sendKeys(dynamictext);
	    	sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Entered the dynamic text value in the field successfully");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
			return 1;
	    	}
	    	else{
	    		sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element not visible");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 2;	
	    	}
	   }*/
		/*public int enter_previous(String strelementid,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			strelement = find_element(strelementid);
		  if(strelement!=null){
			strelement.sendKeys(dynamictext);
	    	sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Entered previous dynamic text value in the field successfully");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
			return 1;
		 }
		  else{
	    		sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element not visible");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 2;	
	    	}
		}*/
		public int page_reload(int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			driver.navigate().refresh();
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Page reloaded successfully");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
			return 1;
		}
		public int checkbox_tick(String strelementid,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			strelement = find_element(strelementid);
		  if(strelement!=null){
			if(strelement.isSelected()){
	    		sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Checkbox is selected");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 1;
		  }
	    	else{
	    		sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Checkbox is not selected");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 2;
	        }
		   }
			else{
	    		sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element not visible");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 2;	
	    	}
	    	
	      }
		public int open_newtab(String strelementid,String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			String xpathfull="/html/body/div[1]/div[7]/div[1]/form/div/div[3]/div/div[2]/div/div[3]/div[2]/div/div[1]/div[3]/div/a";
			String part1=xpathfull.substring(0, 84); // First part of xpath
			String part3=xpathfull.substring(87,100);
		   for(int i=1;i<10;i++){
				String part2="["+i+"]";	
				strelementid="xpath="+part1+part2+part3;
				strelement = find_element(strelementid);
			 if(strelement!=null){
				if(strdatavalue.equals(strelement.getText())){
						strelement.sendKeys(Keys.CONTROL +"t");
					    ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
					    driver.switchTo().window(tabs.get(0));
						sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Object successfully clicked");
						fos = new FileOutputStream(path);
						workbook.write(fos);		
						fos.close();
						return 1;							
			    }
		    }
				else{
					sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Object not visible");
					fos = new FileOutputStream(path);
					workbook.write(fos);		
					fos.close();
					return 2;
					
				}
		    }
		        sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Object with given name not available");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				return 2;	
			}
		public int start_time(int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			D1 = new Date();
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Start time captured successfully");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
			return 1;
		}
		
		public int time_difference(int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx,int extnstatusindx) throws IOException {
				D2=new Date();
				long diff = D2.getTime() - D1.getTime();
			    long diffSeconds = diff / 1000 % 60;
			    long diffMinutes = diff / (60 * 1000) % 60;
			 // long diffHours = diff / (60 * 60 * 1000);
			 //int diffInDays = (int) diff / (1000 * 60 * 60 * 24);
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue(+diffMinutes+" Minutes"+diffSeconds+" Seconds");
			sheet.getRow(counter).getCell(extnstatusindx).setCellValue("Pass");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
			return 1;
			}
		public void HighlightMyElement(WebDriver driver, WebElement strelement) { 
			
			if (strelement!=null){
			   JavascriptExecutor javascript = (JavascriptExecutor) driver;
			   javascript.executeScript("arguments[0].setAttribute('style', arguments[1]);", strelement, "color: orange; border: 4px solid orange;");
			   try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   //javascript.executeScript("arguments[0].setAttribute('style', arguments[1]);", strelement, "color: pink; border: 4px solid pink;");
			   //javascript.executeScript("arguments[0].setAttribute('style', arguments[1]);", strelement, "color: yellow; border: 4px solid yellow;");
			   javascript.executeScript("arguments[0].setAttribute('style', arguments[1]);", strelement, ""); 
			}
			else{
				//System.out.println("element is not visible");
				}
						  } 
			
		public int click_hidden(String strelementid,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException, InterruptedException {
			 JavascriptExecutor executor= (JavascriptExecutor)driver; // Creating instance of Java script executor
			 executor.executeScript("document.getElementById('multiple').style.display='block';"); //Java script executing for showing hidden element
			  Thread.sleep(3000);
			  strelement = find_element(strelementid);
			  strelement.click();
			 // Thread.sleep(1000);
			  //executor.executeScript("document.getElementById('multiple').style.display='block';");
			  // JavascriptExecutor jse = (JavascriptExecutor)driver;
			    sheet.getRow(counter).getCell(extncmtsindx).setCellValue("clicked hidden element");
		  		fos = new FileOutputStream(path);
		  		    workbook.write(fos);		
		  		    fos.close();
		  		  return 1;	
		  			}	
		
		public int unhide_element (String strelementid,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException, InterruptedException {
			 JavascriptExecutor executor= (JavascriptExecutor)driver; // Creating instance of Java script executor
			 strarr1 = strelementid.split("=");
			 strtext = strarr1[1];
			 executor.executeScript("document.getElementById('"+strtext+"').style.display='block';"); //Java script executing for showing hidden element
			 sheet.getRow(counter).getCell(extncmtsindx).setCellValue("The element made visible");
			 fos = new FileOutputStream(path);
	  		    workbook.write(fos);		
	  		    fos.close();
	  		  return 1;	
		}
		
		public int hide_element (String strelementid, int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx)throws IOException, InterruptedException{
			JavascriptExecutor executor=(JavascriptExecutor)driver;
			strarr1=strelementid.split("=");
			strtext=strarr1[1];
			executor.executeScript("document.getElementById('"+strtext+"', arg1).style.display='none';");
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("The element is hidden");
			fos=new FileOutputStream(path);
				workbook.write(fos);
				fos.close();
				return 1;
		}
		
		public int refresh_page(int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			driver.navigate().refresh();                   
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Page Resreshed Successfully");
        	fos = new FileOutputStream(path);
        	workbook.write(fos);		
        	fos.close();
        	return 1;            
			
		}
		
		public int maximize_window(int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			driver.manage().window().maximize();              
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Window Maximised");
        	fos = new FileOutputStream(path);
        	workbook.write(fos);		
        	fos.close();
        	return 1;           
			}

		public int switch_activeWindow(int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx,String windowname) throws IOException{
			//String parentHandle = driver.getWindowHandle(); //to get handle of current window
			//WebElement currentHandle = driver.switchTo().activeElement();
			driver.switchTo().window("windowname");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Driver control changed to new window successfully");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
			return 1;
		}
		
		public int Bulk_upload(String strelementid,String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException, InterruptedException {
			
			//JavascriptExecutor executor= (JavascriptExecutor)driver; // Creating instance of Java script executor
			//executor.executeScript("document.getElementById('files').style.display='block';"); //Java script executing for showing hidden element
			//Thread.sleep(5000);
			strelement = find_element(strelementid);
		    List<String> bulkfiles = Arrays.asList(strdatavalue.split(","));
			int listCount = bulkfiles.size();
			for(int i=0; i<listCount; i++) {
				String filepath=bulkfiles.get(i);
				strelement.sendKeys(filepath);
				Thread.sleep(1000);
			}
			//executor.executeScript("document.getElementById('files').style.display='none';");
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("File uploaded successfully");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
			return 1;
		}
		
		public int click_hold(int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			ArrayList<WebElement> linkList = new ArrayList<WebElement>();
			linkList.add(driver.findElement(By.tagName("a")));

		    for(int i=0 ; i<linkList.size() ; i++)
		    {
		        if(linkList.get(i).getAttribute("href").contains("AddMultipleDocument"))
		        {
		            linkList.get(i).click();
		            break;
		        }
		    }
				
			/*Actions builder = new Actions(driver);
			    strelement = driver.findElement(By.id("multiple"));
			    try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    builder.click();
			    builder.perform();
			    strelement.click();
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Clicked on successfully");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();*/
				return 1;
		    
		   }
		public int click_item(String strdatavalue,int counter,String path,FileOutputStream fos,XSSFWorkbook workbook,XSSFSheet sheet,int extncmtsindx) throws IOException {
			if(strdatavalue!= null){
			//strelement.sendKeys(strdatavalue);
				String item1 = ".//*[contains(text(),'";
				String item2 = "')]";
				String item=item1+strdatavalue+item2;
				driver.findElement(By.xpath(item)).click();
			sheet.getRow(counter).getCell(extncmtsindx).setCellValue("clicked on "+strdatavalue+" successfully");
			fos = new FileOutputStream(path);
			workbook.write(fos);		
			fos.close();
			return 1;
		   }
		    else{
				sheet.getRow(counter).getCell(extncmtsindx).setCellValue("Element not visible");
				fos = new FileOutputStream(path);
				workbook.write(fos);		
				fos.close();
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				// Now you can do whatever you need to do with it, for example copy somewhere
				FileUtils.copyFile(scrFile, new File("C:\\Selenium\\Screenshots\\Errors\\Error"+e+".jpg"));
				e++;
				return 2;
		    }
		}
		
		}




	
	
	
	
	
	
	




