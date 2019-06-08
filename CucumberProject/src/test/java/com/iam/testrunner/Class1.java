package com.iam.testrunner;

import com.iam.stepdefinitions.BaseClass;

public class Class1 extends BaseClass{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String webvalue ="yes";
		
		String excelvalue = BaseClass.getData("testData", "FirstName", "TC_1");
		System.out.println(excelvalue);
		if(webvalue.equalsIgnoreCase(excelvalue)) {
			
			System.out.println("pass");
		}
		else {
		System.out.println("faile");

	
		}

	}}
