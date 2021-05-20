package com.herokuapp.theinternet.loginpagetests;

import io.qameta.allure.*;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.herokuapp.theinternet.base.TestUtilities;
import com.herokuapp.theinternet.pages.LoginPage;
import com.herokuapp.theinternet.pages.SecureAreaPage;
import com.herokuapp.theinternet.pages.WelcomePage;

@Feature("Login Tests")
public class PositiveLogInTests extends TestUtilities {

	@Test (priority = 0, description="Invalid Login Scenario with wrong username and password.")
	@Severity(SeverityLevel.BLOCKER)
	@Description("Test Description: Login test with wrong username and wrong password.")
	@Story("Invalid username and password login test")
	public void logInTest() {
		// open main page
		WelcomePage welcomePage = new WelcomePage(driver, log);
		welcomePage.openPage();
		takeScreenshot("WelcomePage opened");

		// Click on Form Authentication link
		LoginPage loginPage = welcomePage.clickFormAuthenticationLink();
		takeScreenshot("LoginPage opened");
		
		// Add new cookie
		Cookie ck = new Cookie("username", "tomsmith", "the-internet.herokuapp.com", "/", null);
		loginPage.setCookie(ck);
		
		// execute log in
		SecureAreaPage secureAreaPage = loginPage.logIn("tomsmith", "SuperSecretPassword!");
		takeScreenshot("SecureAreaPage opened");
		
		// Get cookies
		String username = secureAreaPage.getCookie("username");
		log.info("Username cookie: " + username);
		String session = secureAreaPage.getCookie("rack.session");
		log.info("Session cookie: " + session);
		
		
		// Verifications
		// New page url is expected
		Assert.assertEquals(secureAreaPage.getCurrentUrl(), secureAreaPage.getPageUrl());

		// log out button is visible
		Assert.assertTrue(secureAreaPage.isLogOutButtonVisible(), "LogOut Button is not visible.");

		// Successful log in message
		String expectedSuccessMessage = "You logged into a secure area!";
		String actualSuccessMessage = secureAreaPage.getSuccessMessageText();
		Assert.assertTrue(actualSuccessMessage.contains(expectedSuccessMessage),
				"actualSuccessMessage does not contain expectedSuccessMessage\nexpectedSuccessMessage: "
						+ expectedSuccessMessage + "\nactualSuccessMessage: " + actualSuccessMessage);
	}
}
