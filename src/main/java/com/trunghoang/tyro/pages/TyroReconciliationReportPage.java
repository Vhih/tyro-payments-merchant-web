package com.trunghoang.tyro.pages;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class TyroReconciliationReportPage {

    @FindBy(id = "startDate")
    private WebElement startDateField;

    @FindBy(id = "endDate")
    private WebElement endDateField;

    @FindBy(id = "submit")
    private WebElement submitButton;

    @FindBy(id = "transactionReportResults")
    private WebElement reportTable;

    @FindBy(id = "logoutFormSubmit")
    private WebElement logoutLink;

    private WebDriver driver;

    public TyroReconciliationReportPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickCreateReport(String date) {
        startDateField.sendKeys(date);
        startDateField.sendKeys(Keys.ESCAPE);
        endDateField.sendKeys(date);
        endDateField.sendKeys(Keys.ESCAPE);
        submitButton.click();

        new WebDriverWait(driver, 3);

        new WebDriverWait(driver, 15).until((Function<WebDriver, Boolean>) input -> {
            assert input != null;
            WebElement submit1 = input.findElement(By.id("submit"));
            String disabled = submit1.getAttribute("disabled");
            return disabled == null || disabled.equals("false");
        });
    }

    public TyroLoginPage navigateToLogout() {
        logoutLink.click();
        return PageFactory.initElements(driver, TyroLoginPage.class);
    }

    public String getTotalSale() {
        WebElement tableFoot = reportTable.findElement(By.tagName("tfoot"));
        WebElement total = tableFoot.findElement(By.xpath("//td[2]"));
        return total.getText();
    }

    public String[] getTerminalNames() {
        List<String> terminalNames = new ArrayList<>();
        List<WebElement> centerTableData = reportTable.findElements(By.className("centerTableData"));
        if (centerTableData != null && centerTableData.size() > 0) {
            for (WebElement webElement : centerTableData) {
                terminalNames.add(webElement.getText());
            }
        }
        return terminalNames.toArray(new String[terminalNames.size()]);
    }

    public TyroTerminalTransactionsPage getReportAsCsv(String terminalName) throws Exception {
        List<WebElement> centerTableData = reportTable.findElements(By.className("centerTableData"));
        if (centerTableData != null && centerTableData.size() > 0) {
            for (WebElement webElement : centerTableData) {
                if (terminalName.equals(webElement.getText())) {
                    webElement.findElement(By.tagName("a")).click();
                    new WebDriverWait(driver, 15).until(ExpectedConditions.presenceOfElementLocated(By.id("reportTitle")));

                    return PageFactory.initElements(driver, TyroTerminalTransactionsPage.class);
                }
            }
        }
        return null;
    }

}
