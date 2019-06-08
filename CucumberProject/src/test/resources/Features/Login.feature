Feature: Verify login functionality of IAM application

  Background: 
    Given User launches the browser
    And Navigate to application url

  @loginTest1
  Scenario: Login verification with valid credentials
    Given user login to IAM application
    And verify whether the home button is displayed
    Then logout of the application

  @loginTest2
  Scenario: test steps
    Given encode and decode
