$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("Login.feature");
formatter.feature({
  "line": 1,
  "name": "Verify login functionality of IAM application",
  "description": "",
  "id": "verify-login-functionality-of-iam-application",
  "keyword": "Feature"
});
formatter.background({
  "line": 3,
  "name": "",
  "description": "",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 4,
  "name": "User launches the browser",
  "keyword": "Given "
});
formatter.step({
  "line": 5,
  "name": "Navigate to application url",
  "keyword": "And "
});
formatter.match({
  "location": "Stepdefinitions.user_launches_the_browser()"
});
formatter.result({
  "duration": 7098450100,
  "status": "passed"
});
formatter.match({
  "location": "Stepdefinitions.navigate_to_application_url()"
});
formatter.result({
  "duration": 6028310200,
  "status": "passed"
});
formatter.scenario({
  "line": 8,
  "name": "Login verification with valid credentials",
  "description": "",
  "id": "verify-login-functionality-of-iam-application;login-verification-with-valid-credentials",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 7,
      "name": "@loginTest1"
    }
  ]
});
formatter.step({
  "line": 9,
  "name": "user login to IAM application",
  "keyword": "Given "
});
formatter.step({
  "line": 10,
  "name": "verify whether the home button is displayed",
  "keyword": "And "
});
formatter.step({
  "line": 11,
  "name": "logout of the application",
  "keyword": "Then "
});
formatter.match({
  "location": "Stepdefinitions.user_login_to_IAM_application()"
});
