package com.trunghoang.tyro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TyroHomePage {

    @FindBy(id = "reconciliation-report")
    private WebElement menuReconciliationLink;

    private WebDriver driver;

    public TyroHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public TyroReconciliationReportPage navigateToReconciliationReport() {
        menuReconciliationLink.click();
        new WebDriverWait(driver, 15).until(ExpectedConditions.presenceOfElementLocated(By.id("transactionsReportForm")));
        return PageFactory.initElements(driver, TyroReconciliationReportPage.class);
    }

}
