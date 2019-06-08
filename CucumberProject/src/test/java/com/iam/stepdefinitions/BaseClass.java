package com.iam.stepdefinitions;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.sikuli.script.SikuliException;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class BaseClass {

	public static WebDriver driver;
	final static Logger logger = Logger.getLogger("BaseClass.class");

	public static void launchBrowser() {
		try {
			BasicConfigurator.configure();
			Properties prop = new Properties();
			File file = new File("src/test/resources/Configuration/config.properties");
			FileInputStream fis = new FileInputStream(file);
			prop.load(fis);
			String browserType = prop.getProperty("browserType");
			logger.info("browser type for execution is: "+browserType);

			if(browserType.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--start-maximized");
				options.addArguments("disable-infobars");
				driver = new ChromeDriver(options);
			}

			else if(browserType.equalsIgnoreCase("internetexplorer")) {
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();  
				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

				System.setProperty("webdriver.ie.driver", "IEDriverServer.exe");
				driver = new InternetExplorerDriver(ieCapabilities);
				driver.manage().window().maximize();
			}

			else if(browserType.equalsIgnoreCase("firefox")) {
				driver = new FirefoxDriver();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 **************************************************************************************
	 * Method Name		: setHighlight
	 * Description		: this method will highlights the UI elements
	 * Input parameter	: web element
	 * Author			: IAM Automation
	 ***************************************************************************************
	 */

	public static void setHighlight(WebElement element) {
		String attributevalue = "border:3px solid green;";
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		String getattib = element.getAttribute("style");
		executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, attributevalue);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, getattib);
	}

	/*
	 **************************************************************************************
	 * Method Name		: isObjectDisplayed
	 * Description		: this method will verify whether an UI element is displayed or not
	 * Input parameter	: web element, driver
	 * Author			: UWeb Automation
	 ***************************************************************************************
	 */

	public boolean isObjectDisplayed(WebElement objDisplayed, String webElementName, String reportingYesNo){
		int attempts = 0;
		boolean isObDisp = false;
		while(attempts < 5){
			try{
				objDisplayed.isDisplayed();
				isObDisp = true;
				if (reportingYesNo.toUpperCase().equals("YES")){
					System.out.println(webElementName + " is displayed as expected in application screen");
					break;
				}
			}
			catch(StaleElementReferenceException ex) { 
				isObDisp= false;
				System.out.println(this.getClass().getName() + " Stale exception occured ***************");}
			catch(NoSuchElementException nEx){
				isObDisp= false;
				if (reportingYesNo.toUpperCase().equals("YES") || reportingYesNo.toUpperCase().equals("NO")){
					System.out.println(webElementName + " is not displayed in application screen");
					break;
				}
			}
			attempts++;
		}
		return isObDisp;
	}

	/*
	 **************************************************************************************
	 * Method Name		: WaitObjEnabled
	 * Description		: this method will highlights the UI elements
	 * Input parameter	: web element, driver
	 * Author			: UWeb Automation
	 ***************************************************************************************
	 */

	public static void WaitObjEnabled(WebElement objEnabled, String webElementName, String reportingYesNo){
		for(int i=0;i<5;i++){
			try{
				Boolean b = objEnabled.isEnabled();
				if (reportingYesNo.toUpperCase().equals("YES") && b == true){
					System.out.println(webElementName + " is Enabled as expected in application screen");
					break;
				}
			}
			catch(Exception ex){
				if (reportingYesNo.toUpperCase().equals("YES")){
					System.out.println(webElementName + " is not Enabled in application screen");
				}
				else{
					System.out.println(webElementName + " is not Enabled in application screen reporting is set to NONE!!!");
				}
			}
		}
	}

	/*
	 **************************************************************************************
	 * Method Name		: safeType
	 * Description		: this method will enter the given text in a web element
	 * Input parameter	: web element
	 * Author			: UWeb Automation
	 ***************************************************************************************
	 */

	public void safeType(WebElement objToEnterValue, String Value, String webElementName){
		try
		{
			setHighlight(objToEnterValue);
			objToEnterValue.clear();
			objToEnterValue.sendKeys(Value);
			System.out.println("Enter Value in - " + webElementName +"'" + Value + "' value is entered in " + webElementName);
		}
		catch(NoSuchElementException ex){
		}
	}

	/*
	 **************************************************************************************
	 * Method Name		: safeClearType
	 * Description		: this method will clear an UI element and enter the given text 
	 * Input parameter	: web element
	 * Author			: UWeb Automation
	 ***************************************************************************************
	 */

	public void safeClearType(WebElement objToEnterValue, String Value, String webElementName){
		try
		{
			setHighlight(objToEnterValue);
			objToEnterValue.clear();
			objToEnterValue.sendKeys(Value);
			System.out.println("Enter Value in - " + webElementName +"'" + Value + "' value is entered in " + webElementName);
		}
		catch(NoSuchElementException ex){

		}
	}

	/*
	 **************************************************************************************
	 * Method Name		: safeClick
	 * Description		: this method will click an UI element 
	 * Input parameter	: web element
	 * Author			: UWeb Automation
	 ***************************************************************************************
	 */

	public void safeClick(WebElement objClick, String webElementName){
		int attempts = 0;

		boolean isObDisp = false;
		while(attempts < 5){
			try{
				objClick.isDisplayed();
				isObDisp = true;
				if (isObDisp==true){
					setHighlight(objClick);
					objClick.click();
					break;
				}
				attempts++;
			}		
			catch(NoSuchElementException ex){
			}
			catch (Exception e) {
			}
		}
	}

	/*
	 **************************************************************************************
	 * Method Name		: safeDoubleClick
	 * Description		: this method will perform double click action upon an UI element 
	 * Input parameter	: web element, webElementName
	 * Author			: UWeb Automation
	 ***************************************************************************************
	 */

	public void safeDoubleClick(WebElement objClick, String webElementName){
		int attempts = 0;

		boolean isObDisp = false;
		while(attempts < 5){
			try{
				objClick.isDisplayed();
				isObDisp = true;
				if (isObDisp==true){
					setHighlight(objClick);
					Actions actions = new Actions(driver);
					actions.moveToElement(objClick).doubleClick().build().perform();
					break;
				}
				attempts++;
			}		
			catch(NoSuchElementException ex){
			}
			catch (Exception e) {
			}
		}
	}

	/*
	 **************************************************************************************
	 * Method Name		: safeClickLink
	 * Description		: this method will click a link of an UI element 
	 * Input parameter	: elementLink
	 * Author			: UWeb Automation
	 ***************************************************************************************
	 */

	public void safeClickLink(String elementLink){
		try{
			WebElement objClick = driver.findElement(By.linkText(elementLink));
			isObjectDisplayed(objClick, elementLink, "Yes");
			WaitObjEnabled(objClick, elementLink, "Yes");
			setHighlight(objClick);
			objClick.click();
		}
		catch(NoSuchElementException ex){
		}
		catch (Exception e) {
		}
	}


	/*
	 ***************************************************************************************
	 * Method Name		: takeSnapshot
	 * Description		: this method will capture screenshot of a whole computer screen
	 * Input parameter	: driver, file suffix
	 * Output parameter	: screenshot will be placed in output folder
	 * Author			: IAM Automation
	 ***************************************************************************************
	 */

	public static void takeSnapshot(WebDriver driver, String fileSuffix) {
		String currdatentimestamp = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("HH_MM_SS");
			Calendar cal = Calendar.getInstance();
			currdatentimestamp = dateFormat.format(cal.getTime());

			TakesScreenshot scrshot = ((TakesScreenshot)driver);
			String filepath = ".//src//test//resources//snapshots//"+fileSuffix;
			File srcFile = scrshot.getScreenshotAs(OutputType.FILE);
			String filename = filepath+"_"+currdatentimestamp+".png";
			logger.info("Screesnhot destination: "+filename);
			File destFile = new File(filename);
			FileUtils.copyFile(srcFile, destFile);
			logger.info("Captured screen shot ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 **************************************************************************************
	 * Method Name		: checkBrowserState
	 * Description		: this method will select a text value from a list box 
	 * Input parameter	: web element, Select value
	 * Output parameter : readystate
	 * Author			: IAM Automation
	 ***************************************************************************************
	 */

	public String checkBrowserState(){
		String readyState = null;
		try{
			readyState = String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"));
			return readyState;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return readyState;
	}

	/*
	 **************************************************************************************
	 * Method Name		: getData
	 * Description		: this method will select a value from an excel file based on column name and test case ID 
	 * Input parameter	: sheet name, column name, test case ID
	 * Output parameter : cell value
	 * Author			: IAM Automation
	 ***************************************************************************************
	 */

	public static String getData(String sheetName, String columnName, String testCaseID) {
		String data = null;
		try {
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection("src//test//resources//TestData//Testdata.xls");
			String strQuery="Select "+columnName+" from "+sheetName+" where TC_ID='"+testCaseID+"'";
			System.out.println("Query for fetching records: "+strQuery);
			Recordset recordset = connection.executeQuery(strQuery);
			recordset.moveFirst();
			data = recordset.getField(columnName);
			System.out.println("Test data: " +columnName+": "+data);
		} catch(FilloException f) {
			System.out.println("Fillo exception occurred!");
			f.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/*
	 **************************************************************************************
	 * Method Name		: setData
	 * Description		: this method will update cell in excel using column name, sheet name, and test case ID 
	 * Input parameter	: sheet name, column name, test case ID, update text
	 * Author			: IAM Automation
	 ***************************************************************************************
	 */

	public static void setData(String sheetName, String columnName, String testCaseID, String updateText) {
		try {
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection("src//test//resources//TestData//Testdata.xls");
			String strQuery="Update "+sheetName+" Set "+columnName+"='"+updateText+"' where TC_ID='"+testCaseID+"'";
			logger.info("Query for updating records: "+strQuery);
			connection.executeUpdate(strQuery);
			connection.close();
			logger.info("Column: "+columnName+ " updated with data: "+updateText);
		} catch(FilloException f) {
			logger.info("Fillo exception occurred!");
			f.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/*
	 **************************************************************************************
	 * Method Name             : decodepass
	 * Description             : this method decode the password
	 * Input parameter   	   : Encoded password 
	 ***************************************************************************************
	 */	
	public static String decodepass(String strToDecrypt){
		Base64.Decoder decoder = Base64.getUrlDecoder();  
		String dStr = new String(decoder.decode(strToDecrypt));  
		return dStr;
	}

	/*
	 **************************************************************************************
	 * Method Name             : Encodepass
	 * Description             : this method Encode the password
	 * Input parameter   	   : Encoded password 
	 ***************************************************************************************
	 */

	public static String EncodePass(String strToEncrypt){
		// Getting encoder  
		Base64.Encoder encoder = Base64.getUrlEncoder();  
		// Encoding URL  
		String eStr = encoder.encodeToString(strToEncrypt.getBytes());  
		System.out.println("Encoded Pass: "+eStr); 
		return eStr;
	}

	/*
	 **************************************************************************************
	 * Method Name		: safeSelectFromListbox
	 * Description		: this method will select a text value from a list box 
	 * Input parameter	: web element, Select value
	 * Author			: UWeb Automation
	 ***************************************************************************************
	 */

	public void safeSelectFromListbox(WebElement iSelect, String selectValue){
		try{
			Select select = new Select(iSelect);
			List<WebElement> allOptions  = select.getOptions();
			for(int i=0; i < allOptions.size(); i++){
				System.out.println(allOptions.get(i).getText());
				if (allOptions.get(i).getText().contains(selectValue)){
					select.selectByIndex(i);
					System.out.println("Select value - '" + allOptions.get(i).getText() + "' from dropdownlist");
					break;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	/*
	 **************************************************************************************
	 * Method Name		: RobotPerformMove 
	 * Description		: this method will move focus to the x,y co-ordinates using robot framework 
	 * Input parameter	: x,y co=ordinates
	 * Author			: IAM Automation
	 ***************************************************************************************
	 */

	public void RobotPerformMove(int offsetX, int offsetY) throws AWTException{
		try{
			Robot robot = new Robot();
			robot.mouseMove(offsetX, offsetY);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/*
	 **************************************************************************************
	 * Method Name		: ListWebelementReturn 
	 * Description		: this method will return the list of webelements  
	 * Input parameter	: locator list
	 * Output parameter : list of webelements
	 * Author			: IAM Automation
	 ***************************************************************************************
	 */

	public static List<WebElement> ListWebelementReturn(String value) {
		List<WebElement> Listvalue=driver.findElements(By.xpath(value));
		return Listvalue;
	}

	/*
	 **************************************************************************************
	 * Method Name		: safeContextClick 
	 * Description		: this method will perform right click of mouse  
	 * Input parameter	: locator
	 * Author			: IAM Automation
	 ***************************************************************************************
	 */

	public static void safeContextClick(WebElement element, String webelementName) {
		try {
			setHighlight(element);
			Actions actions = new Actions(driver);
			actions.contextClick(element).build().perform();
			logger.info("Performed right click on element: "+webelementName);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 **************************************************************************************
	 * Method Name		: safeSelectCheckBox 
	 * Description		: this method will select a checkbox based on its selection status  
	 * Input parameter	: WebElement, webelementName
	 * Author			: IAM Automation
	 ***************************************************************************************
	 */

	public static void safeSelectCheckBox(WebElement element, String webelementName) {
		try {
			WaitObjEnabled(element, webelementName, "Yes");
			setHighlight(element);
			if(element.isSelected()) {
				logger.info("Checkbox: " + webelementName + "is already selected");
			}
			else {
				element.click();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 **************************************************************************************
	 * Method Name		: safeClear
	 * Description		: this method will clear an UI element 
	 * Input parameter	: web element
	 * Author			: UWeb Automation
	 ***************************************************************************************
	 */
	public void safeClear(WebElement objToEnterValue, String webElementName){
		try
		{
			setHighlight(objToEnterValue);
			objToEnterValue.clear();
		}
		catch(NoSuchElementException ex){

		}
	}

	/*
	 **************************************************************************************
	 * Method Name		: safeGetText
	 * Description		: this method will extract text from an UI element 
	 * Input parameter	: web element
	 * Output parameter : String text
	 * Author			: UWeb Automation
	 ***************************************************************************************
	 */

	public String safeGetText(WebElement objToGetText){
		String expText = null;
		try{
			objToGetText.isDisplayed();
			objToGetText.isEnabled();
			expText = objToGetText.getText();
			setHighlight(objToGetText);
		}
		catch(NoSuchElementException noEx){
		}
		catch (Exception ex) {
		}
		return expText;
	}

	/*
	 **************************************************************************************
	 * Method Name		: safeGetAttribute
	 * Description		: this method will return the attribute of an UI element 
	 * Input parameter	: web element, attribute type
	 * Output parameter : String text
	 * Author			: UWeb Automation
	 ***************************************************************************************
	 */

	public String GetAttributeValue(WebElement webElementObj, String attribute){
		String expAttributeVal = null;
		try{
			expAttributeVal = webElementObj.getAttribute(attribute);
		}
		catch(NoSuchElementException noEx){
		}
		catch(Exception ex){
		}
		return expAttributeVal;
	}


	/*
	 **************************************************************************************
	 * Method Name             : robotPressKey
	 * Description             : this method trigger key press and release of any key n number of times
	 * Input parameter   	   : count
	 * Author				   : UWeb Automation
	 ***************************************************************************************
	 */
	public void robotPressKey(int key, int count) {
		try {
			for(int i=1; i<=count; i++) {
				Robot robot = new Robot();
				robot.keyPress(key);
				robot.keyRelease(key);
				Thread.sleep(500);
			}
		} catch (AWTException aex){
			aex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 **************************************************************************************
	 * Method Name             : robotPressKeyCombo
	 * Description             : this method trigger key press and release of two keys combination n number of times
	 * Input parameter   	   : count
	 * Author				   : UWeb Automation
	 ***************************************************************************************
	 */
	public void robotPressKeyCombo(int key1,int key2, int count) {
		try {
			for(int i=1; i<=count; i++) {
				Robot robot = new Robot();
				robot.keyPress(key1);
				robot.keyPress(key2);
				robot.keyRelease(key2);
				robot.keyRelease(key1);
				Thread.sleep(500);
			}
		} catch (AWTException aex){
			aex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 **************************************************************************************
	 * Method Name             : robotMouseClick
	 * Description             : this method trigger key press and release of two keys combination n number of times
	 * Input parameter   	   : count
	 * Author				   : UWeb Automation
	 ***************************************************************************************
	 */
	public void robotMouseClick() {
		try {
			Robot robot = new Robot();
			int mask = InputEvent.BUTTON1_MASK;
			robot.mousePress(mask);
			robot.mouseRelease(mask);
			Thread.sleep(500);
		} catch (AWTException aex){
			aex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 **************************************************************************************
	 * Method Name             : switchFrame
	 * Description             : this method switch to iframe based on the user input
	 * Input parameter   	   : string
	 * Author				   : URelease Automation
	 ***************************************************************************************
	 */	
	public void switchFrame(String frameID){
		try {
			driver.switchTo().defaultContent();
			driver.switchTo().frame(frameID);
		}
		catch(NoSuchFrameException nf) {
			System.out.println("Frame not found: "+frameID);
		}
		catch(Exception ex){
		}
	}

	/*
	 **************************************************************************************
	 * Method Name             : getXYCoordinates
	 * Description             : this method return the x,y co-ordinates of the currentcursor position
	 * Input parameter   	   : 
	 * Output parameter   	   : x and y co-ordinates
	 * Author				   : UWeb Automation
	 ***************************************************************************************
	 */

	public String getXYCoordinates() {
		String coordinates=null;
		try {
			int mouseX = MouseInfo.getPointerInfo().getLocation().x;
			int mouseY = MouseInfo.getPointerInfo().getLocation().y;
			System.out.println("X:" + mouseX);
			System.out.println("Y:" + mouseY);
			coordinates = mouseX+"~"+mouseY;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return coordinates;
	}

	/*
	 **************************************************************************************
	 * Method Name             : getFramesCount
	 * Description             : this method return the total count of iframes in the current window
	 * Input parameter   	   :
	 * Output parameter   	   : integer  
	 * Author				   : URelease Automation
	 ***************************************************************************************
	 */	
	public int getFramesCount(){
		int frameCount = 0;
		try {
			driver.switchTo().defaultContent();
			frameCount = driver.findElements(By.tagName("iframe")).size();
		}
		catch(Exception ex){

		}
		return frameCount;

	}

	/*
	 **************************************************************************************
	 * Method Name             : sikuliClick
	 * Description             : click on UI image provided with filepath and filename
	 * Input parameter   	   : filePath, fileName
	 * Output parameter   	   :   
	 * Author				   : URelease Automation
	 ***************************************************************************************
	 */
	public void sikuliClick(String filePath, String fileName) {
		try {
			Screen screen = new Screen();
			String sysDir = System.getProperty("user.dir");
			String imgFilePath = sysDir+filePath+fileName;
			Pattern logonBtn = new Pattern(imgFilePath);
			screen.click(logonBtn);
			System.out.println("Image file clicked successfully: " +fileName);
		} catch (SikuliException se) {
			se.printStackTrace();
			System.out.println(se.getMessage());
		}
	}

	/*
	 **************************************************************************************
	 * Method Name             : sikuliType
	 * Description             : type on UI image provided with filepath, filename and input text
	 * Input parameter   	   : filePath, fileName
	 * Output parameter   	   :   
	 * Author				   : URelease Automation
	 ***************************************************************************************
	 */
	public void sikuliType(String filePath, String fileName, String inputText) throws SikuliException {
		Screen screen = new Screen();
		String sysDir = System.getProperty("user.dir");
		String imgFilePath = sysDir+filePath+fileName;
		Pattern field = new Pattern(imgFilePath);
		screen.click(field);
		screen.type(inputText);
		System.out.println("Typed successfully on the field: " +fileName);
	}








}
