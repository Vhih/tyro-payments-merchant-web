package pages;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TyroReconciliationReportPage {

    @FindBy(id = "startDate")
    private WebElement startDateField;

    @FindBy(id = "endDate")
    private WebElement endDateField;

    @FindBy(id = "submit")
    private WebElement submitButton;

    @FindBy(id = "transactionReportResults")
    private WebElement reportTable;

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

    public String getTotalSale() {
        WebElement tableFoot = reportTable.findElement(By.tagName("tfoot"));
        WebElement total = tableFoot.findElement(By.xpath("//td[2]"));
        return total.getText();
    }

}
