package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "homepageTitle")
    private WebElement pageTitle;

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement nav_notes_tab;

    @FindBy(id = "addNoteButton")
    private WebElement addNoteButton;

    @FindBy(id = "noteModal")
    private WebElement noteModal;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "retrieved-notes-display")
    private WebElement retrievedNotesDisplay;

    @FindBy(id = "edit-note-button")
    private WebElement editNoteButton;

    @FindBy(className = "btn-danger")
    private WebElement deleteNoteButton;

    // ######## Credentials Tab ##########
    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "add-credential-button")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "retrieved-credentials-display")
    private WebElement retrievedCredentialsDisplay;

    @FindBy(id = "edit-credential-button")
    private WebElement editCredentialButton;


    // Constructor
    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        return this.pageTitle.getAttribute("id");
    }

    public void performLogout() {
        logoutButton.click();
    }

    public String getRetrievedNotesDisplay() {
        return this.retrievedNotesDisplay.getAttribute("id");
    }

    public String getRetrievedCredentialsDisplay() {
        return this.retrievedCredentialsDisplay.getAttribute("id");
    }
}
