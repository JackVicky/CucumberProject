package com.iam.stepdefinitions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.iam.objects.Commonobjects;

import cucumber.api.java.en.Given;

public class Stepdefinitions extends BaseClass {

	@Given("^User launches the browser$")
	public void user_launches_the_browser()  {
		try {
			launchBrowser();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Given("^Navigate to application url$")
	public void navigate_to_application_url() {
		try {
			String url = getData("testData","AppURL","TC_1");
			driver.get(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Given("^user login to IAM application$")
	public void user_login_to_IAM_application() {
		try {
			safeClick(Commonobjects.registrationBtn, "registrationBtn");
			takeSnapshot(driver, "homepage_verified");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Given("^verify whether the home button is displayed$")
	public void verify_whether_the_home_button_is_displayed() {
		try {
			String FirstName = getData("testData","FirstName","TC_1");
			String LastName = getData("testData","LastName","TC_1");
			String Country = getData("testData","Country","TC_1");
			String Month = getData("testData","Month","TC_1");
			String Day = getData("testData","Day","TC_1");
			String Year = getData("testData","annum","TC_1");
			String PhoneNumber = getData("testData","PhoneNumber","TC_1");
			String Yourself = getData("testData","Yourself","TC_1");
			String Password = getData("testData","Password","TC_1");
			String ConfirmPassword = getData("testData","ConfirmPassword","TC_1");
			String SuccessMsg = getData("testData","SuccessMsg","TC_1");

			WaitObjEnabled(Commonobjects.firstNameField, "firstNameField", "Yes");
			safeType(Commonobjects.firstNameField, FirstName, "firstNameField");
			WaitObjEnabled(Commonobjects.lastNameField, "lastNameField", "Yes");
			safeType(Commonobjects.lastNameField, LastName, "lastNameField");
			safeClick(Commonobjects.marriedRadioBtn, "marriedRadioBtn");
			safeSelectCheckBox(Commonobjects.danceCheckBox, "danceCheckBox");
			safeSelectFromListbox(Commonobjects.countryDropDown, Country);
			safeSelectFromListbox(Commonobjects.monthDropDown, Month);
			safeSelectFromListbox(Commonobjects.dayDropDown, Day);
			safeSelectFromListbox(Commonobjects.yearDropDown, Year);
			safeType(Commonobjects.phoneNumberfield, PhoneNumber, "phoneNumberfield");
			DateFormat dateFormat = new SimpleDateFormat("HH_MM_SS");
			Calendar cal = Calendar.getInstance();
			String unamet = dateFormat.format(cal.getTime());
			String unametext = "Autbbkuname"+unamet;
			safeType(Commonobjects.userNameField, unametext, "userNameField");
			String emailtext = "Autbbkemail"+unamet+"@gmail.com";
			safeType(Commonobjects.emailField, emailtext, "emailField");
			safeType(Commonobjects.aboutYourselfField, Yourself, "aboutYourselfField");
			safeType(Commonobjects.passwordField, Password, "passwordField");
			safeType(Commonobjects.confirmPasswordField, ConfirmPassword, "confirmpasswordField");
			safeClick(Commonobjects.submitBtn, "Submit button");
			takeSnapshot(driver, "Registration page values entered");

			// success message validation of the displyed label message
			isObjectDisplayed(Commonobjects.labelMsgField, "labelMsgField", "Yes");
			String labelMsg = safeGetText(Commonobjects.labelMsgField);
			logger.info("Response message for user registration: "+labelMsg);
			if(labelMsg.contains(SuccessMsg)) {
				logger.info("Registration of user passed!");
			}
			else {
				logger.info("Registration of user failed!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Given("^logout of the application$")
	public void logout_of_the_application() {
		try {
			logger.info("Entering exit module");
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Given("^encode and decode$")
	public void encode_and_decode() {
		try {
			setData("testData", "LastName", "TC_2", "test data bbk");
			safeClick(Commonobjects.registrationBtn, "registrationBtn");
			safeContextClick(Commonobjects.firstNameField, "firstNameField");
			safeClick(Commonobjects.firstNameField, "firstNameField");
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}









}
