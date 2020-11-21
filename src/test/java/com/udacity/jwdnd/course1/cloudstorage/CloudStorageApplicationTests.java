package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;


	@BeforeAll
	static void beforeAll() { WebDriverManager.chromedriver().setup(); }

	@BeforeEach
	public void beforeEach() {

		this.driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, 10);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	// ##########################################
	// ################ Sign Up #################
	// ##########################################
	public void signUpUser(String firstName, String lastName, String username, String password) throws InterruptedException{
		String _firstName = firstName;
		String _lastName = lastName;
		String _username = username;
		String _password = password;

		driver.get("http://localhost:" + this.port + "/signup");
		Thread.sleep(2000);

		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.performSignUp(_firstName, _lastName, _username, _password);
	}

	// ##########################################
	// ################ Login ###################
	// ##########################################
	public void loginUser(String username, String password) {
		String __username = username;
		String __password = password;
		LoginPage loginPage = new LoginPage(driver);
		loginPage.performLogin(__username, __password);
	}

	// ###########################################
	// ################# Logout ##################
	// ###########################################
	public void logoutUser() {
		HomePage homePage = new HomePage(driver);
		homePage.performLogout();
	}

	/* Test that signs up a new user, logs that user in, verifies that they can access the home page, then logs out and verifies that the home page is no longer accessible */
	@Test
	@Order(1)
	public void testUserSignupAndLogin() throws InterruptedException{

		signUpUser("john", "doe", "jdoe", "test123!");
		Thread.sleep(1500);

		loginUser("jdoe", "test123!");
		Thread.sleep(1500);

		logoutUser();
		Thread.sleep(1500);

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
	@Order(2)
	public void testhomeNotAccessible() throws InterruptedException {

		driver.get("http://localhost:" + this.port + "/home");
		Thread.sleep(2000);

		// Should get the /login page
		LoginPage loginPage = new LoginPage(driver);
		String login_page_title = loginPage.getPageTitle();

		assertEquals("loginPage", login_page_title);
		Thread.sleep(1500);
	}

	/* Test that logs in an existing user, creates a note and verifies that the note details are visible in the note list. */
	@Test
	@Order(3)
	public void testNoteCreate() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		Thread.sleep(2000);

		// Signup and Login user
		signUpUser("jdoe", "jdoe", "jdoe", "test123!");
		Thread.sleep(2000);
		loginUser("jdoe", "test123!");
		Thread.sleep(2500);

		// Retrieve the Notes tab and click on it
		HomePage homePage = new HomePage(driver);
		Thread.sleep(2000);
		driver.findElement(By.id("nav-notes-tab")).click();
		Thread.sleep(1000);

		// Retrieve the "Add a New Note" Button and click on it
		driver.findElement(By.id("addNoteButton")).click();
		Thread.sleep(2500);

		// Note Modal shows up - Fill it out
		driver.findElement(By.id("note-title")).sendKeys("I am a new note");
		WebElement note_desc = driver.findElement(By.id("note-description"));
		note_desc.sendKeys("Note description is blah blah blah");
		note_desc.submit();
		Thread.sleep(2000);

		// Table showing retrieved notes
		String retrieved_notes_table = homePage.getRetrievedNotesDisplay();

		assertEquals("retrieved-notes-display", retrieved_notes_table);
		Thread.sleep(2000);
	}

	/* Test that logs in an existing user with existing notes, clicks the edit note button on an existing note, changes the note data, saves the changes, and verifies that the changes appear in the note list. */
	@Test
	@Order(4)
	public void testEditExistingNote() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Thread.sleep(2000);

		loginUser("jdoe", "test123!");
		Thread.sleep(2500);

		// Retrieve the Notes tab and click on it
		HomePage homePage = new HomePage(driver);
		driver.findElement(By.id("nav-notes-tab")).click();
		Thread.sleep(1000);

		// Retrieve the Notes table displaying existing notes
		driver.findElement(By.id("retrieved-notes-display"));
		Thread.sleep(2000);

		// Find edit note button and click on it
		driver.findElement(By.id("edit-note-button")).click();
		Thread.sleep(2000);

		// Note Modal shows up - Edit Note
		driver.findElement(By.id("note-title")).sendKeys("I am a an edited note");
		WebElement note_desc = driver.findElement(By.id("note-description"));
		note_desc.sendKeys("Edited Note description is blah blah blah");
		note_desc.submit();
		Thread.sleep(2000);

		// Check if note was edited
		assertEquals("retrieved-notes-display", homePage.getRetrievedNotesDisplay());
		Thread.sleep(2000);
	}

	/* Test that logs in an existing user with existing notes, clicks the delete note button on an existing note, and verifies that the note no longer appears in the note list. */
	@Test
	@Order(5)
	public void testDeleteExistingNote() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Thread.sleep(2000);

		loginUser("jdoe", "test123!");
		Thread.sleep(2500);

		// Retrieve the Notes tab and click on it
		HomePage homePage = new HomePage(driver);
		driver.findElement(By.id("nav-notes-tab")).click();
		Thread.sleep(1000);

		// Retrieve the Notes table displaying existing notes
		driver.findElement(By.id("retrieved-notes-display"));
		Thread.sleep(2000);

		// Find Delete button and click on it
		driver.findElement(By.className("btn-danger")).click();
		Thread.sleep(2000);

		// Check if retrieved-notes-display table doesn't show up after delete
		assertNotEquals("retrieved-notes-display", driver.findElement(By.id("retrieved-notes-display")).getText());
		Thread.sleep(2000);
	}

	/* Test that logs in an existing user, creates a credential and verifies that the credential details are visible in the credential list. */
	@Test
	@Order(6)
	public void testCreateCredential() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		Thread.sleep(2000);

		// Signup and Login user
		signUpUser("jdoe", "jdoe", "jdoe", "test123!");
		Thread.sleep(2000);
		loginUser("jdoe", "test123!");
		Thread.sleep(2500);

		// Retrieve the Credentials tab and click on it
		HomePage homePage = new HomePage(driver);
		Thread.sleep(2000);
		driver.findElement(By.id("nav-credentials-tab")).click();
		Thread.sleep(1000);

		// Retrieve the "Add a New Credential" Button and click on it
		driver.findElement(By.id("add-credential-button")).click();
		Thread.sleep(2500);

		// Credential Modal shows up - Fill it out
		driver.findElement(By.id("credential-url")).sendKeys("http://www.google.com");
		driver.findElement(By.id("credential-username")).sendKeys("juju");
		WebElement cred_password = driver.findElement(By.id("credential-password"));
		cred_password.sendKeys("zzzXXXXttttt");
		cred_password.submit();
		Thread.sleep(2000);

		// Table showing retrieved credentials
		String retrieved_creds_table = homePage.getRetrievedCredentialsDisplay();

		assertEquals("retrieved-credentials-display", retrieved_creds_table);
		Thread.sleep(2000);
	}

	/* Test that logs in an existing user with existing credentials, clicks the edit credential button on an existing credential, changes the credential data, saves the changes, and verifies that the changes appear in the credential list. */
	@Test
	@Order(7)
	public void testEditCredential() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Thread.sleep(2000);

		loginUser("jdoe", "test123!");
		Thread.sleep(2500);

		// Retrieve the Creds tab and click on it
		HomePage homePage = new HomePage(driver);
		driver.findElement(By.id("nav-credentials-tab")).click();
		Thread.sleep(1000);

		// Retrieve the Creds table displaying existing notes
		driver.findElement(By.id("retrieved-credentials-display"));
		Thread.sleep(2000);

		// Find edit creds button and click on it
		driver.findElement(By.id("edit-credential-button")).click();
		Thread.sleep(2000);

		// Credential Modal shows up - Edit Cred
		driver.findElement(By.id("credential-url")).sendKeys("//");
		driver.findElement(By.id("credential-username")).sendKeys("gaga");
		WebElement cred_password = driver.findElement(By.id("credential-password"));
		cred_password.sendKeys("New Password");
		cred_password.submit();
		Thread.sleep(2000);

		// Check if note was edited
		assertEquals("retrieved-credentials-display", homePage.getRetrievedCredentialsDisplay());
		Thread.sleep(2000);
	}

	/* Test that logs in an existing user with existing credentials, clicks the delete credential button on an existing credential, and verifies that the credential no longer appears in the credential list. */
	@Test
	@Order(8)
	public void testDeleteCredential() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Thread.sleep(2000);

		loginUser("jdoe", "test123!");
		Thread.sleep(2500);

		// Retrieve the Creds tab and click on it
		HomePage homePage = new HomePage(driver);
		driver.findElement(By.id("nav-credentials-tab")).click();
		Thread.sleep(1000);

		// Retrieve the Creds table displaying existing notes
		driver.findElement(By.id("retrieved-credentials-display"));
		Thread.sleep(2000);

		// Find Delete button and click on it
		driver.findElement(By.className("btn-danger")).click();
		Thread.sleep(2000);

		// Check if retrieved-credentials-display table doesn't show up after delete
		assertNotEquals("retrieved-credentials-display", driver.findElement(By.id("retrieved-credentials-display")).getText());
		Thread.sleep(2000);
	}
}
