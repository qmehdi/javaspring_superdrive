package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	/* Test that signs up a new user, logs that user in, verifies that they can access the home page, then logs out and verifies that the home page is no longer accessible */
	@Test
	public void testUserSignupAndLogin() throws InterruptedException{
		String _firstName = "John";
		String _lastName = "Doe";
		String _username = "jdoe";
		String _password = "test123!";

		driver.get("http://localhost:" + this.port + "/signup");

		// ##########################################
		// ################ Sign Up #################
		// ##########################################
		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.performSignUp(_firstName, _lastName, _username, _password);
		Thread.sleep(1500);

		// ##########################################
		// ################ Login ###################
		// ##########################################
		LoginPage loginPage = new LoginPage(driver);
		loginPage.performLogin(_username, _password);
		Thread.sleep(1500);

		// ###########################################
		// ################# Logout ##################
		// ###########################################
		HomePage homePage = new HomePage(driver);
		homePage.performLogout();

		/* Home page should no longer be visible
		Hitting /home should redirect to /login */
		driver.get("http://localhost:" + this.port + "/home");
		Thread.sleep(1500);

		LoginPage loginPageAfterLogout = new LoginPage(driver);
		String page_title_after_logout = loginPageAfterLogout.getPageTitle();

		// ##########################################
		// ########## ASSERT HOME PAGE ##############
		// ##########################################
		assertEquals("loginPage", page_title_after_logout);
		Thread.sleep(1500);
	}

	/* Test that verifies that the home page is not accessible without logging in. */
	@Test
	public void testhomeNotAccessible() throws InterruptedException {

		driver.get("http://localhost:" + this.port + "/home");

		// Should get the /login page
		LoginPage loginPage = new LoginPage(driver);
		String login_page_title = loginPage.getPageTitle();

		// ##########################################
		// ########## ASSERT LOGIN PAGE ##############
		// ##########################################
		assertEquals("loginPage", login_page_title);
		Thread.sleep(1500);
	}
}
