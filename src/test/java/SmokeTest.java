import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.UserAgent;
import com.trunghoang.tyro.pages.TyroLoginPage;
import com.trunghoang.tyro.utils.DefaultTrustManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SmokeTest {

    private JBrowserDriver driver;

    @Before
    public void setup() throws IOException {
        driver = new JBrowserDriver(Settings.builder()
                .ssl("trustanything")
                .headless(true)
                .userAgent(UserAgent.CHROME)
                .build());

        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            SSLContext.setDefault(ctx);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
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
