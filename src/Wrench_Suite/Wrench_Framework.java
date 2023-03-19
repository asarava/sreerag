/*
 * Created  On: 05/07/2014
 * Version    : 1.0
 * Tools  Used: Selenium WebDriver, TestNG 6.7.0, JDK 6.0, JRE 7.0, Eclipse 1.4.2
 */
package Wrench_Suite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.sikuli.script.FindFailed;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Wrench_Framework {
	
	private File exist_excelfile = null;
	private FileInputStream fis = null;
	private FileOutputStream fos =null;
	private Keywords strobj = new Keywords();
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;  
	private String strtobeexctd = null, strflowvalue = null, strdatavalue = null, strelementidvalue = null;
	private String[] strarr;
	private int strrowCount = 0, counter1;
	private double strnumber;
	private boolean strboolean;
	private int strresult = 0;
	static Logger Log = Logger.getLogger(Log.class.getName());

	
  @Test
  @Parameters({"excel_path","output_path","tobeexecuted_index","executionstatus_index","executioncomments_index","keywordname_index","elementid_index","data_index","excelsheet_name"})

  public void driver_function(String path,String out_path, int tobeexecuted_index,int executionstatus_index,int executioncomments_index, int keywordname_index, int elementid_index, int data_index, String excelsheet) throws IOException, InterruptedException {
     
     FileManager fileManager=new FileManager();      
     if(fileManager.copy_file(path, out_path)){
    	 path=FileManager.getDestinationPath();
     }
     else{
    	 return;
     }
	  //verifying the existence of excel file
	 exist_excelfile = new File(path);
	 if(exist_excelfile.exists()) {
		  fis = new FileInputStream(path);
		  workbook = new XSSFWorkbook(fis);
		  sheet = workbook.getSheet(excelsheet);
		  strrowCount = sheet.getLastRowNum()-1;
		  Log.info("Got total no. of steps in excel");
		  System.out.println("Number of steps in Execution flow: "+strrowCount);		  
		  
		  for(counter1=1 ; counter1<=strrowCount ; counter1++) {//For loop - for getting the function name from Execution flow sheet
			  strtobeexctd = sheet.getRow(counter1).getCell(tobeexecuted_index).getStringCellValue().trim();
			  if(strtobeexctd.equalsIgnoreCase("Y")) {//If Statement - to help user to run the selective steps
				  strflowvalue = sheet.getRow(counter1).getCell(keywordname_index).getStringCellValue().trim(); 
				  //If statement - verifying the blank entries in Automation functional flow column
				  if(strflowvalue.equals("")) {
					  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
					  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Function name field should not be blank. Step Skipped");
					  fos = new FileOutputStream(path);
					  workbook.write(fos);		
					  fos.close();
				  }
				  else {
					  
					  try {
						  strdatavalue = sheet.getRow(counter1).getCell(data_index).getStringCellValue().trim();
						  System.out.println(strdatavalue); 
						  strarr = strdatavalue.split("=");
						  System.out.println(strarr); //delete
						  if(strarr.length == 2)
							  strdatavalue = strarr[1];
					  } 
						  catch(IllegalStateException e) {
						  try {
							  strnumber = sheet.getRow(counter1).getCell(data_index).getNumericCellValue();
							  strdatavalue = ""+(int)strnumber;  
						  } catch(IllegalStateException e1) {
							  strboolean = sheet.getRow(counter1).getCell(data_index).getBooleanCellValue();
							  strdatavalue = new Boolean(strboolean).toString();
						  }
					  }
					  strelementidvalue = sheet.getRow(counter1).getCell(elementid_index).getStringCellValue().trim();
					  System.out.print(strelementidvalue);
					  if (strflowvalue.equals("open_browser")) {//calling function open_browser()
						  if(strdatavalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Data field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
							  strresult = strobj.open_browser(strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index,strelementidvalue);
					  } 
					  else if (strflowvalue.equals("enter_text")) {//calling function enter_text()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
							  strresult = strobj.enter_text(strelementidvalue,strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  } 
					  else if (strflowvalue.equals("enter_login")) {//calling function enter_text()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
							  strresult = strobj.enter_login(strelementidvalue,strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  } 
					  else if (strflowvalue.equals("verify_value")) {//calling function verify_value()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
							  strresult = strobj.verify_value(strelementidvalue,strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index,executionstatus_index);
					  } 
					 /* else if (strflowvalue.equals("click_png")) {//calling function verify_value()
						  if(strdatavalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
							  strresult = strobj.click_png(strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index,executionstatus_index);
					  } */
					  else if (strflowvalue.equals("verify_hiddenValue")) {//calling function verify_value()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
							  strresult = strobj.verify_hiddenValue(strelementidvalue,strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index,executionstatus_index);
					  }
					  else if (strflowvalue.equals("verify_attribute")) {//calling function verify_value()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
							  strresult = strobj.verify_attribute(strelementidvalue,strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index,executionstatus_index);
					  } 
					  else if (strflowvalue.equals("verify_absence")) {//calling function verify_value()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
							  strresult = strobj.verify_absence(strelementidvalue,counter1,path,fos,workbook,sheet,executioncomments_index,executionstatus_index);
					  } 
					  
					  else if (strflowvalue.equals("verify_presence")) {//calling function verify_value()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
							  strresult = strobj.verify_presence(strelementidvalue,counter1,path,fos,workbook,sheet,executioncomments_index,executionstatus_index);
					  } 
					 			 
					  else if(strflowvalue.equals("close_browser")) //calling function close_browser()
						  strresult = strobj.close_browser(counter1,path,fos,workbook,sheet,executioncomments_index,executionstatus_index);
					  else if(strflowvalue.equals("wait_function")) //calling function wait_function()
						  strresult = strobj.wait_function(counter1,path,fos,workbook,sheet,executioncomments_index,strdatavalue);
					  else if (strflowvalue.equals("click_button")) {//calling function to click button()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
							  strresult = strobj.click_button(strelementidvalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  } 
					/*  else if (strflowvalue.equals("click_image")) {//calling function to click button()
						  if(strdatavalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
							  strresult = strobj.click_image(strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  } */
					  else if (strflowvalue.equals("tabkeypress")) {//calling function to click button()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
							  strresult = strobj.tabkeypress(strelementidvalue,excelsheet, counter1,path,fos,workbook,sheet,executioncomments_index);
					  }
					  
					  
					  else if (strflowvalue.equals("down_key")) {//calling function to click button()
						 /* if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else*/
							  strresult = strobj.down_key(strelementidvalue,excelsheet, counter1,path,fos,workbook,sheet,executioncomments_index);
					  } 
					  else if (strflowvalue.equals("enter_key")) {//calling function to click button()
						 /* if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else*/
							  strresult = strobj.enter_key(counter1,path,fos,workbook,sheet,executioncomments_index);
					  } 
					   else if (strflowvalue.equals("backkeypress")) {//calling function to click button()
						  if(strelementidvalue.equals("")) {
							  //verify_elementID(strflowvalue,path,tobeexecuted_index,executionstatus_index,executioncomments_index, keywordname_index,  elementid_index, data_index,excelsheet);
							sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							 fos = new FileOutputStream(path);
							 workbook.write(fos);		
							 fos.close();	
						  }
						  else
							  strresult = strobj.backkeypress(strelementidvalue,excelsheet, counter1,path,fos,workbook,sheet,executioncomments_index);
					  } 
					  else if (strflowvalue.equals("forwardkeypress")) {//calling function to click button()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
							  strresult = strobj.forwardkeypress(strelementidvalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  } 
					  else if (strflowvalue.equals("new_windowswch")) {//calling function to click button()
						   strresult = strobj.new_windowswch(counter1,path,fos,workbook,sheet,executioncomments_index);
					  } 
					  else if (strflowvalue.equals("mouse_hover")) {//calling function to click button()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
							  strresult = strobj.mouse_hover(strelementidvalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  } 
					  else if (strflowvalue.equals("select_dropdown")) {//calling function to click button()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
							  strresult = strobj.select_dropdown(strelementidvalue,strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  } 
					  else if (strflowvalue.equals("click_item")) {//calling function enter_text()
						  if(strdatavalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Value field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
							  strresult = strobj.click_item(strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  } 
					   /*else if (strflowvalue.equals("scroll_up")) {//calling function to click button()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
						  strresult = strobj.scroll_up(strelementidvalue,strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  } 
					   else if (strflowvalue.equals("scroll_down")) {//calling function to click button()
							  if(strelementidvalue.equals("")) {
								  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
								  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
								  fos = new FileOutputStream(path);
								  workbook.write(fos);		
								  fos.close();	
							  }
							  else
							  strresult = strobj.scroll_down(strelementidvalue,strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
						  } 
					   else if (strflowvalue.equals("scroll_horizontal")) {//calling function to click button()
							  if(strelementidvalue.equals("")) {
								  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
								  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
								  fos = new FileOutputStream(path);
								  workbook.write(fos);		
								  fos.close();	
							  }
							  else
							  strresult = strobj.scroll_horizontal(strelementidvalue,strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
						  } */
					   else if (strflowvalue.equals("click_scroll")) {//calling function to click button()
							  if(strelementidvalue.equals("")) {
								  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
								  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
								  fos = new FileOutputStream(path);
								  workbook.write(fos);		
								  fos.close();	
							  }
							  else
							  strresult = strobj.click_scroll(strelementidvalue,strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
						  } 
					   else if (strflowvalue.equals("scroll_right")) {//calling function to click button()
							  if(strelementidvalue.equals("")) {
								  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
								  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
								  fos = new FileOutputStream(path);
								  workbook.write(fos);		
								  fos.close();	
							  }
							  else
							  strresult = strobj.click_scroll(strelementidvalue,strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
						  }
					   else if (strflowvalue.equals("wait_implicit")) {//calling function to click button()
							  if(strelementidvalue.equals("")) {
								  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
								  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
								  fos = new FileOutputStream(path);
								  workbook.write(fos);		
								  fos.close();	
							  }
							  else
							  strresult = strobj.wait_implicit(strelementidvalue,counter1,path,fos,workbook,sheet,executioncomments_index);
						  }
					   else if (strflowvalue.equals("hidden_upload")) {//calling function to click button()
							  if(strelementidvalue.equals("")) {
								  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
								  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
								  fos = new FileOutputStream(path);
								  workbook.write(fos);		
								  fos.close();	
							  }
							  else
							  strresult = strobj.hidden_upload(strelementidvalue,strdatavalue, counter1,path,fos,workbook,sheet,executioncomments_index);
						  }
					 else if (strflowvalue.equals("click_hidden")) {//calling function to click button()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
						  strresult = strobj. click_hidden(strelementidvalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  }
					   else if (strflowvalue.equals("navigate_link")) {//calling function to click button()
							  if(strelementidvalue.equals("")) {
								  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
								  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
								  fos = new FileOutputStream(path);
								  workbook.write(fos);		
								  fos.close();	
							  }
							  else
							  strresult = strobj.navigate_link(strelementidvalue,strdatavalue, counter1,path,fos,workbook,sheet,executioncomments_index);
						  }
					   else if (strflowvalue.equals("hidden_link")) {//calling function to click button()
							  if(strelementidvalue.equals("")) {
								  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
								  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
								  fos = new FileOutputStream(path);
								  workbook.write(fos);		
								  fos.close();	
							  }
							  else
							  strresult = strobj.hidden_link(strelementidvalue,strdatavalue, counter1,path,fos,workbook,sheet,executioncomments_index);
						  }
					   else if (strflowvalue.equals("get_errormessage")) {//calling function to click button()
							  if(strelementidvalue.equals("")) {
								  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
								  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
								  fos = new FileOutputStream(path);
								  workbook.write(fos);		
								  fos.close();	
							  }
							  else
							  strresult = strobj.get_errormessage(strelementidvalue,counter1,path,fos,workbook,sheet,executioncomments_index);
						  }
					  	 
					   else if (strflowvalue.equals("downarrow_scroll")) {//calling function to click button()
							  if(strelementidvalue.equals("")) {
								  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
								  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
								  fos = new FileOutputStream(path);
								  workbook.write(fos);		
								  fos.close();	
							  }
							  else
							  strresult = strobj.downarrow_scroll(strelementidvalue,strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
						  }
					   else if (strflowvalue.equals("date_entry")) {//calling function to click button()
							  if(strelementidvalue.equals("")) {
								  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
								  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
								  fos = new FileOutputStream(path);
								  workbook.write(fos);		
								  fos.close();	
							  }
							  else
							  strresult = strobj.date_entry(strelementidvalue,strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
						  }
					  
					  else if (strflowvalue.equals("selDropDown")) {//calling function to click button()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
						  strresult = strobj.selDropDown(strelementidvalue,strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  }
					 /* else if (strflowvalue.equals("enter_dynamic")) {//calling function to click button()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
						  strresult = strobj.enter_dynamic(strelementidvalue,strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  }
					  else if (strflowvalue.equals("enter_previous")) {//calling function to click button()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
						  strresult = strobj.enter_previous(strelementidvalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  }*/
					  else if (strflowvalue.equals("tick_checkbox")) {//calling function to click button()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
						  strresult = strobj.tick_checkbox(strelementidvalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  }
					  else if (strflowvalue.equals("unhide_element")) {//calling function to click button()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
						  strresult = strobj.unhide_element(strelementidvalue, counter1,path,fos,workbook,sheet,executioncomments_index);
					  }
					  else if (strflowvalue.equals("hide_element")) {//calling function to click button()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
						  strresult = strobj.hide_element(strelementidvalue, counter1,path,fos,workbook,sheet,executioncomments_index);
					  }
					  else if (strflowvalue.equals("checkbox_tick")) {//calling function to click button()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
						  strresult = strobj.checkbox_tick(strelementidvalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  }
					  else if (strflowvalue.equals("add_file")) {//calling function to click button()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
						  strresult = strobj.add_file(strelementidvalue,strdatavalue, counter1,path,fos,workbook,sheet,executioncomments_index);
					  }
					  else if (strflowvalue.equals("autoit_upload")) {//calling function to click button()
						  if(strelementidvalue.equals("")) {
							  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
							  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Element ID field is a mandatory field for open_browser(). Step Skipped");
							  fos = new FileOutputStream(path);
							  workbook.write(fos);		
							  fos.close();	
						  }
						  else
						  strresult = strobj.autoit_upload(strelementidvalue,strdatavalue, counter1,path,fos,workbook,sheet,executioncomments_index);
					  }
					  else if(strflowvalue.equals("bulk_upload"))
						  strresult=strobj.Bulk_upload(strelementidvalue, strdatavalue, counter1, path, fos, workbook, sheet, executioncomments_index);
					  else if (strflowvalue.equals("click_hold"))
						  strresult = strobj.click_hold(counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if (strflowvalue.equals("Sel_operations"))
						  strresult = strobj.Sel_operations(strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if (strflowvalue.equals("Pin_operations"))
						  strresult = strobj.Pin_operations(strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if (strflowvalue.equals("user_icon")) 
							strresult = strobj.user_icon(strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if (strflowvalue.equals("multiple_docselect")) 
							strresult = strobj.multiple_docselect(strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if (strflowvalue.equals("verify_group")) 
						 strresult = strobj.verify_group(strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if (strflowvalue.equals("file_upload"))
					  strresult = strobj.file_upload(counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if (strflowvalue.equals("page_reload"))
						  strresult = strobj.page_reload(counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if (strflowvalue.equals("objectSelect"))
					        strresult = strobj.objectSelect(strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if (strflowvalue.equals("objectClick"))
						  strresult = strobj.objectClick(strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if (strflowvalue.equals("open_newtab"))
						  strresult = strobj.open_newtab(strelementidvalue,strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if (strflowvalue.equals("grid_select"))
						  strresult = strobj.grid_select(strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if (strflowvalue.equals("folderSelect"))
						  strresult = strobj.folderSelect(strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if (strflowvalue.equals("genealogySelect"))
						  strresult = strobj.genealogySelect(strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if(strflowvalue.equals("capture_screenshot")) //calling function close_browser()
						  strresult = strobj.capture_screenshot(strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if(strflowvalue.equals("date_picker")) //calling function close_browser()
							  strresult = strobj.date_picker(strelementidvalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if(strflowvalue.equals("rowselection")) //calling function close_browser()
						  strresult = strobj.rowselection(counter1,path,fos,workbook,sheet,executioncomments_index);
				   	  else if (strflowvalue.equals("alert_accept")) {//calling function to click button(){
						   strresult = strobj.alert_accept(counter1,path,fos,workbook,sheet,executioncomments_index);}
				  	  else if (strflowvalue.equals("alert_verifytext")) {//calling function to click button(){
						   strresult = strobj.alert_verifytext(strdatavalue,counter1,path,fos,workbook,sheet,executioncomments_index);}
				  	  else if (strflowvalue.equals("alert_reject")) {//calling function to click button(){
						   strresult = strobj.alert_accept(counter1,path,fos,workbook,sheet,executioncomments_index);}
					  else if(strflowvalue.equals("switch_preWindow")) //calling function close_browser()
						 strresult = strobj.switch_preWindow(counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if(strflowvalue.equals("clear_text")) //calling function close_browser()
						  strresult = strobj.clear_text(strelementidvalue,excelsheet, counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if(strflowvalue.equals("close_browser")) //calling function close_browser()
						  strresult = strobj.close_browser(counter1,path,fos,workbook,sheet,executioncomments_index,executionstatus_index);
					  else if(strflowvalue.equals("wait_function")) //calling function wait_function()
						  strresult = strobj.wait_function(counter1,path,fos,workbook,sheet,executioncomments_index,strdatavalue);
					  else if(strflowvalue.equals("start_time")) //calling function wait_function()
						  strresult = strobj.start_time(counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if(strflowvalue.equals("fetch_value")) //calling function wait_function()
						  strresult = strobj.fetch_value(strelementidvalue,counter1,path,fos,workbook,sheet,executioncomments_index,executionstatus_index);
					  else if(strflowvalue.equals("time_difference")) //calling function wait_function()
						  strresult = strobj.time_difference(counter1,path,fos,workbook,sheet,executioncomments_index,executionstatus_index);

					  else if(strflowvalue.equals("multiple_selection")){ //calling function switch_previousWindow to switch driver control back to previous window
						  if(strelementidvalue.equals("")){
							  verify_ElementID(strflowvalue, path,tobeexecuted_index,executionstatus_index,executioncomments_index, keywordname_index,elementid_index, data_index, excelsheet);
						  }
						  else
							  strresult = strobj.multiple_selection(strelementidvalue,counter1,path,fos,workbook,sheet,executioncomments_index);
					  }
					  else if(strflowvalue.equals("maximize_window")) //calling function maximize_browser()
							 strresult = strobj.switch_preWindow(counter1,path,fos,workbook,sheet,executioncomments_index);
					  else if(strflowvalue.equals("switch_newWindow")) //calling function switch_newWindow()
						  strresult = strobj.switch_newWindow(counter1,path,fos,workbook,sheet,executioncomments_index);
					  
					  else if(strflowvalue.equals("refresh_page")) //calling function wait_function()
						  strresult = strobj.refresh_page(counter1,path,fos,workbook,sheet,executioncomments_index);
					  //else if(strflowvalue.equals("switch_specWindow")) //calling function switch_newWindow()
						//  strresult = strobj.switch_specWindow(counter1,path,fos,workbook,sheet,executioncomments_index,strdatavalue); 
					  else if(strflowvalue.equals("switch_activeWindow")) //calling function switch_newWindow()
						  strresult = strobj.switch_activeWindow(counter1,path,fos,workbook,sheet,executioncomments_index, excelsheet);
	
					  
					  
	//Add new additional functions created in an else if here		
					  
					  else {// to write invalid function name error in excel
						  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
						  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Given Function is not part of Framework. Step Skipped");
						  fos = new FileOutputStream(path);
						  workbook.write(fos);		
						  fos.close();
						  strresult = 0;
					  }				
					  if(strresult==1) //writing result to Execution sheet 
						  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Pass");
					  else if(strresult==2)					  
						  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Fail");	
					  if(counter1>strrowCount) {// to write unable to find data error in excel
						  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
						  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Invalid FunctionName Or No DataSheet Entry. Step Skipped");
						  fos = new FileOutputStream(path);
						  workbook.write(fos);		
						  fos.close();						  
					  }
				  }
			  }
		  }
		  fis.close();
	  }
	  
	  else 
		  System.out.println("Invalid Excel Path Specified "+path);
  }
  //This function is to check null value of Element_ID against Keyword name in excel sheet
  public void verify_ElementID(String strflowvalue, String path, int tobeexecuted_index,int executionstatus_index,int executioncomments_index, int keywordname_index, int elementid_index, int data_index, String excelsheet)throws IOException, InterruptedException {
	  sheet.getRow(counter1).getCell(executionstatus_index).setCellValue("Not Completed");
	  sheet.getRow(counter1).getCell(executioncomments_index).setCellValue("Proper Element ID for keyword ("+strflowvalue+") is missing. Hence this step has been skipped");
	  fos = new FileOutputStream(path);
	  workbook.write(fos);		
	  fos.close();
  }
}

