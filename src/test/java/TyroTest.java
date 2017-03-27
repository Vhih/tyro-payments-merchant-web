import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.UserAgent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.TyroHomePage;
import pages.TyroLoginPage;
import pages.TyroReconciliationReportPage;
import pages.TyroTerminalTransactionsPage;
import utils.DefaultTrustManager;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Properties;

public class TyroTest {

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

        TyroLoginPage tyroLoginPage = TyroLoginPage.navigateTo(driver);
        TyroHomePage tyroHomePage = tyroLoginPage.login(username, password);
        TyroReconciliationReportPage tyroReconciliationReportPage = tyroHomePage.navigateToReconciliationReport();

        tyroReconciliationReportPage.clickCreateReport("27/03/2017");

        String totalSale = tyroReconciliationReportPage.getTotalSale();
        System.out.println("totalSale = " + totalSale);

        String[] terminalNames = tyroReconciliationReportPage.getTerminalNames();
        for (String terminalName : terminalNames) {
            TyroTerminalTransactionsPage tyroTerminalTransactionsPage = tyroReconciliationReportPage.getReportAsCsv(terminalName);

            String report = tyroTerminalTransactionsPage.getReportAsCsv();
            System.out.println(terminalName);
            System.out.println(report);
        }

        tyroReconciliationReportPage.navigateToLogout();

    }

    @Test
    public void testMultiple() throws Exception {

        TyroLoginPage tyroLoginPage = TyroLoginPage.navigateTo(driver);
        TyroHomePage tyroHomePage = tyroLoginPage.login(username, password);
        TyroReconciliationReportPage tyroReconciliationReportPage = tyroHomePage.navigateToReconciliationReport();

        String[] reportDates = new String[]{"27/03/2017", "26/03/2017", "25/03/2017", "24/03/2017", "23/03/2017"};

        for (String reportDate : reportDates) {
            tyroReconciliationReportPage.clickCreateReport(reportDate);

            String totalSale = tyroReconciliationReportPage.getTotalSale();
            System.out.println(reportDate + " " + totalSale);
        }

        tyroReconciliationReportPage.navigateToLogout();

    }

}
