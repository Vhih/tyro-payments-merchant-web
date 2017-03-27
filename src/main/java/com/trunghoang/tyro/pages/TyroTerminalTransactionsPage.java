package com.trunghoang.tyro.pages;

import com.trunghoang.tyro.utils.HttpDownloadUtility;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.List;
import java.util.Set;

public class TyroTerminalTransactionsPage {

    @FindBy(partialLinkText = "Back to summary report")
    private WebElement backToSummaryReportLink;

    private WebDriver driver;

    public TyroTerminalTransactionsPage(WebDriver driver) {
        this.driver = driver;
    }

    public TyroReconciliationReportPage navigateBackToSummaryReport() {
        backToSummaryReportLink.click();
        return PageFactory.initElements(driver, TyroReconciliationReportPage.class);
    }

    public String getReportAsCsv() throws Exception {
        WebElement exportlinks = driver.findElement(By.className("exportBanner"));

        StringBuilder cookie = new StringBuilder();
        Set<Cookie> cookies = driver.manage().getCookies();
        for (Cookie aCookie : cookies) {
            cookie.append(aCookie).append(";");
        }

        String href = null;
        List<WebElement> links = exportlinks.findElements(By.tagName("a"));
        for (WebElement link : links) {
            if ("CSV".equals(link.getText())) {
                href = link.getAttribute("href");
                break;
            }
        }
        if (href == null) {
            throw new Exception("CSV link not found");
        }

        String dir = System.getProperty("java.io.tmpdir");
        File file = new HttpDownloadUtility().downloadFile(href, dir, cookie.toString());
        file.deleteOnExit();

        String s = FileUtils.readFileToString(file);
        if (!file.delete()) {
            System.out.println("Couldn't delete file: " + file.toString());
        }

        return s;
    }

}
