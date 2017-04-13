import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class SmokeTest {

    private JBrowserDriver driver;

    @Before
    public void setup() throws IOException {
        // need to look into travis if it has javafx
//        driver = new JBrowserDriver(Settings.builder()
//                .headless(false)
//                .userAgent(UserAgent.CHROME)
//                .logWire(true)
//                .build());
    }

//    @After
//    public void closeDriver() {
//        driver.quit();
//    }

    @Test
    @Ignore // todo
    public void test() throws Exception {

//        TyroLoginPage tyroLoginPage = TyroLoginPage.navigateTo(driver);

    }
}
