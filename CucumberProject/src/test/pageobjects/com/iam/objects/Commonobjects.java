package com.iam.objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.iam.stepdefinitions.BaseClass;

public class Commonobjects extends BaseClass{
	
	public Commonobjects(WebDriver driver){
		//super(driver);
	}

	@FindBy(how=How.LINK_TEXT, using="Registration")
	public static WebElement registrationBtn;

	@FindBy(how=How.XPATH, using="//h3[.='Registration']")
	public static WebElement registrationLabel;

	@FindBy(how=How.NAME, using="first_name")
	public static WebElement firstNameField;

	@FindBy(how=How.NAME, using="last_name")
	public static WebElement lastNameField;

	@FindBy(how=How.XPATH, using="//label[.='Married']/following::input[1]")
	public static WebElement marriedRadioBtn;

	@FindBy(how=How.XPATH, using="//label[.='Dance']/following::input[1]")
	public static WebElement danceCheckBox;

	@FindBy(how=How.XPATH, using="//label[.='Country']/following::select[1]")
	public static WebElement countryDropDown;

	@FindBy(how=How.XPATH, using="//label[.='Date of Birth']/following::select[1]")
	public static WebElement monthDropDown;

	@FindBy(how=How.XPATH, using="//label[.='Date of Birth']/following::select[2]")
	public static WebElement dayDropDown;

	@FindBy(how=How.XPATH, using="//label[.='Date of Birth']/following::select[3]")
	public static WebElement yearDropDown;

	@FindBy(how=How.NAME, using="phone_9")
	public static WebElement phoneNumberfield;

	@FindBy(how=How.NAME, using="username")
	public static WebElement userNameField;

	@FindBy(how=How.NAME, using="e_mail")
	public static WebElement emailField;

	@FindBy(how=How.NAME, using="description")
	public static WebElement aboutYourselfField;

	@FindBy(how=How.NAME, using="password")
	public static WebElement passwordField;

	@FindBy(how=How.ID, using="confirm_password_password_2")
	public static WebElement confirmPasswordField;

	@FindBy(how=How.NAME, using="pie_submit")
	public static WebElement submitBtn;

	@FindBy(how=How.XPATH, using="//h1[.='Registration']/following::p[1]")
	public static WebElement labelMsgField;

}


