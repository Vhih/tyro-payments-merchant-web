package com.trunghoang.tyro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TyroLoginPage {

    @FindBy(id = "j_username")
    private WebElement usernameField;

    @FindBy(id = "j_password")
    private WebElement passwordField;

    @FindBy(id = "submit")
    private WebElement loginButton;

    private WebDriver driver;

    public static TyroLoginPage navigateTo(WebDriver driver) {
        driver.get("https://merchant.tyro.com/extranet/login.htm");
        return PageFactory.initElements(driver, TyroLoginPage.class);
    }

    public TyroLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public TyroHomePage login(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();

        new WebDriverWait(driver, 15).until(ExpectedConditions.presenceOfElementLocated(By.id("navigation-container")));

        return PageFactory.initElements(driver, TyroHomePage.class);
    }

}
