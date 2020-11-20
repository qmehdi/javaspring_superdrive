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

	@Test
	public void testUserSignupAndLogin() throws InterruptedException{
		String _firstName = "John";
		String _lastName = "Doe";
		String _username = "jdoe";
		String _password = "test123!";

		driver.get("http://localhost:" + this.port + "/signup");

		// ########### Sign Up ############
		SignUpPage signUpPage = new SignUpPage(driver);

		signUpPage.performSignUp(_firstName, _lastName, _username, _password);

		Thread.sleep(1000);

		// ########### Login ##############
		LoginPage loginPage = new LoginPage(driver);
		loginPage.performLogin(_username, _password);

		Thread.sleep(1000);

		// ########### ASSERT ##############
		HomePage homePage = new HomePage(driver);
		String home_page_title = homePage.getPageTitle();

		assertEquals("homepageTitle", home_page_title);
	}
}
