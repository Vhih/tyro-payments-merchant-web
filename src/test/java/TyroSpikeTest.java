import com.google.common.base.Function;
import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.UserAgent;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class TyroSpikeTest {

    private static final String LOGIN_PAGE = "https://merchant.tyro.com/extranet/login.htm";

    private String username;
    private String password;

    private JBrowserDriver driver;

    @Before
    public void setup() throws IOException {
        driver = new JBrowserDriver(Settings.builder()
                .ssl("trustanything")
                .headless(false)
                .userAgent(UserAgent.CHROME)
                .build());

        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            SSLContext.setDefault(ctx);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        File credentialsFile = new File("credentials.properties");
        Properties properties = new Properties();
        properties.load(new FileInputStream(credentialsFile));
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    @After
    public void closeDriver() {
        driver.quit();
    }

    @Test
    public void test() throws Exception {

        driver.get(LOGIN_PAGE);

        WebElement usernameElement = driver.findElementById("j_username");
        WebElement passwordElement = driver.findElementById("j_password");

        usernameElement.sendKeys(username);
        passwordElement.sendKeys(password);
        driver.findElementById("submit").click();

        new WebDriverWait(driver, 15).until(ExpectedConditions.presenceOfElementLocated(By.id("navigation-container")));

        driver.findElementById("reconciliation-report").click();

        new WebDriverWait(driver, 15).until(ExpectedConditions.presenceOfElementLocated(By.id("transactionsReportForm")));

        WebElement startDate = driver.findElementById("startDate");
        WebElement endDate = driver.findElementById("endDate");
        WebElement submit = driver.findElementById("submit");

        String date = "21/03/2017";
        startDate.sendKeys(date);
        startDate.sendKeys(Keys.ESCAPE);
        endDate.sendKeys(date);
        endDate.sendKeys(Keys.ESCAPE);
        submit.click();

        new WebDriverWait(driver, 3);

        new WebDriverWait(driver, 15).until((Function<WebDriver, Boolean>) input -> {
            assert input != null;
            WebElement submit1 = input.findElement(By.id("submit"));
            String disabled = submit1.getAttribute("disabled");
            return disabled == null || disabled.equals("false");
        });

        WebElement table = driver.findElementById("transactionReportResults");

        WebElement tableFoot = table.findElement(By.tagName("tfoot"));
        WebElement total = tableFoot.findElement(By.xpath("//td[2]"));
        String totalSale = total.getText();

        if ("$0.00".equals(totalSale)) {
            return;
        }

        List<WebElement> centerTableData = table.findElements(By.className("centerTableData"));
        for (WebElement webElement : centerTableData) {
            if ("1 - Tid 1".equals(webElement.getText())) {
                webElement.findElement(By.tagName("a")).click();
                new WebDriverWait(driver, 15).until(ExpectedConditions.presenceOfElementLocated(By.id("reportTitle")));
                break;
            }
        }

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
        System.out.println(s);

    }

}
