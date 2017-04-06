import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.UserAgent;
import com.trunghoang.tyro.pages.TyroLoginPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class SmokeTest {

    private JBrowserDriver driver;

    @Before
    public void setup() throws IOException {
        driver = new JBrowserDriver(Settings.builder()
                .headless(false)
                .userAgent(UserAgent.CHROME)
                .build());
    }

    @After
    public void closeDriver() {
        driver.quit();
    }

    @Test
    public void test() throws Exception {

        TyroLoginPage tyroLoginPage = TyroLoginPage.navigateTo(driver);

    }
}
